package com.eventchatroom.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eventchatroom.jedis.JedisHandleMessage;
import com.eventchatroom.model.EventChatroomMessage;

@ServerEndpoint("/EventChatRoom/{eventID}/{accountID}")
public class WebSocketEventChatRoom {
	private static final Set<Session> connectedSessions = Collections.synchronizedSet(new HashSet<>());

	@OnOpen
	public void onOpen(@PathParam("eventID") String eventID, @PathParam("accountID") String accountID,
			Session userSession) throws IOException {
		connectedSessions.add(userSession);
		String text = String.format("Session ID = %s, connected; eventID = %s ; accountID=%s", userSession.getId(),
				eventID, accountID);
		System.out.println(text);
	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
		JSONObject jsonObject = null;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
		try {
			jsonObject = new JSONObject(message);
//			jsonObject.put("sendTime", dtf.format(LocalDateTime.now()).toString());
			System.out.println(jsonObject);
			String eventID = jsonObject.getString("eventID");
			if ("history".equals(jsonObject.getString("type"))) {
				List<String> historydata = JedisHandleMessage.getHistoryMsg(eventID);
				List<EventChatroomMessage> eventChatroomMessages = new ArrayList<EventChatroomMessage>();
				for (String historyMsg : historydata) {
					JSONObject jsonObj = new JSONObject(historyMsg);
					EventChatroomMessage eventChatroomMessage = new EventChatroomMessage(jsonObj.getString("type"),
							jsonObj.getString("eventID"), jsonObj.getString("senderID"),
							jsonObj.getString("senderName"), jsonObj.getString("message"));
					eventChatroomMessages.add(eventChatroomMessage);
				}
				System.out.println(eventChatroomMessages.size());
				if (userSession != null && userSession.isOpen()) {
					for (EventChatroomMessage eventChatroomMessage : eventChatroomMessages) {
						JSONObject jsonObj = new JSONObject(eventChatroomMessage);
						System.out.println(jsonObj);
						userSession.getBasicRemote().sendText(jsonObj.toString());
					}
				}
			} else if ("chat".equals(jsonObject.getString("type"))) {
				for (Session session : connectedSessions) {
					if (session.isOpen()) {
						session.getBasicRemote().sendText(message);
					}
				}
				JedisHandleMessage.saveChatMessage(eventID, message);
//				System.out.println("Message received: " + jsonObject.toString());
			}

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		// TODO Auto-generated method stub
		connectedSessions.remove(userSession);
		String text = String.format("session ID = %s, disconnected; close code = %d; reason phrase = %s",
				userSession.getId(), reason.getCloseCode().getCode(), reason.getReasonPhrase());
		System.out.println(text);
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		// TODO Auto-generated method stub
		System.out.println("Error: " + e.toString());

	}
}
