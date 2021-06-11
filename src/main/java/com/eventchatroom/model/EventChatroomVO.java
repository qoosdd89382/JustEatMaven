package com.eventchatroom.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class EventChatroomVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer eventID;
	private Integer messageID;
	private Integer accountID;
	private Timestamp messageTime;
	private String chatText;
	
	public Integer getEventID() {
		return eventID;
	}
	
	public void setEventID(Integer eventID) {
		this.eventID = eventID;
	}
	
	public Integer getMessageID() {
		return messageID;
	}
	
	public void setMessageID(Integer messageID) {
		this.messageID = messageID;
	}
	
	public Integer getAccountID() {
		return accountID;
	}
	
	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}
	
	public Timestamp getMessageTime() {
		return messageTime;
	}
	
	public void setMessageTime(Timestamp messageTime) {
		this.messageTime = messageTime;
	}
	
	public String getChatText() {
		return chatText;
	}
	
	public void setChatText(String chatText) {
		this.chatText = chatText;
	}
}
