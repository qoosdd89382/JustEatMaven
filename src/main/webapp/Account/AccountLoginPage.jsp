<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.accountinfo.model.*"%>
<%
//登入後產生存在session中的accountInfoVOLogin
AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVOLogin");

//取得傳回的使用者輸入參數
String accountMail = request.getParameter("accountMail");
String accountPassword = request.getParameter("accountPassword");
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

<title>揪食-會員登入</title>
</head>

<style>
/* 背景 */
body#Body_Login{
	background-image:url("./images/LoginBackGround.jpg");
	background-size: cover;
	background-repeat: no-repeat;
}
/*整個區塊 */
div#main_area{
	margin-top:80px;
	margin-bottom:-20px;
}
/* 登入區塊 */
div#login_area {	
	text-align:center;
	background-color: rgba(0,0,0,0.6);
	color:white;
	
	width: 150px;
	height: 480px;
	
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
/* 您好歡迎登入 */
strong#login_area_title{
	color: 	#FF8800;
	font-size:25px;
}
/* 信箱 密碼 驗證碼 */
form span#text{
	color: 	#FF8800;
	font-size:20px;
	margin:5px;
}
/* 驗證碼 */
img#login_area_randomnumber_pic{
	margin:5px;
}
/* 還不是會員 忘記密碼 */
span#account_forget_code,
span#account_register_info{
	margin:5px;
	font-size:15px;
}
/* 帳號 密碼 驗證碼 樣式 */
input#account_mail_input,
input#account_password_input,
input#account_randomnumber_input{
	border-radius:2px;
	margin:5px;
}
input#account_mail_input:focus,
input#account_password_input:focus,
input#account_randomnumber_input:focus{
	border-color: #FFDBC0;
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px rgba(252, 146, 76, 0.6);
}
/* 登入 重置按鈕 */
input#account_login_btn,
input#account_reset_btn {
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
input#account_login_btn:hover,
input#account_reset_btn:hover {
	background:#FFAA33;
	text-decoration: none;
}
</style>

<body id="Body_Login">

	<header>
		<%@ include file="/common/header.jsp"%>
	</header>
	
	<div class="container">

		<div id="main_area"class="row">
		
			<div id="login_area" class="col-10 col-sm-6 col-md-6 col-lg-4 col-xl-3 align-self-center">
			
				<strong id="login_area_title">您好，歡迎登入</strong>
				
				<div id="login_area_input"></div>
				
					<form method="post" action="<%=request.getContextPath()%>/Account/accountInfo.do" name="LoginInfo">
						<span id="text">會員信箱 </span><br>
						<input id="account_mail_input" type="text" name="accountMail" 
							value="<%=(accountMail == null) ? "" : accountMail%>"
							placeholder="請輸入...">
						<span style="color:red">${errorMsgs.get("accountMailError")}</span><br>
						
						<span id="text">會員密碼 </span><br>
						<input id="account_password_input" type="text" name="accountPassword"
							value="<%=(accountPassword == null) ? "" : accountPassword%>"
							placeholder="請輸入...">
						<span style="color:red">${errorMsgs.get("accountPasswordError")}</span><br>
			
					    <span id="text">驗證碼</span><br>
					    <input id="account_randomnumber_input" type="text" name="RandomNumberInput" >
					    <span style="color:red">${errorMsgs.get("randomNumberError")}</span><br>
					    
					    <img id="login_area_randomnumber_pic" name="imgValidate" src="RandomNumber.jsp" onclick="refresh()"><br>
			    
						<input type="hidden" name="action" value="getAccountInfoForLogin">
						<input id="account_login_btn" type="submit" value="登入">
						<input id="account_reset_btn" type="reset" value="重置">
					</form>
				
				<span id="account_forget_code"><a id="account_forget_code" href='<%=request.getContextPath()%>/Account/AccountForgetPage.jsp'>忘記密碼了嗎?</a></span>
								
				<span id="account_register_info"><a id="account_register_info" href='<%=request.getContextPath()%>/Account/AccountRegister/AccountRegisterPage.jsp'>還不是會員?</a></span>
				
			</div>
		</div>
	</div>
	
	<footer>
		<%@ include file="/common/footer.jsp"%>
	</footer>
	
	<script type="text/javascript">
	//驗證碼圖片更新
	function refresh() {
	    	LoginInfo.imgValidate.src="RandomNumber.jsp?id="+Math.random();
	    }
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