package com.eventinfo.model;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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

		EventInfoVO eventInfoVO = null;
		EventInfoJDBCDAO eventInfoDAO = new EventInfoJDBCDAO();
		byte[] picBuffer = null;

//		List<String> errorMsgs = new LinkedList<String>();
//		request.setAttribute("errorMsgs", errorMsgs);

		String eventID = request.getParameter("EventID");

		eventInfoVO = eventInfoDAO.findByPrimaryKey(Integer.parseInt(eventID));
		picBuffer = eventInfoVO.getEventPic();

		request.setAttribute("eventInfoVO", eventInfoVO);
		String url = "/Event/TestOneDisplayImage.jsp";
		RequestDispatcher success = request.getRequestDispatcher(url);
		out.write(picBuffer);
		success.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
