package com.thumbsuprecipe.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ThumbsupRecipeVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer accountID;
	private Integer thmupRecipeID;
	private Timestamp thmupTime;
	private Integer tempCount;
	
	public Integer getAccountID() {
		return accountID;
	}

	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}

	public Integer getThmupRecipeID() {
		return thmupRecipeID;
	}

	public void setThmupRecipeID(Integer thmupRecipeID) {
		this.thmupRecipeID = thmupRecipeID;
	}

	public Timestamp getThmupTime() {
		return thmupTime;
	}

	public void setThmupTime(Timestamp thmupTime) {
		this.thmupTime = thmupTime;
	}

	public ThumbsupRecipeVO() {
		// TODO Auto-generated constructor stub
	}

	public Integer getTempCount() {
		return tempCount;
	}

	public void setTempCount(Integer tempCount) {
		this.tempCount = tempCount;
	}

}
