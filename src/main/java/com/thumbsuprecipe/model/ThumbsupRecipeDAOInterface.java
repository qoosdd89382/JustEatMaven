package com.thumbsuprecipe.model;

import java.sql.Timestamp;
import java.util.List;


public interface ThumbsupRecipeDAOInterface {
	
	public int insert(ThumbsupRecipeVO thumbsupRecipe);
//	public int update(ThumbsupRecipeVO thumbsupRecipe);
	public int updateTime(ThumbsupRecipeVO thumbsupRecipe);		// 回傳筆數表示成功
	public int delete(ThumbsupRecipeVO thumbsupRecipe);
	public Timestamp isExist(ThumbsupRecipeVO thumbsupRecipe);
	public List<ThumbsupRecipeVO> getAllByAccount(int accountID);
	public List<ThumbsupRecipeVO> getAllByRecipe(int thmupRecipeID);
}
