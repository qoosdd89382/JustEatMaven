package com.orderinfo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;

	
//	  `order_id` int NOT NULL AUTO_INCREMENT COMMENT '�q��y����',
//	  `account_id` int NOT NULL COMMENT '�|���y����',
//	  `order_start_time` timestamp NOT NULL COMMENT '�q�沣�ͮɶ�',
//	  `order_complete_date` timestamp NOT NULL COMMENT '�q�槹���ɶ�',
//	  `order_type` int NOT NULL COMMENT '�q�檬�A',
//	  `order_total_cost` decimal(10,0) NOT NULL COMMENT '�`���B',
//	  `orderer` varchar(20) NOT NULL COMMENT '�q�ʤH',
//	  `orderer_phone` varchar(10) NOT NULL COMMENT '�q�ʤH���',
//	  `orderer_address` varchar(80) NOT NULL COMMENT '�q�ʤH�a�}',
//	  `order_payment` varchar(50) NOT NULL COMMENT '�I�ڸ�T',
//	  `orderer_card` varchar(16) NOT NULL COMMENT '�q�ʤH�H�Υd��',
//	  `receiver` varchar(20) NOT NULL COMMENT '����H',
//	  `receiver_phone` varchar(10) NOT NULL COMMENT '����H���',
//	  `receiver_address` varchar(80) NOT NULL COMMENT '����H�a�}',
//	  `order_ship_time` timestamp NOT NULL COMMENT '�X�f�ɶ�',
//	  `order_receive_time` timestamp NOT NULL COMMENT '���f�ɶ�',
//	  `order_deliver_id` int NOT NULL COMMENT '���y�s��',
	
	private Integer orderID;
	private Integer accountID;
	private Timestamp orderStartTime;
	private Timestamp orderCompleteDate;
	private Integer orderType;
	private BigDecimal  orderTotalCost;
	private String orderer;
	private String ordererPhone;
	private String ordererAddress;
	private String orderPayment;
	private String ordererCard;
	private String receiver;
	private String receiverPhone;
	private String receiverAddress;
	private Timestamp orderShipTime;
	private Timestamp orderReceiveTime;
	private Integer orderDeliverId;

	
	
	
	public OrderInfoVO() {
		super();
		// TODO Auto-generated constructor stub
	}




	public Integer getOrderID() {
		return orderID;
	}




	public void setOrderID(Integer orderID) {
		this.orderID = orderID;
	}




	public Integer getAccountID() {
		return accountID;
	}




	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}




	public Timestamp getOrderStartTime() {
		return orderStartTime;
	}




	public void setOrderStartTime(Timestamp orderStartTime) {
		this.orderStartTime = orderStartTime;
	}




	public Timestamp getOrderCompleteDate() {
		return orderCompleteDate;
	}




	public void setOrderCompleteDate(Timestamp orderCompleteDate) {
		this.orderCompleteDate = orderCompleteDate;
	}




	public Integer getOrderType() {
		return orderType;
	}




	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}




	public BigDecimal getOrderTotalCost() {
		return orderTotalCost;
	}




	public void setOrderTotalCost(BigDecimal orderTotalCost) {
		this.orderTotalCost = orderTotalCost;
	}




	public String getOrderer() {
		return orderer;
	}




	public void setOrderer(String orderer) {
		this.orderer = orderer;
	}




	public String getOrdererPhone() {
		return ordererPhone;
	}




	public void setOrdererPhone(String ordererPhone) {
		this.ordererPhone = ordererPhone;
	}




	public String getOrdererAddress() {
		return ordererAddress;
	}




	public void setOrdererAddress(String ordererAddress) {
		this.ordererAddress = ordererAddress;
	}




	public String getOrderPayment() {
		return orderPayment;
	}




	public void setOrderPayment(String orderPayment) {
		this.orderPayment = orderPayment;
	}




	public String getOrdererCard() {
		return ordererCard;
	}




	public void setOrdererCard(String ordererCard) {
		this.ordererCard = ordererCard;
	}




	public String getReceiver() {
		return receiver;
	}




	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}




	public String getReceiverPhone() {
		return receiverPhone;
	}




	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}




	public String getReceiverAddress() {
		return receiverAddress;
	}




	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}




	public Timestamp getOrderShipTime() {
		return orderShipTime;
	}




	public void setOrderShipTime(Timestamp orderShipTime) {
		this.orderShipTime = orderShipTime;
	}




	public Timestamp getOrderReceiveTime() {
		return orderReceiveTime;
	}




	public void setOrderReceiveTime(Timestamp orderReceiveTime) {
		this.orderReceiveTime = orderReceiveTime;
	}




	public Integer getOrderDeliverId() {
		return orderDeliverId;
	}




	public void setOrderDeliverId(Integer orderDeliverId) {
		this.orderDeliverId = orderDeliverId;
	}

	

//    constraint FK_OrderInfo_AccountInfo Foreign Key (account_id) References AccountInfo (account_id)

}
