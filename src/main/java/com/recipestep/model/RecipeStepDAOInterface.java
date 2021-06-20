package com.recipestep.model;

import java.sql.Connection;
import java.util.List;
//import java.util.Map;

public interface RecipeStepDAOInterface {

	public RecipeStepVO insert(RecipeStepVO recipeStep);	// 傳回整個物件，讓前端可以記錄ID和順序(前端監控n秒不動後自動儲存?)
	public RecipeStepVO insertByRecipe(RecipeStepVO recipeStep, Connection con);
	
	public int update(RecipeStepVO recipeStep);
	public int refreshOrder(int[] recipeStepIDs);
	
	public int exchangeOrder(RecipeStepVO firstRecipeStepVO, RecipeStepVO secondRecipeStepVO);
	// 傳入int[]或List<RecipeStepVO>?
	
	public void delete(int recipeStepID);
	public void deleteByRecipe(RecipeStepVO recipeStep, Connection con);
	public RecipeStepVO getOneByID(int recipeStepID);
//	public List<RecipeStepVO> getAll();		// 用不到
	public List<RecipeStepVO> getAllByRecipe(int recipeID);

	// 一些麻煩的操作流程可能如下：
	// [拖曳移動順序][刪除中間筆數]
	// 先依靠前端 js 抓物件，取前端畫面上"所有" elements 由上至下的 ID 順序，及紀錄總個數
	// 再呼叫 refreshOrder 按照順序排序，重新記錄新排序 (set VO)，回傳一個更新筆數
	// 將筆數回傳給前端畫面，與紀錄總個數吻合，前端即顯示「順序已自動存檔」的非彈出視窗提示 
	// 內容更新則要倚靠手動存檔
	// [交換順序] 可依照上面方式實作，或可：
	// 另定方法，只交換兩者的方法

}
