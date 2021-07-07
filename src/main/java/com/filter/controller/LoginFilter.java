package com.filter.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

	private FilterConfig config;

	public void init(FilterConfig config) {
		this.config = config;
	}

	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// 【取得 session】
		HttpSession session = req.getSession();
		// 【從 session 判斷此user是否登入過】
		Object isLogin = session.getAttribute("accountInfoVOLogin");
//		Object isLogin = session.getAttribute("nowAccount");

		if (isLogin == null) {
			System.out.println("第一層驗證");
			//記錄前一個頁面的網址
			session.setAttribute("location",req.getServletPath());
			System.out.println("第一支濾器="+req.getServletPath());

			//轉接到登入頁面
			res.sendRedirect(req.getContextPath() + "/Account/AccountLoginPage.jsp");
			
			return;
		} else {
			chain.doFilter(request, response);
		}
	}
}