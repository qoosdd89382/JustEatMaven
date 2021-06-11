package com.dislikeingredient.model;

import java.io.Serializable;

public class DislikeIngredientVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer accountID;
	private Integer dislikeIngredientID;

	public DislikeIngredientVO() {
		// TODO Auto-generated constructor stub
	}

	public Integer getAccountID() {
		return accountID;
	}

	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}

	public Integer getDislikeIngredientID() {
		return dislikeIngredientID;
	}

	public void setDislikeIngredientID(Integer dislikeIngredientID) {
		this.dislikeIngredientID = dislikeIngredientID;
	}

}
