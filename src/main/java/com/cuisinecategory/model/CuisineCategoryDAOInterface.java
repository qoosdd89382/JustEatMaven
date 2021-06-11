package com.cuisinecategory.model;

import java.util.List;
import java.util.Set;

public interface CuisineCategoryDAOInterface {

	public int insert(CuisineCategoryVO cuisineCategory);	// 回傳流水號
	public int update(CuisineCategoryVO cuisineCategory);	// 回傳筆數
	public int delete(int cuisineCategoryID);	// 回傳筆數
	public int delete(int[] cuisineCategoryIDs);	// 回傳筆數
//	public void deleteAll();
	public List<CuisineCategoryVO> getAll();
	public CuisineCategoryVO getOneByID(int cuisineCategoryID);
	public boolean isExist(String cuisineCategoryName);
//	public Set<CuisineCategoryVO> getManyByID(int cuisineCategoryID);	// 參考別人的表才需要	


}
