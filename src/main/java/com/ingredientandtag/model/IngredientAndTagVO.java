package com.ingredientandtag.model;

import java.io.Serializable;

public class IngredientAndTagVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer ingredientTagID;
	private Integer ingredientID;

	public IngredientAndTagVO() {
		// TODO Auto-generated constructor stub
	}

	public Integer getIngredientTagID() {
		return ingredientTagID;
	}

	public void setIngredientTagID(Integer ingredientTagID) {
		this.ingredientTagID = ingredientTagID;
	}

	public Integer getIngredientID() {
		return ingredientID;
	}

	public void setIngredientID(Integer ingredientID) {
		this.ingredientID = ingredientID;
	}

}
