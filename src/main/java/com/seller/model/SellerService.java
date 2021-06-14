package com.seller.model;


import java.math.BigDecimal;
import java.util.List;




public class SellerService {
	private SellerDAOinterface dao;

	public SellerService() {
		dao = new SellerJDBCDAO();
	}

	public SellerVO addSeller(
			 Integer accountID ,
			 byte[] sellerCertification,
			 String sellerName,
			 String sellerMasterName,
			 String sellerPhone,
			 String sellerTaxnumber,
			 String sellerCompany,
			 String sellerAddress
			) {

		SellerVO vo = new SellerVO();
		vo.setAccountID(accountID);
		vo.setSellerCertification(sellerCertification);
		vo.setSellerName(sellerName);
		vo.setSellerMasterName(sellerMasterName);
		vo.setSellerPhone(sellerPhone);
		vo.setSellerTaxnumber(sellerTaxnumber);
		vo.setSellerCompany(sellerCompany);
		vo.setSellerAddress(sellerAddress);
		
//		vo.setProductID(productID);
//		vo.setOrderID(orderID);
//		vo.setProductBuyAmount(productBuyAmount);
//		vo.setOrderCost(orderCost);
//		vo.setOrderDiscount(orderDiscount);
		
		

		dao.add(vo);

		return vo;
	}

	public SellerVO updateSeller(
		
			 byte[] sellerCertification,
			 String sellerName,
			 String sellerMasterName,
			 String sellerPhone,
			 String sellerTaxnumber,
			 String sellerCompany,
			 String sellerAddress
			) {

		SellerVO vo = new SellerVO();

		vo.setSellerCertification(sellerCertification);
		vo.setSellerName(sellerName);
		vo.setSellerMasterName(sellerMasterName);
		vo.setSellerPhone(sellerPhone);
		vo.setSellerTaxnumber(sellerTaxnumber);
		vo.setSellerCompany(sellerCompany);
		vo.setSellerAddress(sellerAddress);
		
//		vo.setProductID(productID);
//		vo.setOrderID(orderID);
//		vo.setProductBuyAmount(productBuyAmount);
//		vo.setOrderCost(orderCost);
//		vo.setOrderDiscount(orderDiscount);
		
		

		dao.update(vo);

		return vo;
	}


	public void deleteSeller(Integer sellerID) {
		dao.delete(sellerID);
	}

	public SellerVO getOneOrderProductItem(Integer sellerID) {
		return dao.findByPK(sellerID);
	}

	public List<SellerVO> getAll() {
		return dao.getAll();
	}
}
