package com.eventchatroom.model;

import java.sql.Timestamp;
import java.util.List;

public class EventChatroomService {
	private EventChatroomRedisDAO dao;
	
	public EventChatroomService() {
		dao = new EventChatroomRedisDAO();
	}
	
	public EventChatroomVO addChatMessage(int eventID,int accountID,Timestamp messageTime,String chatText) {
		EventChatroomVO eventChatroomVO = new EventChatroomVO();
		eventChatroomVO.setAccountID(accountID);
		eventChatroomVO.setEventID(eventID);
		eventChatroomVO.setMessageTime(messageTime);
		eventChatroomVO.setChatText(chatText);
		dao.insert(eventChatroomVO);
		return eventChatroomVO;
	}
	
	public List<EventChatroomVO> getAllByText(int eventID , int accountID , String chatText){
		return dao.getAllByText(eventID, accountID, chatText);
	}
	
	public List<EventChatroomVO> getAllByMember(int eventID, int accountID){
		return dao.getAllByMember(eventID, accountID);
	}
}
