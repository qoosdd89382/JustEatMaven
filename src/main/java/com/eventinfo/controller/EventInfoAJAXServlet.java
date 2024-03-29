package com.eventinfo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cuisinecategory.model.CuisineCategoryService;
import com.dish.model.DishService;
import com.dish.model.DishVO;
import com.eventcuisinecategory.model.EventCuisineCategoryService;
import com.eventcuisinecategory.model.EventCuisineCategoryVO;
import com.eventinfo.model.EventInfoService;
import com.eventinfo.model.EventInfoVO;
import com.eventmember.model.EventMemberService;


@WebServlet("/Event/EventInfoAJAXServlet.do")
public class EventInfoAJAXServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EventInfoAJAXServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String eventID = request.getParameter("eventID");
		
		EventInfoService eventInfoService = new EventInfoService();
		EventInfoVO eventInfoVO = eventInfoService.getEventID(Integer.parseInt(eventID));
		DishService dishService = new DishService();
		List<DishVO> dishList = dishService.getEventID(Integer.parseInt(eventID));
		EventCuisineCategoryService eventCuisineCategoryService = new EventCuisineCategoryService();
		List<EventCuisineCategoryVO> eventCuisineCategoryList = eventCuisineCategoryService.getAllByEventID(Integer.parseInt(eventID));
		CuisineCategoryService categoryService = new CuisineCategoryService();
		EventMemberService eventMemberSvc = new EventMemberService();
		int currentMember = eventMemberSvc.getMemberCount(Integer.parseInt(eventID));
		JSONObject jsonData = new JSONObject();
		JSONArray dishArray = new JSONArray();
		JSONArray cuisineCatArray = new JSONArray();
		
		try {
			jsonData.put("eventID", eventInfoVO.getEventID());
			jsonData.put("groupType", eventInfoVO.getGroupType());
			jsonData.put("eventName",eventInfoVO.getEventName());
			jsonData.put("eventMember", eventInfoVO.getEventCurrentCount());
			jsonData.put("currentMemberCount", currentMember);
			jsonData.put("eventRegStart", eventInfoVO.getEventRegistartionStartTime());
			jsonData.put("eventRegEnd", eventInfoVO.getEventRegistartionEndTime());
			jsonData.put("eventStart", eventInfoVO.getEventStartTime());
			jsonData.put("eventEnd", eventInfoVO.getEventEndTime());
			jsonData.put("city", eventInfoVO.getGroupCity());
			jsonData.put("address", eventInfoVO.getGroupAddress());
			jsonData.put("eventDescription", eventInfoVO.getEventDescription());
			for(EventCuisineCategoryVO eventCuisineCategoryVO:eventCuisineCategoryList) {
				cuisineCatArray.put(categoryService.getOneCategory(eventCuisineCategoryVO.getCuisinecategoryID()).getCuisineCategoryName()+" ");
			}
			for(DishVO dishVO:dishList) {
				dishArray.put("<span class='border'>"+dishVO.getDishName()+"</span>");
			}
			jsonData.put("cuisineCatName", cuisineCatArray);
			jsonData.put("dishName", dishArray);
			out.print(jsonData.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
