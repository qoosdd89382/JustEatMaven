package com.websocket.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session; // 不是 http session，是WebSocket的，使用者連線進來，就可以得到使用者的session
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.accountinfo.model.AccountInfoService;
import com.announce.model.AnnounceService;
import com.announce.model.AnnounceVO;

@ServerEndpoint("/Announce/{accountID}")
public class WebSocketAnnounce {
	
	Gson gson = new Gson();
	AccountInfoService acountSvc = new AccountInfoService();
	AnnounceService announceSvc = new AnnounceService();
	
	private static Map<String, WebSocketAnnounce> accountConnectionMap = 
			new ConcurrentHashMap<String, WebSocketAnnounce>();
	private String accountID;
    private Session userSession; 

	@OnOpen
	public void onOpen(@PathParam("accountID") String accountID, Session userSession) throws IOException {
		this.accountID = accountID;
		this.userSession = userSession;
		System.out.println(accountID);
		
		accountConnectionMap.put(accountID, this);
		System.out.println(accountConnectionMap);
		
		List<AnnounceVO> historyData = announceSvc.getAll();
//		String historyMsg = gson.toJson(historyData.get(historyData.size()-1));
		String historyMsg = gson.toJson(historyData);

		if (userSession != null && userSession.isOpen()) {
			userSession.getAsyncRemote().sendText(historyMsg);
			System.out.println("history = " + historyMsg);
			return;
		}
	}
	
	@OnMessage
	public void onMessage(String message) {
//		AnnounceVO anAnnounce = gson.fromJson(message, AnnounceVO.class);		
		for (WebSocketAnnounce item : accountConnectionMap.values()) { 
//			if (item.accountID.equals(anAnnounce.getAccountID().toString())) {
				item.userSession.getAsyncRemote().sendText(message); 
//            } 
		}
	}
	
	@OnError
	public void onError(Session userSession, Throwable e) {
		e.printStackTrace();
	}
	
	@OnClose
	public void onClose() {
		accountConnectionMap.remove(accountID); 
		System.out.println(accountID + " close the connection");
		System.out.println("closed by announce");
	}
	
    public static synchronized Map<String, WebSocketAnnounce> getClients() { 
        return accountConnectionMap; 
    } 

}
