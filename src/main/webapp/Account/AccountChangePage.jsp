<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.accountinfo.model.*"%>
<!DOCTYPE html>

<%
AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVO"); 
//EmpServlet.java(Concroller), 存入req的VO物件
%>

<html>

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap 的 CSS -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/vendors/slick/slick.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/vendors/slick/slick-theme.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/common/css/header.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/common/css/footer.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/index.css">

<title>AccountChangePage</title>

<style>
div.container{
margin-top:100px;
}
div#register {
	list-style: none;
	font-size: 25px;
}

/* div#function_select_area li{ */
/* border:1px ridge orange; */
/* font-size:25px; */
/* margin:15px; */
/* } */
</style>

</head>
<body>
	<header>
		<%@ include file="/common/header.jsp"%>
	</header>

	<div class="container">
		<div class="row">
		
<!-- 			<div id="function_select_area" class="col-4"> -->
<!-- 				<ul> -->
<!-- 					<li><a href='#'>會員資料</a></li> -->
<!-- 					<li><a href='#'>我的活動</a></li> -->
<!-- 					<li><a href='#'>我的評價</a></li> -->
<!-- 					<li><a href='#'>我的好友</a></li> -->
<!-- 					<li><a href='#'>我的文章</a></li> -->
<!-- 					<li><a href='#'>我的收藏</a></li> -->
<!-- 					<li><a href='#'>我的訂單</a></li> -->
<!-- 					<li><a href='#'>成為商家(這裡記得要加判斷)</a></li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		
		
			<div id="register" class="col-6">
				<b>請輸入您的會員資料</b>
				<form id="register" method="post" action="accountInfo.do">
					<b>請輸入會員信箱 (如JerryMouse@gmail.com):</b> 
<%-- 					<input type="text" name="accountMail" value="<%=request.getAttribute("accountMail")%>">  --%>
					<input type="text" name="accountMail" value="${accountInfoVO.accountMail}"> 
					<br> 
					<b>請輸入會員暱稱 (如王陽明):</b> 
					<input type="text" name="accountNickname" value="${accountInfoVO.accountNickname}"><br> 
					<b>請輸入會員密碼(如1q2w3e4r5t):之後改寄驗證信修改密碼</b> 
					<input type="text" name="accountPassword" value="${accountInfoVO.accountPassword}"><br>
					<!-- 			<b>請輸入會員狀態 (如1):</b> -->
					<!-- 			<input type="text" name="accountState"><br> -->
					<!-- 			<b>請輸入會員層級 (如1):</b> -->
					<!-- 			<input type="text" name="accountLevel"><br> -->

					<b>請輸入會員姓名 (如王曉明):</b> <input type="text" name="accountName" value="${accountInfoVO.accountName}"><br>
					<b>請輸入會員性別 :""""還要改"""</b> 
					<input type="radio" name="accountGender" value="1">男
					<input type="radio" name="accountGender" value="2">女 <br>
					<b>請輸入會員生日</b> 
					<input type="date" name="accountBirth" value="${accountInfoVO.accountBirth}"> <br>
					<b>請輸入會員電話 (如0912345678):</b> 
					<input type="text" name="accountPhone" value="${accountInfoVO.accountPhone}"><br> 
					<b>請輸入會員照片 :照片要用JSP照片要用JSP照片要用JSP</b> 
					<input type="file" name="accountPic" value="${accountInfoVO.accountPic}"><br> 
					<b>請輸入會員身分證正面:</b>
					<input type="file" name="accountIDcardFront"><br> 
					<b>請輸入會員身分證背面:</b>
					<input type="file" name="accountIDcardBack"><br> 
					<b>請輸入會員自我介紹:</b>
					<input type="text" name="accountText" value="${accountInfoVO.accountText}"><br>
					<!-- 			<b>請輸入會員註冊時間</b> -->
					<!-- 			<input type="text" name="accountPhone"><br> -->

					<input type="hidden" name="action" value="setAccountInfo_For_Change"> 
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
	<script
		src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.6.0.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
	<%-- <script src="https://cdnjs.cloudflare.com/ajax/libs/skrollr/0.6.30/skrollr.min.js"></script> --%>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/vendors/slick/slick.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/footer.js"></script>
	<script src="<%=request.getContextPath()%>/js/index.js"></script>
</body>
</html>