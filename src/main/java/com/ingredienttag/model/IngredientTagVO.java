package com.ingredienttag.model;

import java.io.Serializable;

public class IngredientTagVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer ingredientTagID;
	private String ingredientTagName;
	
	public IngredientTagVO() {
		// TODO Auto-generated constructor stub
	}

	public Integer getIngredientTagID() {
		return ingredientTagID;
	}

	public void setIngredientTagID(Integer ingredientTagID) {
		this.ingredientTagID = ingredientTagID;
	}

	public String getIngredientTagName() {
		return ingredientTagName;
	}

	public void setIngredientTagName(String ingredientTagName) {
		this.ingredientTagName = ingredientTagName;
	}


}
