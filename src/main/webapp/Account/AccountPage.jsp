<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<title>AccountPage</title>
</head>

<style>
body{
	background-color: #E7E7E7;
}
h3#title {
	width: 250px;
	background-color: #FFC78E;
	margin-top: 130px;
	margin-bottom: 10px;
	padding:20px;
	border: 3px ridge orange;
	height: 80px;
	text-align: center;
}
div#basic{
margin:100px;
padding-left:100px;
}

ul.li.a#AccountLogin{
font-size:20px;
}

</style>

</head>


<body>
	<header>
		<%@ include file="/common/header.jsp"%>
	</header>
	
	<div id="basic">
	
	<h3 id="title">會員中心</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${requestScope.errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<ul>
		<li><h3><a id="AccountLogin" href='AccountLoginPage.jsp'>會員登入</a></h3><br></li>
		<li><h3><a id="AccountLogin" href='/justeat-maven/index.jsp'>首頁</a></h3><br></li>
		<li><h3><a id="AccountLogin" href='/justeat-maven/Dashboard/DashboardPage.jsp'>後臺頁面</a></h3><br></li>
	</ul>
		
	
	<p>=============</p>
	<h3><p>=====各頁面施工進度=====</p></h3>
	<p>===緊急開發中===</p>
	。COOKIE驗證<br>
	。註冊驗證功能(圖片預覽)<br>
	。基本後臺頁面
	。商家頁面
	<p>===會員功能===</p>
	<p>
	OK。基本登入功能:檢測帳號密碼驗證碼<br>
	OK。轉接到註冊會員頁面<br>
	。會員中心按鈕新增<br>
	。忘記密碼轉接到確認信箱頁面<br>
	。做FILTER驗證此帳號是否登入過<br>
	。記住我按鈕功能<br>
	。會員註冊跟修改資料驗證<br>
	。倒回頁面<br>
	</p>
	<p>===好友功能===</p>
	OK。基本頁面<br>
	OK。列出該帳號的好友<br>
	。刪除好友功能<br>
	。好友搜尋<br>
	<p>===公告功能===</p>
	OK。查詢公告<br>
	<p>===通知功能===</p>
	OK。查看通知<br>
	<p>===瀏覽紀錄功能===</p>	
	。查看瀏覽紀錄放在COOKIE，並給後台紀錄<br>
	
	</div>
	
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