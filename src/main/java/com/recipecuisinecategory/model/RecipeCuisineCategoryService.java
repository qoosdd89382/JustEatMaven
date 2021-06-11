package com.recipecuisinecategory.model;

import java.util.List;

public class RecipeCuisineCategoryService {
	
	private RecipeCuisineCategoryDAOInterface dao;

	public RecipeCuisineCategoryService() {
		dao = new RecipeCuisineCategoryJDBCDAO();
	}
	
	public RecipeCuisineCategoryVO addRecipeCategory(int recipeID, int cuisineCategoryID) {
		RecipeCuisineCategoryVO vo = new RecipeCuisineCategoryVO();
		vo.setRecipeID(recipeID);
		vo.setCuisineCategoryID(cuisineCategoryID);
		dao.insert(vo);
		
		return vo;
	}
	
	public void deleteRecipeCategory(int recipeID, int cuisineCategoryID) {
		RecipeCuisineCategoryVO vo = new RecipeCuisineCategoryVO();
		vo.setRecipeID(recipeID);
		vo.setCuisineCategoryID(cuisineCategoryID);
		
		dao.delete(vo);
	}
	
	public boolean isExist(int recipeID, int cuisineCategoryID) {
		RecipeCuisineCategoryVO vo = new RecipeCuisineCategoryVO();
		vo.setRecipeID(recipeID);
		vo.setCuisineCategoryID(cuisineCategoryID);
		
		return dao.isExist(vo);
	}
	
	public List<RecipeCuisineCategoryVO> getAllByRecipe(int recipeID) {
		return dao.getAllByRecipe(recipeID);
	}
	
	public List<RecipeCuisineCategoryVO> getAllByCuisineCategory(int cuisineCategoryID) {
		return dao.getAllByCuisineCategory(cuisineCategoryID);
	}

}
