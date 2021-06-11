package com.recipestep.model;

import java.io.Serializable;

public class RecipeStepVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer recipeStepID;
	private Integer recipeID;	// FK
	private Integer recipeStepOrder;
	private String recipeStepText;
	private byte[] recipeStepPic;
	
	public RecipeStepVO() {
	}

	public Integer getRecipeStepID() {
		return recipeStepID;
	}

	public void setRecipeStepID(Integer recipeStepID) {
		this.recipeStepID = recipeStepID;
	}

	public Integer getRecipeID() {
		return recipeID;
	}

	public void setRecipeID(Integer recipeID) {
		this.recipeID = recipeID;
	}

	public Integer getRecipeStepOrder() {
		return recipeStepOrder;
	}

	public void setRecipeStepOrder(Integer recipeStepOrder) {
		this.recipeStepOrder = recipeStepOrder;
	}

	public String getRecipeStepText() {
		return recipeStepText;
	}

	public void setRecipeStepText(String recipeStepText) {
		this.recipeStepText = recipeStepText;
	}

	public byte[] getRecipeStepPic() {
		return recipeStepPic;
	}

	public void setRecipeStepPic(byte[] recipeStepPic) {
		this.recipeStepPic = recipeStepPic;
	}

}
