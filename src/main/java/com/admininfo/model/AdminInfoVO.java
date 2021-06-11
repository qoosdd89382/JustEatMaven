package com.admininfo.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class AdminInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer adminID;
	private String adminMail;
	private String adminNickname;
	private String adminPassword;
	private byte[] adminPic;
	private Timestamp adminRegisterTime;
	private Boolean adminState;
	public Integer getAdminID() {
		return adminID;
	}
	public void setAdminID(Integer adminID) {
		this.adminID = adminID;
	}
	public String getAdminMail() {
		return adminMail;
	}
	public void setAdminMail(String adminMail) {
		this.adminMail = adminMail;
	}
	public String getAdminNickname() {
		return adminNickname;
	}
	public void setAdminNickname(String adminNickname) {
		this.adminNickname = adminNickname;
	}
	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	public byte[] getAdminPic() {
		return adminPic;
	}
	public void setAdminPic(byte[] adminPic) {
		this.adminPic = adminPic;
	}
	public Timestamp getAdminRegisterTime() {
		return adminRegisterTime;
	}
	public void setAdminRegisterTime(Timestamp adminRegisterTime) {
		this.adminRegisterTime = adminRegisterTime;
	}
	public Boolean getAdminState() {
		return adminState;
	}
	public void setAdminState(Boolean adminState) {
		this.adminState = adminState;
	}
	
	
}
