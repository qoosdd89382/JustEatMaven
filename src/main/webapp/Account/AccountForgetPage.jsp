<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.accountinfo.model.*"%>
<!DOCTYPE html>
<% 
AccountInfoVO accountInfoVO = (AccountInfoVO) request.getAttribute("accountInfoVO"); 
String accountMail = (String) request.getAttribute("accountMailInput");

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

<title>揪食-忘記密碼</title>

<style>
body#body_forget{
	background-image:url("./images/LoginBackGround.jpg");
	background-size: cover;
	background-attachment:fixed; 
	background-repeat: no-repeat;
}

/*整個區塊 */
div#main_area{
	margin-top:80px;
}
/* 登入區塊 */
div#register_area {	
	text-align:center;
	background-color: rgba(0,0,0,0.6);
	color:white;
	
	width: 300px;
	height: 250px;
	
 	margin: 35px auto; 
 	padding: 30px; 
	
	
	border-top-left-radius: 10px;
	border-bottom-left-radius: 10px;
	border-top-right-radius: 10px;
	border-bottom-right-radius: 10px;
	
 	box-shadow:0px 1px 2px 1px #aaaaaa, 
 	           inset 0px 1px 1px rgba(255,255,255,0.7); 
	border-radius: 3px solid orange;
}

strong#register_area_title{
	color: 	#FF8800;

	font-size:18px;
}

input#register_submit_btn,
input#register_reset_btn {
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

input#register_submit_btn:hover,
input#register_reset_btn:hover {
	background:#FFAA33;
	text-decoration: none;
}
form#register_area input#input_box{
	font-size:10px;
	width:300px;
	height:50px;
}
textarea#textarea {
	resize:none;
	overflow:hidden;
}
</style>

</head>
<body id="body_forget">
	<header>
		<%@ include file="/common/header.jsp"%>
	</header>
	
	<div class="container">
	
		<div id="main_area" class="row">
		
			<div id="register_area" class="col-10 col-sm-10 col-md-8 col-lg-6 col-xl-6 align-self-center">
			
			<Strong id="register_area_title">您好~歡迎來到揪食!</Strong><br>
			<strong id="register_area_title">忘記密碼了嗎? 我們將協助您取得密碼 ~</strong>
			
				<form id="register_area" method="post" action="<%=request.getContextPath()%>/Account/accountInfo.do">
				
					<span style="color:red">*</span><span>請輸入會員信箱 :</span><br>
					<input id="input_box" type="text" name="accountMail" 
					value="<%=(accountMail == null) ? "" : accountMail%>"
					placeholder="如JustEat@gmail.com"><br>
					<span style="color:red">${errorMsgs.get("accountMailError")}</span><br> 
						
					<input type="hidden" name="action" value="getAccountInfoForForget"> 
					<input id="register_submit_btn" type="submit" value="送出驗證信"> 
				</form>
				
				<span id="account_register_info"><a id="account_register_info" href='<%=request.getContextPath()%>/Account/AccountRegister/AccountRegisterPage.jsp'>還不是會員?</a></span>
				
			</div>
		</div>
	</div>
	
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