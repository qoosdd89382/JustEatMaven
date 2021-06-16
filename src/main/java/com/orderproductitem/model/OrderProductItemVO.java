package com.orderproductitem.model;



import java.math.BigDecimal;

public class OrderProductItemVO {
	private static final long serialVersionUID = 1L;
	
//	CREATE TABLE `OrderProductItem` (
//			  `OrderProductItem_id` int NOT NULL AUTO_INCREMENT COMMENT '�q����Ӭy����',
//			  `product_id` int DEFAULT NULL COMMENT '�ӫ~�y����',
//			  `order_id` int DEFAULT NULL COMMENT '�q��y����',
//			  `product_buy_amount` int NOT NULL COMMENT '�ӫ~�ʶR�ƶq',
//			  `order_cost` decimal(10,0) NOT NULL COMMENT '���ت��B',
//			  `order_discount` decimal(3,2) NOT NULL DEFAULT '1.00' COMMENT '�ӫ~�馩',
//			  PRIMARY KEY (`OrderProductItem_id`),
//			  UNIQUE KEY `UK_OrderProductItem` (`product_id`,`order_id`),
//			  KEY `FK_OrderProductItem_OrderInfo` (`order_id`),
//			  CONSTRAINT `FK_OrderProductItem_OrderInfo` FOREIGN KEY (`order_id`) REFERENCES `OrderInfo` (`order_id`),
//			  CONSTRAINT `FK_OrderProductItem_Product` FOREIGN KEY (`product_id`) REFERENCES `Product` (`product_id`) ON DELETE SET NULL
	
	
	private Integer orderProductItemID;
	private Integer productID;//	product_id				Int comment "�ӫ~�y����", FK
	private Integer orderID;//    order_id				Int comment "�q��y����", FK
	private Integer productBuyAmount;
	private BigDecimal orderCost;
	private BigDecimal orderDiscount;
//	private Integer orderCostDiscount;
//    constraint PK_OrderProductItem Primary Key (OrderProductItem_id),
//    constraint UK_OrderProductItem Unique key (product_id, order_id),
//    constraint FK_OrderProductItem_Product Foreign Key (product_id) References Product (product_id) on delete set null,
//    constraint FK_OrderProductItem_OrderInfo Foreign Key (order_id) References OrderInfo (order_id)	-- �`�N�G�q��L�k�R���A�u�����
	
	
	
	
	public OrderProductItemVO() {
		super();
		// TODO Auto-generated constructor stub
	}




	public Integer getOrderProductItemID() {
		return orderProductItemID;
	}




	public void setOrderProductItemID(Integer orderProductItemID) {
		this.orderProductItemID = orderProductItemID;
	}




	public Integer getProductID() {
		return productID;
	}




	public void setProductID(Integer productID) {
		this.productID = productID;
	}




	public Integer getOrderID() {
		return orderID;
	}




	public void setOrderID(Integer orderID) {
		this.orderID = orderID;
	}




	public Integer getProductBuyAmount() {
		return productBuyAmount;
	}




	public void setProductBuyAmount(Integer productBuyAmount) {
		this.productBuyAmount = productBuyAmount;
	}




	public BigDecimal getOrderCost() {
		return orderCost;
	}




	public void setOrderCost(BigDecimal orderCost) {
		this.orderCost = orderCost;
	}




	public BigDecimal getOrderDiscount() {
		return orderDiscount;
	}




	public void setOrderDiscount(BigDecimal orderDiscount) {
		this.orderDiscount = orderDiscount;
	}
	
	
	
}
