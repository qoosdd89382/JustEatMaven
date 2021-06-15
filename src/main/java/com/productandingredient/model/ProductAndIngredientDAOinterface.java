package com.productandingredient.model;


import java.util.List;


public interface ProductAndIngredientDAOinterface {
	
//	public void add(FavoriteProductVO favoriteProductVO);

//	public void update(FavoriteProductVO favoriteProductVO);
//
//	public void delete(FavoriteProductVO favoriteProductVO);
//
//	FavoriteProductVO findByPK(int account_id);
//	
//	List<FavoriteProductVO> getAll();
	
	
	
	public void add(ProductAndIngredientVO productAndIngredientVO);

	public void update(ProductAndIngredientVO productAndIngredientVO);

	public void delete(ProductAndIngredientVO productAndIngredientVO);

	ProductAndIngredientVO findByPK(int product_id);
	
	List<ProductAndIngredientVO> getAll();
}
