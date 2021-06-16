package com.seller.model;


import java.util.List;



public interface SellerDAOinterface {
	public void add(SellerVO sellerVO);

	public void update(SellerVO SellerVO);

	public void delete(int sellerID);

	SellerVO findByPK(int sellerID);
	
	List<SellerVO> getAll();
}
