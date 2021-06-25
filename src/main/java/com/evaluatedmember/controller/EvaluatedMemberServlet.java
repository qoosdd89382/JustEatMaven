package com.evaluatedmember.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.DateFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.accountinfo.model.AccountInfoService;
import com.dish.model.DishService;
import com.dish.model.DishVO;
import com.dishandingredient.model.DishAndIngredientVO;
import com.dishandingredient.model.DishandingredientService;
import com.eventinfo.model.EventInfoService;
import com.eventinfo.model.EventInfoVO;
import com.eventmember.model.EventMemberService;
import com.eventmember.model.EventMemberVO;
import com.ingredient.model.IngredientService;
import com.ingredient.model.IngredientVO;

public class EvaluatedMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EvaluatedMemberServlet() {
		String action = String.getParameter("action");
		if ("giveScore".equals(action)) {	

			int eventID = new Integer(req.getParameter("eventID"));
			int giverID = new Integer(req.getParameter("giverID"));
			int accepterID = new Integer(req.getParameter("accepterID"));
			int giverScore = new Integer(req.getParameter("score"));
			
			EvaluatedMemberService evMemberSvc = new EvaluatedMemberService();
			evMemberSvc.addEvaluatedMember(giverID, accepterID, giverScore);
			
			RequestDispatcher successView = req.getRequestDispatcher("/Event/EventMember.jsp?eventID=" + eventID);
			successView.foward(req, res);
		}


	