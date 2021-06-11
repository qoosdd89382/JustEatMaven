package com.likeingredient.model;

import java.io.Serializable;

public class LikeIngredientVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer accountID;
	private Integer likeIngredientID;
	
	public LikeIngredientVO() {
		// TODO Auto-generated constructor stub
	}

	public Integer getAccountID() {
		return accountID;
	}

	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}

	public Integer getLikeIngredientID() {
		return likeIngredientID;
	}

	public void setLikeIngredientID(Integer likeIngredientID) {
		this.likeIngredientID = likeIngredientID;
	}

}
