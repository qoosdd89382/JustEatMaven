package com.seller.model;


import java.io.Serializable;


public class SellerVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer sellerID;
	private Integer accountID; //FK
	private byte[] sellerCertification;
	private String sellerName;
	private String sellerMasterName;
	private String sellerPhone;
	private String sellerTaxnumber;
	private String sellerCompany;
	private String sellerAddress;

	
	




	public Integer getSellerID() {
		return sellerID;
	}







	public void setSellerID(Integer sellerID) {
		this.sellerID = sellerID;
	}







	public Integer getAccountID() {
		return accountID;
	}







	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}







	public byte[] getSellerCertification() {
		return sellerCertification;
	}







	public void setSellerCertification(byte[] sellerCertification) {
		this.sellerCertification = sellerCertification;
	}







	public String getSellerName() {
		return sellerName;
	}







	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}







	public String getSellerMasterName() {
		return sellerMasterName;
	}







	public void setSellerMasterName(String sellerMasterName) {
		this.sellerMasterName = sellerMasterName;
	}







	public String getSellerPhone() {
		return sellerPhone;
	}







	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}







	public String getSellerTaxnumber() {
		return sellerTaxnumber;
	}







	public void setSellerTaxnumber(String sellerTaxnumber) {
		this.sellerTaxnumber = sellerTaxnumber;
	}







	public String getSellerCompany() {
		return sellerCompany;
	}







	public void setSellerCompany(String sellerCompany) {
		this.sellerCompany = sellerCompany;
	}







	public String getSellerAddress() {
		return sellerAddress;
	}







	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}







	public SellerVO() {
		super();
	}




}
