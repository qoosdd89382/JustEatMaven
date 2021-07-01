package com.ingredient.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cuisinecategory.model.CuisineCategoryService;
import com.eventcuisinecategory.model.EventCuisineCategoryService;
import com.google.gson.Gson;
import com.ingredient.model.IngredientService;
import com.recipecuisinecategory.model.RecipeCuisineCategoryService;
import com.recipeingredientunit.model.RecipeIngredientUnitService;

@WebServlet("/Dashboard/Ingredient/ingredient.do")
public class IngredientAjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();

		Gson gson = new Gson();
		RecipeIngredientUnitService recipeIngSvc = new RecipeIngredientUnitService();
		IngredientService ingSvc = new IngredientService();
		
		String action = req.getParameter("action");

		if ("delete".equals(action)) {
			String ingredientID = req.getParameter("ingredientID");
System.out.println(ingredientID);
			if (recipeIngSvc.getAllByIngredient(new Integer(ingredientID)).size() != 0) {
				out.print("此食材有食譜使用，不可刪除");
				System.out.println("此分類有食譜使用，不可刪除");
				return;
			}

			ingSvc.deleteIngredient(new Integer(ingredientID));
			System.out.println(ingredientID + "刪除成功");
			out.print("success");
			return;
		}

		if ("insert".equals(action)) {
			String ingredientName = req.getParameter("ingredientName");
			if (ingredientName == null || ingredientName.trim().length() == 0) {
				out.print("新增失敗,不得為空白");
				return;
			}

			String regEx = "^[A-z\\u4e00-\\u9fa5]*$";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(ingredientName);
			if (!m.matches()) {
				out.print("新增失敗,不得含有特殊字符或數字");
				System.out.println("新增失敗,不得含有特殊字符或數字");
				return;
			} else if (ingSvc.isExist(ingredientName)) {
				out.print("已有相同名稱的食譜分類");
				System.out.println("已有相同名稱的食譜分類");
				return;
			}
			ingSvc.addIngredient(ingredientName);
			out.print("success");
			System.out.println("success");
		}

	}

}
