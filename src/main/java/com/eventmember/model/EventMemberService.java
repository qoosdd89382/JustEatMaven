package com.eventmember.model;

import java.util.List;

public class EventMemberService {
	private EventMemberJDBCDAO dao;
	
	public EventMemberService() {
		dao = new EventMemberJDBCDAO();
	}
	
	public EventMemberVO addEventMember(int eventID , int accountID,int participationState,boolean isHostIdentifier) {
		
		EventMemberVO eventMemberVO = new EventMemberVO();
		eventMemberVO.setEventID(eventID);
		eventMemberVO.setAccountID(accountID);
		eventMemberVO.setParticipationState(participationState);
		eventMemberVO.setHostIdentifier(isHostIdentifier);
		dao.insert(eventMemberVO);
		
		return eventMemberVO;
	}
	
	public EventMemberVO updateEventMember(int participationState,int eventID,int accountID,boolean isHostIdentifier) {
		
		EventMemberVO eventMemberVO = new EventMemberVO();
		eventMemberVO.setParticipationState(participationState);
		eventMemberVO.setEventID(eventID);
		eventMemberVO.setAccountID(accountID);
		eventMemberVO.setHostIdentifier(isHostIdentifier);
		dao.update(eventMemberVO);
		return eventMemberVO;
	}
	
	public void deleteEventMember(int eventID,int accountID) {
		dao.delete(eventID, accountID);
	}
	
	public List<EventMemberVO> getAll() {
		return dao.getAll();	
	}
	
	public List<EventMemberVO> getAllByMemberID(Integer accountID) {
		return dao.getAllByMemberID(accountID);
	}
	
	public List<EventMemberVO> getAllByEventID(Integer eventID) {
		return dao.getAllByEventID(eventID);
	}
}
