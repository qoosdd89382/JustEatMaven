<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="java.util.List"%>
<%@ page import="com.accountinfo.model.*"%>
<%@ page import="com.admininfo.model.*"%>
<%@ page import="com.dish.model.*"%>
<%@ page import="com.ingredient.model.*"%>
<%@ page import="com.dishandingredient.model.*"%>
<%@ page import="com.eventmember.model.*"%>

<jsp:useBean id="eventMemberSvc" scope="page" class="com.eventmember.model.EventMemberService" />
<jsp:useBean id="accountSvc" scope="page" class="com.accountinfo.model.AccountInfoService" />
<jsp:useBean id="dishSvc" scope="page" class="com.dish.model.DishService" />
<jsp:useBean id="ingredientSvc" scope="page" class="com.ingredient.model.IngredientService" />
<jsp:useBean id="dishAndIngredientService" scope="page" class="com.dishandingredient.model.DishandingredientService" />
<%
	List<EventMemberVO> eventMemberVOList = eventMemberSvc.getAllByEventID(300001);
	pageContext.setAttribute("eventMemberVOList", eventMemberVOList);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<table class="row" border=1>
		<c:forEach var="eventMemberVO" items="${eventMemberVOList}" varStatus="i">
		<tbody>
			<tr>
				<td class="col-sm-3 col-3" rowspan=${fn:length(dishSvc.getAccountID(eventMemberVO.accountID))}>
					${eventMemberVO.accountID}
				</td>
				<c:forEach var="dishVO" items="${dishSvc.getAccountIDAndEventID(eventMemberVO.accountID, 300001)}">
					<td>${dishVO.dishName}</td>
					<td>
						<c:forEach var="dishAndIngVO" items="${dishAndIngredientService.getAllByDish(dishVO.dishID)}">
							<div>${ingredientSvc.getOneIngredient(dishAndIngVO.ingredientID).ingredientName}</div>
						</c:forEach>
					</td></tr>
			<tr>
				</c:forEach>
			</tr>
		</tbody>
		</c:forEach>
	</table>
</body>
</html>
