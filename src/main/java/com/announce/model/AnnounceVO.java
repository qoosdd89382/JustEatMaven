package com.announce.model;

import java.io.Serializable;
import java.sql.Timestamp;

//郭建巖2021.0521.1000
//set auto_increment_offset = 700001;
//set auto_increment_increment = 1;

//table Announce
public class AnnounceVO implements Serializable {
//	private static final long serialVersionUID = 1L;
	private Integer announceID;
	private String announceText;
	private Timestamp announceTime;
	private Integer announceState;
	
	public AnnounceVO () {
		super();
	}

	public Integer getAnnounceID() {
		return announceID;
	}
	public void setAnnounceID(Integer announceID) {
		this.announceID = announceID;
	}
	public String getAnnounceText() {
		return announceText;
	}
	public void setAnnounceText(String announceText) {
		this.announceText = announceText;
	}
	public Timestamp getAnnounceTime() {
		return announceTime;
	}
	public void setAnnounceTime(Timestamp announceTime) {
		this.announceTime = announceTime;
	}

	public Integer getAnnounceState() {
		return announceState;
	}

	public void setAnnounceState(Integer announceState) {
		this.announceState = announceState;
	}
}
