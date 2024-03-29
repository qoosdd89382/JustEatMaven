package com.eventchatroom.model;

public class EventChatroomMessage {
	private String type;
	private String senderID;
	private String eventID;
	private String senderName;
	private String message;
	private String sendTime;

	public EventChatroomMessage() {
		super();
	}
	
	public EventChatroomMessage(String type, String eventID,String senderID, String senderName,String message,String sendTime) {
		this.type = type;
		this.eventID = eventID;
		this.senderID = senderID;
		this.senderName = senderName;
		this.message = message;
		this.sendTime = sendTime;
	}

	public String getEventID() {
		return eventID;
	}

	public void setEventID(String eventID) {
		this.eventID = eventID;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderID() {
		return senderID;
	}

	public void setSenderID(String sender) {
		this.senderID = sender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
}