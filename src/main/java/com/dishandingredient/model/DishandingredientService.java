package com.dishandingredient.model;

import java.util.List;

public class DishandingredientService {

	private DishAndIngredientDAOinterface dao;

	public DishandingredientService() {
		dao = new DishAndIngredientJDBCDAO();
		
	

	}
	  public List<DishAndIngredientVO> getAllByDish(Integer dishID){
		  return dao.getAllByDish(dishID);
	  }
	  public List<DishAndIngredientVO> getALLByIngredientID(Integer ingredienID){
		  return dao.getALLByIngredientID(ingredienID);
	  }
}
