package com.eventmember.model;

import java.sql.Connection;
import java.util.List;

import com.dish.model.DishVO;
import com.dishandingredient.model.DishAndIngredientVO;
import com.eventinfo.model.EventInfoVO;

public interface EventMemberDAOInterface {
	public void insert(EventMemberVO eventMemberVO);
	public void update(EventMemberVO eventMemberVO);
	public void delete(Integer eventID , Integer accountID);
	public List<EventMemberVO> getAll();
	public List<EventMemberVO> getAllByMemberID(Integer accountID);
	public List<EventMemberVO> getAllByEventID(Integer eventID);
	public List<EventMemberVO> getAllByAccount(Integer accountID);
	public int getAvgScoreByAccountID(Integer accountID);
	public int getTotalEventByAccountID(Integer accountID);
	public int getTotalAttendanceByAccountID(Integer accountID);
	public int getEventStatusByAccountID(Integer accountID, Integer eventID);
	public int getOneByEventAndHost(int eventID);
	public void insertByEventInfo(EventMemberVO eventMemberVO,Connection con);
	public void insertWithDishIngredient(EventMemberVO eventMemberVO,List<DishVO> dishList,List<DishAndIngredientVO> dishAndIngredientList);
	public List<EventMemberVO> getAuditPassbyeventID(Integer eventID);
	public EventMemberVO getByEventIDAndMemberID(Integer eventID, Integer accountID);


}
