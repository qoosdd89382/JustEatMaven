package com.friendship.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

import com.accountinfo.model.AccountInfoService;
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

			//設定編碼與確認回應
			req.setCharacterEncoding("UTF-8");
			String action = req.getParameter("action");
			
		if("getAccount_Friendship".equals(action)) {

			try {
				HttpSession session = req.getSession();
				AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVOLogin");
				
				FriendshipService friendshipSvc = new FriendshipService();
				
				List<AccountInfoVO> friendshipVO = friendshipSvc.getAccountFriendByAccountID(accountInfoVO.getAccountID());
				

				req.setAttribute("friendshipVO", friendshipVO); 
				String url = "/Account/AccountFriendPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
			
	if ("removeFriendship".equals(action)) {
		
		System.out.println("收到 停止會員關係 請求");
		Map<String, String> errorMsgs = new HashMap<String, String>();
		req.setAttribute("errorMsgs", errorMsgs);
		
		FriendshipService friendshipSvc = new FriendshipService();
		
		try {
			//取得停權請求的ID
			Integer accountID = Integer.parseInt(req.getParameter("accountID"));
			Integer friendID = Integer.parseInt(req.getParameter("friendID"));
			System.out.println(accountID);
			friendshipSvc.removeAccountFriendByAccountID(accountID,friendID);
			
//			String url = "/Dashboard/Account/ListAllAccountInfoPage.jsp";
			String url = "/Account/AccountFriendPage.jsp";
			System.out.println("取消關係會員 完成準備轉交");
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}catch(Exception e) {
			e.printStackTrace();
			errorMsgs.put("UnexceptionError","無法取得資料");
			RequestDispatcher failureView = req
//					.getRequestDispatcher("/Dashboard/Account/ListAllAccountInfoPage.jsp");
					.getRequestDispatcher("/Account/AccountFriendPage.jsp");
			failureView.forward(req, res);
		}
	}
		
		
	}
}