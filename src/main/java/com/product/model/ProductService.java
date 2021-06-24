package com.product.model;



import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;





public class ProductService {
	private ProfuctDAOinterface dao;

	public ProductService() {
		dao = new ProductJDBCDAO();
	}

	public ProductVO addOrderProductItem(
			 Integer sellerID,
			 Integer productState,
			 Integer productPrice,
			 Integer productAmount,
			 String productUnit,
			 String productSpecification,
			 String productOrigin,
			 String productStorageMethod,
			 Timestamp productReleaseTime,
			 Timestamp productExpireTime,
			 Boolean productDiscount,
			 String productText,
			 byte[] productSgsPic
			) {

		ProductVO vo = new ProductVO();
		
		vo.setSellerID(sellerID);
		vo.setProductState(productState);
		vo.setProductPrice(productPrice);
		vo.setProductAmount(productAmount);
		vo.setProductUnit(productUnit);
		vo.setProductSpecification(productSpecification);
		vo.setProductOrigin(productOrigin);
		vo.setProductStorageMethod(productStorageMethod);
		vo.setProductReleaseTime(productReleaseTime);
		vo.setProductExpireTime(productExpireTime);
		vo.setProductDiscount(productDiscount);
		vo.setProductText(productText);
		vo.setProductSgsPic(productSgsPic);
//		vo.setProductID(productID);
//		vo.setOrderID(orderID);
//		vo.setProductBuyAmount(productBuyAmount);
//		vo.setOrderCost(orderCost);
//		vo.setOrderDiscount(orderDiscount);
		
		
//		empVO.setEname(ename);
//		empVO.setJob(job);
//		empVO.setHiredate(hiredate);
//		empVO.setSal(sal);
//		empVO.setComm(comm);
//		empVO.setDeptno(deptno);
		dao.add(vo);

		return vo;
	}

	public ProductVO updateOrderProductItem(
			 
			 Integer productState,
			 Integer productPrice,
			 Integer productAmount,
			 String productUnit,
			 String productSpecification,
			 String productOrigin,
			 String productStorageMethod,
			 Timestamp productReleaseTime,
			 Timestamp productExpireTime,
			 Boolean productDiscount,
			 String productText,
			 byte[] productSgsPic
			) {

		ProductVO vo = new ProductVO();
		
	
		vo.setProductState(productState);
		vo.setProductPrice(productPrice);
		vo.setProductAmount(productAmount);
		vo.setProductUnit(productUnit);
		vo.setProductSpecification(productSpecification);
		vo.setProductOrigin(productOrigin);
		vo.setProductStorageMethod(productStorageMethod);
		vo.setProductReleaseTime(productReleaseTime);
		vo.setProductExpireTime(productExpireTime);
		vo.setProductDiscount(productDiscount);
		vo.setProductText(productText);
		vo.setProductSgsPic(productSgsPic);
//		vo.setProductID(productID);
//		vo.setOrderID(orderID);
//		vo.setProductBuyAmount(productBuyAmount);
//		vo.setOrderCost(orderCost);
//		vo.setOrderDiscount(orderDiscount);
		
		
//		empVO.setEname(ename);
//		empVO.setJob(job);
//		empVO.setHiredate(hiredate);
//		empVO.setSal(sal);
//		empVO.setComm(comm);
//		empVO.setDeptno(deptno);
		dao.update(vo);

		return vo;
	}

	public void deleteProduct(Integer productID) {
		dao.delete(productID);
	}

	public ProductVO getOneOrderProductItem(Integer productID) {
		return dao.findByPK(productID);
	}

	public List<ProductVO> getAll() {
		return dao.getAll();
	}
	
	public List<ProductVO> getAllByClickCount() {
		return dao.getAllByClickCount();
	}
	
	
}
