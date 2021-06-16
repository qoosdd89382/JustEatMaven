<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@	page import="com.eventinfo.model.EventInfoVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	EventInfoVO eventInfoVO = (EventInfoVO) request.getAttribute("eventInfoVO");

	if(eventInfoVO!=null){
		if(eventInfoVO.getEventStartTime()!=null){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String formatStartDateTime = df.format(eventInfoVO.getEventStartTime());
			pageContext.setAttribute("formatStartDateTime", formatStartDateTime);
		}
		if(eventInfoVO.getEventEndTime()!=null){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String formatEndDateTime = df.format(eventInfoVO.getEventEndTime());
			pageContext.setAttribute("formatEndDateTime", formatEndDateTime);
		}	
		if(eventInfoVO.getEventRegistartionStartTime()!=null){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String formatRegStartDateTime = df.format(eventInfoVO.getEventRegistartionStartTime());
			pageContext.setAttribute("formatRegStartDateTime", formatRegStartDateTime);
		}
		if(eventInfoVO.getEventRegistartionEndTime()!=null){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String formatRegEndDateTime = df.format(eventInfoVO.getEventRegistartionEndTime());
			pageContext.setAttribute("formatRegEndDateTime", formatRegEndDateTime);
		}
	}
	request.getAttribute("errorMsgs");
	
	String dishAndIngJson = request.getParameter("dishAndIngJson");
	String replaceDishAndIngJson = dishAndIngJson.replaceAll("\"","&quot;");

	
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>建立活動</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/header.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/footer.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/datetimepicker/css/jquery.datetimepicker.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/Event/css/style.css">
</head>

