package com.thumbsuprecipe.model;

import java.sql.Timestamp;
import java.util.List;

public class ThumbsupRecipeService {
	
	private ThumbsupRecipeDAOInterface dao;
	
	public ThumbsupRecipeService() {
		dao = new ThumbsupRecipeJDBCDAO();
	}

	public ThumbsupRecipeVO addThumbsupRecipe(int accountID, int recipeID) {
		ThumbsupRecipeVO vo = new ThumbsupRecipeVO();
		vo.setAccountID(accountID);
		vo.setThmupRecipeID(recipeID);
		dao.insert(vo);
		
		return vo;
	}
	
	public int deleteThumbsupRecipe(int accountID, int recipeID) {
		ThumbsupRecipeVO vo = new ThumbsupRecipeVO();
		vo.setAccountID(accountID);
		vo.setThmupRecipeID(recipeID);
		
		return dao.delete(vo);
	}
	public Timestamp isExist(int accountID, int recipeID) {
		ThumbsupRecipeVO vo = new ThumbsupRecipeVO();
		vo.setAccountID(accountID);
		vo.setThmupRecipeID(recipeID);
		
		return dao.isExist(vo);
	}
	
	public List<ThumbsupRecipeVO> getAllByAccount(int accountID) {
		return dao.getAllByAccount(accountID);
	}
	public List<ThumbsupRecipeVO> getAllByRecipe(int thmupRecipeID) {
		return dao.getAllByRecipe(thmupRecipeID);
	}

	public int countAllByRecipe(int thmupRecipeID) {
		return dao.countAllByRecipe(thmupRecipeID);
	}
	
	public List<ThumbsupRecipeVO> getAllByCountInDays(int days, int limit) {
		return dao.getAllByCountInDays(days, limit);
	}
	
}
