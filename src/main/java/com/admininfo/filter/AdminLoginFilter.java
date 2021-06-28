package com.admininfo.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(
		filterName = "adminLoginFilter", 
		dispatcherTypes = { 
				javax.servlet.DispatcherType.REQUEST,
				javax.servlet.DispatcherType.FORWARD, 
				javax.servlet.DispatcherType.INCLUDE, 
				javax.servlet.DispatcherType.ERROR,
				javax.servlet.DispatcherType.ASYNC
			}, 
		urlPatterns = {
				"/Dashboard/index.jsp",
				"/Dashboard/Admin/*"
			}
//		, servletNames = "" 
)
public class AdminLoginFilter implements Filter {

	private FilterConfig filterConfig = null;

	public void init(FilterConfig config) {
		this.filterConfig = config;
	}

	public void destroy() {
		filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		HttpServletRequest req = (HttpServletRequest) request; // 上面是父介面，轉成子型別
		HttpServletResponse res = (HttpServletResponse) response; // 上面是父介面，轉成子型別
		
		HttpSession session = req.getSession();

		Object loginAdminID = session.getAttribute("loginAdminID");
		if (loginAdminID == null) {
			session.setAttribute("adminLocation", req.getRequestURI());
			res.sendRedirect(req.getContextPath() + "/Dashboard/login.jsp");
			return;
		} else {
			chain.doFilter(request, response);	// 將控制權轉交給下一個過濾器
		}
	}

}
