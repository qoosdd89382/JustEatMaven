package com.eventinfo.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dish.model.DishVO;
import com.dishandingredient.model.DishAndIngredientVO;
import com.eventcuisinecategory.model.EventCuisineCategoryService;
import com.eventcuisinecategory.model.EventCuisineCategoryVO;
import com.eventmember.model.EventMemberVO;

public class EventInfoService {
	private EventInfoDAOinterface dao;

	public EventInfoService() {
		dao = new EventInfoJDBCDAO();
	}

	public EventInfoVO addEventInfo(String eventName, Integer eventCurrentCount, String eventDescription,
			Integer groupType, String groupCity, String groupAddress, String eventRegistartionStartTime,
			String eventRegistartionEndTime, String eventStartTime, String eventEndTime, Integer eventState,
			byte[] eventPic) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime localRegistartionStartTime = LocalDateTime.parse(eventRegistartionStartTime, formatter);
		LocalDateTime localRegistartionEndTime = LocalDateTime.parse(eventRegistartionEndTime, formatter);
		LocalDateTime localStartTime = LocalDateTime.parse(eventStartTime, formatter);
		LocalDateTime localEndTime = LocalDateTime.parse(eventEndTime, formatter);
		Timestamp timestampRegistartionStartTime = Timestamp.valueOf(localRegistartionStartTime);
		Timestamp timestampRegistartionEndTime = Timestamp.valueOf(localRegistartionEndTime);
		Timestamp timestampStartTime = Timestamp.valueOf(localStartTime);
		Timestamp timestampEndTime = Timestamp.valueOf(localEndTime);

		EventInfoVO eventInfoVO = new EventInfoVO();
		eventInfoVO.setEventName(eventName);
		eventInfoVO.setEventCurrentCount(eventCurrentCount);
		eventInfoVO.setEventDescription(eventDescription);
		eventInfoVO.setGroupType(groupType);
		eventInfoVO.setGroupCity(groupCity);
		eventInfoVO.setGroupAddress(groupAddress);
		eventInfoVO.setEventRegistartionStartTime(timestampRegistartionStartTime);
		eventInfoVO.setEventRegistartionEndTime(timestampRegistartionEndTime);
		eventInfoVO.setEventStartTime(timestampStartTime);
		eventInfoVO.setEventEndTime(timestampEndTime);
		eventInfoVO.setEventState(eventState);
		eventInfoVO.setEventPic(eventPic);
		dao.insert(eventInfoVO);

