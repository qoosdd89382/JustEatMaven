package com.orderinfo.model;



import java.util.List;




public interface OrderInfoDAOinterface {
	public void add(OrderInfoVO orderInfoVO);

	public void update(OrderInfoVO orderInfoVO);

	public void delete(int orderID);

	OrderInfoVO findByPK(int orderID);
	
	List<OrderInfoVO> getAll();
}
