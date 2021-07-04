package com.websocket.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.accountinfo.model.AccountInfoService;
import com.accountinfo.model.AccountInfoVO;
import com.google.gson.Gson;

@WebServlet("/Dashboard/getAccountInfo.do")
public class AdminChatroomGetAccountInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = res.getWriter();
		AccountInfoService accountSvc = new AccountInfoService();
		Gson gson = new Gson();
		
		String accountID = req.getParameter("accountID");
		if (accountID == null) {
			out.print("");
			return;
		}
		AccountInfoVO accountVO = accountSvc.selectOneAccountInfo(new Integer(accountID));
		String message = gson.toJson(accountVO);
		out.print(message);
	}

}
