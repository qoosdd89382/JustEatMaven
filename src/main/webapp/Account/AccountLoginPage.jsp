<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@ page import="com.accountinfo.model.*"%>
<%
//登入後產生存在session中的accountInfoVOLogin
AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVOLogin");
//如果SESSIN沒有登入消息的話 就取前面的REQUEST
// if(accountInfoVO==null){
// 	accountInfoVO = (AccountInfoVO) request.getAttribute("accountInfoVO");
// }
//取得傳回的使用者輸入參數
// String accountMail = accountInfoVO.getAccountMail();
// String accountPassword = accountInfoVO.getAccountPassword();
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

div#main_area{
	margin-top:150px;
}

div#login_area {
	background-color:white;
	list-style: none;
	border:1px solid gray;
}
strong#login_area_title{
	font-size:30px;
}

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

input#account_forget_code,
input#account_register_info {
	border:none;
	font-family: Arial;
	color:#0000FF;
	font-size: 20px;
	padding: 10px 20px 10px 20px;
	background: #FFFFFF;
	text-decoration: underline;
}

</style>

<body>

	<header>
		<%@ include file="/common/header.jsp"%>
	</header>
	
	<div class="container">

		<div id="main_area"class="row">
		
			<div id="intro_area" class="col col-sm-8">
			
				<img src="<%=request.getContextPath()%>/img/header.png" width="730" height="435" border="0">

			</div>

			<div id="login_area" class="col col-sm-4">
			
				<strong id="login_area_title">您好，歡迎登入</strong>
				
				<div id="login_area_input"></div>
				
					<form method="post" action="accountInfo.do" name="LoginInfo">
						<span>請輸入會員信箱 (如JerryMouse@gmail.com):</span><br>
						<input id="account_mail_input" type="text" name="accountMail" 
							value="<%=(accountMail == null) ? "" : accountMail%>">
						<span style="color:red">${errorMsgs.get("accountMailError")}</span><br>
						
						<span>請輸入會員密碼 (至少8~16碼任意大小寫英文數字):</span><br>
						<input id="account_password_input" type="text" name="accountPassword"
							value="<%=(accountPassword == null) ? "" : accountPassword%>">
						<span style="color:red">${errorMsgs.get("accountPasswordError")}</span><br>
			
					    <span>請輸入驗證碼 (點擊圖片可刷新):測試密碼"1111"</span><br>
					    <input id="account_randomnumber_input" type="text" name="RandomNumberInput" value="1111">
					    <span style="color:red">${errorMsgs.get("randomNumberError")}</span><br>
					    
					    <img id="login_area_randomnumber_pic" name="imgValidate" src="RandomNumber.jsp" onclick="refresh()"><br>
			    
						<input type="hidden" name="action" value="getAccountInfoForLogin">
						<input id="account_login_btn" type="submit" value="登入">
						<input id="account_reset_btn" type="reset" value="重置">
					</form>
				
				
				<h3><a id="account_forget_code" href='<%=request.getContextPath()%>/Account/AccountForgetPage.jsp'>忘記密碼了嗎?</a></h3>
								
				<h3><a id="account_register_info" href='<%=request.getContextPath()%>/Account/AccountRegister/AccountRegisterPage.jsp'>還不是會員?</a></h3>
				
					
			</div>
		</div>
	</div>
	
	<p>後台區</p>
	<span>${errorMsgs}</span>
	<p>${errorMsgs.get("UnexceptionError")}</p>
	<p>====</p>
	

	
	<footer>
		<%@ include file="/common/footer.jsp"%>
	</footer>
	
	<script type="text/javascript">
	//互動區
	$("#login_area_randomnumber_pic").on("click",function(){
		LoginInfo.imgValidate.src="RandomNumber.jsp?id="+Math.random();
	})

	//功能區
		//頁面載入執行
// 		function window.onload(){
// 			if(window.sessionStorage){
// 				var 
// 			}
// 		}
	
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