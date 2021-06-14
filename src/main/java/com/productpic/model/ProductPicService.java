package com.productpic.model;


import java.math.BigDecimal;
import java.util.List;





public class ProductPicService {
	private ProductPicDAOinterface dao;

	public ProductPicService() {
		dao = new ProductPicJDBCDAO();
	}

	public ProductPicVO addProductPic(
		
			 Integer product_id,
			 byte[] product_pic
			) {

		ProductPicVO vo = new ProductPicVO();
		
		vo.setProduct_id(product_id);
		vo.setProduct_pic(product_pic);
		
//		vo.setProductID(productID);
//		vo.setOrderID(orderID);
//		vo.setProductBuyAmount(productBuyAmount);
//		vo.setOrderCost(orderCost);
//		vo.setOrderDiscount(orderDiscount);
		
		

		dao.add(vo);

		return vo;
	}

	public ProductPicVO updateProductPic(
			
			 Integer product_id,
			 byte[] product_pic
			) {

		ProductPicVO vo = new ProductPicVO();
		
		vo.setProduct_id(product_id);
		vo.setProduct_pic(product_pic);
		
//		vo.setProductID(productID);
//		vo.setOrderID(orderID);
//		vo.setProductBuyAmount(productBuyAmount);
//		vo.setOrderCost(orderCost);
//		vo.setOrderDiscount(orderDiscount);
		
		

		dao.update(vo);

		return vo;
	}

	public void deleteProductPic(Integer pic_id) {
		dao.delete(pic_id);
	}

	public ProductPicVO getOneProductPic(Integer pic_id) {
		return dao.findByPK(pic_id);
	}

	public List<ProductPicVO> getAll() {
		return dao.getAll();
	}
}
