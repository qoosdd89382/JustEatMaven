package com.recipecomment.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class RecipeCommentVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer recipeCommentID;
	private Integer recipeID;	// FK
	private Integer accountID;	// FK
	private String recipeCommentText;
	private Timestamp recipeCommentTime;


	public Integer getRecipeCommentID() {
		return recipeCommentID;
	}


	public void setRecipeCommentID(Integer recipeCommentID) {
		this.recipeCommentID = recipeCommentID;
	}


	public Integer getRecipeID() {
		return recipeID;
	}


	public void setRecipeID(Integer recipeID) {
		this.recipeID = recipeID;
	}


	public Integer getAccountID() {
		return accountID;
	}


	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}


	public String getRecipeCommentText() {
		return recipeCommentText;
	}


	public void setRecipeCommentText(String recipeCommentText) {
		this.recipeCommentText = recipeCommentText;
	}


	public Timestamp getRecipeCommentTime() {
		return recipeCommentTime;
	}


	public void setRecipeCommentTime(Timestamp recipeCommentTime) {
		this.recipeCommentTime = recipeCommentTime;
	}


	public RecipeCommentVO() {
		// TODO Auto-generated constructor stub
	}

}
