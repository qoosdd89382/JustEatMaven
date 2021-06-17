package com.notice.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.notice.model.NoticeService;
import com.notice.model.NoticeVO;

public class NoticeServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		//設定編碼與確認回應
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
	if("getAccount_Notice".equals(action)) {

		try {
			HttpSession session = req.getSession();
			String accountNotice = (String) session.getAttribute("accountMail");
			
			NoticeService noticeSvc = new NoticeService();
			
			List<NoticeVO> noticeVO = noticeSvc.getAccountNoticeByAccountMail(accountNotice);
			

			req.setAttribute("noticeVO", noticeVO); 
			String url = "/Account/AccountNoticePage.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
		
	}
}