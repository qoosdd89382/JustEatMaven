<%@page import="com.eventinfo.model.EventInfoVO"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.eventmember.model.*"%>
<%@ page import="com.accountinfo.model.*"%>

<jsp:useBean id="accountSvc" scope="page" class="com.accountinfo.model.AccountInfoService" />
<jsp:useBean id="eventMemberSvc" scope="page" class="com.eventmember.model.EventMemberService" />
<jsp:useBean id="eventSvc" scope="page" class="com.eventinfo.model.EventInfoService" />

<%
	String eventID = request.getParameter("eventID");
	List<EventMemberVO> list = eventMemberSvc.getAllByEventID(new Integer(eventID));
// 	AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVOLogin"); 
// 	List<EventMemberVO> list = eventMemberSvc.getAllByEventID(300002);
// 	pageContext.setAttribute("eventID", 300002);
// 	pageContext.setAttribute("list", list);
// 	int accountAvgScore = eventMemberSvc.getAvgScoreByAccountID(100001);
// 	pageContext.setAttribute("accountAvgScore", accountAvgScore);

	EventInfoVO event = eventSvc.getEventID(new Integer(eventID));
	Timestamp now = new Timestamp(System.currentTimeMillis());
	if (now.after(event.getEventStartTime())) {
		System.out.println("out");
		for (EventMemberVO vo : list) {
			if (vo.getParticipationState() == 1) {
				eventMemberSvc.deleteEventMember(new Integer(eventID), vo.getAccountID());
			}
		}
		list = eventMemberSvc.getAllByEventID(new Integer(eventID));
	}
	pageContext.setAttribute("list", list);

%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>成員列表</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/Event/css/abc.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/footer.css">
</head>
<body>

	<header>
		<%@ include file="/common/header.jsp"%>
	</header>
	<h2>成員列表</h2>
	<nav aria-label="breadcrumb" style="-bs-breadcrumb-divider: '&gt;';">
		<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/index.jsp">首頁</a></li>
<%-- 			<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/Event/MyEvent.jsp?accountID=<%=accountInfoVO.getAccountID()%>">我的活動</a></li> --%>
			<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/Event/MyEvent.jsp?accountID=${accountInfoVOLogin.accountID}">我的活動</a></li>
			<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/Event/EventDetailReview.jsp?eventID=<%=request.getParameter("eventID")%>">活動詳情</a></li>
			<li class="breadcrumb-item active" aria-current="page">成員列表</li>
		</ol>
	</nav>
	
<%-- 錯誤表列 --%>

	
	<table> 
	<tr> 
		<th>帳號</th> 
		<th>性別</th>
		<th>平均星數</th>
		<th>總活動次數</th>
		<th>出席次數</th>
		<th>狀態</th>
		
	</tr>
		<c:forEach var="eventMemberVO" items="${list}" >
			<tr> 
				<td>${accountSvc.selectOneAccountInfo(eventMemberVO.accountID).accountNickname}</td> 
				<td>
					<c:if test="${accountSvc.selectOneAccountInfo(eventMemberVO.accountID).accountGender == 1}">男</c:if>
					<c:if test="${accountSvc.selectOneAccountInfo(eventMemberVO.accountID).accountGender == 2}">女</c:if>
				</td>
				
				<td>${eventMemberSvc.getAvgScoreByAccountID(eventMemberVO.accountID)}</td> 
				<td>${eventMemberSvc.getTotalEventByAccountID(eventMemberVO.accountID)}</td> 	
				<td>${eventMemberSvc.getTotalAttendanceByAccountID(eventMemberVO.accountID)}</td> 
		
				<td>	
				     <c:if test="${eventMemberSvc.getEventStatusByAccountID(eventMemberVO.accountID, eventMemberVO.eventID) == 1}">審核中</c:if>
				     <c:if test="${eventMemberSvc.getEventStatusByAccountID(eventMemberVO.accountID, eventMemberVO.eventID) == 2}">參與中</c:if>
				     
				</td>
		
			    
			
				
			</tr> 
		</c:forEach>
	</table>
	  <div class="btn_margin" align="right"  >
	  
	         <a href="<%= request.getContextPath()%>/Event/EventDetailReview.jsp?eventID=${param.eventID}">返回活動詳情</a>   
	  </div>
			
	<footer>
		<%@ include file="/common/footer.jsp"%>
	</footer>
	<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.6.0.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/js/footer.js"></script>
	<script>
		$(function() {
			$(".header").load("header.html");
			$(".footer").load("footer.html");
		});
		
		$("[name=position]").on("change",function(){
			if($(this).is(":checked")){
				$(".positionSubmit").submit();
			}
		});
	</script>
</body>
</html>