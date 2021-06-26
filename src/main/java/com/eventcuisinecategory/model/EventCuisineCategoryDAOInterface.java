package com.eventcuisinecategory.model;

import java.sql.Connection;
import java.util.List;

import com.dishandingredient.model.DishAndIngredientVO;

public interface EventCuisineCategoryDAOInterface {
	public void insert(EventCuisineCategoryVO eventcuisinecategoryVO);
	public void insertbyEventInfo(EventCuisineCategoryVO eventcuisinecategoryVO,Connection con);
	public void update(EventCuisineCategoryVO eventcuisinecategoryVO);
	public void delete(Integer eventID);
    public List<EventCuisineCategoryVO> getAll();
    public List<EventCuisineCategoryVO> getAllByEventID(Integer eventID);
    public List<EventCuisineCategoryVO> getALLByCuisineCategoryID(Integer cuisinecategoryID);
}
