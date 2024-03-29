package com.evaluatedmember.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.evaluatedmember.model.EvaluatedMemberService;


@WebServlet("/Event/evaluatedMember.do")
public class EvaluatedMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		if ("giveScore".equals(action)) {	
			
			int eventID = new Integer(req.getParameter("eventID"));
			int giverID = new Integer(req.getParameter("giverID"));
			int accepterID = new Integer(req.getParameter("accepterID"));
			int giverScore = new Integer(req.getParameter("score"));
			
			System.out.println("action" + action);
			System.out.println("giverID" + giverID);
			System.out.println("eventID" + eventID);
			System.out.println("accepterID" + accepterID);
			System.out.println("giverScore" + giverScore);
			
			EvaluatedMemberService evMemberSvc = new EvaluatedMemberService();
			evMemberSvc.updateEvaluatedMember(accepterID, giverID, eventID, giverScore);
			
			RequestDispatcher successView = req.getRequestDispatcher("/Event/EvaluatedMember.jsp?eventID=" + eventID);
			successView.forward(req, res);
		
		
		
			}

		}
	}
