package com.eventmember.model;

import java.sql.Connection;
import java.util.List;

public interface EventMemberDAOInterface {
	public void insert(EventMemberVO eventMemberVO);
	public void update(EventMemberVO eventMemberVO);
	public void delete(Integer eventID , Integer accountID);
	public List<EventMemberVO> getAll();
	
	public void insertByEventInfo(EventMemberVO eventMemberVO,Connection con);
}