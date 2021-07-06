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
//	private Integer recipeLikeCount;
//	private Integer recipeCollectCount;
	private Integer accountID;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((recipeID == null) ? 0 : recipeID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecipeVO other = (RecipeVO) obj;
		if (recipeID == null) {
			if (other.recipeID != null)
				return false;
		} else if (!recipeID.equals(other.recipeID))
			return false;
		return true;
	}

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
	public synchronized Integer getRecipeViewCount() {
		return recipeViewCount;
	}
	public synchronized void setRecipeViewCount(Integer recipeViewCount) {
		this.recipeViewCount = recipeViewCount;
	}
//	public Integer getRecipeLikeCount() {
//		return recipeLikeCount;
//	}
//	public void setRecipeLikeCount(Integer likeCount) {
//		this.recipeLikeCount = likeCount;
//	}
//	public Integer getRecipeCollectCount() {
//		return recipeCollectCount;
//	}
//	public void setRecipeCollectCount(Integer collectCount) {
//		this.recipeCollectCount = collectCount;
//	}
	public Integer getAccountID() {
		return accountID;
	}
	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}

}
