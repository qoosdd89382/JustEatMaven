package com.viewrecipe.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ViewRecipeVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer viewRecipeID;
	private Integer accountID;
	private Integer recipeID;
	private Timestamp viewTime;
	
	public Integer getViewRecipeID() {
		return viewRecipeID;
	}
	public void setViewRecipeID(Integer viewRecipeID) {
		this.viewRecipeID = viewRecipeID;
	}
	public Integer getAccountID() {
		return accountID;
	}
	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}
	public Integer getRecipeID() {
		return recipeID;
	}
	public void setRecipeID(Integer recipeID) {
		this.recipeID = recipeID;
	}
	public Timestamp getViewTime() {
		return viewTime;
	}
	public void setViewTime(Timestamp viewTime) {
		this.viewTime = viewTime;
	}
	
	
	

}
