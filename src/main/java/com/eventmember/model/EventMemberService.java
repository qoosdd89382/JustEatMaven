package com.eventmember.model;

import java.util.List;

public class EventMemberService {
	private EventMemberJDBCDAO dao;
	
	public EventMemberService() {
		dao = new EventMemberJDBCDAO();
	}
	
	public EventMemberVO addEventMember(int eventID , int accountID,int participationState,int totalScore,int totalJudger) {
		
		EventMemberVO eventMemberVO = new EventMemberVO();
		eventMemberVO.setEventID(eventID);
		eventMemberVO.setAccountID(accountID);
		eventMemberVO.setParticipationState(participationState);
		eventMemberVO.setTotalScore(totalScore);
		eventMemberVO.setTotalJudger(totalJudger);
		dao.insert(eventMemberVO);
		
		return eventMemberVO;
	}
	
	public EventMemberVO updateEventMember(int participationState,int eventID,int accountID) {
		
		EventMemberVO eventMemberVO = new EventMemberVO();
		eventMemberVO.setParticipationState(participationState);
		eventMemberVO.setEventID(eventID);
		eventMemberVO.setAccountID(accountID);
		dao.update(eventMemberVO);
		return eventMemberVO;
	}
	
	public void deleteEventMember(int eventID,int accountID) {
		dao.delete(eventID, accountID);
	}
	
	public List<EventMemberVO> getAll() {
		return dao.getAll();	
	}
}
