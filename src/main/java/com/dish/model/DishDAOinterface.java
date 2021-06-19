package com.dish.model;

import java.sql.Connection;
import java.util.List;

import com.dishandingredient.model.DishAndIngredientVO;

public interface DishDAOinterface {
	public void insert(DishVO dishVO);
	public void update(DishVO dishVO);
	public void delete(Integer dishID);
    public DishVO findByPrimaryKey(Integer dishID);
    public List<DishVO> findByEventID(Integer eventID);
    public List<DishVO> findByAccountID(Integer AccountID);
    public List<DishVO> getAll();
    
    //被活動資訊連鎖用
    public void insertByEventInfo(DishVO dishVO,Connection con);
    //菜色連鎖菜色食材 用
    public void insertWithDishAndIngredient(DishVO dishVO,List<DishAndIngredientVO> dishAndIngredientList);
    //新增活動 連鎖 菜色 再連鎖 菜色食材
    public void insertByEventInfoAndWithDishAndIngredient(DishVO dishVO,List<DishAndIngredientVO> dishAndIngredientList,Connection con);
}
