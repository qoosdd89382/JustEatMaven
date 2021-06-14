package com.product.model;



import java.util.List;



public interface ProfuctDAOinterface {
	public void add(ProductVO productVO);

	public void update(ProductVO productVO);

	public void delete(int productID);

	ProductVO findByPK(int productID);
	
	List<ProductVO> getAll();
}
