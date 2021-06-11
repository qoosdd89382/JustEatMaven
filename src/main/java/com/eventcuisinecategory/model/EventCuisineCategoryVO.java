package com.eventcuisinecategory.model;

import java.io.Serializable;

public class EventCuisineCategoryVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer eventID;
	private Integer cuisinecategoryID;
	
	public Integer getEventID() {
		return eventID;
	}
	
	public void setEventID(Integer eventid_id) {
		this.eventID = eventid_id;
	}
	
	public Integer getCuisinecategoryID() {
		return cuisinecategoryID;
	}
	
	public void setCuisinecategoryID(Integer cuisinecategory_id) {
		this. cuisinecategoryID = cuisinecategory_id;
	}
}
	