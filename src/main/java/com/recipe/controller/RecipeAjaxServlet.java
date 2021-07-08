package com.recipe.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cuisinecategory.model.CuisineCategoryService;
import com.eventcuisinecategory.model.EventCuisineCategoryService;
import com.google.gson.Gson;
import com.recipe.model.RecipeService;
import com.recipecuisinecategory.model.RecipeCuisineCategoryService;

@WebServlet("/Dashboard/Recipe/recipeAjax.do")
public class RecipeAjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		Gson gson = new Gson();
		RecipeService recipeSvc = new RecipeService(); 

		String action = req.getParameter("action");

		if ("deleteForAjax".equals(action)) {
			String recipeID = req.getParameter("recipeID");
			System.out.println(recipeID);

			recipeSvc.deleteRecipe(new Integer(recipeID));
			System.out.println(recipeID + "刪除成功");
			out.print("success");
			return;
		}

	}

}
