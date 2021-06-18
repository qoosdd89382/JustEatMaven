package com.announce.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.announce.model.AnnounceService;
import com.announce.model.AnnounceVO;

public class AnnounceServlet extends HttpServlet{
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
		
	if("getAccount_Announce".equals(action)) {

		try {
			AnnounceService announceSvc = new AnnounceService();
			
			List<AnnounceVO> announceVO = announceSvc.getAnnounce();
			

			req.setAttribute("announceVO", announceVO); 
			String url = "/Account/AccountAnnouncePage.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
		
	}
}
