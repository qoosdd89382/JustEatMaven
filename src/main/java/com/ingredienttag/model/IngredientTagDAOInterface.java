package com.ingredienttag.model;

import java.util.List;

public interface IngredientTagDAOInterface {
	public int insert(IngredientTagVO ingredientTag);	// 回傳流水號
	public int update(IngredientTagVO ingredientTag);	// 回傳筆數，檢測成功
	public int delete(int ingredientTagID);				// 回傳筆數，檢測成功
	public int delete(int[] ingredientTagIDs);			// 回傳筆數，檢測成功
	public List<IngredientTagVO> getAll();
	public IngredientTagVO getOneByID(int ingredientTagID);
	public boolean isExist(String ingredientTagName);
}
