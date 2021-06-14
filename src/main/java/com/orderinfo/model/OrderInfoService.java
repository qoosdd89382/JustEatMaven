package com.orderinfo.model;



import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;




public class OrderInfoService {
	private OrderInfoDAOinterface dao;
	
	public OrderInfoService() {
		dao = new OrderInfoJDBCDAO();
	}
	
	public OrderInfoVO addOrderInfo(Integer account_id,
			 Timestamp orderStartTime,
	 Timestamp orderCompleteDate,
	 Integer orderType,
	 BigDecimal  orderTotalCost,
	 String orderer,
	 String ordererPhone,
	 String ordererAddress,
	 String orderPayment,
	 String ordererCard,
	 String receiver,
	 String receiverPhone,
	 String receiverAddress,
	 Timestamp orderShipTime,
	 Timestamp orderReceiveTime,
	 Integer orderDeliverId) {
		OrderInfoVO vo = new OrderInfoVO();
		
		vo.setAccountID(account_id);
		vo.setOrderStartTime(orderStartTime);
		vo.setOrderCompleteDate(orderCompleteDate);
		vo.setOrderType(orderType);
		vo.setOrderTotalCost(orderTotalCost);
		vo.setOrderer(orderer);
		vo.setOrdererPhone(ordererPhone);
		vo.setOrdererAddress(ordererAddress);
		vo.setOrderPayment(orderPayment);
		vo.setOrdererCard(ordererCard);
		vo.setReceiver(receiver);
		vo.setReceiverPhone(receiverPhone);
		vo.setReceiverAddress(receiverAddress);
		vo.setOrderShipTime(orderShipTime);
		vo.setOrderReceiveTime(orderReceiveTime);
		vo.setOrderDeliverId(orderDeliverId);
//		vo.setAccount_id(account_id);
//		vo.setFav_product_id(fav_product_id);

		dao.add(vo);
		return vo;

	}
	
	
	
	public OrderInfoVO updateOrderInfo(
			 Timestamp orderStartTime,
	 Timestamp orderCompleteDate,
	 Integer orderType,
	 BigDecimal  orderTotalCost,
	 String orderer,
	 String ordererPhone,
	 String ordererAddress,
	 String orderPayment,
	 String ordererCard,
	 String receiver,
	 String receiverPhone,
	 String receiverAddress,
	 Timestamp orderShipTime,
	 Timestamp orderReceiveTime,
	 Integer orderDeliverId) {
		OrderInfoVO vo = new OrderInfoVO();
		
		
		vo.setOrderStartTime(orderStartTime);
		vo.setOrderCompleteDate(orderCompleteDate);
		vo.setOrderType(orderType);
		vo.setOrderTotalCost(orderTotalCost);
		vo.setOrderer(orderer);
		vo.setOrdererPhone(ordererPhone);
		vo.setOrdererAddress(ordererAddress);
		vo.setOrderPayment(orderPayment);
		vo.setOrdererCard(ordererCard);
		vo.setReceiver(receiver);
		vo.setReceiverPhone(receiverPhone);
		vo.setReceiverAddress(receiverAddress);
		vo.setOrderShipTime(orderShipTime);
		vo.setOrderReceiveTime(orderReceiveTime);
		vo.setOrderDeliverId(orderDeliverId);
//		vo.setAccount_id(account_id);
//		vo.setFav_product_id(fav_product_id);

		dao.update(vo);
		return vo;

	}
	
	
	public void deleteOrderInfo(Integer orderID) {
		dao.delete(orderID);
	}

	public OrderInfoVO getOneOrderInfo(Integer orderID) {
		return dao.findByPK(orderID);
	}

	public List<OrderInfoVO> getAll() {
		return dao.getAll();
	}
	
	
}
