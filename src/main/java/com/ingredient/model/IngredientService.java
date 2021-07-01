package com.ingredient.model;

import java.util.List;

public class IngredientService {
	
	private IngredientDAOInterface dao;

	public IngredientService() {
		dao = new IngredientJDBCDAO();
	}

	public synchronized IngredientVO addIngredient(String ingredientName) {
		IngredientVO vo = new IngredientVO();
		vo.setIngredientName(ingredientName);
		int ingredientID = dao.insert(vo);
		vo.setIngredientID(ingredientID);
		/************注意：交易問題************/
		vo.setIngredientSearchCount(dao.getOneByID(ingredientID).getIngredientSearchCount());
		
		
		return vo;
	}
	
	public synchronized IngredientVO updateIngredient(int ingredientID, String ingredientName) {
		IngredientVO vo = new IngredientVO();
		vo.setIngredientID(ingredientID);
		vo.setIngredientName(ingredientName);
		/************注意：交易問題************/
		vo.setIngredientSearchCount(dao.getOneByID(ingredientID).getIngredientSearchCount());
		
		dao.update(vo);
		
		return vo;
	}
	
	public IngredientVO updateSearchCount(int ingredientID) {
		IngredientVO vo = dao.getOneByID(ingredientID);
		int count = vo.getIngredientSearchCount();
		vo.setIngredientSearchCount(++count);
		dao.updateSearchCount(vo);
		return vo;
	}


	public int deleteIngredient(int ingredientID) {
		return dao.delete(ingredientID);
	}
	public void deleteIngredients(int[] ingredientIDs) {
		dao.delete(ingredientIDs);
	}
	
	public List<IngredientVO> getAll() {
		return dao.getAll();
	}

	public List<IngredientVO> getAll(String sqlStatement) {
		return dao.getAll(sqlStatement);
	}
	
	public IngredientVO getOneIngredient(int ingredientID) {
		return dao.getOneByID(ingredientID);
	}
	
	public boolean isExist(String ingredientName) {
		return dao.isExist(ingredientName);
	}
	
}
