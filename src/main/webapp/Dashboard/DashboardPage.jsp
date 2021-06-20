<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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

<title>AccountChangePage</title>
</head>
<style>
</style>
</head>
<body>
	<header>
		<%@ include file="/common/header.jsp"%>
	</header>

<p>=============</p>
		<h3>會員資料管理:</h3>
		<li>
			<FORM METHOD="post" ACTION="accountInfo.do">
				<b>輸入會員編號 (如100001):</b> <input type="text" name="accountID">
				<input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>

		<jsp:useBean id="accountInfoSvc" scope="page"
			class="com.accountinfo.model.AccountInfoService" />
		<%--    <% com.emp.model.EmpService dao =new com.emp.model.EmpService(); --%>
		<!-- //    		pageContext.setAttribute("dao",dao); -->
		<%--    %> --%>

		<li>
			<FORM METHOD="post" ACTION="accountInfo.do">
				<b>選擇會員編號:</b> 
				<select size="1" name="accountID">
					<c:forEach var="accountInfoVO" items="${accountInfoSvc.all}">
						<option value="${accountInfoVO.accountID}">${accountInfoVO.accountID}
					</c:forEach>
				</select> 
				<input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>

		<li>
			<FORM METHOD="post" ACTION="accountInfo.do">
				<b>選擇會員姓名:</b> <select size="1" name="accountID">
					<c:forEach var="accountInfoVO" items="${accountInfoSvc.all}">
						<option value="${accountInfoVO.accountID}">${accountInfoVO.accountMail}
					</c:forEach>
				</select>
				<input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>
		
		<li><a href='listAllAccountinfo.jsp'>List</a> all AccountInfo.</li>
	</ul>
	<ul>
		<li><a href='addAccountInfo.jsp'>Add</a> a new AccountInfo.</li>
	</ul>





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