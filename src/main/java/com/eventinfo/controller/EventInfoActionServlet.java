package com.eventinfo.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

import com.eventinfo.model.EventInfoService;
import com.eventinfo.model.EventInfoVO;

public class EventInfoActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EventInfoActionServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");

		if ("searchEventName".equals(action)) {

			List<String> errMsg = new LinkedList<String>();
			request.setAttribute("errMsg", errMsg);

			String str = request.getParameter("search");

			if (str == null || (str.trim().length() == 0)) {
				RequestDispatcher notthingView = request.getRequestDispatcher("/Event/EventList.jsp");
				notthingView.forward(request, response);
				return;
			}

			EventInfoService eventInfoSvc = new EventInfoService();
			List<EventInfoVO> list = eventInfoSvc.getEventName(str);

			if (list == null || list.isEmpty() || list.size() == 0) {
				errMsg.add("查無資料");
				RequestDispatcher notfindView = request.getRequestDispatcher("/Event/EventList.jsp");
				notfindView.forward(request, response);
				return;
			} else {
				request.setAttribute("listfind", list);
				RequestDispatcher findView = request.getRequestDispatcher("/Event/EventList.jsp");
				findView.forward(request, response);
			}
		}

		if ("searchEventGroupCity".equals(action)) {
			String[] position = request.getParameterValues("position");
			EventInfoService eventInfoSvc = new EventInfoService();
			List<EventInfoVO> allList = eventInfoSvc.getAll();
			List<EventInfoVO> saveList = null;
			for (int i = 0; i < position.length; i++) {
				if (position[i].equals("0")) {
					List<String> filterList = new ArrayList<String>();
					filterList.add("基隆市");
					filterList.add("新北市");
					filterList.add("台北市");
					filterList.add("桃園市");
					filterList.add("宜蘭縣");
					filterList.add("新竹縣");
					filterList.add("新竹市");

					saveList = allList.stream().filter((EventInfoVO vo) -> filterList.contains(vo.getGroupCity()))
							.collect(Collectors.toList());

					request.setAttribute("listfind", saveList);
					RequestDispatcher findView = request.getRequestDispatcher("/Event/EventList.jsp");
					findView.forward(request, response);
				} else if (position[i].equals("1")) {
					List<String> filterList = new ArrayList<String>();
					filterList.add("苗栗縣");
					filterList.add("台中市");
					filterList.add("彰化縣");
					filterList.add("南投縣");
					filterList.add("雲林縣");

					saveList = allList.stream().filter((EventInfoVO vo) -> filterList.contains(vo.getGroupCity()))
							.collect(Collectors.toList());

					request.setAttribute("listfind", saveList);
					RequestDispatcher findView = request.getRequestDispatcher("/Event/EventList.jsp");
					findView.forward(request, response);
				} else if (position[i].equals("2")) {
					List<String> filterList = new ArrayList<String>();
					filterList.add("澎湖縣");
					filterList.add("金門縣");
					filterList.add("連江縣");

					saveList = allList.stream().filter((EventInfoVO vo) -> filterList.contains(vo.getGroupCity()))
							.collect(Collectors.toList());

					request.setAttribute("listfind", saveList);
					RequestDispatcher findView = request.getRequestDispatcher("/Event/EventList.jsp");
					findView.forward(request, response);
				} else if (position[i].equals("3")) {
					List<String> filterList = new ArrayList<String>();
					filterList.add("嘉義縣");
					filterList.add("嘉義市");
					filterList.add("台南市");
					filterList.add("高雄市");
					filterList.add("屏東縣");

					saveList = allList.stream().filter((EventInfoVO vo) -> filterList.contains(vo.getGroupCity()))
							.collect(Collectors.toList());

					request.setAttribute("listfind", saveList);
					RequestDispatcher findView = request.getRequestDispatcher("/Event/EventList.jsp");
					findView.forward(request, response);
				} else {
					List<String> filterList = new ArrayList<String>();
					filterList.add("花蓮縣");
					filterList.add("台東縣");

					saveList = allList.stream().filter((EventInfoVO vo) -> filterList.contains(vo.getGroupCity()))
							.collect(Collectors.toList());

					request.setAttribute("listfind", saveList);
					RequestDispatcher findView = request.getRequestDispatcher("/Event/EventList.jsp");
					findView.forward(request, response);
				}
			}
		}
		if ("searchEventDate".equals(action)) {
			List<String> errMsg = new LinkedList<String>();
			request.setAttribute("errMsg", errMsg);

			EventInfoService eventInfoSvc = new EventInfoService();
			String dateStr = request.getParameter("date");
			List<EventInfoVO> list = eventInfoSvc.getEventDate(dateStr);
			if (list == null || list.isEmpty() || list.size() == 0) {
				errMsg.add("查無資料");
				RequestDispatcher notfindView = request.getRequestDispatcher("/Event/EventList.jsp");
				notfindView.forward(request, response);
				return;
			} else {
				request.setAttribute("listfind", list);
				RequestDispatcher findView = request.getRequestDispatcher("/Event/EventList.jsp");
				findView.forward(request, response);
			}
		}

		if ("新增菜色".equals(action)) {
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
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

			if (groupType == null || groupType.trim().length() == 0 || groupType.isEmpty()) {

			} else {
				eventInfoVO.setGroupType(Integer.parseInt(groupType));
			}

			if (eventName == null || eventName.trim().length() == 0 || eventName.isEmpty()) {

			} else {
				eventInfoVO.setEventName(eventName);
			}

			if (eventMember == null || eventMember.trim().length() == 0 || eventName.isEmpty()) {

			} else if (eventMember.equals("0")) {

			} else {
				eventInfoVO.setEventCurrentCount(Integer.parseInt(eventMember));
			}
			if (eventStart == null || eventStart.trim().length() == 0 || eventStart.isEmpty()
					|| eventStart.trim() == "") {

			}else {
				LocalDateTime StartDateTime = LocalDateTime.parse(eventStart, formatter);
				eventInfoVO.setEventStartTime(Timestamp.valueOf(StartDateTime));
			}
			if (eventEnd == null || eventEnd.trim().length() == 0 || eventEnd.isEmpty() || eventEnd.trim() == "") {
				
			}else {
				LocalDateTime EndDateTime = LocalDateTime.parse(eventEnd, formatter);
				eventInfoVO.setEventEndTime(Timestamp.valueOf(EndDateTime));
			}
			if (eventRegStart == null || eventRegStart.trim().length() == 0 || eventRegStart.isEmpty()
					|| eventRegStart.trim() == "") {
				
			}else {
				LocalDateTime RegStartDateTime = LocalDateTime.parse(eventRegStart, formatter);
				eventInfoVO.setEventRegistartionStartTime(Timestamp.valueOf(RegStartDateTime));
			}
			if (eventRegEnd == null || eventRegEnd.trim().length() == 0 || eventRegEnd.isEmpty()
					|| eventRegEnd.trim() == "") {
				
			}else {
				LocalDateTime RegEndDateTime = LocalDateTime.parse(eventRegEnd, formatter);
				eventInfoVO.setEventRegistartionEndTime(Timestamp.valueOf(RegEndDateTime));
			}
			if (groupCity == null || groupCity.trim().length() == 0 || groupCity.isEmpty()) {
				
			} else {
				eventInfoVO.setGroupCity(groupCity);
			}

			if (groupAddress == null || groupAddress.trim().length() == 0 || groupAddress.isEmpty()) {
			} else {
				eventInfoVO.setGroupAddress(groupAddress);
			}
			request.setAttribute("eventInfoVO", eventInfoVO);
			RequestDispatcher notfullView = request.getRequestDispatcher("/Event/InsertDish.jsp");
			notfullView.forward(request, response);
		}
		if ("邀請好友".equals(action)) {

		}
		if ("取消建立".equals(action)) {
			request.getRequestDispatcher("/Event/EventList.jsp").forward(request, response);
		}
		if ("確定建立".equals(action)) {
			Map<String, String> errorMsgs = new HashMap<String, String>();
			request.setAttribute("errorMsgs", errorMsgs);
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
			String TimeRegex = "[0-9]{1,}[-]{1,1}[01]?[0-9]{1,1}[-]{1,1}[0-3]?[0-9]{1,1}[\\s]{1,1}[012]{1,1}[0-9]{1,1}[:]{1,1}[0-5]{1,1}[0-9]{1,1}[:]?[0-9]{0,2}";
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

			System.out.println(eventStart);
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

			if (eventMember == null || eventMember.trim().length() == 0 || eventName.isEmpty()) {
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
				LocalDateTime StartDateTime = LocalDateTime.parse(eventStart, formatter);
				eventInfoVO.setEventStartTime(Timestamp.valueOf(StartDateTime));
			}

			if (eventEnd == null || eventEnd.trim().length() == 0 || eventEnd.isEmpty() || eventEnd.trim() == "") {
				errorMsgs.put("EventEndTimeIsNull", "活動結束時間必須選擇");
			} else if (!eventEnd.matches(TimeRegex)) {
				errorMsgs.put("EventEndTimeNotConform", "請輸入正確的活動結束時間");
			} else {
				LocalDateTime EndDateTime = LocalDateTime.parse(eventEnd, formatter);
				eventInfoVO.setEventEndTime(Timestamp.valueOf(EndDateTime));
			}

			if (eventRegStart == null || eventRegStart.trim().length() == 0 || eventRegStart.isEmpty()
					|| eventRegStart.trim() == "") {
				errorMsgs.put("EventRegStartTimeIsNull", "活動報名開始時間必須選擇");
			} else if (!eventRegStart.matches(TimeRegex)) {
				errorMsgs.put("EventRegStartTimeNotConform", "請輸入正確的活動報名開始時間");
			} else {
				LocalDateTime RegStartDateTime = LocalDateTime.parse(eventRegStart, formatter);
				eventInfoVO.setEventRegistartionStartTime(Timestamp.valueOf(RegStartDateTime));
			}

			if (eventRegEnd == null || eventRegEnd.trim().length() == 0 || eventRegEnd.isEmpty()
					|| eventRegEnd.trim() == "") {
				errorMsgs.put("EventRegEndTimeIsNull", "活動報名結束時間必須選擇");
			} else if (!eventRegEnd.matches(TimeRegex)) {
				errorMsgs.put("EventRegEndTimeNotConform", "請輸入正確的活動報名結束時間");
			} else {
				LocalDateTime RegEndDateTime = LocalDateTime.parse(eventRegEnd, formatter);
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

			if (errorMsgs != null) {
				request.setAttribute("eventInfoVO", eventInfoVO);
				RequestDispatcher notfullView = request.getRequestDispatcher("/Event/CreateEvent.jsp");
				notfullView.forward(request, response);
			}
		}
		if ("上一頁".equals(action)) {
			
			RequestDispatcher returnView = request.getRequestDispatcher("/Event/CreateEvent.jsp");
			returnView.forward(request, response);
		}
		if ("邀請好友".equals(action)) {

		}
		if ("邀請好友".equals(action)) {

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
