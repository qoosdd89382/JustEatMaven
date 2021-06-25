package com.recipe.model;

import java.io.InputStream;
import java.util.*;

import com.recipecuisinecategory.model.RecipeCuisineCategoryVO;
import com.recipeingredientunit.model.RecipeIngredientUnitVO;
import com.recipestep.model.RecipeStepVO;


public interface RecipeDAOInterface {
	
	// 思考回傳值是否需要存在，以及用途
	public int insert(RecipeVO recipe);			// 回傳新增食譜的 ID，由 controller 呼叫完這支以後再呼叫 getOneByPK(回傳VO的話包不到時間) 
	public int insertWithDetails(RecipeVO recipe, List<RecipeCuisineCategoryVO> catList,
												  List<RecipeIngredientUnitVO> ingUnitList,
												  List<RecipeStepVO> stepList);
	public void updateWithDetails(RecipeVO recipe, List<RecipeCuisineCategoryVO> delCatList, List<RecipeCuisineCategoryVO> addCatList, 
												   List<RecipeIngredientUnitVO> delIngUnitList, List<RecipeIngredientUnitVO> addIngUnitList,
											       List<RecipeStepVO> delStepList, List<RecipeStepVO> addStepList);
	
	public int delete(int recipeID);
	public int delete(int[] recipeIDs);			// 後臺用
	public void update(RecipeVO recipe);		// 理論上要呼叫時 controller 已經透過 getOneByPK 取得這筆食譜的所有包裝資料，使用者更改後 controller 再包裝好送進 update，所以不需要再有回傳
	public RecipeVO getOneByPK(int recipeID);
	public InputStream getOnePicByPK(int recipeID);
	public List<RecipeVO> getAllByWriter(int accountID);
	// 刪除食譜的料理分類、食材標籤會在關聯處理(join表controller做)
	
	public List<RecipeVO> getAll();

	public List<RecipeVO> getSomeNew();
	public List<RecipeVO> getSomeHot();
	public List<RecipeVO> getSomeBest();
	// 首頁輪播要做 set?

}
