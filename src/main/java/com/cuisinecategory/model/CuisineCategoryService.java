package com.cuisinecategory.model;

import java.util.List;

public class CuisineCategoryService {
	private CuisineCategoryDAOInterface dao;

	public CuisineCategoryService() {
		dao = new CuisineCategoryJDBCDAO();
	}

	public CuisineCategoryVO insertCategory(String cuisineCategoryName) {
		CuisineCategoryVO vo = new CuisineCategoryVO();
		vo.setCuisineCategoryName(cuisineCategoryName);
		vo.setCuisineCategoryID(dao.insert(vo));
		
		return vo;
	}
	
	public CuisineCategoryVO updateCategory(Integer cuisineCategoryID, String cuisineCategoryName) {
		CuisineCategoryVO vo = new CuisineCategoryVO();
		vo.setCuisineCategoryID(cuisineCategoryID);
		vo.setCuisineCategoryName(cuisineCategoryName);
		dao.update(vo);
		
		return vo;
	}
	
	public void deleteCategory(int cuisineCategoryID) {
		dao.delete(cuisineCategoryID);
	}

	public List<CuisineCategoryVO> getAll() {
		return dao.getAll();
	}
	
	public List<CuisineCategoryVO> getAll(String sqlStatement) {
		return dao.getAll(sqlStatement);
	}
	
	public CuisineCategoryVO getOneCategory(int cuisineCategoryID) {
		return dao.getOneByID(cuisineCategoryID);
	}
	
	public boolean isExist(String cuisineCategoryName) {
		return dao.isExist(cuisineCategoryName);
	}
	

}
