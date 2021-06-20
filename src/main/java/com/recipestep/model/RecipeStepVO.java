package com.recipestep.model;

import java.io.Serializable;
import java.util.Arrays;

public class RecipeStepVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer recipeStepID;
	private Integer recipeID;	// FK
	private Integer recipeStepOrder;
	private String recipeStepText;
	private byte[] recipeStepPic;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((recipeID == null) ? 0 : recipeID.hashCode());
//		result = prime * result + ((recipeStepID == null) ? 0 : recipeStepID.hashCode());
		result = prime * result + ((recipeStepOrder == null) ? 0 : recipeStepOrder.hashCode());
		result = prime * result + Arrays.hashCode(recipeStepPic);
		result = prime * result + ((recipeStepText == null) ? 0 : recipeStepText.hashCode());
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
		RecipeStepVO other = (RecipeStepVO) obj;
		if (recipeID == null) {
			if (other.recipeID != null)
				return false;
		} else if (!recipeID.equals(other.recipeID))
			return false;
//		if (recipeStepID == null) {
//			if (other.recipeStepID != null)
//				return false;
//		} else if (!recipeStepID.equals(other.recipeStepID))
//			return false;
		if (recipeStepOrder == null) {
			if (other.recipeStepOrder != null)
				return false;
		} else if (!recipeStepOrder.equals(other.recipeStepOrder))
			return false;
		if (!Arrays.equals(recipeStepPic, other.recipeStepPic))
			return false;
		if (recipeStepText == null) {
			if (other.recipeStepText != null)
				return false;
		} else if (!recipeStepText.equals(other.recipeStepText))
			return false;
		return true;
	}

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
