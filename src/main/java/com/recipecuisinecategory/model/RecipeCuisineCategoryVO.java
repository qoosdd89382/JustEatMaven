package com.recipecuisinecategory.model;

import java.io.Serializable;

public class RecipeCuisineCategoryVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer recipeID;
	private Integer cuisineCategoryID;
	
	
	public Integer getRecipeID() {
		return recipeID;
	}


	public void setRecipeID(Integer recipeID) {
		this.recipeID = recipeID;
	}


	public Integer getCuisineCategoryID() {
		return cuisineCategoryID;
	}


	public void setCuisineCategoryID(Integer cuisineCategoryID) {
		this.cuisineCategoryID = cuisineCategoryID;
	}


	public RecipeCuisineCategoryVO() {
		// TODO Auto-generated constructor stub
	}

}