		return eventInfoVO;
	}

	public EventInfoVO updateEventInfo(Integer eventID, String eventName, Integer eventCurrentCount,
			String eventDescription, Integer groupType, String groupCity, String groupAddress,
			String eventRegistartionStartTime, String eventRegistartionEndTime, String eventStartTime,
			String eventEndTime, Integer eventState, byte[] eventPic) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime localRegistartionStartTime = LocalDateTime.parse(eventRegistartionStartTime, formatter);
		LocalDateTime localRegistartionEndTime = LocalDateTime.parse(eventRegistartionEndTime, formatter);
		LocalDateTime localStartTime = LocalDateTime.parse(eventStartTime, formatter);
		LocalDateTime localEndTime = LocalDateTime.parse(eventEndTime, formatter);
		Timestamp timestampRegistartionStartTime = Timestamp.valueOf(localRegistartionStartTime);
		Timestamp timestampRegistartionEndTime = Timestamp.valueOf(localRegistartionEndTime);
		Timestamp timestampStartTime = Timestamp.valueOf(localStartTime);
		Timestamp timestampEndTime = Timestamp.valueOf(localEndTime);

		EventInfoVO eventInfoVO = new EventInfoVO();
		eventInfoVO.setEventID(eventID);
		eventInfoVO.setEventName(eventName);
		eventInfoVO.setEventCurrentCount(eventCurrentCount);
		eventInfoVO.setEventDescription(eventDescription);
		eventInfoVO.setGroupType(groupType);
		eventInfoVO.setGroupCity(groupCity);
		eventInfoVO.setGroupAddress(groupAddress);
		eventInfoVO.setEventRegistartionStartTime(timestampRegistartionStartTime);
		eventInfoVO.setEventRegistartionEndTime(timestampRegistartionEndTime);
		eventInfoVO.setEventStartTime(timestampStartTime);
		eventInfoVO.setEventEndTime(timestampEndTime);
		eventInfoVO.setEventState(eventState);
		eventInfoVO.setEventPic(eventPic);
		dao.update(eventInfoVO);

		return eventInfoVO;
	}

	public EventInfoVO updateEventInfo(Integer eventID,Integer eventState) {


		EventInfoVO eventInfoVO = new EventInfoVO();
		eventInfoVO.setEventID(eventID);
		eventInfoVO.setEventState(eventState);
		dao.updateEventState(eventInfoVO);

		return eventInfoVO;
	}
	
	public EventInfoVO updateEventViewCount(Integer eventID) {


		EventInfoVO eventInfoVO = dao.findByPrimaryKey(eventID);
		int count = eventInfoVO.getEventViewCount();
		eventInfoVO.setEventViewCount(++count);
		System.out.println(eventInfoVO.getEventViewCount());
		dao.updateEventViewCount(eventInfoVO);

		return eventInfoVO;
	}
	
	public EventInfoVO updateEventInfoWithEventCuisineCat(Integer eventID, String eventName, Integer eventCurrentCount,
			String eventDescription, Integer groupType, String groupCity, String groupAddress,
			String eventRegistartionStartTime, String eventRegistartionEndTime, String eventStartTime,
			String eventEndTime, Integer eventState, byte[] eventPic,List<EventCuisineCategoryVO> delCatList,List<EventCuisineCategoryVO> addCatList) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime localRegistartionStartTime = LocalDateTime.parse(eventRegistartionStartTime, formatter);
		LocalDateTime localRegistartionEndTime = LocalDateTime.parse(eventRegistartionEndTime, formatter);
		LocalDateTime localStartTime = LocalDateTime.parse(eventStartTime, formatter);
		LocalDateTime localEndTime = LocalDateTime.parse(eventEndTime, formatter);
		Timestamp timestampRegistartionStartTime = Timestamp.valueOf(localRegistartionStartTime);
		Timestamp timestampRegistartionEndTime = Timestamp.valueOf(localRegistartionEndTime);
		Timestamp timestampStartTime = Timestamp.valueOf(localStartTime);
		Timestamp timestampEndTime = Timestamp.valueOf(localEndTime);

		EventInfoVO eventInfoVO = new EventInfoVO();
		eventInfoVO.setEventID(eventID);
		eventInfoVO.setEventName(eventName);
		eventInfoVO.setEventCurrentCount(eventCurrentCount);
		eventInfoVO.setEventDescription(eventDescription);
		eventInfoVO.setGroupType(groupType);
		eventInfoVO.setGroupCity(groupCity);
		eventInfoVO.setGroupAddress(groupAddress);
		eventInfoVO.setEventRegistartionStartTime(timestampRegistartionStartTime);
		eventInfoVO.setEventRegistartionEndTime(timestampRegistartionEndTime);
		eventInfoVO.setEventStartTime(timestampStartTime);
		eventInfoVO.setEventEndTime(timestampEndTime);
		eventInfoVO.setEventState(eventState);
		eventInfoVO.setEventPic(eventPic);
		dao.updateWithCuisineCat(eventInfoVO, delCatList, addCatList);

		return eventInfoVO;
	}
	
	public void deleteEventInfo(Integer eventID) {
		dao.delete(eventID);
	}

	public EventInfoVO getEventID(Integer eventID) {
		return dao.findByPrimaryKey(eventID);
	}

	public List<EventInfoVO> getEventName(String eventName) {
		return dao.findByName(eventName);
	}
	
	public List<EventInfoVO> getEventDate(String eventDate) {
		return dao.findByStartDate(eventDate);
	}
	
	public List<EventInfoVO> getAll() {
		return dao.getAll();
	}

	
	public List<EventInfoVO> getSomeNew() {
		return dao.getSomeNew();
	}

	//連鎖菜色
	public void addDishByEventInfo(String eventName, Integer eventCurrentCount, String eventDescription,
			Integer groupType, String groupCity, String groupAddress, String eventRegistartionStartTime,
			String eventRegistartionEndTime, String eventStartTime, String eventEndTime, Integer eventState,
			byte[] eventPic, String dishName,Integer accountID) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime localRegistartionStartTime = LocalDateTime.parse(eventRegistartionStartTime, formatter);
		LocalDateTime localRegistartionEndTime = LocalDateTime.parse(eventRegistartionEndTime, formatter);
		LocalDateTime localStartTime = LocalDateTime.parse(eventStartTime, formatter);
		LocalDateTime localEndTime = LocalDateTime.parse(eventEndTime, formatter);
		Timestamp timestampRegistartionStartTime = Timestamp.valueOf(localRegistartionStartTime);
		Timestamp timestampRegistartionEndTime = Timestamp.valueOf(localRegistartionEndTime);
		Timestamp timestampStartTime = Timestamp.valueOf(localStartTime);
		Timestamp timestampEndTime = Timestamp.valueOf(localEndTime);

		EventInfoVO eventInfoVO = new EventInfoVO();
		eventInfoVO.setEventName(eventName);
		eventInfoVO.setEventCurrentCount(eventCurrentCount);
		eventInfoVO.setEventDescription(eventDescription);
		eventInfoVO.setGroupType(groupType);
		eventInfoVO.setGroupCity(groupCity);
		eventInfoVO.setGroupAddress(groupAddress);
		eventInfoVO.setEventRegistartionStartTime(timestampRegistartionStartTime);
		eventInfoVO.setEventRegistartionEndTime(timestampRegistartionEndTime);
		eventInfoVO.setEventStartTime(timestampStartTime);
		eventInfoVO.setEventEndTime(timestampEndTime);
		eventInfoVO.setEventState(eventState);
		eventInfoVO.setEventPic(eventPic);

		List<DishVO> dishList = new ArrayList<DishVO>();
		DishVO dishVO = new DishVO();
		dishVO.setDishName(dishName);
		dishVO.setAccountID(accountID); // 再討論
		dishList.add(dishVO);
		dao.insertWithDish(eventInfoVO, dishList);

	}
	
	//雙重連鎖
	public EventInfoVO addDishAndIngredientByEventInfo(String eventName, Integer eventCurrentCount, String eventDescription,
			Integer groupType, String groupCity, String groupAddress, String eventRegistartionStartTime,
			String eventRegistartionEndTime, String eventStartTime, String eventEndTime, Integer eventState,
			byte[] eventPic, String[] dishNames,Integer[][] IngIDs,Integer[] cuisinecategoryID,Integer accountID) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime localRegistartionStartTime = LocalDateTime.parse(eventRegistartionStartTime, formatter);
		LocalDateTime localRegistartionEndTime = LocalDateTime.parse(eventRegistartionEndTime, formatter);
		LocalDateTime localStartTime = LocalDateTime.parse(eventStartTime, formatter);
		LocalDateTime localEndTime = LocalDateTime.parse(eventEndTime, formatter);
		Timestamp timestampRegistartionStartTime = Timestamp.valueOf(localRegistartionStartTime);
		Timestamp timestampRegistartionEndTime = Timestamp.valueOf(localRegistartionEndTime);
		Timestamp timestampStartTime = Timestamp.valueOf(localStartTime);
		Timestamp timestampEndTime = Timestamp.valueOf(localEndTime);

		EventInfoVO eventInfoVO = new EventInfoVO();
		eventInfoVO.setEventName(eventName);
		eventInfoVO.setEventCurrentCount(eventCurrentCount);
		eventInfoVO.setEventDescription(eventDescription);
		eventInfoVO.setGroupType(groupType);
		eventInfoVO.setGroupCity(groupCity);
		eventInfoVO.setGroupAddress(groupAddress);
		eventInfoVO.setEventRegistartionStartTime(timestampRegistartionStartTime);
		eventInfoVO.setEventRegistartionEndTime(timestampRegistartionEndTime);
		eventInfoVO.setEventStartTime(timestampStartTime);
		eventInfoVO.setEventEndTime(timestampEndTime);
		eventInfoVO.setEventState(eventState);
		eventInfoVO.setEventPic(eventPic);

		List<DishVO> dishList = new ArrayList<DishVO>();
		
		for(int i =0;i<dishNames.length;i++) {
			DishVO dishVO = new DishVO();
			dishVO.setDishName(dishNames[i]);
			dishVO.setAccountID(accountID); // 再討論
			dishList.add(dishVO);
		}
		
		List<DishAndIngredientVO> dishAndIngredientList = new ArrayList<DishAndIngredientVO>();
		for(int i =0;i<dishNames.length;i++) {
			for(int j =0;j<IngIDs[i].length;j++) {
				DishAndIngredientVO dishAndIngredientVO = new DishAndIngredientVO();
				dishAndIngredientVO.setIngredientID(IngIDs[i][j]);
				dishAndIngredientList.add(dishAndIngredientVO);
			}
		}

		List<EventMemberVO> eventMemberList = new ArrayList<EventMemberVO>();
		EventMemberVO eventMemberVO = new EventMemberVO();
		eventMemberVO.setAccountID(accountID);
		eventMemberVO.setParticipationState(2);
		eventMemberVO.setHostIdentifier(true);
		eventMemberList.add(eventMemberVO);
		
		List<EventCuisineCategoryVO> eventCuisineCategoryList = new ArrayList<EventCuisineCategoryVO>();
		for(int i =0;i < cuisinecategoryID.length;i++) {
			EventCuisineCategoryVO eventCuisineCategoryVO = new EventCuisineCategoryVO();
			eventCuisineCategoryVO.setCuisinecategoryID(cuisinecategoryID[i]);
			eventCuisineCategoryList.add(eventCuisineCategoryVO);
		}
		
		dao.insertWithDishIngredientMember(eventInfoVO, dishList, dishAndIngredientList, eventMemberList,eventCuisineCategoryList);
		
		return eventInfoVO;
	}
}
