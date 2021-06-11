package com.recipeingredientunit.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class RecipeIngredientUnitVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer recipeIngredientUnitID;	// 感覺真多餘...
	private Integer recipeID;
	private Integer ingredientID;
	private Integer unitID;
	private BigDecimal unitAmount;	// 接法：BigDecimal.valueOf(Double number);
	
	public Integer getRecipeIngredientUnitID() {
		return recipeIngredientUnitID;
	}
	public void setRecipeIngredientUnitID(Integer recipeIngredientUnitID) {
		this.recipeIngredientUnitID = recipeIngredientUnitID;
	}
	public Integer getRecipeID() {
		return recipeID;
	}
	public void setRecipeID(Integer recipeID) {
		this.recipeID = recipeID;
	}
	public Integer getIngredientID() {
		return ingredientID;
	}
	public void setIngredientID(Integer ingredientID) {
		this.ingredientID = ingredientID;
	}
	public Integer getUnitID() {
		return unitID;
	}
	public void setUnitID(Integer unitID) {
		this.unitID = unitID;
	}
	public BigDecimal getUnitAmount() {
		return unitAmount;
	}
	public void setUnitAmount(BigDecimal unitAmount) {
		this.unitAmount = unitAmount;
	}


	
}