<body>
    <header>
		<%@ include file="/common/header.jsp"%>
	</header>

	<nav aria-label="breadcrumb" style="-bs-breadcrumb-divider: '&gt;';">
		<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href=" # ">首頁</a></li>
			<li class="breadcrumb-item"><a href=" # ">活動列表</a></li>
			<li class="breadcrumb-item active" aria-current="page">建立活動</li>
		</ol>
	</nav>
	
	    <div class="title">
	        <h2>建立活動</h2>
	    </div>
	   	<form method="post" action="<%= request.getContextPath()%>/Event/EventInfo.do">
	   	<div class="temp_data">
	   		<input type="hidden" name="dishAndIngJson" value="<%=replaceDishAndIngJson%>">
	   	</div>
	    <div class="event_content">
	        <div class="info">
	            <div class="title_separate">
	                請選擇揪團類型
	                <label>
	                    <input type="radio" name="choose_type" value="1" <%= (eventInfoVO==null)?"":(eventInfoVO.getGroupType()==null)?"":(eventInfoVO.getGroupType()==1)?"checked":"" %>>一人一菜
	                    <input type="radio" name="choose_type" value="2" <%= (eventInfoVO==null)?"":(eventInfoVO.getGroupType()==null)?"":(eventInfoVO.getGroupType()==2)?"checked":"" %>>我當主廚
	                </label>
					<span class="error">${errorMsgs.get("GroupTypeIsNull")}</span>
	            </div>
	            <div>
	                <input type="text" name="event_name" value="<%=(eventInfoVO==null)?"":(eventInfoVO.getEventName()==null)?"":(eventInfoVO.getEventName()) %>" placeholder="請輸入活動名稱">
	                <input type="number" name="event_member" value="<%=(eventInfoVO==null)?"":(eventInfoVO.getEventCurrentCount()==null)?"":(eventInfoVO.getEventCurrentCount()) %>" placeholder="請輸入活動人數">
	            	<div>
	            	<span class="error">${errorMsgs.get("EventNameIsNull")}</span><pre></pre><span class="error">${errorMsgs.get("EventMemberIsNull")}${errorMsgs.get("EventMemberMustBeGreaterThanZero")}</span>
	            	</div>
	            </div>
	            <div>
	                活動開始日期:
	                <input type="text" name="event_start" id="eventStart" value="<%=(eventInfoVO==null)?"":(eventInfoVO.getEventStartTime()==null)?"":(pageContext.getAttribute("formatStartDateTime")) %>">
	                <span class="error">${errorMsgs.get("EventStartTimeIsNull")} ${errorMsgs.get("EventStartTimeNotConform")}</span>
	            </div>
	            <div>
	                活動結束日期:
	                <input type="text" name="event_end" id="eventEnd" value="<%=(eventInfoVO==null)?"":(eventInfoVO.getEventEndTime()==null)?"":(pageContext.getAttribute("formatEndDateTime"))%>">
	            	<span class="error">${errorMsgs.get("EventEndTimeIsNull")} ${errorMsgs.get("EventEndTimeNotConform")}</span>
	            </div>
	            <div>
	                活動報名開始日期:
	                <input type="text" name="event_reg_start" id="eventRegStart" value="<%=(eventInfoVO==null)?"":(eventInfoVO.getEventRegistartionStartTime()==null)?"":(pageContext.getAttribute("formatRegStartDateTime"))%>">
	            	<span class="error">${errorMsgs.get("EventRegStartTimeIsNull")} ${errorMsgs.get("EventRegStartTimeNotConform")}</span>
	            </div>
	            <div>
	                活動報名結束日期:
	                <input type="text" name="event_reg_end" id="eventRegEnd" value="<%=(eventInfoVO==null)?"":(eventInfoVO.getEventRegistartionEndTime()==null)?"":(pageContext.getAttribute("formatRegEndDateTime"))%>">
	            	<span class="error">${errorMsgs.get("EventRegEndTimeIsNull")} ${errorMsgs.get("EventRegEndTimeNotConform")}</span>
	            </div>
	            <div>
	                類型:
	                <input type="checkbox" name="eat_style">中式
	                <input type="checkbox" name="eat_style">日式
	                <input type="checkbox" name="eat_style">越式
	                <input type="checkbox" name="eat_style">西式
	                <input type="checkbox" name="eat_style">泰式
	                <input type="checkbox" name="eat_style">不拘
	            </div>
	            <div>
	                <select name="city">
	                <!--北台灣-->
	                <option value="基隆市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity()=="基隆市"?"selected":"" %>>基隆市</option>
	                <option value="新北市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity()=="新北市"?"selected":"" %>>新北市</option>
	                <option value="台北市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity()=="台北市"?"selected":"" %>>台北市</option>
	                <option value="桃園市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity()=="桃園市"?"selected":"" %>>桃園市</option>
	                <option value="宜蘭縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity()=="宜蘭市"?"selected":"" %>>宜蘭縣</option>
	                <option value="新竹縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity()=="新竹縣"?"selected":"" %>>新竹縣</option>
	                <option value="新竹市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity()=="新竹市"?"selected":"" %>>新竹市</option>
	                <!--中台灣-->
	                <option value="苗栗縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity()=="苗栗市"?"selected":"" %>>苗栗縣</option>
	                <option value="台中市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity()=="台中市"?"selected":"" %>>台中市</option>
	                <option value="彰化縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity()=="彰化市"?"selected":"" %>>彰化縣</option>
	                <option value="南投縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity()=="南投市"?"selected":"" %>>南投縣</option>
	                <option value="雲林縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity()=="雲林市"?"selected":"" %>>雲林縣</option>
	                <!--南台灣-->
	                <option value="嘉義縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity()=="嘉義縣"?"selected":"" %>>嘉義縣</option>
	                <option value="嘉義市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity()=="嘉義市"?"selected":"" %>>嘉義市</option>
	                <option value="台南市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity()=="台南市"?"selected":"" %>>台南市</option>
	                <option value="高雄市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity()=="高雄市"?"selected":"" %>>高雄市</option>
	                <option value="屏東縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity()=="屏東市"?"selected":"" %>>屏東縣</option>
	                <!--東台灣-->
	                <option value="花蓮縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity()=="花蓮縣"?"selected":"" %>>花蓮縣</option>
	                <option value="台東縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity()=="台東縣"?"selected":"" %>>台東縣</option>
	                <!--離島區域-->
	                <option value="澎湖縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity()=="澎湖縣"?"selected":"" %>>澎湖縣</option>
	                <option value="金門縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity()=="金門縣"?"selected":"" %>>金門縣</option>
	                <option value="連江縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity()=="連江縣"?"selected":"" %>>連江縣</option>
	            </select>
	            <input type="text" name="address" placeholder="請輸入地址" class="inputAddress" value="<%=(eventInfoVO==null)?"":(eventInfoVO.getGroupAddress()==null)?"":(eventInfoVO.getGroupAddress()) %>">
	            <span class="error">${errorMsgs.get("GroupAddressIsNull")}</span>
	            </div>
	            <div>
	                <input type="submit" name="action" value="新增菜色">
	                <input type="submit" name="action" value="邀請好友">
	                <input type="submit" name="action" value="取消建立">
	                <input type="submit" name="action" value="確定建立" class="confirmCreate">
	            </div>
	        </div>
	        <div class="overflow">
		        <div class="info">
		            <div class="event_description">
		                <textarea name="event_description" cols="60" rows="20" placeholder="活動說明"></textarea>
		            </div>
		        </div>
	        </div>
	    </div>
	    </form>
	<footer>
		<%@ include file="/common/footer.jsp"%>
	</footer>
	<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.6.0.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/datetimepicker/js/jquery.datetimepicker.full.js"></script>
	<script>
		 $.datetimepicker.setLocale('zh'); // kr ko ja en
	     $('#eventStart').datetimepicker({
	        theme: '',          //theme: 'dark',
	        timepicker: true,   //timepicker: false,
	        step: 1,            //step: 60 (這是timepicker的預設間隔60分鐘)
		    format: 'Y-m-d H:i',
	     });
	     $('#eventEnd').datetimepicker({
		        theme: '',          //theme: 'dark',
		        timepicker: true,   //timepicker: false,
		        step: 1,            //step: 60 (這是timepicker的預設間隔60分鐘)
			    format: 'Y-m-d H:i',
		 });
	     $('#eventRegStart').datetimepicker({
		        theme: '',          //theme: 'dark',
		        timepicker: true,   //timepicker: false,
		        step: 1,            //step: 60 (這是timepicker的預設間隔60分鐘)
			    format: 'Y-m-d H:i',
		 });
	     $('#eventRegEnd').datetimepicker({
		        theme: '',          //theme: 'dark',
		        timepicker: true,   //timepicker: false,
		        step: 1,            //step: 60 (這是timepicker的預設間隔60分鐘)
			    format: 'Y-m-d H:i',
		 });
	  
	</script>
</body>
</html>