package com.index.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Index/search")
public class IndexSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		Map<String, String> errorMsgs = new HashMap<String, String>();
		req.setAttribute("errorMsgs", errorMsgs);
		
		String searchFor = req.getParameter("searchFor");
		String searchString = req.getParameter("searchString");
		
		if (searchFor == null || searchFor.trim().length() == 0) {
			errorMsgs.put("selectOptionErr", "請選擇搜尋類別");
		} 
		
		if (searchString == null || searchString.trim().length() == 0) {
			errorMsgs.put("emptySearchErr", "請輸入搜尋內容");
		}
		
		// 失敗，導回
		if (!errorMsgs.isEmpty()) {
			RequestDispatcher failureView = req.getRequestDispatcher("/");
			failureView.forward(req, res);
			return;
		}
		
		
		// 檢測成功，轉交
		String forwardString = null;
		if ("event".equals(searchFor)) {
			forwardString = "/Event/EventInfo.do?action=searchEventName&search=" + searchString;
		} else if ("recipe".equals(searchFor)) {
			forwardString = "/Recipe/recipe.do?action=search&recipeName=" + searchString;
		}
		
		if (forwardString != null) {
			RequestDispatcher successView = req.getRequestDispatcher(forwardString);
			successView.forward(req, res);
			return;
		}
		
	}

}
