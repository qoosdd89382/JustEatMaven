package com.notice.model;

import java.io.Serializable;
import java.sql.Timestamp;

//郭建巖2021.0521.1000
//set auto_increment_offset = 710001;
//set auto_increment_increment = 1;

//table Notice
public class NoticeVO implements Serializable{
//	private static final long serialVersionUID = 1L;
	private Integer noticeID;
	private Integer accountID;
	private String noticeType;
	private String noticeText;
	private Timestamp noticeTime;
	private Integer noticeState;
	
	public NoticeVO() {
		super();
	}
	
	public Integer getNoticeID() {
		return noticeID;
	}
	public void setNoticeID(Integer noticeID) {
		this.noticeID = noticeID;
	}
	public Integer getAccountID() {
		return accountID;
	}
	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}
	public String getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	public String getNoticeText() {
		return noticeText;
	}
	public void setNoticeText(String noticeText) {
		this.noticeText = noticeText;
	}
	public Timestamp getNoticeTime() {
		return noticeTime;
	}
	public void setNoticeTime(Timestamp noticeTime) {
		this.noticeTime = noticeTime;
	}
	public Integer getNoticeState() {
		return noticeState;
	}
	public void setNoticeState(Integer noticeState) {
		this.noticeState = noticeState;
	}

	public NoticeVO(Integer noticeID, Integer accountID, String noticeType, String noticeText, Timestamp noticeTime,
			Integer noticeState) {
		super();
		this.noticeID = noticeID;
		this.accountID = accountID;
		this.noticeType = noticeType;
		this.noticeText = noticeText;
		this.noticeTime = noticeTime;
		this.noticeState = noticeState;
	}
	
	
}
