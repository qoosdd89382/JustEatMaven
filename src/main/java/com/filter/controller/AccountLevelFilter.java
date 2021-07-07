package com.filter.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.accountinfo.model.AccountInfoVO;

public class AccountLevelFilter implements Filter {

	private FilterConfig config;

	public void init(FilterConfig config) {
		this.config = config;
	}

	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// 【取得 session】
		HttpSession session = req.getSession();
		// 【從 session 判斷此user是否登入過】
		AccountInfoVO isLogin = (AccountInfoVO)session.getAttribute("nowAccount");
		Integer accountLevel = isLogin.getAccountLevel();
		//過濾會員的層級
		if (accountLevel < 3) {
			System.out.println(accountLevel);
			System.out.println("層級不符合");
			
			Map<String, String> errorMsgs = new HashMap<String, String>();
			errorMsgs.put("accountLevelFromEventError","(如需參加活動請向管理員申請將帳號升級為特權會員)");
			session.setAttribute("errorMsgsLevel", errorMsgs);
			
			//記錄前一個頁面的網址
			session.setAttribute("location",req.getServletPath());
			System.out.println("第二支濾器="+req.getServletPath());
			//轉接到登入頁面
			RequestDispatcher failureView = req
					.getRequestDispatcher("/Account/AccountInfoPage.jsp");
			failureView.forward(req, res);
//			res.sendRedirect(req.getContextPath() + "/Account/AccountInfoPage.jsp");

			return;
		} else {
			System.out.println(accountLevel);
			System.out.println("層級符合");
			chain.doFilter(request, response);
		}
	}
}
