package com.websocket.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Account/noticeWebSocket.do")
public class SocketServletNotify extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String accountID = req.getParameter("accountID");
		if (accountID != null) {
			RequestDispatcher dispatcher = 
					req.getRequestDispatcher("/Test/TestReadAlert.jsp");
			dispatcher.forward(req, res);
			return;
		}
		
	}

}
