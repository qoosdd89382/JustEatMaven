package com.favoriteproduct.model;



import java.io.Serializable;

public class FavoriteProductVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer account_id;//FK
	private Integer fav_product_id;// (FK)
	

	public FavoriteProductVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getAccount_id() {
		return account_id;
	}

	public void setAccount_id(Integer account_id) {
		this.account_id = account_id;
	}

	public Integer getFav_product_id() {
		return fav_product_id;
	}

	public void setFav_product_id(Integer fav_product_id) {
		this.fav_product_id = fav_product_id;
	}

	
	
}
