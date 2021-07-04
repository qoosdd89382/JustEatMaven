package com.announce.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.accountinfo.model.AccountInfoService;
import com.accountinfo.model.AccountInfoVO;
import com.announce.model.AnnounceService;
import com.announce.model.AnnounceVO;
import com.google.gson.Gson;
import com.notice.model.NoticeService;
import com.notice.model.NoticeVO;
import com.websocket.controller.WebSocketAnnounce;
import com.websocket.controller.WebSocketNotice;


@WebServlet("/Account/AnnouceTest")
public class AnnouceTest extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String annouceText = req.getParameter("annouceText");
		if (annouceText != null && annouceText.trim().length() != 0) {
			testSend(annouceText);
		}
		
	}
	
	
	public static void testSend(String annouceContext) {
		Gson gson = new Gson();
		
		AnnounceService announceSvc = new AnnounceService();
		AnnounceVO announceVO = announceSvc.addAnnounce(annouceContext);
		String message = gson.toJson(announceVO);
		
		WebSocketAnnounce annouceWS = new WebSocketAnnounce();
		annouceWS.onMessage(message);
	}
	
}
