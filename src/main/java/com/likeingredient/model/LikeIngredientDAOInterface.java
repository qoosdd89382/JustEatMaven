package com.likeingredient.model;

import java.util.List;

import com.ingredient.model.IngredientVO;

public interface LikeIngredientDAOInterface {

	public int insert(LikeIngredientVO likeIngredient);	// 回傳筆數表示成功
//	public int update(LikeIngredientVO likeIngredient);	// 回傳筆數表示成功	// 用不到
	public int delete(LikeIngredientVO likeIngredient);	// 回傳筆數表示成功
	public boolean isExist(LikeIngredientVO likeIngredient);	// 檢查時可能會用到
	public List<LikeIngredientVO> getAllByAccount(int accountID);
	public List<LikeIngredientVO> getAllByIngredient(int likeIngredientID);	// 可能用不到
	
	public void addAccountLikeIngredient(List<IngredientVO> likeIngredientVOs,Integer accountID);

}
