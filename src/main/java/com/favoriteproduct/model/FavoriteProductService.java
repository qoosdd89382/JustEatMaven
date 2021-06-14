package com.favoriteproduct.model;



import java.util.List;



public class FavoriteProductService {

	private FavoriteProductDAOinterface dao;

	public FavoriteProductService() {
		dao = new FavoriteProductJDBCDAO();
	}

	public FavoriteProductVO addFavoriteProduct(Integer account_id, Integer fav_product_id) {
		FavoriteProductVO vo = new FavoriteProductVO();
		vo.setAccount_id(account_id);
		vo.setFav_product_id(fav_product_id);

		dao.add(vo);
		return vo;

	}

	public FavoriteProductVO updateFavoriteProduct(Integer account_id, Integer fav_product_id) {
		FavoriteProductVO vo = new FavoriteProductVO();
		vo.setAccount_id(account_id);
		vo.setFav_product_id(fav_product_id);

		dao.update(vo);
		return vo;

	}

	public void deleteFavoriteProduct(FavoriteProductVO favoriteProductVO) {
		dao.delete(favoriteProductVO);
	}

//	public EmpVO getOneEmp(Integer empno) {
//		return dao.findByPrimaryKey(empno);
//	}
//	//	FavoriteProductVO findByPK(int account_id);

	public FavoriteProductVO findByPKFavoriteProduct(Integer account_id) {
		return dao.findByPK(account_id);
	}
//
	public List<FavoriteProductVO> getAll() {
		return dao.getAll();
	}

//	
//	List<FavoriteProductVO> getAll();



}
