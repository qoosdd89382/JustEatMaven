package com.recipe.model;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.recipecuisinecategory.model.RecipeCuisineCategoryVO;
import com.recipeingredientunit.model.RecipeIngredientUnitVO;
import com.recipestep.model.RecipeStepVO;

public class RecipeService {
	
	private RecipeDAOInterface recipeDAO;

	public RecipeService() {
		recipeDAO = new RecipeJDBCDAO();
	}
	
	public RecipeVO addRecipeWithDetails(
			String recipeName, String recipeIntroduction, byte[] recipePicTop,
			Integer recipeServe, Integer accountID, 
			List<RecipeCuisineCategoryVO> catList, List<RecipeIngredientUnitVO> ingUnitList, List<RecipeStepVO> stepList) {

		RecipeVO recipeVO = new RecipeVO();
		
		recipeVO.setRecipeName(recipeName);
		recipeVO.setRecipeIntroduction(recipeIntroduction);
		recipeVO.setRecipePicTop(recipePicTop);
		recipeVO.setRecipeServe(recipeServe);
		recipeVO.setAccountID(accountID);
		recipeVO.setRecipeID(recipeDAO.insertWithDetails(recipeVO, catList, ingUnitList, stepList));
		
		return recipeVO;
	}
	

	public void updateRecipeWithDetails(RecipeVO recipeVO, 
							List<RecipeCuisineCategoryVO> delCatList, List<RecipeCuisineCategoryVO> addCatList, 
							List<RecipeIngredientUnitVO> delIngUnitList, List<RecipeIngredientUnitVO> addIngUnitList,
							List<RecipeStepVO> delStepList, List<RecipeStepVO> addStepList) {

		recipeDAO.updateWithDetails(recipeVO, delCatList, addCatList, delIngUnitList, addIngUnitList, delStepList, addStepList);
	}
	
//	public RecipeVO addRecipe(
//			String recipeName, String recipeIntroduction, byte[] recipePicTop,
//			Integer recipeServe, Integer accountID) {
//		
//		RecipeVO recipeVO = new RecipeVO();
//		
//		recipeVO.setRecipeName(recipeName);
//		recipeVO.setRecipeIntroduction(recipeIntroduction);
//		recipeVO.setRecipePicTop(recipePicTop);
//		recipeVO.setRecipeServe(recipeServe);
//		recipeVO.setAccountID(accountID);
//		recipeVO.setRecipeID(recipeDAO.insert(recipeVO));
//		
//		return recipeVO;
//	}
	
	public RecipeVO updateRecipe(
			Integer recipeID,
			String recipeName, String recipeIntroduction, byte[] recipePicTop,
			Integer recipeServe, Timestamp recipeTime, Integer accountID) {
		
		RecipeVO recipeVO = new RecipeVO();
		
		recipeVO.setRecipeID(recipeID);
		recipeVO.setRecipeName(recipeName);
		recipeVO.setRecipeIntroduction(recipeIntroduction);
		recipeVO.setRecipePicTop(recipePicTop);
		recipeVO.setRecipeServe(recipeServe);
		recipeVO.setAccountID(accountID);
		recipeDAO.update(recipeVO);
		
		return recipeVO;
	}
	
	public synchronized void updateViewCount(int recipeID) {
		RecipeVO recipeVO = recipeDAO.getOneByPK(recipeID);
		int count = recipeVO.getRecipeViewCount();
		recipeVO.setRecipeViewCount(++count);
		recipeDAO.updateViewCount(recipeVO);
	}

	public synchronized void deleteRecipe(int recipeID) {
		recipeDAO.delete(recipeID);
	}
	
	public void deleteRecipes(int[] recipeIDs) {
		recipeDAO.delete(recipeIDs);
	}

	public RecipeVO getOneRecipe(int recipeID) {
		return recipeDAO.getOneByPK(recipeID);
	}

//	public InputStream getOneTopPic(int recipeID) {
//		return recipeDAO.getOnePicByPK(recipeID);
//	}
	
	public List<RecipeVO> getAll() {
		return recipeDAO.getAll();
	}
	

	public List<RecipeVO> getAll(Map<String, String[]> map) {
		return recipeDAO.getAll(map);
	}
	
	public List<RecipeVO> getSomeNew() {
		return recipeDAO.getSomeNew();
	}
	
	public List<RecipeVO> getAllByWriter(int accountID) {
		return recipeDAO.getAllByWriter(accountID);
	}
	
	// HOT, BEST?

}
