package com.dishandingredient.model;

import java.io.Serializable;

public class DishAndIngredientVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer dishID;
	private Integer ingredientID;
	
	public Integer getDishID() {
		return dishID;
	}
	
	public void setDishID(Integer dish_id) {
		this.dishID = dish_id;
	}
	public Integer getIngredientID() {
		return ingredientID;
	}
	
	public void setIngredientID(Integer ingredient_id) {
		this.ingredientID = ingredient_id;
	}



	

	

	
}
	