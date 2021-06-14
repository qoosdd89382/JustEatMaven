package com.ingredient.model;

import java.util.List;

public class IngredientService {
	
	private IngredientDAOInterface dao;

	public IngredientService() {
		dao = new IngredientJDBCDAO();
	}

	public IngredientVO addIngredient(String ingredientName) {
		IngredientVO vo = new IngredientVO();
		vo.setIngredientName(ingredientName);
		int ingredientID = dao.insert(vo);
		vo.setIngredientID(ingredientID);
		
//		/************注意：交易問題************/
//		vo.setIngredientSearchCount(dao.getOneByID(ingredientID).getIngredientSearchCount());
		
		return vo;
	}
	
	public IngredientVO updateIngredient(int ingredientID, String ingredientName) {
		IngredientVO vo = new IngredientVO();
		vo.setIngredientID(ingredientID);
		vo.setIngredientName(ingredientName);
		
//		/************注意：交易問題************/
//		vo.setIngredientSearchCount(dao.getOneByID(ingredientID).getIngredientSearchCount());
		
		return vo;
	}

	public void deleteIngredient(int ingredientID) {
		dao.delete(ingredientID);
	}
	public void deleteIngredients(int[] ingredientIDs) {
		dao.delete(ingredientIDs);
	}
	
	public List<IngredientVO> getAll() {
		return dao.getAll();
	}
	
	public IngredientVO getOneIngredient(int ingredientID) {
		return dao.getOneByID(ingredientID);
	}
	
	public boolean isExist(String ingredientName) {
		return dao.isExist(ingredientName);
	}
	
}