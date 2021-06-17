package com.recipecuisinecategory.model;

import java.io.Serializable;

public class RecipeCuisineCategoryVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer recipeID;
	private Integer cuisineCategoryID;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cuisineCategoryID == null) ? 0 : cuisineCategoryID.hashCode());
		result = prime * result + ((recipeID == null) ? 0 : recipeID.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		RecipeCuisineCategoryVO other = (RecipeCuisineCategoryVO) obj;
		if (cuisineCategoryID == null) {
			if (other.cuisineCategoryID != null) {
				return false;
			}
		} else if (!cuisineCategoryID.equals(other.cuisineCategoryID)) {
			return false;
		}
		if (recipeID == null) {
			if (other.recipeID != null) {
				return false;
			}
		} else if (!recipeID.equals(other.recipeID)) {
			return false;
		}
		return true;
	}


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
