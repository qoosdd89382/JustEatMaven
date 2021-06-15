package com.orderproductitem.model;


import java.math.BigDecimal;
import java.util.List;





public class OrderProductItemService {
	private OrderProductItemDAOinterface dao;

	public OrderProductItemService() {
		dao = new OrderProductItemJDBCDAO();
	}

	public OrderProductItemVO addOrderProductItem(
			 Integer productID,
			 Integer orderID,
			 Integer productBuyAmount,
			 BigDecimal orderCost,
			 BigDecimal orderDiscount
			) {

		OrderProductItemVO vo = new OrderProductItemVO();
		vo.setProductID(productID);
		vo.setOrderID(orderID);
		vo.setProductBuyAmount(productBuyAmount);
		vo.setOrderCost(orderCost);
		vo.setOrderDiscount(orderDiscount);
		
		
//		empVO.setEname(ename);
//		empVO.setJob(job);
//		empVO.setHiredate(hiredate);
//		empVO.setSal(sal);
//		empVO.setComm(comm);
//		empVO.setDeptno(deptno);
		dao.add(vo);

		return vo;
	}

	public OrderProductItemVO updateOrderProductItem(
			 Integer productID,
			 Integer orderID,
			 Integer productBuyAmount,
			 BigDecimal orderCost,
			 BigDecimal orderDiscount
			) {

		OrderProductItemVO vo = new OrderProductItemVO();
		vo.setProductID(productID);
		vo.setOrderID(orderID);
		vo.setProductBuyAmount(productBuyAmount);
		vo.setOrderCost(orderCost);
		vo.setOrderDiscount(orderDiscount);
		
		
//		empVO.setEname(ename);
//		empVO.setJob(job);
//		empVO.setHiredate(hiredate);
//		empVO.setSal(sal);
//		empVO.setComm(comm);
//		empVO.setDeptno(deptno);
		dao.update(vo);

		return vo;
	}


	public void deleteOrderProductItem(Integer orderProductItemID) {
		dao.delete(orderProductItemID);
	}

	public OrderProductItemVO getOneOrderProductItem(Integer orderProductItemID) {
		return dao.findByPK(orderProductItemID);
	}

	public List<OrderProductItemVO> getAll() {
		return dao.getAll();
	}
}
