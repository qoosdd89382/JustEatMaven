package com.admininfo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import com.admininfo.model.AdminInfoService;
import com.admininfo.model.AdminInfoVO;

@WebServlet("/Dashboard/AdminLoginHandler")
public class AdminLoginHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected AdminInfoVO allowUser(String adminMail, String adminPassword) {
		AdminInfoService adminSvc = new AdminInfoService();
		AdminInfoVO adminVO = adminSvc.getOneAdmin(adminMail);
		if (adminVO != null) {
			String hashedPassword = adminVO.getAdminPassword();
			if (BCrypt.checkpw(adminPassword, hashedPassword)) {
				return adminVO;	// 匹配
			}
		}
		return null;	// 無此e-mail
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("Big5");
		res.setContentType("text/html; charset=Big5");

		String adminMail = req.getParameter("adminMail");
		String adminPassword = req.getParameter("adminPassword");

		Map<String, String> errorMsgs = new HashMap<String, String>();
		req.setAttribute("errorMsgs", errorMsgs);
		
		AdminInfoVO adminVO = allowUser(adminMail, adminPassword);
		if (adminVO == null) {
			errorMsgs.put("loginAllowErr", "帳號或密碼錯誤");
			req.setAttribute("adminMail", adminMail);
			RequestDispatcher failureView = 
					req.getRequestDispatcher("/Dashboard/login.jsp");
			failureView.forward(req, res);
			return;
		} else {
			HttpSession session = req.getSession();
			session.setAttribute("loginAdminID", adminVO.getAdminID());
			try {
				String location = (String) session.getAttribute("location");
				if (location != null) {
					session.removeAttribute("location");
					res.sendRedirect(location);
					return;
				}
			} catch (Exception ignored) {
			}

			res.sendRedirect(req.getContextPath() + "/Dashboard/index.jsp");
		}
	}
}