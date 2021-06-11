package com.dashboardnotice.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class DashboardNoticeVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer dashboardNoticeID;
	private Integer dashboardNoticeType;
	private String dashboardNoticeText;
	private Timestamp dashboardNoticeTime;
	private Integer dashboardNoticeState;

	public DashboardNoticeVO() {
	}

	public Integer getDashboardNoticeID() {
		return dashboardNoticeID;
	}

	public void setDashboardNoticeID(Integer dashboardNoticeID) {
		this.dashboardNoticeID = dashboardNoticeID;
	}

	public Integer getDashboardNoticeType() {
		return dashboardNoticeType;
	}

	public void setDashboardNoticeType(Integer dashboardNoticeType) {
		this.dashboardNoticeType = dashboardNoticeType;
	}

	public String getDashboardNoticeText() {
		return dashboardNoticeText;
	}

	public void setDashboardNoticeText(String dashboardNoticeText) {
		this.dashboardNoticeText = dashboardNoticeText;
	}

	public Timestamp getDashboardNoticeTime() {
		return dashboardNoticeTime;
	}

	public void setDashboardNoticeTime(Timestamp dashboardNoticeTime) {
		this.dashboardNoticeTime = dashboardNoticeTime;
	}

	public Integer getDashboardNoticeState() {
		return dashboardNoticeState;
	}

	public void setDashboardNoticeState(Integer dashboardNoticeState) {
		this.dashboardNoticeState = dashboardNoticeState;
	}

}
