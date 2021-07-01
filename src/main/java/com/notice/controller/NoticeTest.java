package com.notice.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.accountinfo.model.AccountInfoVO;
import com.google.gson.Gson;
import com.notice.model.NoticeService;
import com.notice.model.NoticeVO;
import com.websocket.controller.WebSocketNotice;


@WebServlet("/Account/NoticeTest")
public class NoticeTest extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		testSend();
		
	}
	
//	public static void testFormat() {
//		NoticeService noticeSvc = new NoticeService();
//		Gson gson = new Gson();
//		
//		List<NoticeVO> historyData = noticeSvc.getAllByAccountID(new Integer(100001));
//		String historyMsg = gson.toJson(historyData);
//		System.out.println(historyMsg);
//	}
	
	public static void testSend() {
		Gson gson = new Gson();
		
		NoticeService noticeSvc = new NoticeService();
		NoticeVO noticeVO = noticeSvc.addNotice(100001, "系統", "歡迎註冊成為本站會員！");
		String message = gson.toJson(noticeVO);
		
		WebSocketNotice noticeWS = new WebSocketNotice();
		noticeWS.onMessage(message);
	}
}
