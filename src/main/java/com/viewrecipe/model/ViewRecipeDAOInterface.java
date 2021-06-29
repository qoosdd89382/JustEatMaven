package com.viewrecipe.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


public interface ViewRecipeDAOInterface {

	public int insert(ViewRecipeVO viewRecipe);
	public List<ViewRecipeVO> getAllByAccount(int accountID);
	public List<ViewRecipeVO> getAllByRecipe(int recipeID);
	public Map<Integer, Integer> getSomeHot();
	public int countAllByRecipe(int recipeID);
}
