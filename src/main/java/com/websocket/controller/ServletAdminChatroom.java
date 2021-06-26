package com.websocket.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/adminChat.do")
public class ServletAdminChatroom extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String accountID = req.getParameter("accountID");
		if (accountID == null) {
			RequestDispatcher dispatcher = 
					req.getRequestDispatcher("/Dashboard/Admin/adminChat.jsp");
			dispatcher.forward(req, res);
			return;
		}
		
//		int accountID = (int) req.getSession().getAttribute("loginUserID");
		req.setAttribute("accountID", accountID);
		
		RequestDispatcher dispatcher = 
				req.getRequestDispatcher("/Test/adminChat.jsp");
		dispatcher.forward(req, res);
	}
	
	
//	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		req.setCharacterEncoding("UTF-8");
//		
//		String enterFrom = req.getParameter("enterFrom");
//		
//		String loginUserID = req.getParameter("loginUserID");
////		String contactAdminID = req.getParameter("contactAdminID");
//		
//		String loginAdminID = req.getParameter("loginAdminID");
////		String contactUserID = req.getParameter("contactUserID");
//		
//		RequestDispatcher dispatcher = null;
//		
//		if (enterFrom == null || enterFrom.trim().length() == 0) {
//			RequestDispatcher failureView = req.getRequestDispatcher("/");
//			failureView.forward(req, res);
//			return;
//		} else if (enterFrom.contains("Dashboard")) {
////			req.setAttribute("adminID", loginAdminID);
////			req.setAttribute("userID", contactUserID);
//			dispatcher = req.getRequestDispatcher("/Dashboard/adminChat.jsp");
//		} else {
////			req.setAttribute("adminID", contactAdminID);
//			req.setAttribute("userID", loginUserID);
//			dispatcher = req.getRequestDispatcher("/Account/adminChat.jsp");
//		}
//		dispatcher.forward(req, res);
//	}

}
