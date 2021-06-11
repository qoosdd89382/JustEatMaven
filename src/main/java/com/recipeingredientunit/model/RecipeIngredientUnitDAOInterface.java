package com.recipeingredientunit.model;

import java.sql.Connection;
import java.util.List;

public interface RecipeIngredientUnitDAOInterface {
	
	public int insert(RecipeIngredientUnitVO recipeIngredientUnit);	// 回傳流水號給前端紀錄
	public int insertByRecipe(RecipeIngredientUnitVO recipeIngredientUnit, Connection con);
	
	public int update(RecipeIngredientUnitVO recipeIngredientUnit);	// 回傳筆數確認更新成功
	public int delete(int recipeIngredientUnitID);	// 回傳筆數確認刪除成功
	public RecipeIngredientUnitVO getOneByID(int recipeIngredientUnitID);	// 給前端用，前端透過ID找到三個FK，呼叫FK的getOne顯示資料
	public List<RecipeIngredientUnitVO> getAllByRecipe(int recipeID);	// 一定會用到，因為這些東西都跟食譜一起出現，然後直接foreach顯示到畫面上就好
	public List<RecipeIngredientUnitVO> getAllByIngredient(int ingredientID);
//	public List<RecipeIngredientUnitDAO> getAllByUnit(int unitID);	// 用不到

}
