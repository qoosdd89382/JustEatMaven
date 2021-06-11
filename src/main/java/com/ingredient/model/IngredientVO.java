package com.ingredient.model;

import java.io.Serializable;

public class IngredientVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer ingredientID;
	private String ingredientName;
	private Integer ingredientSearchCount;

	public IngredientVO() {
	}

	public Integer getIngredientID() {
		return ingredientID;
	}

	public void setIngredientID(Integer ingredientID) {
		this.ingredientID = ingredientID;
	}

	public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}

	public Integer getIngredientSearchCount() {
		return ingredientSearchCount;
	}

	public void setIngredientSearchCount(Integer ingredientSearchCount) {
		this.ingredientSearchCount = ingredientSearchCount;
	}

}
