<%@page import="java.sql.Timestamp"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.evaluatedmember.model.*"%>
<%@ page import="com.accountinfo.model.*"%>
<%@ page import="com.eventmember.model.*"%>
<%@ page import="com.admininfo.model.*"%>
<%@ page import="com.eventinfo.model.*"%>

<jsp:useBean id="accountSvc" scope="page" class="com.accountinfo.model.AccountInfoService" />
<jsp:useBean id="admininfoSvc" scope="page" class="com.admininfo.model.AdminInfoService" />
<jsp:useBean id="evaluatedmemberSvc" scope="page" class="com.evaluatedmember.model.EvaluatedMemberService" />
<jsp:useBean id="eventMemberSvc" scope="page" class="com.eventmember.model.EventMemberService" />
<jsp:useBean id="eventInfoSvc" scope="page" class="com.eventinfo.model.EventInfoService" />
<%
	String accountID = request.getParameter("accountID");
	List<EventMemberVO> eventMemberList = eventMemberSvc.getAllByAccount(new Integer(accountID));
	pageContext.setAttribute("eventMemberList", eventMemberList);
	
// 	for(EventMemberVO Abc:Eventlist)
// 	List<EventMemberVO> eventMemberList1 = eventInfoSvc.getEventID(eventMemberVO.eventID).eventStartTime);
	
// 	Date date = new Date();
// 	Timestamp timestamp = new Timestamp(date.getTime());
	
	
// 	for(int i=0;i<listAll.size();i++){
// 		EventInfoVo eventInfoVOtemp = listALL.get(i);
// 		if(eventInfoVotemp.getEventStarTime().after(timestamp)){
// 			list.add(eventInfoVOtemp);
// 			}
// 		}
		
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>參加/結束的活動</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/Event/css/abc.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/footer.css">
</head>
<body>

	<header>
		<%@ include file="/common/header.jsp"%>
	</header>
	<h2>參加/結束的活動</h2>
	<nav aria-label="breadcrumb" style="-bs-breadcrumb-divider: '&gt;';">
		<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href="%=request.getContextPath()%>/index.jsp" >首頁</a></li>
			<li class="breadcrumb-item"><a href="">參加/結束的活動</a></li>
			
			
		</ol>
	</nav>
	
<%-- 錯誤表列 --%>

	
	<table> 
	<tr> 
		<th>活動編號</th> 
		<th>活動名稱</th>
		<th>主辦人</th>
		<th>地區</th>
		<th>人數</th>
		<th>活動開始日期</th>
		<th>活動結束日期</th>
		<th>狀態</th>
		<th>評鑑</th>
		
	</tr>
	<c:forEach var="eventMemberVO" items="${eventMemberList}">
		<tr>
			<td>${eventMemberVO.eventID}</td>
			<td> <a href="<%= request.getContextPath()%>/Event/EventDetailReview.jsp?eventID=${eventMemberVO.eventID}&accountID=100001">${eventInfoSvc.getEventID(eventMemberVO.eventID).eventName}</a> </td>
			<td>${accountSvc.selectOneAccountInfo(eventMemberSvc.getOneByEventAndHost(eventMemberVO.eventID)).accountNickname}</td>
<%-- 			<td>${accountSvc.getOneAccount(eventMemberSvc.getOneByEventAndHost(eventInfoSvc.getEventID(eventMemberVO.eventID).eventID)).accountNickname}</td> --%>
			<td>${eventInfoSvc.getEventID(eventMemberVO.eventID).groupCity}</td>
			<td>${eventInfoSvc.getEventID(eventMemberVO.eventID).eventCurrentCount}</td>
			<td><fmt:formatDate value="${eventInfoSvc.getEventID(eventMemberVO.eventID).eventStartTime}" pattern="yyyy-MM-dd hh:mm"/></td> 
			<td><fmt:formatDate value="${eventInfoSvc.getEventID(eventMemberVO.eventID).eventEndTime}" pattern="yyyy-MM-dd hh:mm"/></td> 
			<td>
				
			
			
				<fmt:parseDate value="${eventInfoSvc.getEventID(eventMemberVO.eventID).eventEndTime}" pattern="yyyy-MM-dd HH:mm" var="endDate"/>
				
				<% Date nowDate = new Date(); request.setAttribute("nowDate", nowDate); %>
				<c:if test="${nowDate > endDate}">
				    <font class="">已結束</font>
				</c:if>
 					<c:if test="${nowDate < endDate}">
					  <font class="">進行中</font>
				</c:if>

<%-- 				<fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy-MM-dd " var="nowDate"/>  --%>
<%-- 				<fmt:formatDate value="${eventInfoSvc.getEventID(eventMemberVO.eventID).eventEndTime}" type="both" dateStyle="long" pattern="yyyy-MM-dd " var="Edt"/>  --%>
<%-- 					<c:if test="${nowDate gt Edt}" var="rs"> --%>
<!-- 						已結束 -->
<%-- 					</c:if> --%>
<%-- 					<c:if test="${!rs}"> --%>
<!-- 						進行中 -->
<%-- 					</c:if> --%>
<%-- 				<fmt:formatDate value="${eventInfoSvc.getEventID(eventMemberVO.eventID).eventEndTime}" type="both" dateStyle="long" pattern="yyyy-MM-dd hh:mm" var="Edt"/>   --%>
<%-- 				<c:if test="${nowDate gt std & lt edt }" var="rs"> --%>
<!-- 				進行中 -->
<%-- 				</c:if> --%>
<%-- 				<c:if test="${nowDate gt edt  }" var="re"> --%>
<!-- 				已結束 -->
<%-- 				</c:if> --%>
<%-- 				<c:if test="${nowDate lt std }"var="rd"> --%>
<!-- 				尚未開始 -->
													
			</td>
			<td>  
			
				<fmt:parseDate value="${eventInfoSvc.getEventID(eventMemberVO.eventID).eventEndTime}" pattern="yyyy-MM-dd HH:mm" var="endDate"/>
				
				<% Date nowDate1 = new Date(); request.setAttribute("nowDate", nowDate); %>
				<c:if test="${nowDate < endDate}">
				
					  <font class="">未評鑑</font>
				
				</c:if>
 					<c:if test="${nowDate > endDate}">
					<a href="<%= request.getContextPath()%>/Event/EvaluatedMember.jsp?eventID=${eventMemberVO.eventID}">評鑑</a>   	
			</c:if>
			</td>
			
		</tr>
	</c:forEach>
</table>
	  <div class="btn_margin" align="right"  >
	  
	         <a href="<%= request.getContextPath()%>/Event/EventList.jsp?eventID=${eventID}">返回活動列表</a>   
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

		$("[name=position]").on("change",function(){
			if($(this).is(":checked")){
				$(".positionSubmit").submit();
			}
		});
	</script>
</body>
</html>