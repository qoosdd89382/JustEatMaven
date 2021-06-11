package com.ingredientandtag.model;

import java.util.List;

public interface IngredientAndTagDAOInterface {
	
	public int insert(IngredientAndTagVO ingredientAndTag);
//	public int update(IngredientAndTagVO ingredientAndTag);
	public int delete(IngredientAndTagVO ingredientAndTag);
	public boolean isExist(IngredientAndTagVO ingredientAndTag);	// 雖然感覺用不到
	public List<IngredientAndTagVO> getAllByIngredient(int ingredientID);
	public List<IngredientAndTagVO> getAllByTag(int ingredientTagID);

}
