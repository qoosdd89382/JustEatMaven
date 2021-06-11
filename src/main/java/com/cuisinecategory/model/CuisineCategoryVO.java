package com.cuisinecategory.model;

import java.io.Serializable;

public class CuisineCategoryVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer cuisineCategoryID;
	private String cuisineCategoryName;

	public CuisineCategoryVO() {
		super();
	}

//	public CuisineCategoryVO(Integer cuisineCategoryID, String cuisineCategoryName) {
//		super();
//		this.CuisineCategoryID = cuisineCategoryID;
//		this.CuisineCategoryName = cuisineCategoryName;
//	}

	public Integer getCuisineCategoryID() {
		return cuisineCategoryID;
	}

	public void setCuisineCategoryID(Integer cuisineCategoryID) {
		this.cuisineCategoryID = cuisineCategoryID;
	}

	public String getCuisineCategoryName() {
		return cuisineCategoryName;
	}

	public void setCuisineCategoryName(String cuisineCategoryName) {
		this.cuisineCategoryName = cuisineCategoryName;
	}
}
