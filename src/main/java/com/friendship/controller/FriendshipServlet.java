package com.friendship.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import com.accountinfo.model.AccountInfoVO;
import com.friendship.model.FriendshipService;
import com.friendship.model.FriendshipVO;

	public class FriendshipServlet extends HttpServlet{
		private static final long serialVersionUID = 1L;

		public void doGet(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException {
			doPost(req, res);
		}
	
		public void doPost(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException {
			System.out.println("1");
			//設定編碼與確認回應
			req.setCharacterEncoding("UTF-8");
			String action = req.getParameter("action");
			
		if("getAccount_Friendship".equals(action)) {
			System.out.println("2");
			try {
				HttpSession session = req.getSession();
				String accountFriend = (String) session.getAttribute("accountMail");
				
				FriendshipService friendshipSvc = new FriendshipService();
				
				List<AccountInfoVO> friendshipVO = friendshipSvc.getAccountFriendByAccountMail(accountFriend);
				

				req.setAttribute("friendshipVO", friendshipVO); 
				String url = "/Account/AccountFriendPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			
			
			
			
			
		}
}