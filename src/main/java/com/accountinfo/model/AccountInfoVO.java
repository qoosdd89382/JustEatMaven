package com.accountinfo.model;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Timestamp;

public class AccountInfoVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer accountID;
	private String accountMail;
	private String accountNickname;
	private String accountPassword;
	private Boolean accountState;
	private Integer accountLevel;
	private String accountName;
	private Integer accountGender;
	private Date accountBirth;
	private String accountPhone;
	private byte[] accountPic;
	private byte[] accountIDcardFront;
	private byte[] accountIDcardBack;
	private String accountText;
	private Timestamp accountRegisterTime;
	private String accountCode;
	
	public AccountInfoVO() {
		super();
	}
	
	public Integer getAccountID() {
		return accountID;
	}
	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}
	public String getAccountMail() {
		return accountMail;
	}
	public void setAccountMail(String accountMail) {
		this.accountMail = accountMail;
	}
	public String getAccountNickname() {
		return accountNickname;
	}
	public void setAccountNickname(String accountNickname) {
		this.accountNickname = accountNickname;
	}
	public String getAccountPassword() {
		return accountPassword;
	}
	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}
	public Boolean getAccountState() {
		return accountState;
	}
	public void setAccountState(Boolean accountState) {
		this.accountState = accountState;
	}
	public Integer getAccountLevel() {
		return accountLevel;
	}
	public void setAccountLevel(Integer accountLevel) {
		this.accountLevel = accountLevel;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Integer getAccountGender() {
		return accountGender;
	}
	public void setAccountGender(Integer accountGender) {
		this.accountGender = accountGender;
	}
	public Date getAccountBirth() {
		return accountBirth;
	}
	public void setAccountBirth(Date accountBirth) {
		this.accountBirth = accountBirth;
	}
	public String getAccountPhone() {
		return accountPhone;
	}
	public void setAccountPhone(String accountPhone) {
		this.accountPhone = accountPhone;
	}
	public byte[] getAccountPic() {
		return accountPic;
	}
	public void setAccountPic(byte[] accountPic) {
		this.accountPic = accountPic;
	}
	public byte[] getAccountIDcardFront() {
		return accountIDcardFront;
	}
	public void setAccountIDcardFront(byte[] accountIDcardFront) {
		this.accountIDcardFront = accountIDcardFront;
	}
	public byte[] getAccountIDcardBack() {
		return accountIDcardBack;
	}
	public void setAccountIDcardBack(byte[] accountIDcardBack) {
		this.accountIDcardBack = accountIDcardBack;
	}
	public String getAccountText() {
		return accountText;
	}
	public void setAccountText(String accountText) {
		this.accountText = accountText;
	}
	public Timestamp getAccountRegisterTime() {
		return accountRegisterTime;
	}
	public void setAccountRegisterTime(Timestamp accountRegisterTime) {
		this.accountRegisterTime = accountRegisterTime;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
}
