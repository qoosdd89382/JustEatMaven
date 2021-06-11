package com.eventcuisinecategory.model;

import java.util.List;

public class EventCuisineCategoryService {
	
		private EventCuisineCategoryDAOInterface dao;
		
		public EventCuisineCategoryService() {
			dao = new EventcuisinecategoryJDBCDAO ();
		}
	
	 public List<EventCuisineCategoryVO> getAllByEventID(Integer eventID){
		 return dao.getAllByEventID(eventID);
	 }
	 public List<EventCuisineCategoryVO> getALLByCuisineCategoryID(Integer cuisinecategoryID){
		 return dao.getAllByEventID(cuisinecategoryID);
	 }

}
