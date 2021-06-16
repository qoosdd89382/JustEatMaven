package com.dishandingredient.model;

import java.sql.Connection;
import java.util.List;

import com.dish.model.DishVO;

public interface DishAndIngredientDAOinterface {
	public void insert(DishAndIngredientVO dishAndIngredientVO);
  //public void update(DishAndIngredientVO dishAndIngredientVO);
	public void delete(Integer dishAndIngredienID);
 // public DishAndIngredientVO findByPrimaryKey(Integer dishID);
    public List<DishAndIngredientVO> getAll();
    public List<DishAndIngredientVO> getAllByDish(Integer dishID);
    public List<DishAndIngredientVO> getALLByIngredientID(Integer ingredienID);

    //被菜色連鎖
    public void insertbyDish(DishAndIngredientVO dishAndIngredientVO,Connection con);
    

}