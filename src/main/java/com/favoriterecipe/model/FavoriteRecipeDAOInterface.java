package com.favoriterecipe.model;

import java.sql.Timestamp;
import java.util.List;

public interface FavoriteRecipeDAOInterface {

	// 傳物件？
	public int insert(FavoriteRecipeVO favoriteRecipe);	// 回傳筆數表示成功
//	public int update(FavoriteRecipeVO favoriteRecipe);	// 回傳筆數表示成功	// 不會有這種東西
	public int delete(FavoriteRecipeVO favoriteRecipe);	// 回傳筆數表示成功
	
//	// 傳2個參數進去？
//	public int insert(int accountID, int favRecipeID);	// 回傳筆數表示成功
//	public int delete(int accountID, int favRecipeID);	// 回傳筆數表示成功

	public Timestamp isExist(FavoriteRecipeVO favoriteRecipe);	//  insert/updateTime前檢查是否重複！
	public int updateTime(FavoriteRecipeVO favoriteRecipe);		// 回傳筆數表示成功
	
	public List<FavoriteRecipeVO> getAllByAccount(int accountID);
	public List<FavoriteRecipeVO> getAllByRecipe(int favRecipeID);
	public int countAllByRecipe(int favRecipeID);

}
