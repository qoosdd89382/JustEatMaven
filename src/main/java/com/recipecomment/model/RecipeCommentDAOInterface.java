package com.recipecomment.model;

import java.util.List;

public interface RecipeCommentDAOInterface {
	
	public int insert(RecipeCommentVO recipeComment);	// 成功回傳流水號 ID 讓前端記住
	public int update(RecipeCommentVO recipeComment);	// 成功需要一個值讓前端知道，改變頁面
	public int delete(int recipeCommentID);				// 成功需要一個值讓前端知道，改變頁面
	public RecipeCommentVO getOnebyID(int recipeCommentID);
	public List<RecipeCommentVO> getAllByRecipe(int recipeID);
	public List<RecipeCommentVO> getAllByAccount(int accountID);

}
