package com.admininfo.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.admininfo.model.AdminInfoService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/Dashboard/adminAjax.do")
public class AdminInfoAjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();

		Gson gson = new Gson();
		AdminInfoService adminSvc = new AdminInfoService();

		String action = req.getParameter("action");
		String adminIDs = req.getParameter("adminIDs");

		JSONArray jsonArr;
		try {
			jsonArr = new JSONArray(adminIDs);
			
			for (int i = 0; i < jsonArr.length(); i++) {
				int adminID = jsonArr.getInt(i);
				int state = 0;
				
				if ("promote".equals(action)) { state = 3; }
				else if ("demote".equals(action)) { state = 2; }
				else if ("suspend".equals(action)) { state = 0; }
				
				adminSvc.updateState(adminID, state);
				System.out.println(adminID + "更動成功");
			}
			out.print(jsonArr);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
