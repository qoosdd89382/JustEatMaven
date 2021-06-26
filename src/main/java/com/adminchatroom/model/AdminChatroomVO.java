package com.adminchatroom.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class AdminChatroomVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String type;
	private String sender;
	private String receiver;
//	private String sendBy;
	private String time;
	private String message;
	
	public AdminChatroomVO(String type, String sender, String receiver, String time, String message) {
		super();
		this.type = type;
		this.sender = sender;
		this.receiver = receiver;
		this.time = time;
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
