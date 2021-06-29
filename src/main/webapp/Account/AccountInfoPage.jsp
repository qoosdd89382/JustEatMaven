<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.accountinfo.model.*"%>

<%
//儲存所有資料的accountInfoVO
AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVOLogin"); 
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

<title>揪食-會員資料</title>
<style>
body{
	background: #ffe259; 
	background: -webkit-linear-gradient(to left, #ffa751, #ffe259); 
	background: linear-gradient(to left, #ffa751, #ffe259);
}
div#main_block{
	margin-top:150px;
}
div#account_welcom{
	margin:15px;
}
span#accountWelcom{
	text-align:center;
	font-size:30px;
}
div#account_name{
	margin:5px;
	text-align:center;
}
div#account_picture{
	text-align:center;
}
div#function_select_area form,
div#function_select_area a {
	border:1px ridge orange;
	font-size:25px;
	margin:15px;
}
div#account_info_area{;
	background-color: rgba(0,0,0,0.6);
	color:white;
	
	border-top-left-radius: 10px;
	border-bottom-left-radius: 10px;
	border-top-right-radius: 10px;
	border-bottom-right-radius: 10px;
	
 	box-shadow:0px 1px 2px 1px #aaaaaa, 
 	           inset 0px 1px 1px rgba(255,255,255,0.7); 
	border-radius: 3px solid orange;
 	
}
div#account_info_area form,
div#account_info_area span {
	font-size:25px;
	margin:30px;
}


input#account_change_info,
input#account_logout {
	margin:5px;
	border:none;
	-webkit-border-radius: 20;
	-moz-border-radius: 20;
	border-radius: 20px;
	color: #ffffff;
	font-size: 15px;
	background: 	#FF8800;
	padding: 5px 15px 5px 15px;
	text-decoration: none;
}
input#account_change_info:hover,
input#account_logout:hover {
	background:#FFAA33;
	text-decoration: none;
}

</style>
</head>
<body>
	<header>
		<%@ include file="/common/header.jsp"%>
	</header>
	
	<div class="container" id="main_block">
	
		<div class="row justify-content-center" id="account_welcom">
			<div class="col-8">
				<span id="accountWelcom">歡迎來到揪食JustEat~!距離您上次登入已經過了</span><br>
			</div>
		</div>
		
		<div class="row">
		
			<div class="col-4" id="function_select_area">
			
				<form method="post" action="accountInfo.do">
					<input type="hidden" name="action" value="gotoAccountInfoPage">
					<input type="submit" value="會員資料">
				</form>
				
				<a href='#'>我的活動</a><br>
				
				<a href='#'>我的評價</a><br>
				
				<form method="post" action="friendship.do">
						<input type="hidden" name="action" value="getAccount_Friendship">
						<input type="submit" value="我的好友">
				</form>
				
				<a href='#'>我的文章</a><br>
				
				<a href='#'>我的收藏</a><br>
				
				<a href='#'>我的訂單</a><br>
				
				<a href='#'>成為商家(這裡記得要加判斷)</a><br>
				
				<form method="post" action="notice.do">
					<input type="hidden" name="action" value="getAccount_Notice">
					<input type="submit" value="查看我的通知">
				</form>
				
				<form method="post" action="announce.do">
					<input type="hidden" name="action" value="getAccount_Announce">
					<input type="submit" value="查看我的公告">
				</form>
				
			</div>
			
			<div class="col-6" id="account_info_area">
				<div id="account_name">
					<span style="text-align:center">用戶  ${accountInfoVOLogin.accountNickname}</span><br>
					<span style="text-align:center">以下是您的資料</span><br>				
				</div>
				<span>用戶信箱:${accountInfoVOLogin.accountMail}</span><br>
				<span>用戶暱稱:${accountInfoVOLogin.accountNickname}</span><br>
				<span>用戶名稱:${accountInfoVOLogin.accountName}</span><br>
				<span>用戶性別:${(accountInfoVOLogin.accountGender==1?"男":"女")}</span><br>
				<span>用戶生日:${accountInfoVOLogin.accountBirth}</span><br>
				<span>用戶電話:${accountInfoVOLogin.accountPhone}</span><br>
				<div id="account_picture">
				<span>用戶照片:</span><br>					
				<img src="<%=request.getContextPath()%>/Account/Pic/Pic/${accountInfoVOLogin.accountID}" width="300px" height="150px"><br>
					
				<span>用戶身分證正面:</span><br>
				<img src="<%=request.getContextPath()%>/Account/Pic/Front/${accountInfoVOLogin.accountID}" width="300px" height="150px"><br>
				<span>用戶身分證背面:</span><br>
				<img src="<%=request.getContextPath()%>/Account/Pic/Back/${accountInfoVOLogin.accountID}" width="300px" height="150px"><br>
				</div>
										
				<div id="account_name">
				<span>用戶自我介紹:<br>${accountInfoVOLogin.accountText}</span><br>
				</div>
				<br>
				<span>用戶註冊時間:${accountInfoVOLogin.accountRegisterTime}</span><br>
				
				<form method="post" action="accountInfo.do">
					<input type="hidden" name="action" value="gotoAccountChangePage">
					<input id="account_change_info" type="submit" value="修改我的會員資料">	
				</form>
				
				<form method="post" action="accountInfo.do">
					<input type="hidden" name="action" value="getAccountLogout">
					<input id="account_logout" type="submit" value="登出帳戶">						
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