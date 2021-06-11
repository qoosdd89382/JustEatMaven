package com.eventchatroom.model;

import java.util.List;

public interface EventChatroomDAOInterface {
	public void insert(EventChatroomVO eventChatroomVO);
	public List<EventChatroomVO> getAllByText(int eventID , int accountID , String chatText);
	public List<EventChatroomVO> getAllByMember(int eventID, int accountID);
}
