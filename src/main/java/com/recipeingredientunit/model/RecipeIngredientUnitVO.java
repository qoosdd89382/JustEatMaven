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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ingredientID == null) ? 0 : ingredientID.hashCode());
		result = prime * result + ((recipeID == null) ? 0 : recipeID.hashCode());
//		result = prime * result + ((unitAmount == null) ? 0 : unitAmount.hashCode());
		result = prime * result + ((unitAmount == null) ? 0 : unitAmount.intValue());
		result = prime * result + ((unitID == null) ? 0 : unitID.hashCode());
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
		RecipeIngredientUnitVO other = (RecipeIngredientUnitVO) obj;
		if (ingredientID == null) {
			if (other.ingredientID != null)
				return false;
		} else if (!ingredientID.equals(other.ingredientID))
			return false;
		if (recipeID == null) {
			if (other.recipeID != null)
				return false;
		} else if (!recipeID.equals(other.recipeID))
			return false;
		if (unitID == null) {
			if (other.unitID != null)
				return false;
		} else if (!unitID.equals(other.unitID))
			return false;
		if (unitAmount == null) {
			if (other.unitAmount != null)
				return false;
//		} else if (!unitAmount.equals(other.unitAmount))
		} else if (unitAmount.compareTo(other.unitAmount) != 0)		// 對於BigDecimal的大小比較，用equals方法的話會不僅會比較值的大小，還會比較兩個物件的精確度，
			return false;											// 而compareTo方法則不會比較精確度，只比較數值的大小。
		return true;												// https://codertw.com/%E7%A8%8B%E5%BC%8F%E8%AA%9E%E8%A8%80/314278/
	}
	
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
