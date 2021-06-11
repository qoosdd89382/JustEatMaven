package com.visit.model;

import java.io.Serializable;
import java.sql.Timestamp;

//郭建巖2021.0521.1000
//set auto_increment_offset = 900001;
//set auto_increment_increment = 1;

//table visit
public class VisitVO implements Serializable {
//	private static final long serialVersionUID = 1L;
	private Integer visitID;
	private Integer accountID;
	private Timestamp visitRecord;
	
	public Integer getVisitID() {
		return visitID;
	}
	public void setVisitID(Integer visitID) {
		this.visitID = visitID;
	}
	public Integer getAccountID() {
		return accountID;
	}
	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}
	public Timestamp getVisitRecord() {
		return visitRecord;
	}
	public void setVisitRecord(Timestamp visitRecord) {
		this.visitRecord = visitRecord;
	}
}
