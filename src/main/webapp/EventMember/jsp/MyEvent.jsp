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
<%
	String accountID = request.getParameter("accountID");
	List<EventMemberVO> eventMemberList = eventMemberSvc.getAllByAccount(new Integer(accountID));
%>
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>參加/結束的活動</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/EventMember/css/style.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/footer.css">
</head>
<body>

	<header>
		<%@ include file="/common/header.jsp"%>
	</header>

	<nav aria-label="breadcrumb" style="-bs-breadcrumb-divider: '&gt;';">
		<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href=" # ">首頁</a></li>
			<li class="breadcrumb-item"><a href=" # ">我的活動</a></li>
			<li class="breadcrumb-item"><a href=" # ">參加/結束的活動</a></li>
			
			
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
		<th>狀態</th>
		<th>評鑑</th>
		
	</tr>
	<c:forEach var="eventMemberVO" items="${eventMemberList}">
		<tr>
			<td>${eventInfoSvc.getEventID(eventMemberVO.eventID).eventID}</td>
			<td>${eventInfoSvc.getEventID(eventMemberVO.eventID).eventName}</td>
			<td>${accountSvc.getOneAccount(eventInfoSvc.getOneByEventAndHost(eventInfoSvc.getEventID(eventMemberVO.eventID).eventID)).accountNickname}</td>
			<td>${eventInfoSvc.getEventID(eventMemberVO.eventID).groupCity}</td>
			<td>${eventInfoSvc.getEventID(eventMemberVO.eventID).eventCurrentCount}</td>
			<td>${eventInfoSvc.getEventID(eventMemberVO.eventID).getEventDate}</td>
			
		</tr>
	</c:forEach>
</table>
	
			
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