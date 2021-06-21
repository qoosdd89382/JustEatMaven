package com.evaluatedmember.model;

import java.io.Serializable;

public class EvaluatedMemberVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer accepterAccountID;
	private Integer giverAccountID;
	private Integer eventID;
	private Integer giveScore;
	
	
	public Integer getAccepterAccountID() {
		return accepterAccountID;
	}
	
	public void setAccepterAccountID(Integer accepterAccountID) {
		this.accepterAccountID = accepterAccountID;
	}
	
	public Integer getGiverAccountID() {
		return giverAccountID;
	}
	
	public void setGiverAccountID(Integer giverAccountID) {
		this.giverAccountID = giverAccountID;
	}
	
	public Integer getEventID() {
		return eventID;
	}
	
	public void setEventID(Integer eventID) {
		this.eventID = eventID;
	}
	
	public Integer getGiveScore() {
		return giveScore;
	}
	
	public void setGiveScore(Integer giveScore) {
		this.giveScore = giveScore;
	}
	
	
}
