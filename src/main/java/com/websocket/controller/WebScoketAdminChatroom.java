package com.websocket.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session; // 不是 http session，是WebSocket的，使用者連線進來，就可以得到使用者的session
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONException;
import org.json.JSONObject;

import com.adminchatroom.model.AdminChatroomService;
import com.adminchatroom.model.AdminChatroomState;
import com.adminchatroom.model.AdminChatroomVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.websocket.jedis.JedisHandleAdminChatroom;

//@ServerEndpoint("/AdminChat/{adminID}/{accountID}")
@ServerEndpoint("/AdminChat/{accountID}")
public class WebScoketAdminChatroom {
	
	private static Map<String, Session> sessionsMap = 
					new ConcurrentLinkedHashMap.Builder<String, Session>()
				    	.maximumWeightedCapacity(1000)
				    	.build();
	Gson gson = new Gson();

	@OnOpen
	public void onOpen(@PathParam("accountID") String userID, Session userSession) throws IOException {
		/* save the new user in the map */
		sessionsMap.put(userID, userSession);
		/* Sends all the connected users to the new user */

		Set<String> userIDs = new LinkedHashSet<String>();
		for(Map.Entry<String, Session> entry : sessionsMap.entrySet()) {
			userIDs.add(entry.getKey());
		}
		
		AdminChatroomState stateMessage = new AdminChatroomState("open", userID, userIDs);
		String stateMessageJson = gson.toJson(stateMessage);
		
		Collection<Session> sessions = sessionsMap.values();
		for (Session session : sessions) {
			if (session.isOpen()) {
				session.getAsyncRemote().sendText(stateMessageJson);
			}
		}

		String text = String.format(
				"Session ID = %s, connected; userName = %s%nuserIDs: %s", 
				userSession.getId(),
				userID, userIDs);
		System.out.println(text);
	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
		AdminChatroomVO chatMessage = gson.fromJson(message, AdminChatroomVO.class);
		String sender = chatMessage.getSender();
		String receiver = chatMessage.getReceiver();
		String time = chatMessage.getTime();
		
		if ("history".equals(chatMessage.getType())) {
			List<String> historyData = JedisHandleAdminChatroom.getHistoryMsg(sender, receiver);
			String historyMsg = gson.toJson(historyData);
			AdminChatroomVO cmHistory = new AdminChatroomVO("history", sender, receiver, time, historyMsg);
			
			if (userSession != null && userSession.isOpen()) {
				userSession.getAsyncRemote().sendText(gson.toJson(cmHistory));
				System.out.println("history = " + gson.toJson(cmHistory));
				return;
			}
		}
		
		Session receiverSession = sessionsMap.get(receiver);
		
		if ("admin".equals(receiver) && receiverSession == null) {
			JedisHandleAdminChatroom.saveChatMessage(sender, receiver, message);
			// 有待觀察中
			if (userSession != null && userSession.isOpen()) {
				userSession.getAsyncRemote().sendText(message);
			}
		} else if (receiverSession != null && receiverSession.isOpen()) {
			receiverSession.getAsyncRemote().sendText(message);
			userSession.getAsyncRemote().sendText(message);
			JedisHandleAdminChatroom.saveChatMessage(sender, receiver, message);
		}
		System.out.println("Message received: " + message);
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		String userIDClose = null;
		Set<String> userIDs = new LinkedHashSet<String>();
		for(Map.Entry<String, Session> entry : sessionsMap.entrySet()) {
			userIDs.add(entry.getKey());
		}
		
		for (String userID : userIDs) {
			if (sessionsMap.get(userID).equals(userSession)) {
				userIDClose = userID;
				sessionsMap.remove(userID);
				break;
			}
		}

		if (userIDClose != null) {
			AdminChatroomState stateMessage = new AdminChatroomState("close", userIDClose, userIDs);
			String stateMessageJson = gson.toJson(stateMessage);
			Collection<Session> sessions = sessionsMap.values();
			for (Session session : sessions) {
				session.getAsyncRemote().sendText(stateMessageJson);
			}
		}

		String text = String.format("session ID = %s, disconnected; close code = %d%nuserIDs: %s", userSession.getId(),
				reason.getCloseCode().getCode(), userIDs);
		System.out.println(text);
	}
	

	
	// -----------------------------------------------------------------------
	
//	private static Map<String, Set<Session>> rooms = new ConcurrentHashMap<>();
//	Gson gson = new Gson();
//	AdminChatroomService adminChatSvc = new AdminChatroomService(); 
//
//
//	@OnOpen
//	public void onOpen(@PathParam("adminID") String adminID, @PathParam("accountID") String accountID, 
//			Session session) {
//
//		// 將session按照房間名來儲存，將各個房間的使用者隔離
//		if (!rooms.containsKey(adminID + accountID)) {
//			// 建立房間不存在時，建立房間
//			Set<Session> room = new HashSet<>();
//			// 新增使用者
//			room.add(session);
//			rooms.put(adminID + accountID, room);
//		} else {
//			// 房間已存在，直接新增使用者到相應的房間
//			rooms.get(adminID + accountID).add(session);
//		}
//		session.getAsyncRemote().sendText("open");
//		System.out.println("a client has connected!");
//	}
//
//	@OnClose
//	public void onClose(@PathParam("adminID") String adminID, @PathParam("accountID") String accountID, 
//						String message, Session session) {
//        rooms.get(adminID + accountID).remove(session);
//        System.out.println("a client has disconnected!");
//    }
//	
//	@OnMessage
//	public void onMessage(@PathParam("adminID") String adminID, @PathParam("accountID") String accountID, 
//			Boolean sendBy, Timestamp sendTime, String sendText,
//			Session session) {
////		Set<Session> room = rooms.get(adminID + accountID);
//		AdminChatroomVO oneMessage = 
//				adminChatSvc.sendOneMessage(new Integer(adminID), new Integer(accountID),
//						sendBy, sendTime, sendText);
//		
//		List<AdminChatroomVO> adminChatroomVOList = adminChatSvc.getHistoryMessages(new Integer(adminID), new Integer(accountID));
////		List<String> allMessages = new ArrayList<String>();
////		for (AdminChatroomVO adminChatroomVO : adminChatroomVOList) {
////			allMessages.add(adminChatroomVO.getSendText());
////		}
//		String historyMessageObjectList = gson.toJson(adminChatroomVOList);
//		
//		if (session != null && session.isOpen()) {
//			session.getAsyncRemote().sendObject(historyMessageObjectList);
////			for (AdminChatroomVO adminChatroomVO : adminChatroomVOList) {
////				String historyMessageObject = gson.toJson(adminChatroomVO);
////				session.getAsyncRemote().sendObject(historyMessageObject);
////			}
//		}
//		
//		try {
//			broadcast(adminID + accountID, gson.toJson(oneMessage));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
//	
//	public static void broadcast(String roomID, Object oneMessage) throws Exception {
//		for (Session session : rooms.get(roomID)) {
//			session.getAsyncRemote().sendObject(oneMessage);
//		}
//	}
//
//	@OnError
//	public void onError() {
//
//	}

}
