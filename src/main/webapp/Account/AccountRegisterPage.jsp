<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.accountinfo.model.*"%>
<!DOCTYPE html>

<%
//先不用
// AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVO"); 
//EmpServlet.java(Concroller), 存入req的VO物件
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

<title>AccountRegisterPage</title>

<style>
div#register_area{
	margin-top:150px;
}
</style>

</head>
<body>
	<header>
		<%@ include file="/common/header.jsp"%>
	</header>

	<div class="container">
	
		<div id="main_area" class="row">
		
			<div id="register_area" class="col">
			
				<strong>請輸入您的會員資料</strong>
				
				<form method="post" action="accountInfo.do">
					<span>請輸入會員信箱 (如JerryMouse@gmail.com):</span><br>
					<input type="text" name="accountMail"><span style="color:red">${errorMsgs.get("accountMailError")}</span><br> 
					
					<span>請輸入會員暱稱 (如王陽明):</span><br>
					<input type="text" name="accountNickname"><span style="color:red">${errorMsgs.get("accountNicknameError")}</span><br> 
					
					<span>請輸入會員密碼(如1q2w3e4r5t):之後改寄驗證信修改密碼</span><br>
					<input type="text" name="accountPassword"><span style="color:red">${errorMsgs.get("accountPasswordError")}</span><br>
					
					<!-- 			<b>請輸入會員狀態 (如1):</b> -->
					<!-- 			<input type="text" name="accountState"><br> -->
					<!-- 			<b>請輸入會員層級 (如1):</b> -->
					<!-- 			<input type="text" name="accountLevel"><br> -->

					<span>請輸入會員姓名 (如王曉明):</span><br> 
					<input type="text" name="accountName"><span style="color:red">${errorMsgs.get("accountNameError")}</span><br>
					
					<span>請輸入會員性別 :""""還要改"""</span>
					<input type="radio" name="accountGender" value="1">男
					<input type="radio" name="accountGender" value="2">女 <br>
					
					<span>請輸入會員生日</span> 
					<input type="date" name="accountBirth" value="${accountInfoVO.accountBirth}"> <br>
					
					<span>請輸入會員電話 (如0912345678):</span> 
					<input type="text" name="accountPhone" value="${accountInfoVO.accountPhone}"><br> 
					
					<span>如要進行活動，則需上傳照片</span><br>
					<span>請輸入會員照片 :照片要用JSP照片要用JSP照片要用JSP</span><br> 
					<input type="file" name="accountPic" value="${accountInfoVO.accountPic}"><br> 
					<span>請輸入會員身分證正面:</span>
					<input type="file" name="accountIDcardFront"><br> 
					<span>請輸入會員身分證背面:</span>
					<input type="file" name="accountIDcardBack"><br> 
					
					<span>請輸入會員自我介紹:</span>
					<input type="text" name="accountText" value="${accountInfoVO.accountText}"><br>
					<!-- 			<b>請輸入會員註冊時間</b> -->
					<!-- 			<input type="text" name="accountPhone"><br> -->

					<input type="hidden" name="action" value="setAccountInfo_For_Register"> 
					<input type="submit" value="提交送出"> 
					<input type="reset" value="重置">
				</form>
			</div>
		</div>
	</div>

	<h3><a id="AccountLogin" href='AccountPage.jsp'>回到會員中心</a></h3>

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