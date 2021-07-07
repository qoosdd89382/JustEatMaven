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
<%@ page import="com.dish.model.*"%>

<jsp:useBean id="accountSvc" scope="page" class="com.accountinfo.model.AccountInfoService" />
<jsp:useBean id="eventMemberSvc" scope="page" class="com.eventmember.model.EventMemberService" />
<jsp:useBean id="dishSvc" scope="page" class="com.dish.model.DishService" />
<%  
	String eventID = request.getParameter("eventID");
	if(session.getAttribute("accountInfoVOLogin")==null) {
		response.sendRedirect(request.getContextPath() + "/Event/EventDetailReview.jsp?eventID=" + eventID);
		return;
	}
	int loginAccounID = ((AccountInfoVO) session.getAttribute("accountInfoVOLogin")).getAccountID();
	
	if (loginAccounID != eventMemberSvc.getOneByEventAndHost(new Integer(eventID))) {
		response.sendRedirect(request.getContextPath() + "/Event/EventDetailReview.jsp?eventID=" + eventID);
		return;
	}
	
	List<EventMemberVO> list = eventMemberSvc.getAllByEventID(new Integer(eventID));
	pageContext.setAttribute("list", list);
	AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVOLogin"); 
// 	int accountAvgScore = eventMemberSvc.getAvgScoreByAccountID(100001);
// 	pageContext.setAttribute("accountAvgScore", accountAvgScore);

%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>成員審核</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/Event/css/abc.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/footer.css">
</head>
<body>
	
	<header>
		<%@ include file="/common/header.jsp"%>
	</header>
	<h2>成員未審核</h2>
	<nav aria-label="breadcrumb" style="-bs-breadcrumb-divider: '&gt;';">
		<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/index.jsp ">首頁</a></li>
			<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/Event/MyEvent.jsp?accountID=<%=accountInfoVO.getAccountID()%>">我的活動</a></li>
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
		<th>菜名</th>
		<th>審核</th>
		
	</tr>
		<c:forEach var="eventMemberVO" items="${list}">
			<tr> 
				<td>${eventMemberVO.accountID}</td> 
				<td>
					<c:if test="${accountSvc.selectOneAccountInfo(eventMemberVO.accountID).accountGender == 1}">男</c:if>
					<c:if test="${accountSvc.selectOneAccountInfo(eventMemberVO.accountID).accountGender == 2}">女</c:if>
				</td>
				
				<td>${eventMemberSvc.getAvgScoreByAccountID(eventMemberVO.accountID)}</td> 
				<td>${eventMemberSvc.getTotalEventByAccountID(eventMemberVO.accountID)}</td> 	
				<td>
					<c:forEach var="dishVO" items="${dishSvc.getAccountIDAndEventID(eventMemberVO.accountID, eventMemberVO.eventID)}">
						<div id="${dishVO.dishID}">${dishVO.dishName}</div>
					</c:forEach>
				</td>
				<td>
				<c:if test="${accountInfoVOLogin.accountID != eventMemberVO.accountID}">
					<c:if test="${eventMemberVO.participationState == 1}">
						<a href="<%= request.getContextPath()%>/Event/audit.do?eventID=${eventMemberVO.eventID}&accountID=${eventMemberVO.accountID}&action=pass">通過</a>          
						<a href="<%= request.getContextPath()%>/Event/audit.do?eventID=${eventMemberVO.eventID}&accountID=${eventMemberVO.accountID}&action=reject">不通過</a>
					</c:if> 
					<c:if test="${eventMemberVO.participationState == 2}">
						<a href="<%= request.getContextPath()%>/Event/auditpass.do?eventID=${eventMemberVO.eventID}&accountID=${eventMemberVO.accountID}&action=reject">剔除成員</a> 
					</c:if>
				</c:if>
				</td>
			
				
			</tr> 
		</c:forEach>
	</table>
<!-- 	  <div class="btn_margin" align="right"  > -->
	  
<%-- 	          <a href="<%= request.getContextPath()%>/Event/EventMember.jsp?eventID=${eventID}">確認</a> 	 --%>

<!-- 	  </div> -->
			
	<footer>
		<%@ include file="/common/footer.jsp"%>
	</footer>
	<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.6.0.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/js/footer.js"></script>
	<script>
	<%@ include file="/common/js/scriptFooter.page"%>

		
		$("[name=position]").on("change",function(){
			if($(this).is(":checked")){
				$(".positionSubmit").submit();
			}
		});
	</script>
</body>
</html>