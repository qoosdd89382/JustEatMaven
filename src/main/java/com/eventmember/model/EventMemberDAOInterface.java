package com.eventmember.model;

import java.sql.Connection;
import java.util.List;

public interface EventMemberDAOInterface {
	public void insert(EventMemberVO eventMemberVO);
	public void update(EventMemberVO eventMemberVO);
	public void delete(Integer eventID , Integer accountID);
	public List<EventMemberVO> getAll();
	public List<EventMemberVO> getAllByMemberID(Integer accountID);
	public List<EventMemberVO> getAllByEventID(Integer eventID);
	public int getAvgScoreByAccountID(Integer accountID);
	public int getTotalEventByAccountID(Integer accountID);
	public int getTotalAttendanceByAccountID(Integer accountID);
	public int getEventStatusByAccountID(Integer accountID);
	public void insertByEventInfo(EventMemberVO eventMemberVO,Connection con);
	
}
