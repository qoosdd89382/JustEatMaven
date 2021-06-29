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

import com.cuisinecategory.model.CuisineCategoryService;
import com.cuisinecategory.model.CuisineCategoryVO;

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
		 
		
			String regEx="^[A-z\\u4e00-\\u9fa5]*$";
			Pattern p=Pattern.compile(regEx);
			Matcher m=p.matcher(modifyname);
			if(!m.matches()) {
				PrintWriter out = res.getWriter();
				CuisineCategoryVO currentvo= ccsve.getOneCategory(test);
				String st= currentvo.getCuisineCategoryName();
				out.print("更新失敗,不得含有特殊字符或數字@"+st);
				System.out.println("含有特殊字符");
				
				
			}else {
				ccsve.updateCategory(test, modifyname);
				System.out.println("沒有特殊字符");
			}
	
		 
//		PrintWriter out = res.getWriter();
//		out.print("hello"+modifyname);
		
	}

}
