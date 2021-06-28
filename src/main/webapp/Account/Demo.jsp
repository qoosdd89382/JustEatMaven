<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import=""%>
<!DOCTYPE html>
<% 
//會員登入頁面紀錄




%>
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

<title>揪食-</title>

<style>

</style>

</head>
<body>
	<header>
		<%@ include file="/common/header.jsp"%>
	</header>
	
	<P>開發紀錄</P>
	<textarea rows="" cols="">
	。會員登入
	。展示檢查會員=>進入註冊區域=>
	。註冊產生驗證信=>註冊驗證完成登入=>
	。可以查看資料修改資料=>
	。忘記密碼送出驗證信修改
	</textarea>
	<p>備忘區</p>
	<h3><a id="AccountLogin" href='AccountPage.jsp'>回到會員中心</a></h3>
	<p>設計登入板塊始終保持置中</p>
	<p>借我標記一下session(EL)=>${accountMail}</p>
	<p>記得改type password</p>
	<p>重新整理帶入資料</p>
	<p>切到這個頁面檢查登入狀態直接到會員中心</p>
	<p>新增記住我按鈕 COOKIE</p>
	<p>新增GOOGLE或FB登入</p>
	<p></p>





	<footer>
		<%@ include file="/common/footer.jsp"%>
	</footer>
	
	<script type="text/javascript">

	</script>

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