package com.accountinfo.model;

import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestOnePic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TestOnePic() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletOutputStream out = response.getOutputStream();

		AccountInfoVO accountInfoVO = null;
		AccountInfoJDBCDAO accountInfoJDBCDAO = new AccountInfoJDBCDAO();
		byte[] picBuffer = null;

//		List<String> errorMsgs = new LinkedList<String>();
//		request.setAttribute("errorMsgs", errorMsgs);

		String accountID = request.getParameter("account_id");

		accountInfoVO = accountInfoJDBCDAO.findByPrimaryKey(Integer.parseInt(accountID));
		picBuffer = accountInfoVO.getAccountPic();

		request.setAttribute("accountInfoVO", accountInfoVO);
		String url = "/Account/TestOneDisplayImage.jsp";
		RequestDispatcher success = request.getRequestDispatcher(url);
		out.write(picBuffer);
		success.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}