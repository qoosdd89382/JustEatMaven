package com.eventinfo.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.cuisinecategory.model.CuisineCategoryVO;
import com.eventcuisinecategory.model.EventCuisineCategoryService;
import com.eventcuisinecategory.model.EventCuisineCategoryVO;
import com.eventinfo.model.EventInfoService;
import com.eventinfo.model.EventInfoVO;
import com.recipeingredientunit.model.RecipeIngredientUnitVO;

@WebServlet("/Dashboard/Admin/EventInfo.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 3 * 1024 * 1024, maxRequestSize = 30 * 3 * 1024 * 1024)
public class EventInfoServletForDashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EventInfoServletForDashboard() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		// ========================後台活動列表=======================================================
		String action = request.getParameter("action");
		if ("getOneForUpdate".equals(action)) {
			Map<String, String> errorMsgs = new HashMap<String, String>();
			request.setAttribute("errorMsgs", errorMsgs);
			try {
				Integer eventID = new Integer(request.getParameter("eventID"));

				EventInfoService eventInfoSvc = new EventInfoService();
				EventInfoVO eventInfoVO = eventInfoSvc.getEventID(eventID);

				request.setAttribute("eventInfoVO", eventInfoVO);
				RequestDispatcher successView = request.getRequestDispatcher("/Dashboard/Admin/eventInfo.jsp");
				successView.forward(request, response);
			} catch (Exception e) {
				errorMsgs.put("UnknowErr", "發生錯誤，或您輸入的活動編號不存在！");
				e.printStackTrace();
//						RequestDispatcher failureView = request.getRequestDispatcher("/Dashboard/Admin/listALLEvent.jsp");
//						failureView.forward(request, response);
			}
		}
		
		System.out.println(action);
		if("delete".equals(action)) {
			Map<String, String> errorMsgs = new HashMap<String, String>();
			request.setAttribute("errorMsgs", errorMsgs);

			try {
				Integer eventID = Integer.parseInt(request.getParameter("eventID"));

				EventInfoService eventInfoSvc = new EventInfoService();
				eventInfoSvc.deleteEventInfo(eventID);
				RequestDispatcher successView = request.getRequestDispatcher("/Dashboard/Admin/eventInfo.jsp");
				successView.forward(request, response);
			} catch (Exception e) {
				errorMsgs.put("UnknowErr", "發生錯誤，或您輸入的活動編號不存在！");
				e.printStackTrace();
				RequestDispatcher failureView = request.getRequestDispatcher("/Dashboard/Admin/eventInfo.jsp");
				failureView.forward(request, response);
			}
		}
		
		
		if ("update".equals(action)) {
			Map<String, String> errorMsgs = new HashMap<String, String>();
			request.setAttribute("errorMsgs", errorMsgs);
			EventCuisineCategoryService eventCuisineCategoryService = new EventCuisineCategoryService();
			EventInfoVO eventInfoVO = new EventInfoVO();
			String groupType = request.getParameter("choose_type");
			String eventName = request.getParameter("event_name");
			String eventMember = request.getParameter("event_member");
			String eventStart = request.getParameter("event_start");
			String eventEnd = request.getParameter("event_end");
			String eventRegStart = request.getParameter("event_reg_start");
			String eventRegEnd = request.getParameter("event_reg_end");
			String groupCity = request.getParameter("city");
			String groupAddress = request.getParameter("address");
			String eventDescription = request.getParameter("event_description");
			String TimeRegex = "[0-9]{1,}[-]{1,1}[01]?[0-9]{1,1}[-]{1,1}[0-3]?[0-9]{1,1}[\\s]{1,1}[012]{1,1}[0-9]{1,1}[:]{1,1}[0-5]{1,1}[0-9]{1,1}[:]?[0-9]{0,2}";
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			LocalDateTime StartDateTime = null;
			LocalDateTime EndDateTime = null;
			LocalDateTime RegStartDateTime = null;
			LocalDateTime RegEndDateTime = null;

			System.out.println(groupCity);
			
			if (!eventStart.isEmpty()) {
				StartDateTime = LocalDateTime.parse(eventStart, formatter);
			}
			if (!eventEnd.isEmpty()) {
				EndDateTime = LocalDateTime.parse(eventEnd, formatter);
			}
			if (!eventRegStart.isEmpty()) {
				RegStartDateTime = LocalDateTime.parse(eventRegStart, formatter);
			}
			if (!eventRegEnd.isEmpty()) {
				RegEndDateTime = LocalDateTime.parse(eventRegEnd, formatter);
			}

			if (groupType == null || groupType.trim().length() == 0 || groupType.isEmpty()) {
				errorMsgs.put("GroupTypeIsNull", "請選擇其中一項類型");
			} else {
				eventInfoVO.setGroupType(Integer.parseInt(groupType));
			}

			if (eventName == null || eventName.trim().length() == 0 || eventName.isEmpty()) {
				errorMsgs.put("EventNameIsNull", "請輸入活動名稱");
			} else {
				eventInfoVO.setEventName(eventName);
			}

			if (eventMember == null || eventMember.trim().length() == 0 || eventMember.isEmpty()) {
				errorMsgs.put("EventMemberIsNull", "請輸入活動人數");
			} else if (eventMember.equals("0")) {
				errorMsgs.put("EventMemberMustBeGreaterThanZero", "活動人數必須大於0");
			} else {
				eventInfoVO.setEventCurrentCount(Integer.parseInt(eventMember));
			}

			if (eventStart == null || eventStart.trim().length() == 0 || eventStart.isEmpty()
					|| eventStart.trim() == "") {
				errorMsgs.put("EventStartTimeIsNull", "活動開始時間必須選擇");
			} else if (!eventStart.matches(TimeRegex)) {
				errorMsgs.put("EventStartTimeNotConform", "請輸入正確的活動開始時間");
			} else {
				eventInfoVO.setEventStartTime(Timestamp.valueOf(StartDateTime));
			}

			if (eventEnd == null || eventEnd.trim().length() == 0 || eventEnd.isEmpty() || eventEnd.trim() == "") {
				errorMsgs.put("EventEndTimeIsNull", "活動結束時間必須選擇");
			} else if (!eventEnd.matches(TimeRegex)) {
				errorMsgs.put("EventEndTimeNotConform", "請輸入正確的活動結束時間");
			} else {
				eventInfoVO.setEventEndTime(Timestamp.valueOf(EndDateTime));
			}

			if (eventRegStart == null || eventRegStart.trim().length() == 0 || eventRegStart.isEmpty()
					|| eventRegStart.trim() == "") {
				errorMsgs.put("EventRegStartTimeIsNull", "活動報名開始時間必須選擇");
			} else if (!eventRegStart.matches(TimeRegex)) {
				errorMsgs.put("EventRegStartTimeNotConform", "請輸入正確的活動報名開始時間");
			} else {
				eventInfoVO.setEventRegistartionStartTime(Timestamp.valueOf(RegStartDateTime));
			}

			if (eventRegEnd == null || eventRegEnd.trim().length() == 0 || eventRegEnd.isEmpty()
					|| eventRegEnd.trim() == "") {
				errorMsgs.put("EventRegEndTimeIsNull", "活動報名結束時間必須選擇");
			} else if (!eventRegEnd.matches(TimeRegex)) {
				errorMsgs.put("EventRegEndTimeNotConform", "請輸入正確的活動報名結束時間");
			} else {
				eventInfoVO.setEventRegistartionEndTime(Timestamp.valueOf(RegEndDateTime));
			}

			if (groupCity == null || groupCity.trim().length() == 0 || groupCity.isEmpty()) {
				errorMsgs.put("GroupCityIsNull", "不知道你怎麼弄到Null的????");
			} else {
				eventInfoVO.setGroupCity(groupCity);
			}

			if (groupAddress == null || groupAddress.trim().length() == 0 || groupAddress.isEmpty()) {
				errorMsgs.put("GroupAddressIsNull", "請輸入地址");
			} else {
				eventInfoVO.setGroupAddress(groupAddress);
			}

			if (eventDescription == null) {
				eventInfoVO.setEventDescription("");
			} else {
				eventInfoVO.setEventDescription(eventDescription);
			}

			if (StartDateTime != null && EndDateTime != null && RegStartDateTime != null && RegEndDateTime != null) {
				if (RegEndDateTime.isBefore(RegStartDateTime) || RegEndDateTime.isEqual(RegStartDateTime)) {
					errorMsgs.put("RegEndMustAfterRegStart", "活動報名結束日期 必須大於 活動報名開始日期");
				}
				if (StartDateTime.isBefore(RegEndDateTime)) {
					errorMsgs.put("StartMustAfterRegStart", "活動開始日期 必須大於 活動報名結束日期");
				}
				if (EndDateTime.isBefore(StartDateTime)) {
					errorMsgs.put("EndMustAfterStart", "活動結束日期 必須大於 活動開始日期");
				}
			}

			// ===================================編輯活動的圖片處理=============================
			Part eventPic = request.getPart("eventPic");
			byte[] eventPicToByte = null;
			EventInfoService eventInfoService = new EventInfoService();
			byte[] tempPic = eventInfoService.getEventID(Integer.parseInt(request.getParameter("eventID")))
					.getEventPic();
			if (eventPic.getSubmittedFileName().length() == 0 || eventPic.getContentType() == null) {
				eventPicToByte = tempPic;
				System.out.println("使用者沒有上傳活動圖片");
			} else if (!eventPic.getContentType().startsWith("image")) {
				errorMsgs.put("eventPicError", "上傳必須是image類型");
			} else if (eventPic.getSize() > 1024 * 1024 * 3) {
				errorMsgs.put("eventPicError", "image檔案需要小於3MB");
			} else {
				InputStream is = eventPic.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				eventPicToByte = new byte[bis.available()];
				bis.read(eventPicToByte);
				bis.close();
				is.close();
			}

			// =======================================類型============================
			//原本有的類型
			List<EventCuisineCategoryVO> orgEventCuisineCategoryVOs = eventCuisineCategoryService.getAllByEventID(Integer.parseInt(request.getParameter("eventID")));
			List<EventCuisineCategoryVO> eventCuisineCategoryVOs = new ArrayList<EventCuisineCategoryVO>();
			
			List<CuisineCategoryVO> cuisineCategoryVOs = new ArrayList<CuisineCategoryVO>();
			String cuisineCatIDStr = request.getParameter("cuisineCatID");
			Integer[] cuisineCatID = null;
			
			if (cuisineCatIDStr == null || cuisineCatIDStr.trim().length() == 0) {
				errorMsgs.put("cuisineCatError", "請至少選取一個類型");
			} else {
				String[] cuisineCatIDs = cuisineCatIDStr.trim().split(" ");
				System.out.println(cuisineCatIDs.length);
				cuisineCatID = new Integer[cuisineCatIDs.length];
				int i = 0;
				for (String cuisineCategoryID : cuisineCatIDs) {
					cuisineCatID[i] = new Integer(cuisineCategoryID);
					
					EventCuisineCategoryVO eventCuisineCategoryVO = new EventCuisineCategoryVO();
					eventCuisineCategoryVO.setCuisinecategoryID(cuisineCatID[i]);
					eventCuisineCategoryVO.setEventID(Integer.parseInt(request.getParameter("eventID")));
					eventCuisineCategoryVOs.add(eventCuisineCategoryVO);
					
					CuisineCategoryVO cuisineCategoryVO = new CuisineCategoryVO();
					cuisineCategoryVO.setCuisineCategoryID(new Integer(cuisineCategoryID));
					cuisineCategoryVOs.add(cuisineCategoryVO);
					i++;
				}				
			}

			//===========================================================================
			Collection<EventCuisineCategoryVO> orgEventCuisineCatCol = new ArrayList<EventCuisineCategoryVO>(
					orgEventCuisineCategoryVOs);
			Collection<EventCuisineCategoryVO> eventCuisineCatCol = new ArrayList<EventCuisineCategoryVO>(
					eventCuisineCategoryVOs);
			Set<EventCuisineCategoryVO> noRepeatEventCuisineCatVO = new HashSet<EventCuisineCategoryVO>();
			noRepeatEventCuisineCatVO.addAll(orgEventCuisineCategoryVOs);
			noRepeatEventCuisineCatVO.addAll(eventCuisineCategoryVOs);
			List<EventCuisineCategoryVO> eventCuisineCatDiff = new ArrayList<EventCuisineCategoryVO>(noRepeatEventCuisineCatVO);
			eventCuisineCatDiff.removeAll(eventCuisineCatCol); 
			List<EventCuisineCategoryVO> eventCuisineCatAdd = new ArrayList<EventCuisineCategoryVO>(noRepeatEventCuisineCatVO);
			eventCuisineCatAdd.removeAll(orgEventCuisineCatCol); 
			
			if (!errorMsgs.isEmpty()) {
				request.setAttribute("eventInfoVO", eventInfoVO);
				RequestDispatcher notfullView = request.getRequestDispatcher("/Dashboard/Admin/eventInfo.jsp");
				notfullView.forward(request, response);
			} else {
				EventInfoService eventInfoSvc = new EventInfoService();
				eventInfoSvc.updateEventInfoWithEventCuisineCat(Integer.parseInt(request.getParameter("eventID")), eventName,
						Integer.parseInt(eventMember), eventDescription, Integer.parseInt(groupType), groupCity,
						groupAddress, eventRegStart, eventRegEnd, eventStart, eventEnd, 1, eventPicToByte,eventCuisineCatDiff,eventCuisineCatAdd);
				
				RequestDispatcher detailView = request.getRequestDispatcher("/Dashboard/Admin/listAllEvent.jsp");
				detailView.forward(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
