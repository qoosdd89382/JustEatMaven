package com.favoriteproduct.model;



import java.util.List;



public interface FavoriteProductDAOinterface {
	public void add(FavoriteProductVO favoriteProductVO);
//
	public void update(FavoriteProductVO favoriteProductVO);

	public void delete(FavoriteProductVO favoriteProductVO);

	FavoriteProductVO findByPK(int account_id);
	
	List<FavoriteProductVO> getAll();
	
//	void add(Employee employee);
//	void update(Employee employee);
//	void delete(int empno);
//	Employee findByPK(int empno);
//	List<Employee> getAll();
	
}
