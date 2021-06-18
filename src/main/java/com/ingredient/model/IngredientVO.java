package com.ingredient.model;

import java.io.Serializable;

public class IngredientVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer ingredientID;
	private String ingredientName;
	private Integer ingredientSearchCount;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ingredientID == null) ? 0 : ingredientID.hashCode());
		result = prime * result + ((ingredientName == null) ? 0 : ingredientName.hashCode());
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
		IngredientVO other = (IngredientVO) obj;
		if (ingredientID == null) {
			if (other.ingredientID != null)
				return false;
		} else if (!ingredientID.equals(other.ingredientID))
			return false;
		if (ingredientName == null) {
			if (other.ingredientName != null)
				return false;
		} else if (!ingredientName.equals(other.ingredientName))
			return false;
		return true;
	}

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
