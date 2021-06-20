package com.dish.model;

import java.util.List;

public class DishService {
	private DishJDBCDAO dao;
	
	public DishService() {
		dao = new DishJDBCDAO();
	}
	
	public DishVO addDish(String dishName,int accountID ,int eventID) {
		DishVO dishVO = new DishVO();
		dishVO.setDishName(dishName);
		dishVO.setEventID(eventID);
		dishVO.setAccountID(accountID);
		return dishVO;
	}
	
	public DishVO updateDish(int dishID,String dishName) {
		DishVO dishVO = new DishVO();
		dishVO.setDishID(dishID);
		dishVO.setDishName(dishName);
		dao.update(dishVO);
		return dishVO;
	}
	
	public void deleteDish(int dishID) {
		dao.delete(dishID);
	}
	
	public DishVO getDishID(int dishID) {
		return dao.findByPrimaryKey(dishID);
	}
	
	public List<DishVO> getEventID(int eventID) {
		return dao.findByEventID(eventID);
	}
	
	public List<DishVO> getAccountID(int accountID) {
		return dao.findByAccountID(accountID);
	}
	
	public List<DishVO> getAccountIDAndEventID(int accountID , int eventID) {
		return dao.findByAccountIDAndEventID(accountID, eventID);
	}
	
	public List<DishVO> getAll(){
		return dao.getAll();
	}
}
