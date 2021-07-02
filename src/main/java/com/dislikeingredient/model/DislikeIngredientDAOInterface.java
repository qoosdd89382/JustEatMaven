package com.dislikeingredient.model;

import java.util.List;

import com.ingredient.model.IngredientVO;

public interface DislikeIngredientDAOInterface {

	public int insert(DislikeIngredientVO dislikeIngredient);	// 回傳筆數表示成功
//	public int update(DislikeIngredientVO dislikeIngredient);	// 回傳筆數表示成功	// 用不到
	public int delete(DislikeIngredientVO dislikeIngredient);	// 回傳筆數表示成功
	public boolean isExist(DislikeIngredientVO dislikeIngredient);	// 檢查時可能會用到
	public List<DislikeIngredientVO> getAllByAccount(int accountID);
	public List<DislikeIngredientVO> getAllByIngredient(int dislikeIngredientID);	// 可能用不到
	
	public void addAccountDislikeIngredient(List<IngredientVO> dislikeIngredientVOs,Integer accountID);

}
