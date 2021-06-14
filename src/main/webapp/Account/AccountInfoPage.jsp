<!-- 
//郭建巖
//待修: 
-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.accountinfo.model.*"%>

<%
AccountInfoVO accountInfoVO = (AccountInfoVO) request.getAttribute("accountInfoVO"); 
//EmpServlet.java(Concroller), 存入req的VO物件
%>

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

<title>AccountInfoPage</title>
<style>
h1#accountWelcom{
margin-top:100px;
}

ul#accountInfo{
margin-top:40px;
}
ul#accountInfo li{
margin:20px;
}
div#account_welcom{
text-align:center;
}
div#function_select_area li{
border:1px ridge orange;
font-size:25px;
margin:15px;
}


</style>
</head>
<body>
	<header>
		<%@ include file="/common/header.jsp"%>
	</header>
	
	<div class="container" id="main_block">
		<div class="row" id="account_welcom">
			<div class="col">
			<h1 id="accountWelcom">歡迎，用戶<%=accountInfoVO.getAccountNickname()%></h1>
			<h2>以下是您的資料</h2>
			</div>
		</div>
		<div class="row">
			<div class="col-4" id="function_select_area">
				<ul>
					<li><a href='#'>會員資料</a></li>
					<li><a href='#'>我的活動</a></li>
					<li><a href='#'>我的評價</a></li>
					<li><a href='#'>我的好友</a></li>
					<li><a href='#'>我的文章</a></li>
					<li><a href='#'>我的收藏</a></li>
					<li><a href='#'>我的訂單</a></li>
					<li><a href='#'>成為商家(這裡記得要加判斷)</a></li>
				</ul>
			</div>
			
			<div class="col-6" id="account_info_area">
				<ul id="accountInfo">
			<%-- 		<li><%=accountInfoVO.getAccountID()%></li> --%>
					
					<li>用戶信箱:<%=accountInfoVO.getAccountMail()%></li>
					<li>用戶暱稱:<%=accountInfoVO.getAccountNickname()%></li>
			<%-- 		<li><%=accountInfoVO.getAccountPassword()%></li> --%>
			<%-- 		<li><%=accountInfoVO.getAccountState()%></li> --%>
			<%-- 		<li><%=accountInfoVO.getAccountLevel()%></li> --%>
					
					<li>用戶名稱:<%=accountInfoVO.getAccountName()%></li>
					<li>用戶性別:<%=accountInfoVO.getAccountGender()==1?"男":"女"%></li>
					<li>用戶生日:<%=accountInfoVO.getAccountBirth()%></li>
					<li>用戶電話:<%=accountInfoVO.getAccountPhone()%></li>
					<li>用戶照片:<%=accountInfoVO.getAccountPic()%><img src="images/accountTest.jpg" width="100" height="100" border="0"></li>
					
					<li>用戶身分證正面:<%=accountInfoVO.getAccountIDcardFront()%><img src="images/accountIDFTest.jpg" width="100" height="100" border="0"></li>
					<li>用戶身分證背面:<%=accountInfoVO.getAccountIDcardBack()%><img src="images/accountIDBTest.jpg" width="100" height="100" border="0"></li>
					<li>用戶自我介紹:<%=accountInfoVO.getAccountText()%></li>
					<li>用戶註冊時間:<%=accountInfoVO.getAccountRegistTime()%></li>
					<!--儲存用戶資料在session -->
					<%session.setAttribute("accountMail",accountInfoVO.getAccountMail());%>
					<p>借我標記一下session==${accountMail}</p>
					
				</ul>
			</div>
		</div>	
	</div>
	<p>會員中心:更改密碼、修改密碼、更改資料</p>
	<p>我的好友頁面施工中</p>
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