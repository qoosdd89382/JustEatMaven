package com.admininfo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Dashboard/admin.do")
public class AdminInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String action = req.getParameter("action");
		
		if ("addAdminInfo".equals(action)) {
			List<String> errorMsgs = new ArrayList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String adminMail = req.getParameter("adminMail");
			String adminMailReg = "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";
			if (adminMail.trim() == null || adminMail.trim().length() == 0) {
				errorMsgs.add("e-mail 不可為空！");
			} else if (!adminMail.trim().matches(adminMailReg)) {
				errorMsgs.add("e-mail 格式錯誤！");
			}
			
			String adminPassword = req.getParameter("adminPassword");
			String adminPasswordReg = "^[a-zA-Z0-9]{8,20}$";
			if (adminPassword.trim() == null || adminPassword.trim().length() == 0) {
				errorMsgs.add("密碼不可空白或只輸入空白鍵作為密碼！");
			} else if (!adminPassword.trim().matches(adminPasswordReg)) {
				errorMsgs.add("密碼須為8-20字英文大小寫與數字");
			}

			
			if(!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/Dashboard/addAdmin.jsp");
				failureView.forward(req, res);
				return;
			}
			
		}
	}

}
