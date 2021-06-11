package com.dislikeingredient.model;

import java.util.List;

public interface DislikeIngredientDAOInterface {

	public int insert(DislikeIngredientVO dislikeIngredient);	// 回傳筆數表示成功
//	public int update(DislikeIngredientVO dislikeIngredient);	// 回傳筆數表示成功	// 用不到
	public int delete(DislikeIngredientVO dislikeIngredient);	// 回傳筆數表示成功
	public boolean isExist(DislikeIngredientVO dislikeIngredient);	// 檢查時可能會用到
	public List<DislikeIngredientVO> getAllByAccount(int accountID);
	public List<DislikeIngredientVO> getAllByIngredient(int dislikeIngredientID);	// 可能用不到

}
