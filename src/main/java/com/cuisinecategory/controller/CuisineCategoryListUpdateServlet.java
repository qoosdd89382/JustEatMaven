package com.cuisinecategory.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cuisinecategory.model.CuisineCategoryService;

@WebServlet("/CuisineCategoryListUpdateServlet")
public class CuisineCategoryListUpdateServlet extends HttpServlet  {
	private static final long serialVersionUID = 1L;

	public CuisineCategoryListUpdateServlet() {
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
		Integer test=  new Integer( (String) req.getParameter("categoryid"));
		CuisineCategoryService ccsve = new CuisineCategoryService();
		 ccsve.updateCategory(test, modifyname);
		
//		PrintWriter out = res.getWriter();
//		out.print("hello"+modifyname);
		
	}

}
