package com.adminchatroom.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class AdminChatroomVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer messageID;	// Redis不用此欄位?
	private Integer accountID;
	private Integer adminID;
	private Boolean sendBy;
	private Timestamp sendTime;
	private String sendText;

	public AdminChatroomVO() {
		// TODO Auto-generated constructor stub
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

	public Integer getAdminID() {
		return adminID;
	}

	public void setAdminID(Integer adminID) {
		this.adminID = adminID;
	}

	public Boolean getSendBy() {
		return sendBy;
	}

	public void setSendBy(Boolean sendBy) {
		this.sendBy = sendBy;
	}

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public String getSendText() {
		return sendText;
	}

	public void setSendText(String sendText) {
		this.sendText = sendText;
	}
	
	

}
