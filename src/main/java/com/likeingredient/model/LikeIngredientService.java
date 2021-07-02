package com.likeingredient.model;

import java.util.List;

import com.ingredient.model.IngredientVO;

public class LikeIngredientService {
	private LikeIngredientDAOInterface dao;
	
	public LikeIngredientService() {
		dao = new LikeIngredientJDBCDAO();
	}

	public void addAccountLikeIngredient(List<IngredientVO> likeIngredientVOs,Integer accountID) {
		dao.addAccountLikeIngredient(likeIngredientVOs,accountID);	
	}
	
	public List<LikeIngredientVO> getAccountLikeIngredient(Integer accountID) {
		return dao.getAllByAccount(accountID);
	}
}
