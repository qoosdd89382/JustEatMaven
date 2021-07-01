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
import com.notice.model.NoticeService;
import com.notice.model.NoticeVO;

@ServerEndpoint("/Notify/{accountID}")
public class WebSocketNotice {
	
	Gson gson = new Gson();
	AccountInfoService acountSvc = new AccountInfoService();
	NoticeService noticeSvc = new NoticeService();
	
	private static Map<String, WebSocketNotice> accountConnectionMap = 
			new ConcurrentHashMap<String, WebSocketNotice>();
	private String accountID;
    private Session userSession; 

	@OnOpen
	public void onOpen(@PathParam("accountID") String accountID, Session userSession) throws IOException {
		this.accountID = accountID;
		this.userSession = userSession;
		System.out.println(accountID);
		
		accountConnectionMap.put(accountID, this);
		System.out.println(accountConnectionMap);
		
		List<NoticeVO> historyData = noticeSvc.getAllByAccountID(new Integer(accountID));
		String historyMsg = gson.toJson(historyData);

		if (userSession != null && userSession.isOpen()) {
			userSession.getAsyncRemote().sendText(historyMsg);
			System.out.println("history = " + historyMsg);
			return;
		}
	}
	
	@OnMessage
	public void onMessage(String message) {
		NoticeVO aNotice = gson.fromJson(message, NoticeVO.class);		
		for (WebSocketNotice item : accountConnectionMap.values()) { 
			
			if (item.accountID.equals(aNotice.getAccountID().toString())) {
				item.userSession.getAsyncRemote().sendText(message); 
            } 
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
		System.out.println("closed by notice");
	}
	
    public static synchronized Map<String, WebSocketNotice> getClients() { 
        return accountConnectionMap; 
    } 

}
