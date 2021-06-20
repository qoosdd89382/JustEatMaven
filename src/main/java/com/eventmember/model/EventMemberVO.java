package com.eventmember.model;

import java.io.Serializable;

public class EventMemberVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer eventID;
	private Integer accountID;
	private Integer participationState;
	private Integer totalScore;
	private Integer totalJudger;
	private Integer avgScore;
	private Integer totalevent;
	private Integer totalattendance;
	private Integer eventstatus;
	

	public Integer getEventID() {
		return eventID;
	}

	public void setEventID(Integer eventID) {
		this.eventID = eventID;
	}

	public Integer getAccountID() {
		return accountID;
	}

	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}

	public Integer getParticipationState() {
		return participationState;
	}

	public void setParticipationState(Integer participationState) {
		this.participationState = participationState;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public Integer getTotalJudger() {
		return totalJudger;
	}

	public void setTotalJudger(Integer totalJudger) {
		this.totalJudger = totalJudger;
	}
	public Integer getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(Integer avgScore) {
		this.avgScore = avgScore;
	}
	public Integer getTotalEvent() {
		return totalevent;
	}

	public void setTotalEvent(Integer totalevent) {
		this.totalevent= totalevent;
	}
	
	public Integer getTotalAttendance() {
			return totalattendance;
		}

	public void setTotalAttendance(Integer totalattendance) {
			this.totalattendance= totalattendance;
	}
	
	public Integer getEventStatus() {
		return eventstatus;
	}

	public void setEventStatus(Integer eventstatus) {
		 this.eventstatus= eventstatus;
}
	
		
		
	

}
