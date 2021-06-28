package com.notice.model;

import java.io.Serializable;
import java.sql.Timestamp;

//郭建巖2021.0521.1000
//set auto_increment_offset = 710001;
//set auto_increment_increment = 1;

//table Notice
public class NoticeVOString implements Serializable{
//	private static final long serialVersionUID = 1L;
	private String noticeID;
	private String accountID;
	private String noticeType;
	private String noticeText;
	private String noticeTime;
	private String noticeState;
	
}
