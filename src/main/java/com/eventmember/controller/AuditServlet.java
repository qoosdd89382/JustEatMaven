package com.eventmember.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.evaluatedmember.model.EvaluatedMemberService;
import com.eventmember.model.EventMemberService;
import com.eventmember.model.EventMemberVO;

/**
 * Servlet implementation class AuditServlet
 */
@WebServlet("/Event/audit.do")
public class AuditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String action = req.getParameter("action");
		int eventID = new Integer(req.getParameter("eventID"));
		int accountID = new Integer(req.getParameter("accountID"));
		
		if ("pass".equals(action)) {
//			int participationState = new Integer(req.getParameter("participationState"));
//			boolean isHostIdentifier = new boolean(req.getParameter("isHostIdentifier"));

			// 這裡寫 通過邏輯
			System.out.println("action" + action);
			System.out.println("accountID" + accountID);
			System.out.println("eventID" + eventID);
//			System.out.println("participationState" + participationState);
//			System.out.println("isHostIdentifier" + isHostIdentifier);

			EventMemberService evMemberSvc = new EventMemberService();
			EventMemberVO evVO = evMemberSvc.updateEventMember(2, eventID, accountID);
			if (evVO != null) {
				RequestDispatcher successView = req.getRequestDispatcher("/Event/Audit.jsp?eventID=" + eventID);
				successView.forward(req, res);
			}
		}

		if ("reject".equals(action)) {

			// 這裡寫 不通過邏輯
			System.out.println("action" + action);
			System.out.println("accountID" + accountID);
			System.out.println("eventID" + eventID);

			EventMemberService evMemberSvc = new EventMemberService();
			evMemberSvc.deleteEventMember(eventID, accountID);
			RequestDispatcher successView = req.getRequestDispatcher("/Event/Audit.jsp?eventID=" + eventID);
			successView.forward(req, res);
		}
	}
}
