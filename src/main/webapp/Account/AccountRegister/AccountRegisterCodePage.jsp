<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.accountinfo.model.*"%>

<jsp:useBean id="accountInfoSvc" scope="page" class="com.accountinfo.model.AccountInfoService" />

<!DOCTYPE html>

<%
AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVO"); 
if(accountInfoVO==null){
	//假設使用者關掉瀏覽器，從信箱進入
	Integer accountID = Integer.parseInt(request.getParameter("accountID"));
	accountInfoVO = accountInfoSvc.selectOneAccountInfo(accountID);
	session.setAttribute("accountInfoVO",accountInfoVO);
}



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

<title>揪食-會員註冊</title>

<style>
body#body_register{
	background-image:url("images/LoginBackGround.jpg");
	background-size: cover;
	background-repeat: no-repeat;
}

/*整個區塊 */
div#main_area{
	margin-top:80px;
}
div#register_area{
	text-align:center;
	background-color: rgba(0,0,0,0.6);
	color:white;
	
	width: 200px;
	height: 320px;
	
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
input#input_box{
	border-top-left-radius: 10px;
	border-bottom-left-radius: 10px;
	border-top-right-radius: 10px;
	border-bottom-right-radius: 10px;
}

div#register_area_title{
	color: 	#FF8800;
	font-size:20px;
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
<body id="body_register">
	<header>
		<%@ include file="/common/header.jsp"%>
	</header>

	<div class="container">
	
		<div id="main_area" class="row">
		
			<div id="register_area" class="col-sm-6 align-self-center">
			<div id="register_area_title">
			<Strong>您好!您所輸入的信箱可以使用</Strong><br>
			<strong>請至您的信箱查看驗證碼</strong>
			</div>
			
				<form id="register_area" method="post" action="<%=request.getContextPath()%>/Account/accountInfo.do">
					
					<span>您的會員信箱為 :</span>${accountInfoVO.accountMail}<br>
			
					<span>請輸入驗證碼:</span><br>
					<input id="input_box" type="text" name="accountCode"><br>
					<span style="color:red">${errorMsgs.get("accountCodeError")}</span><br> 

					<a href="<%=request.getContextPath()%>/Account/AccountRegister/AccountRegisterPage.jsp">(如無收到驗證碼請點我返回前一頁面重新申請)</a><br>					
					<input type="hidden" name="action" value="getAccountCode"> 
					<input id="register_submit_btn" type="submit" value="開始填寫我的詳細資料"> 
					
				</form>
				
			</div>
		</div>
	</div>

	<h3><a id="AccountLogin" href='AccountPage.jsp'>回到會員中心</a></h3>

	<footer>
		<%@ include file="/common/footer.jsp"%>
	</footer>
	
	<script type="text/javascript">
	//功能區

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