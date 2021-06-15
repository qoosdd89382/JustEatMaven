package com.product.model;



import java.io.Serializable;
import java.sql.Timestamp;


public class ProductVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer productID;
	private Integer sellerID;
	private Integer productState;
	private Integer productPrice;
	private Integer productAmount;
	private String productUnit;
	private String productSpecification;
	private String productOrigin;
	private String productStorageMethod;
	private Timestamp productReleaseTime;//
	private Timestamp productExpireTime;
	private Boolean productDiscount;
	private String productText;
	private byte[] productSgsPic;

	public ProductVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getProductID() {
		return productID;
	}

	public void setProductID(Integer productID) {
		this.productID = productID;
	}

	public Integer getSellerID() {
		return sellerID;
	}

	public void setSellerID(Integer sellerID) {
		this.sellerID = sellerID;
	}

	public Integer getProductState() {
		return productState;
	}

	public void setProductState(Integer productState) {
		this.productState = productState;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(Integer productAmount) {
		this.productAmount = productAmount;
	}

	public String getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}

	public String getProductSpecification() {
		return productSpecification;
	}

	public void setProductSpecification(String productSpecification) {
		this.productSpecification = productSpecification;
	}

	public String getProductOrigin() {
		return productOrigin;
	}

	public void setProductOrigin(String productOrigin) {
		this.productOrigin = productOrigin;
	}

	public String getProductStorageMethod() {
		return productStorageMethod;
	}

	public void setProductStorageMethod(String productStorageMethod) {
		this.productStorageMethod = productStorageMethod;
	}

	public Timestamp getProductReleaseTime() {
		return productReleaseTime;
	}

	public void setProductReleaseTime(Timestamp productReleaseTime) {
		this.productReleaseTime = productReleaseTime;
	}

	public Timestamp getProductExpireTime() {
		return productExpireTime;
	}

	public void setProductExpireTime(Timestamp productExpireTime) {
		this.productExpireTime = productExpireTime;
	}

	public Boolean getProductDiscount() {
		return productDiscount;
	}

	public void setProductDiscount(Boolean productDiscount) {
		this.productDiscount = productDiscount;
	}

	public String getProductText() {
		return productText;
	}

	public void setProductText(String productText) {
		this.productText = productText;
	}

	public byte[] getProductSgsPic() {
		return productSgsPic;
	}

	public void setProductSgsPic(byte[] productSgsPic) {
		this.productSgsPic = productSgsPic;
	}

	
	

}
