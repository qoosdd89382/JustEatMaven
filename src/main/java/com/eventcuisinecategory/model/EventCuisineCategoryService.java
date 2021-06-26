package com.eventcuisinecategory.model;

import java.util.List;

import com.dish.model.DishVO;

public class EventCuisineCategoryService {

	private EventCuisineCategoryDAOInterface dao;

	public EventCuisineCategoryService() {
		dao = new EventcuisinecategoryJDBCDAO();
	}

	public EventCuisineCategoryVO addEventCuisineCategory(int eventID,int cuisinecategoryID) {
		EventCuisineCategoryVO eventCuisineCategoryVO = new EventCuisineCategoryVO();
		eventCuisineCategoryVO.setEventID(eventID);
		eventCuisineCategoryVO.setCuisinecategoryID(cuisinecategoryID);
		dao.insert(eventCuisineCategoryVO);
		return eventCuisineCategoryVO;
	}
	
	public EventCuisineCategoryVO updateEventCuisineCategory(int cuisineCategoryID,int eventID) {
		EventCuisineCategoryVO eventCuisineCategoryVO = new EventCuisineCategoryVO();
		eventCuisineCategoryVO.setEventID(eventID);
		eventCuisineCategoryVO.setCuisinecategoryID(cuisineCategoryID);
		dao.update(eventCuisineCategoryVO);
		return eventCuisineCategoryVO;
	}
	
	public List<EventCuisineCategoryVO> getAllByEventID(Integer eventID) {
		return dao.getAllByEventID(eventID);
	}

	public List<EventCuisineCategoryVO> getALLByCuisineCategoryID(Integer cuisinecategoryID) {
		return dao.getAllByEventID(cuisinecategoryID);
	}

}
