package com.eventinfo.model;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Timestamp;

public class EventInfoVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer eventID;
	private String eventName;
	private Integer eventCurrentCount;
	private String eventDescription;
	private Integer groupType;
	private String groupCity;
	private String groupAddress;
	private Timestamp eventRegistartionStartTime;
	private Timestamp eventRegistartionEndTime;
	private Timestamp eventStartTime;
	private Timestamp eventEndTime;
	private Integer eventState;
	private byte[] eventPic;
	private Integer eventViewCount;
	
	public Integer getEventViewCount() {
		return eventViewCount;
	}

	public void setEventViewCount(Integer eventViewCount) {
		this.eventViewCount = eventViewCount;
	}

	public Integer getEventID() {
		return eventID;
	}

	public void setEventID(Integer eventID) {
		this.eventID = eventID;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Integer getEventCurrentCount() {
		return eventCurrentCount;
	}

	public void setEventCurrentCount(Integer eventCurrentCount) {
		this.eventCurrentCount = eventCurrentCount;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

	public String getGroupCity() {
		return groupCity;
	}

	public void setGroupCity(String groupCity) {
		this.groupCity = groupCity;
	}

	public String getGroupAddress() {
		return groupAddress;
	}

	public void setGroupAddress(String groupAddress) {
		this.groupAddress = groupAddress;
	}

	public Timestamp getEventRegistartionStartTime() {
		return eventRegistartionStartTime;
	}

	public void setEventRegistartionStartTime(Timestamp eventRegistartionStartTime) {
		this.eventRegistartionStartTime = eventRegistartionStartTime;
	}

	public Timestamp getEventRegistartionEndTime() {
		return eventRegistartionEndTime;
	}

	public void setEventRegistartionEndTime(Timestamp eventRegistartionEndTime) {
		this.eventRegistartionEndTime = eventRegistartionEndTime;
	}

	public Timestamp getEventStartTime() {
		return eventStartTime;
	}

	public void setEventStartTime(Timestamp eventStartTime) {
		this.eventStartTime = eventStartTime;
	}

	public Timestamp getEventEndTime() {
		return eventEndTime;
	}

	public void setEventEndTime(Timestamp eventEndTime) {
		this.eventEndTime = eventEndTime;
	}

	public Integer getEventState() {
		return eventState;
	}

	public void setEventState(Integer eventState) {
		this.eventState = eventState;
	}

	public byte[] getEventPic() {
		return eventPic;
	}

	public void setEventPic(byte[] eventPic) {
		this.eventPic = eventPic;
	}

}
