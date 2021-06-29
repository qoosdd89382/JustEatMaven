package com.favoriterecipe.model;

import java.sql.Timestamp;
import java.util.List;

public class FavoriteRecipeService {
	private FavoriteRecipeDAOInterface dao;

	public FavoriteRecipeService() {
		dao = new FavoriteRecipeJDBCDAO();
	}

	public FavoriteRecipeVO addFavoriteRecipe(Integer accountid, Integer recipeid) {
		FavoriteRecipeVO vo = new FavoriteRecipeVO();
		vo.setAccountID(accountid);
		vo.setFavRecipeID(recipeid);
		dao.insert(vo);
		return vo;
	}

	public int deleteFavoriteRecipe(Integer accountid, Integer recipeid) {
		FavoriteRecipeVO vo = new FavoriteRecipeVO();
		vo.setAccountID(accountid);
		vo.setFavRecipeID(recipeid);
		
		return dao.delete(vo);
	}
	
	
	public Timestamp isExist( Integer accountid, Integer recipeid) {
		FavoriteRecipeVO vo = new FavoriteRecipeVO();
		vo.setAccountID(accountid);
		vo.setFavRecipeID(recipeid);
		
		return dao.isExist(vo);
		
	}
	
//	public int updateTimeFavoriteRecipe(Integer accountid, Integer recipeid) {
//		FavoriteRecipeVO vo = new FavoriteRecipeVO();
//		vo.setAccountID(accountid);
//		vo.setFavRecipeID(recipeid);
//		
//		return dao.updateTime(vo);
//		
//	};
	
	
//	
	
	public List<FavoriteRecipeVO> getAllByAccount(Integer accountID){
		return dao.getAllByAccount(accountID);
	}
	
	public List<FavoriteRecipeVO> getAllByRecipe(Integer favRecipeID){
		return dao.getAllByRecipe(favRecipeID);
	}
	
	public int countAllByRecipe(int favRecipeID) {
		return dao.countAllByRecipe(favRecipeID);
	}
	

}
