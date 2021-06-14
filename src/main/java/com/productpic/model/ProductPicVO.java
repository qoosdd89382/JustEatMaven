package com.productpic.model;


import java.io.Serializable;

public class ProductPicVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer pic_id; //delect ,update where
	private Integer product_id; //add
	private byte[] product_pic; //add
//	pic_id		Int not null comment "�ӫ~�Ϥ��y����" auto_increment,
//	product_id	Int not null comment "�ӫ~�y����",
//    ProductPic	MediumBLOB not null comment "�ӫ~�Ϥ�",
//    constraint PK_ProductPic Primary Key (pic_id, product_id),	-- "�i"�אּ�� PK�F�M���ШB�J���p���P�A�Ϥ��\�񶶧ǥѫe�ݳB�z
//    constraint FK_ProductPic_Product Foreign Key (product_id) References Product (product_id) on delete cascade
	public ProductPicVO() {
		super();
	}
	public Integer getPic_id() {
		return pic_id;
	}
	public void setPic_id(Integer pic_id) {
		this.pic_id = pic_id;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public byte[] getProduct_pic() {
		return product_pic;
	}
	public void setProduct_pic(byte[] product_pic) {
		this.product_pic = product_pic;
	}

	

}
