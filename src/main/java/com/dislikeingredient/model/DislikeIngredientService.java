package com.dislikeingredient.model;

import java.util.List;

import com.ingredient.model.IngredientVO;
import com.likeingredient.model.LikeIngredientVO;



public class DislikeIngredientService {
	private DislikeIngredientDAOInterface dao;
	
	public DislikeIngredientService() {
		dao = new DislikeIngredientJDBCDAO();
	}

	public void addAccountDislikeIngredient(List<IngredientVO> dislikeIngredientVOs,Integer accountID) {
		dao.addAccountDislikeIngredient(dislikeIngredientVOs,accountID);	
	}
	
	public List<DislikeIngredientVO> getAccountDislikeIngredient(Integer accountID) {
		return dao.getAllByAccount(accountID);
	}
}
