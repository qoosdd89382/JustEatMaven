package com.orderproductitem.model;



import java.util.List;



public interface OrderProductItemDAOinterface {
	public void add(OrderProductItemVO orderProductItemVO);

	public void update(OrderProductItemVO orderProductItemVO);

	public void delete(int orderProductItemID);

	OrderProductItemVO findByPK(int orderProductItemID);
	
	List<OrderProductItemVO> getAll();
}
