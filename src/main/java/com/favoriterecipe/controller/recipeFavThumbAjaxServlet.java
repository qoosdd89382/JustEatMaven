package com.favoriterecipe.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.favoriterecipe.model.FavoriteRecipeService;
import com.favoriterecipe.model.FavoriteRecipeVO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.thumbsuprecipe.model.ThumbsupRecipeService;
import com.thumbsuprecipe.model.ThumbsupRecipeVO;

@WebServlet("/Recipe/recipeFavThumb.do")
public class recipeFavThumbAjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		
		Gson gson = new Gson();
		
		String action = req.getParameter("action");
		String accountID = req.getParameter("accountID");
		String recipeID = req.getParameter("recipeID");
		System.out.println("action: " + action);
		System.out.println("accountID: " + accountID);
		System.out.println("recipeID: " + recipeID);
		
//		if ((accountID == null || accountID.trim().length() == 0)
//				|| (recipeID == null || recipeID.trim().length() == 0) ) {
//			out.print("nullErr");
//			return;
//		}
		
		try {
			
			if ("insertFavoriteRecipe".equals(action)) {
				FavoriteRecipeService favRecipeSvc = new FavoriteRecipeService();
				
				if (favRecipeSvc.isExist(new Integer(accountID), new Integer(recipeID)) == null) {
					FavoriteRecipeVO favRecipeVO = favRecipeSvc.addFavoriteRecipe(new Integer(accountID), new Integer(recipeID));
					if (favRecipeVO != null) {
						JsonObject jsonObj = new JsonObject();
						int count = favRecipeSvc.countAllByRecipe(new Integer(recipeID));
						jsonObj.addProperty("status", "insertSucess");
						jsonObj.addProperty("accountID", accountID);
						jsonObj.addProperty("recipeID", recipeID);
						jsonObj.addProperty("count", count);
						out.print(jsonObj.toString());
					}
				} else {
					int row = favRecipeSvc.deleteFavoriteRecipe(new Integer(accountID), new Integer(recipeID));
					if (row > 0) {
						JsonObject jsonObj = new JsonObject();
						int count = favRecipeSvc.countAllByRecipe(new Integer(recipeID));
						jsonObj.addProperty("status", "deleteSucess");
						jsonObj.addProperty("accountID", accountID);
						jsonObj.addProperty("recipeID", recipeID);
						jsonObj.addProperty("count", count);
						out.print(jsonObj.toString());
					}
				}
				return;
			}
			
			if ("insertThumbsupRecipe".equals(action)) {
				ThumbsupRecipeService thmupRecipeSvc = new ThumbsupRecipeService();
				if (thmupRecipeSvc.isExist(new Integer(accountID), new Integer(recipeID)) == null) {
					ThumbsupRecipeVO thmupRecipeVO = thmupRecipeSvc.addThumbsupRecipe(new Integer(accountID), new Integer(recipeID));
					if (thmupRecipeVO != null) {
						JsonObject jsonObj = new JsonObject();
						int count = thmupRecipeSvc.countAllByRecipe(new Integer(recipeID));
						jsonObj.addProperty("status", "insertSucess");
						jsonObj.addProperty("accountID", accountID);
						jsonObj.addProperty("recipeID", recipeID);
						jsonObj.addProperty("count", count);
						out.print(jsonObj.toString());
					}
				} else {
					int row = thmupRecipeSvc.deleteThumbsupRecipe(new Integer(accountID), new Integer(recipeID));
					if (row > 0) {
						JsonObject jsonObj = new JsonObject();
						int count = thmupRecipeSvc.countAllByRecipe(new Integer(recipeID));
						jsonObj.addProperty("status", "deleteSucess");
						jsonObj.addProperty("accountID", accountID);
						jsonObj.addProperty("recipeID", recipeID);
						jsonObj.addProperty("count", count);
						out.print(jsonObj.toString());
					}
				}
				return;
			}
			
		} catch (Exception ignore) {}
		
	}

}