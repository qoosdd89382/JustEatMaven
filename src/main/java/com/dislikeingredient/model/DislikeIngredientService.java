package com.dislikeingredient.model;

import java.util.List;

import com.ingredient.model.IngredientVO;



public class DislikeIngredientService {
	private DislikeIngredientDAOInterface dao;
	
	public DislikeIngredientService() {
		dao = new DislikeIngredientJDBCDAO();
	}

	public void addAccountDislikeIngredient(List<IngredientVO> dislikeIngredientVOs,Integer accountID) {
		dao.addAccountDislikeIngredient(dislikeIngredientVOs,accountID);	
	}
}
