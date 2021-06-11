package com.dish.model;

import java.io.Serializable;

public class DishVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer dishID;
	private String dishName;
	private Integer accountID;
	private Integer eventID;
	
	public Integer getDishID() {
		return dishID;
	}
	
	public void setDishID(Integer dishID) {
		this.dishID = dishID;
	}
	
	public String getDishName() {
		return dishName;
	}
	
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}
	
	public Integer getAccountID() {
		return accountID;
	}
	
	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}
	
	public Integer getEventID() {
		return eventID;
	}
	
	public void setEventID(Integer eventID) {
		this.eventID = eventID;
	}
	
}
