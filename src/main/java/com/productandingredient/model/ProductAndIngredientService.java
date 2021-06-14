package com.productandingredient.model;


import java.util.List;




public class ProductAndIngredientService {
	private ProductAndIngredientDAOinterface dao;

	public ProductAndIngredientService() {
		dao = new ProductAndIngredientJDBCDAO();
	}

	public ProductAndIngredientVO addProductAndIngredient(
			 Integer product_id,
			 Integer ingredient_id
			) {

		ProductAndIngredientVO vo = new ProductAndIngredientVO();
		vo.setProduct_id(product_id);
		vo.setIngredient_id(ingredient_id);
//		empVO.setEname(ename);
//		empVO.setJob(job);
//		empVO.setHiredate(hiredate);
//		empVO.setSal(sal);
//		empVO.setComm(comm);
//		empVO.setDeptno(deptno);
		dao.add(vo);

		return vo;
	}

	public ProductAndIngredientVO updateProductAndIngredient(
			 Integer product_id,
			 Integer ingredient_id
			) {

		ProductAndIngredientVO vo = new ProductAndIngredientVO();
		vo.setProduct_id(product_id);
		vo.setIngredient_id(ingredient_id);
//		empVO.setEname(ename);
//		empVO.setJob(job);
//		empVO.setHiredate(hiredate);
//		empVO.setSal(sal);
//		empVO.setComm(comm);
//		empVO.setDeptno(deptno);
		dao.update(vo);

		return vo;
	}

	public void deleteFavoriteProduct(ProductAndIngredientVO productAndIngredientVO) {
		dao.delete(productAndIngredientVO);
	}

//	public EmpVO getOneEmp(Integer empno) {
//		return dao.findByPrimaryKey(empno);
//	}
//	//	FavoriteProductVO findByPK(int account_id);

	public ProductAndIngredientVO findByPKProductAndIngredient(Integer product_id) {
		return dao.findByPK(product_id);
	}
//
	public List<ProductAndIngredientVO> getAll() {
		return dao.getAll();
	}
}
