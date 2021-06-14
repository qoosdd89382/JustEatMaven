<%@page import="java.sql.Timestamp"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.eventinfo.model.*"%>
<%
	EventInfoService eventInfoSvc = new EventInfoService();
	EventInfoVO eventInfoVO = null;
	List<EventInfoVO> list = eventInfoSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>活動列表</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/Event/css/style.css">
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
			<li class="breadcrumb-item active" aria-current="page">活動列表</li>
		</ol>
	</nav>
	<div class="add_Event">
		<div class="Create_or_Join_Event col-10">
			<button type="button" class="col-12 col-sm-3 col-md-2 col-lg-1">建立活動</button>
			<button type="button" class="col-12 col-sm-3 col-md-2 col-lg-1">加入活動</button>
		</div>
	</div>
	<div class="event_list_content row">
		<div class="center_content col-10">

			<table class="filter">
				<tr class="row">
					<td class="col-12 col-sm-3">類型: <br> 
						<input type="checkbox" name="style_filter" id="" value="中式" class="col-1">中式 
						<input type="checkbox" name="style_filter" id="" value="日式" class="col-1">日式 
						<input type="checkbox" name="style_filter" id="" value="越式" class="col-1">越式 
						<input type="checkbox" name="style_filter" id="" value="西式" class="col-1">西式 
						<input type="checkbox" name="style_filter" id="" value="泰式" class="col-1">泰式 
						<input type="checkbox" name="style_filter" id="" value="不拘" class="col-1">不拘
					</td>
					<td class="col-12 col-sm-3">地區: <br> 
						<form method="post" action="<%=request.getContextPath() %>/Event/EventInfo.do" class="positionSubmit">
							<input type="radio" name="position" id="" value="0" class="col-1">北部 
							<input type="radio" name="position" id="" value="1" class="col-1">中部 
							<input type="radio" name="position" id="" value="2" class="col-1">離島<br> 
							<input type="radio" name="position" id="" value="3" class="col-1">南部 
							<input type="radio" name="position" id="" value="4" class="col-1">東部
							<input type="hidden" name="action" value="searchEventGroupCity">
						</form>
					</td>
					<td class="col-12 col-sm-3">日期: 
						<form method="post" action="<%=request.getContextPath()%>/Event/EventInfo.do" class="dateSubmit">
							<input type="date" name="date" id="" class="date col-12">
							<input type="hidden" name="action" value="searchEventDate">
						</form>
					</td>
					<td class="col-12 col-sm-3">
						<form method="post" action="<%=request.getContextPath()%>/Event/EventInfo.do">
							<input type="text" name="search" placeholder="搜尋活動名稱" class="col-12"> 
							<input type="hidden" name="action" value="searchEventName">
						</form>
					</td>
				</tr>
			</table>
			<table class="event_list">
				<tr class="row">
					<th class="col-sm-2 col-2">活動圖片</th>
					<th class="col-sm-2 col-2">活動名稱</th>
					<th class="col-sm-2 col-2">活動類型</th>
					<th class="col-sm-2 col-2">活動地區</th>
					<th class="col-sm-2 col-2">活動時間</th>
					<th class="col-sm-2 col-2">點擊率</th>
				</tr>
				<c:choose>
					<c:when test="${not empty listfind}">
						<c:forEach var="eventInfoVO" items="${listfind}" varStatus="i">
							<tr class="row">
								<td class="col-sm-2 col-2"><img src="<%=request.getContextPath() %>/Event/EventInfoPicServlet?image=${i.index}" class="img"></td>
								<td class="col-sm-2 col-2">${eventInfoVO.eventName}</td>
								<c:if test="${eventInfoVO.groupType == 1}">
									<td class="col-sm-2 col-2">一人一菜</td>
								</c:if>
								<c:if test="${eventInfoVO.groupType == 2}">
									<td class="col-sm-2 col-2">我當主廚</td>
								</c:if>
								<td class="col-sm-2 col-2">${eventInfoVO.groupCity}</td>
								<td class="col-sm-2 col-2"><fmt:formatDate type="date" value="${eventInfoVO.eventStartTime}" pattern="yyyy-MM-dd HH:mm"/></td>
								<td class="col-sm-2 col-2">1000</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:when test="${empty listfind && not empty errMsg}">
						<tr class="row">
							<td class="col-sm-12 col-12">${errMsg}</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="eventInfoVO" items="${list}" varStatus="i">
							<tr class="row">
								<td class="col-sm-2 col-2"><img src="<%=request.getContextPath() %>/Event/EventInfoPicServlet?image=${i.index}" class="img"></td>
								<td class="col-sm-2 col-2">${eventInfoVO.eventName}</td>
								<c:if test="${eventInfoVO.groupType == 1}">
									<td class="col-sm-2 col-2">一人一菜</td>
								</c:if>
								<c:if test="${eventInfoVO.groupType == 2}">
									<td class="col-sm-2 col-2">我當主廚</td>
								</c:if>
								<td class="col-sm-2 col-2">${eventInfoVO.groupCity}</td>
								<td class="col-sm-2 col-2"><fmt:formatDate type="date" value="${eventInfoVO.eventStartTime}" pattern="yyyy-MM-dd HH:mm"/></td>
								<td class="col-sm-2 col-2">1000</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</table>

		</div>
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
		
		$("[name=date]").on("change",function(){
			$(".dateSubmit").submit();
		});
	</script>
</body>
</html>