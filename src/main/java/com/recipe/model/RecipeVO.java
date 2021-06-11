package com.recipe.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class RecipeVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer recipeID;
	private String recipeName;
	private String recipeIntroduction;
	private byte[] recipePicTop;
	private Integer recipeServe;
	private Timestamp recipeTime;
	private Integer recipeViewCount;
	private Integer recipeLikeCount;
	private Integer recipeCollectCount;
	private Integer accountID;
	
	public RecipeVO() {
	}
	
	public Integer getRecipeID() {
		return recipeID;
	}
	public void setRecipeID(Integer recipeID) {
		this.recipeID = recipeID;
	}
	public String getRecipeName() {
		return recipeName;
	}
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}
	public String getRecipeIntroduction() {
		return recipeIntroduction;
	}
	public void setRecipeIntroduction(String recipeIntroduction) {
		this.recipeIntroduction = recipeIntroduction;
	}
	public byte[] getRecipePicTop() {
		return recipePicTop;
	}
	public void setRecipePicTop(byte[] recipePicTop) {
		this.recipePicTop = recipePicTop;
	}
	public Integer getRecipeServe() {
		return recipeServe;
	}
	public void setRecipeServe(Integer recipeServe) {
		this.recipeServe = recipeServe;
	}
	public Timestamp getRecipeTime() {
		return recipeTime;
	}
	public void setRecipeTime(Timestamp recipeTime) {
		this.recipeTime = recipeTime;
	}
	public Integer getRecipeViewCount() {
		return recipeViewCount;
	}
	public void setRecipeViewCount(Integer recipeViewCount) {
		this.recipeViewCount = recipeViewCount;
	}
	public Integer getRecipeLikeCount() {
		return recipeLikeCount;
	}
	public void setRecipeLikeCount(Integer likeCount) {
		this.recipeLikeCount = likeCount;
	}
	public Integer getRecipeCollectCount() {
		return recipeCollectCount;
	}
	public void setRecipeCollectCount(Integer collectCount) {
		this.recipeCollectCount = collectCount;
	}
	public Integer getAccountID() {
		return accountID;
	}
	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}

}
