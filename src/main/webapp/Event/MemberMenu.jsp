<%@page import="com.ingredient.model.IngredientService"%>
<%@page import="java.util.List"%>
<%@page import="com.eventmember.model.EventMemberVO"%>
<%@page import="com.eventmember.model.EventMemberService"%>
<%@page import="com.dish.model.DishVO"%>
<%@page import="com.dishandingredient.model.DishandingredientService"%>
<%@page import="com.dishandingredient.model.DishAndIngredientVO"%>
<%@page import="com.dish.model.DishService"%>
<%@page import="com.accountinfo.model.AccountInfoService"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:useBean id="eventMemberSvc" scope="page" class="com.eventmember.model.EventMemberService" />
<jsp:useBean id="accountInfoSvc" scope="page" class="com.accountinfo.model.AccountInfoService" />
<jsp:useBean id="dishSvc" scope="page" class="com.dish.model.DishService" />
<jsp:useBean id="ingredientSvc" scope="page" class="com.ingredient.model.IngredientService" />
<jsp:useBean id="dishAndIngredientSvc" scope="page" class="com.dishandingredient.model.DishandingredientService" />
<%
// 	EventMemberService eventMemberSvc = new EventMemberService();
// 	AccountInfoService accountInfoSvc = new AccountInfoService();
// 	DishService dishSvc = new DishService();
// 	DishandingredientService dishAndIngredientSvc = new DishandingredientService();
// 	IngredientService ingredientSvc = new IngredientService();
	
	List<EventMemberVO> eventMemberVOList = eventMemberSvc.getAllByEventID(Integer.parseInt(request.getParameter("eventID")));
	request.setAttribute("eventMemberVOList",eventMemberVOList);
	
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>成員菜單</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/jquery-ui/css/jquery-ui.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/Event/css/style.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/header.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/footer.css">
</head>

<body>
    <header class="header">
    	<%@ include file="/common/header.jsp"%>
    </header>
    <nav aria-label="breadcrumb" style="-bs-breadcrumb-divider: '&gt;';">
		<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/index.jsp">首頁</a></li>
			<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/Event/EventList.jsp">活動列表</a></li>
			<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/Event/EventDetailReview.jsp?eventID=${param.eventID}">活動詳情</a></li>
			<li class="breadcrumb-item active" aria-current="page">成員菜單</li>
		</ol>
	</nav>
	<form action="<%=request.getContextPath()%>/Event/EventInfo.do" method="POST" id="formID">
		<div class="temp_data">
			<input type="hidden" name="eventID" value="${param.eventID}">
			<input type="hidden" name="accountID" value="${param.accountID}">
		</div>
		<div class="title">
	        <h2>成員菜單</h2>
	    </div>
	    <main  class="memberMenu col-lg-12 col-12 row">
			<div class="col-lg-12 col-12">
				<table class="borderforMenu">
					<thead>
						<tr class="">
							<th class="">名字</th>
							<th class="">菜名</th>
							<th class="">食材</th>
							<th class="">狀態</th>
						</tr>
					</thead>
					<c:forEach var="eventMemberVO" items="${eventMemberVOList}" varStatus="i">
						<tbody>
							<tr class="">
								<td class="" rowspan=${fn:length(dishSvc.getAccountID(eventMemberVO.accountID))}>
								${accountInfoSvc.selectOneAccountInfo(eventMemberVO.accountID).accountNickname}
								</td>
									<c:forEach var="dishVO" items="${dishSvc.getAccountIDAndEventID(eventMemberVO.accountID,param.eventID)}">
										<td class="">${dishVO.dishName}</td>
										<td class="">
										<c:forEach var="dishAndIngredientVO" items="${dishAndIngredientSvc.getAllByDish(dishVO.dishID)}">
											<span>${ingredientSvc.getOneIngredient(dishAndIngredientVO.ingredientID).ingredientName}</span>
										</c:forEach>
										</td>
										<td>已確認</td></tr><tr>		
									</c:forEach>
							</tr>
						</tbody>						
					</c:forEach>
				</table>
				<div class="menuBtn">
					<input type="submit" name="action" value="返回活動詳情" class="btn btn-secondary">
					<input type="submit" name="action" value="返回活動列表" class="btn btn-secondary">
				</div>
			</div>
		</main>
    </form>
    <footer>
		<%@ include file="/common/footer.jsp"%>
	</footer>
	
	<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.6.0.min.js"></script>
	<script src="<%=request.getContextPath()%>/Event/js/DateFormat.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/footer.js"></script>
	<script>
	<%@ include file="/common/js/scriptFooter.page"%>

	</script>
</body>

</html>