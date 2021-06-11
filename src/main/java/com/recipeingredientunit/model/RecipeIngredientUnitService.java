package com.recipeingredientunit.model;

import java.util.List;

public class RecipeIngredientUnitService {
	private RecipeIngredientUnitDAOInterface dao;

	public RecipeIngredientUnitService() {
		dao = new RecipeIngredientUnitJDBCDAO();
	}

	public List<RecipeIngredientUnitVO> getAllByRecipe(int recipeID) {
		return dao.getAllByRecipe(recipeID);
	}
	public List<RecipeIngredientUnitVO> getAllByIngredient(int ingredientID) {
		return dao.getAllByIngredient(ingredientID);
	}
//	public int add(RecipeIngredientUnitVO recipeIngredientUnit);
//	public int insertByRecipe(RecipeIngredientUnitVO recipeIngredientUnit, Connection con);
//	public int update(RecipeIngredientUnitVO recipeIngredientUnit);
//	public int delete(int recipeIngredientUnitID);

	
}
