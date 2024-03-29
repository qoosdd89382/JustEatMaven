package com.eventmember.model;

import java.util.ArrayList;
import java.util.List;

import com.dish.model.DishVO;
import com.dishandingredient.model.DishAndIngredientVO;

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
	
//	public EventMemberVO updateEventMember(int participationState,int eventID,int accountID,boolean isHostIdentifier) {
	public EventMemberVO updateEventMember(int participationState,int eventID,int accountID) {
		
		EventMemberVO eventMemberVO = new EventMemberVO();
		eventMemberVO.setParticipationState(participationState);
		eventMemberVO.setEventID(eventID);
		eventMemberVO.setAccountID(accountID);
//		eventMemberVO.setHostIdentifier(isHostIdentifier);
		dao.update(eventMemberVO);
		return eventMemberVO;
	}
	
	public void deleteEventMember(int eventID,int accountID) {
		dao.delete(eventID, accountID);
	}
	
	public EventMemberVO getByEventAndMemberID(int eventID,int accountID) {
		return dao.getByEventIDAndMemberID(eventID, accountID);
	}
	
	public List<EventMemberVO> getAll() {
		return dao.getAll();	
	}
	
	public List<EventMemberVO> getAllByMemberID(Integer accountID) {
		return dao.getAllByMemberID(accountID);
	}
	
	public List<EventMemberVO> getAllByAccount(Integer accountID) {
		return dao.getAllByMemberID(accountID);
	}
	public List<EventMemberVO> getAllByEventID(Integer eventID) {
		return dao.getAllByEventID(eventID);
	}
	public int getOneByEventAndHost(int eventID) {
		return dao.getOneByEventAndHost(eventID);
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
	public int  getEventStatusByAccountID(Integer accountID, int eventID) {
		return dao.getEventStatusByAccountID(accountID, eventID);
	}
	public List<EventMemberVO> getAuditPassbyeventID(Integer eventID) {
		return dao.getAuditPassbyeventID( eventID);
	}
	
	public int getMemberCount(Integer eventID) {
		return dao.getCountMemberbyEvent(eventID);
	}

	
	public void addEventMemberAndDish(int eventID , int accountID,int participationState,boolean isHostIdentifier ,String[] dishNames,Integer[][] IngIDs) {
		
		EventMemberVO eventMemberVO = new EventMemberVO();
		eventMemberVO.setEventID(eventID);
		eventMemberVO.setAccountID(accountID);
		eventMemberVO.setParticipationState(participationState);
		eventMemberVO.setHostIdentifier(isHostIdentifier);

		
		List<DishVO> dishList = new ArrayList<DishVO>();
		
		for(int i =0;i<dishNames.length;i++) {
			DishVO dishVO = new DishVO();
			dishVO.setDishName(dishNames[i]);
			dishVO.setAccountID(accountID); // 再討論
			dishList.add(dishVO);
		}
		
		List<DishAndIngredientVO> dishAndIngredientList = new ArrayList<DishAndIngredientVO>();
		for(int i =0;i<dishNames.length;i++) {
			for(int j =0;j<IngIDs[i].length;j++) {
				DishAndIngredientVO dishAndIngredientVO = new DishAndIngredientVO();
				dishAndIngredientVO.setIngredientID(IngIDs[i][j]);
				dishAndIngredientList.add(dishAndIngredientVO);
			}
		}
		dao.insertWithDishIngredient(eventMemberVO, dishList, dishAndIngredientList);
	}
}
