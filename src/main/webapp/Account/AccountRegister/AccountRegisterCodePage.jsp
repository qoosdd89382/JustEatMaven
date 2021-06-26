<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.accountinfo.model.*"%>
<!DOCTYPE html>

<%
AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVO"); 
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
div#register_area{
	margin-top:150px;
	border:1px solid gray;
	margin-right:300px;
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
<body>
	<header>
		<%@ include file="/common/header.jsp"%>
	</header>

	<div class="container">
	
		<div id="main_area" class="row">
		
			<div id="register_area" class="col offset-3">
			
			<Strong>您好~歡迎來到揪食!</Strong><br>
			<strong>請輸入您的資料協助您成為我們的一員</strong>
			
				<form id="register_area" method="post" action="<%=request.getContextPath()%>/Account/accountInfo.do">
				
					<span>您的會員信箱為 :</span>${accountInfoVO.accountMail}<br>
					
					<span>您的會員暱稱 :</span>${accountInfoVO.accountNickname}<br>

					<span>請輸入驗證碼:</span><br>
					<input id="input_box" type="text" name="accountCode">
					<span style="color:red">${errorMsgs.get("accountCodeError")}</span><br> 

						
					<input type="hidden" name="action" value="getAccountCode"> 
					<input id="register_submit_btn" type="submit" value="開始填寫我的詳細資料"> 
					
				</form>
			</div>
		</div>
	</div>
	<P>除錯區</P>
	<P>全部的訊息${errorMsgs}</P>
<%-- 	<p>?${aaa}</p> --%>
<%-- 	<p>?${bbb}</p> --%>
<%-- 	<p>???${ccc}</p> --%>


	<h3><a id="AccountLogin" href='AccountPage.jsp'>回到會員中心</a></h3>

	<footer>
		<%@ include file="/common/footer.jsp"%>
	</footer>
	
	<script type="text/javascript">
	//功能區
		function autogrow(textarea){
		var adjustedHeight=textarea.clientHeight;
		
		    adjustedHeight=Math.max(textarea.scrollHeight,adjustedHeight);
		    
		    if (adjustedHeight>textarea.clientHeight){
		        textarea.style.height=adjustedHeight+'px';
		    }
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