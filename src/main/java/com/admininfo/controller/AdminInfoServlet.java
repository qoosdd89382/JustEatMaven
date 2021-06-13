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

import com.admininfo.model.AdminInfoVO;

@WebServlet("/Dashboard/admin.do")
public class AdminInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String action = req.getParameter("action");
		
		if ("insert".equals(action)) {
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
			
			String adminNickname = req.getParameter("adminNickname");
			String adminNicknameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]{3,45}$";
			if (adminNickname.trim() == null || adminMail.trim().length() == 0) {
				errorMsgs.add("暱稱不可空白！");
			} else if (!adminNickname.trim().matches(adminNicknameReg)) {
				errorMsgs.add("密碼須為3至45個英文字母或數字，或1個中文字至15個中文字！");
			}

			AdminInfoVO adminVO = new AdminInfoVO();
			adminVO.setAdminMail(adminMail);
			adminVO.setAdminPassword(adminPassword);
			adminVO.setAdminPassword(adminNickname);
			
			req.setAttribute("adminVO", adminVO);
			
			if(!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/Dashboard/addAdmin.jsp");
				failureView.forward(req, res);
				return;
			}
			
			// =========================================
			
			
			
			
		}
	}

}
