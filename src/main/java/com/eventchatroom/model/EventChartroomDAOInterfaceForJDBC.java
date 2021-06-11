package com.eventchatroom.model;

import java.util.List;

public interface EventChartroomDAOInterfaceForJDBC {
	public void insert(EventChatroomVO eventChatroomVO);
	public void update(EventChatroomVO eventChatroomVO);
//	public void delete();
	public EventChatroomVO findAccountID(Integer accountID);
	public List<EventChatroomVO> getAll();
}
