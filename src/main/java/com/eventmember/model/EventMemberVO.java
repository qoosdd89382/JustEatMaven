package com.eventmember.model;

import java.io.Serializable;

public class EventMemberVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer eventID;
	private Integer accountID;
	private Integer participationState;
	private boolean hostIdentifier;

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

	public boolean isHostIdentifier() {
		return hostIdentifier;
	}

	public void setHostIdentifier(boolean hostIdentifier) {
		this.hostIdentifier = hostIdentifier;
	}
}
