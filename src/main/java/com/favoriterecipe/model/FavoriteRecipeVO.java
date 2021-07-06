package com.favoriterecipe.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class FavoriteRecipeVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer accountID;
	private Integer favRecipeID;
	private Timestamp favTime;
	private Integer tempCount;
	// 是否有必要新增 "加入時間"

	public FavoriteRecipeVO() {
		// TODO Auto-generated constructor stub
	}

	public Integer getTempCount() {
		return tempCount;
	}

	public void setTempCount(Integer tempCount) {
		this.tempCount = tempCount;
	}

	public Integer getAccountID() {
		return accountID;
	}

	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}

	public Integer getFavRecipeID() {
		return favRecipeID;
	}

	public void setFavRecipeID(Integer favRecipeID) {
		this.favRecipeID = favRecipeID;
	}

	public Timestamp getFavTime() {
		return favTime;
	}

	public void setFavTime(Timestamp favTime) {
		this.favTime = favTime;
	}


}
