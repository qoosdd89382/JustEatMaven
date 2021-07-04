package com.cuisinecategory.controller;

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
import com.recipecuisinecategory.model.RecipeCuisineCategoryService;

@WebServlet("/Dashboard/Category/category.do")
public class CuisineCategoryAjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		Gson gson = new Gson();
		CuisineCategoryService catSvc = new CuisineCategoryService();
		RecipeCuisineCategoryService recipeCatSvc = new RecipeCuisineCategoryService();
		EventCuisineCategoryService eventCatSvc = new EventCuisineCategoryService();

		String action = req.getParameter("action");

		if ("delete".equals(action)) {
			String categoryID = req.getParameter("categoryID");
			System.out.println(categoryID);
			if (eventCatSvc.getALLByCuisineCategoryID(new Integer(categoryID)).size() != 0
					|| recipeCatSvc.getAllByCuisineCategory(new Integer(categoryID)).size() != 0) {
				out.print("此分類下有食譜或活動，不可刪除");
				System.out.println("此分類下有食譜或活動，不可刪除");
				return;
			}

			catSvc.deleteCategory(new Integer(categoryID));
			System.out.println(categoryID + "刪除成功");
			out.print("success");
			return;
		}

		if ("insert".equals(action)) {
			String categoryName = req.getParameter("categoryName");
			if (categoryName == null || categoryName.trim().length() == 0) {
				out.print("新增失敗,不得為空白");
				return;
			}

			String regEx = "^[A-z\\u4e00-\\u9fa5]*$";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(categoryName);
			if (!m.matches()) {
				out.print("新增失敗,不得含有特殊字符或數字");
				System.out.println("新增失敗,不得含有特殊字符或數字");
				return;
			} else if (catSvc.isExist(categoryName)) {
				out.print("已有相同名稱的食譜分類");
				System.out.println("已有相同名稱的食譜分類");
				return;
			}
			catSvc.insertCategory(categoryName);
			out.print("success");
			System.out.println("success");
		}

	}

}
