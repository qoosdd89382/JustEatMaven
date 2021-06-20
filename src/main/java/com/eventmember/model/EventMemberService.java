package com.eventmember.model;

import java.util.List;

public class EventMemberService {
	private EventMemberJDBCDAO dao;
	
	public EventMemberService() {
		dao = new EventMemberJDBCDAO();
	}
	
	public EventMemberVO addEventMember(int eventID , int accountID,int participationState,int totalScore,int totalJudger,int Avgscore,int totalevent,int totalattendance,int eventstatus ) {
		
		EventMemberVO eventMemberVO = new EventMemberVO();
		eventMemberVO.setEventID(eventID);
		eventMemberVO.setAccountID(accountID);
		eventMemberVO.setParticipationState(participationState);
		eventMemberVO.setTotalScore(totalScore);
		eventMemberVO.setTotalJudger(totalJudger);
		eventMemberVO.setAvgScore(Avgscore);
		eventMemberVO.setTotalEvent(totalevent);
		eventMemberVO.setTotalAttendance(totalattendance);
		eventMemberVO.setEventStatus(eventstatus);
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
	
	public List<EventMemberVO> getAllByMemberID(Integer accountID) {
		return dao.getAllByMemberID(accountID);
	}
	
	public List<EventMemberVO> getAllByEventID(Integer eventID) {
		return dao.getAllByEventID(eventID);
	}
	public int getAvgScoreByAccountID(Integer accountID) {
		return dao.getAvgScoreByAccountID(accountID);
	}
	public int getTotalEventByAccountID(Integer accountID) {
		return dao.getTotalEventByAccountID(accountID);
	}
	public int  getTotalAttendanceByAccountID(Integer accountID)  {
		return dao.getTotalAttendanceByAccountID(accountID);
	}
	public int  getEventStatusByAccountID(Integer accountID)  {
		return dao.getEventStatusByAccountID(accountID);
	}


}
