package com.eventinfo.model;

import java.util.List;

import com.dish.model.DishVO;
import com.dishandingredient.model.DishAndIngredientVO;
import com.eventmember.model.EventMemberVO;

public interface EventInfoDAOinterface {
	public void insert(EventInfoVO eventVO);
	public void update(EventInfoVO eventVO);
	public void delete(Integer eventID);
    public EventInfoVO findByPrimaryKey(Integer eventID);
    public List<EventInfoVO> findByName(String eventName);
    public List<EventInfoVO> findByStartDate(String eventStartDate);
    public List<EventInfoVO> getAll();
    
    //連鎖菜色
    public void insertWithDish(EventInfoVO eventInfoVO,List<DishVO> dishList); 
    //連鎖菜色和菜色食材
    public void insertWithDishIngredient(EventInfoVO eventInfoVO,List<DishVO> dishList,List<DishAndIngredientVO> dishAndIngredientList);
    public void insertWithDishIngredientMember(EventInfoVO eventInfoVO,List<DishVO> dishList,List<DishAndIngredientVO> dishAndIngredientList,List<EventMemberVO> eventMemberList);
}
