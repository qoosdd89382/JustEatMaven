package com.favoriterecipe.model;

import java.sql.Timestamp;
import java.util.List;

public class FavoriteRecipeService {
	private FavoriteRecipeDAOInterface dao;

	public FavoriteRecipeService() {
		dao = new FavoriteRecipeJDBCDAO();
	}

	public FavoriteRecipeVO insertFavoriteRecipe(Integer accountid, Integer recipeid) {
		FavoriteRecipeVO vo = new FavoriteRecipeVO();
		vo.setAccountID(accountid);
		vo.setFavRecipeID(recipeid);
		dao.insert(vo);
		return vo;
	}

	public FavoriteRecipeVO deleteFavoriteRecipe(Integer accountid, Integer recipeid) {
		FavoriteRecipeVO vo = new FavoriteRecipeVO();
		vo.setAccountID(accountid);
		vo.setFavRecipeID(recipeid);
		dao.delete(vo);
		return vo;

	}
	
	
	public Timestamp isExistFavoriteRecipe( Integer accountid, Integer recipeid) {
		FavoriteRecipeVO vo = new FavoriteRecipeVO();
		vo.setAccountID(accountid);
		vo.setFavRecipeID(recipeid);
		
		return dao.isExist(vo);
		
	}
	
	public int updateTimeFavoriteRecipe(Integer accountid, Integer recipeid) {
		FavoriteRecipeVO vo = new FavoriteRecipeVO();
		vo.setAccountID(accountid);
		vo.setFavRecipeID(recipeid);
		
		return dao.updateTime(vo);
		
	};
	
	
	public List<FavoriteRecipeVO> getAllByAccount(Integer accountID){
		return dao.getAllByAccount(accountID);
		
	}
	
	
	
	public List<FavoriteRecipeVO> getAllByRecipe(Integer favRecipeID){
		return dao.getAllByRecipe(favRecipeID);
		
	};
	
	

}
