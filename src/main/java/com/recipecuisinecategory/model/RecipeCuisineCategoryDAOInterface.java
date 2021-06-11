package com.recipecuisinecategory.model;

import java.sql.Connection;
import java.util.List;

public interface RecipeCuisineCategoryDAOInterface {
	
	// 傳物件？
	public int insert(RecipeCuisineCategoryVO recipeCuisineCategory);	// 回傳筆數表示成功
	public int insertByRecipe(RecipeCuisineCategoryVO recipeCuisineCategory, Connection con);
	
//	public int update(RecipeCuisineCategoryVO recipeCuisineCategory);	// 回傳筆數表示成功	// 用不到
	public int delete(RecipeCuisineCategoryVO recipeCuisineCategory);	// 回傳筆數表示成功
	public boolean isExist(RecipeCuisineCategoryVO recipeCuisineCategory);	// 檢查時可能會用到

//	// 傳2個參數進去？
//	public int insert(int recipeID, int cuisineCategoryID);	// 回傳筆數表示成功
//	public int update(int recipeID, int cuisineCategoryID);	// 回傳筆數表示成功
//	public int delete(int recipeID, int cuisineCategoryID);	// 回傳筆數表示成功
//	public boolean isExist(int recipeID, int cuisineCategoryID);	// 檢查時可能會用到

	// 另外，因為我們有cascade，目前 delete by recipeID/cuisineCategoryID 都沒有存在的必要
	
	public List<RecipeCuisineCategoryVO> getAllByRecipe(int recipeID);
	public List<RecipeCuisineCategoryVO> getAllByCuisineCategory(int cuisineCategoryID);
	// 要做好VO裡面引入VO時，關聯表的get方法都要改的打算?
}
