package com.eventmember.model;

import java.io.Serializable;

public class EventMemberVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer eventID;
	private Integer accountID;
	private Integer participationState;
	private Integer totalScore;
	private Integer totalJudger;

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

}
