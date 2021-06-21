<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVO"); 
%>


<jsp:useBean id="accountInfoSvc" scope="page" class="com.accountinfo.model.AccountInfoService" />
<%--    <% com.emp.model.EmpService dao =new com.emp.model.EmpService(); --%>
<!-- //    		pageContext.setAttribute("dao",dao); -->

<%@ page import="com.accountinfo.model.*"%>
<!DOCTYPE html>

<html>

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap 的 CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/vendors/slick/slick.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/vendors/slick/slick-theme.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/footer.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/index.css">

<title>DashboardPage-ListOneAccountInfo</title>
</head>
<style>

</style>
</head>
<body>
	<header>
		<%@ include file="/common/header.jsp"%>
	</header>
	
	<table>
		<tr>
			<th>會員編號</th>
			<th>會員信箱</th>
			<th>會員暱稱</th>
			<th>會員密碼</th>
			<th>會員狀態</th>
			
			<th>會員名稱</th>
			<th>會員性別</th>
			<th>會員生日</th>
			<th>會員電話</th>
			<th>會員生日</th>
			
			<th>會員照片</th>
			<th>會員身分證正面</th>
			<th>會員身分證背面</th>
			<th>會員自我介紹</th>
		</tr>
		<tr>
			<td><%=accountInfoVO.getAccountID()%></td>
			<td><%=accountInfoVO.getAccountID()%></td>
			<td><%=accountInfoVO.getAccountID()%></td>
			<td><%=accountInfoVO.getAccountID()%></td>
			<td><%=accountInfoVO.getAccountID()%></td>
			<td><%=accountInfoVO.getAccountID()%></td>
			<td><%=accountInfoVO.getAccountID()%></td>
			<td><%=accountInfoVO.getAccountID()%></td>
		</tr>
	</table>


	<footer>
		<%@ include file="/common/footer.jsp"%>
	</footer>
	
	<%-- body 結束標籤之前，載入Bootstrap 的 JS 及其相依性安裝(jQuery、Popper) --%>
	<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.6.0.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
	<%-- <script src="https://cdnjs.cloudflare.com/ajax/libs/skrollr/0.6.30/skrollr.min.js"></script> --%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/vendors/slick/slick.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/footer.js"></script>
	<script src="<%=request.getContextPath()%>/js/index.js"></script>

</body>
</html>