package com.ingredient.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cuisinecategory.model.CuisineCategoryService;
import com.ingredient.model.IngredientService;


@WebServlet("/IngredientListUpdateServlet")
public class IngredientListUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public IngredientListUpdateServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/plain");
		String modifyname = req.getParameter("modifyname");
		Integer test=  new Integer( (String) req.getParameter("ingredientid"));
		IngredientService isvc = new IngredientService();
		isvc.updateIngredient(test, modifyname);
		System.out.println(test+modifyname);
		//		CuisineCategoryService ccsve = new CuisineCategoryService();
//		 ccsve.updateCategory(test, modifyname);
	}

}
