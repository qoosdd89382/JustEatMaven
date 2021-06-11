package com.recipestep.model;

import java.util.List;

public class RecipeStepService {
	
	private RecipeStepDAOInterface dao;

	public RecipeStepService() {
		dao = new RecipeStepJDBCDAO();
	}

	public List<RecipeStepVO> getAllByRecipe(int recipeID) {
		return dao.getAllByRecipe(recipeID);
	}

}
