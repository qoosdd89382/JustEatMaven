package com.ingredient.model;

import java.util.List;

public interface IngredientDAOInterface {
	public int insert(IngredientVO ingredient);	// 回傳流水號
	public int update(IngredientVO ingredient);	// 回傳筆數，檢測成功
	public int delete(int ingredientID);			// 回傳筆數，檢測成功
	public int delete(int[] ingredientIDs);		// 回傳筆數，檢測成功
	public List<IngredientVO> getAll();
	public IngredientVO getOneByID(int ingredientID);
	public boolean isExist(String ingredientName);

}
