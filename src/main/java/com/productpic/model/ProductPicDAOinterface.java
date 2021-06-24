package com.productpic.model;


import java.util.List;




public interface ProductPicDAOinterface  {
	public void add(ProductPicVO productPicVO);

	public void update(ProductPicVO productPicVO);

	public void delete(int picID);

	ProductPicVO findByPK(int picID);
	
	List<ProductPicVO> getAll();
	
	List<ProductPicVO> getAllByProduct(int productID);

}
