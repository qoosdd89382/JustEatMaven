<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.accountinfo.model.*"%>
<!DOCTYPE html>

<%
//當 確認註冊 取得 使用者存在頁面中的數值
AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVO"); 
AccountInfoVO accountInfoVORequest = (AccountInfoVO) request.getAttribute("accountInfoVORequest");
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
			
				<form id="register_area" method="post" action="<%=request.getContextPath()%>/Account/accountInfo.do" enctype="multipart/form-data">
				
					<span style="color:red">*</span><span>會員信箱 :</span>
					<span><%=(accountInfoVO == null) ? "" : accountInfoVO.getAccountMail()%></span><br>

					
					<span style="color:red">*</span><span>會員暱稱 :</span>
					<span><%=(accountInfoVO == null) ? "" : accountInfoVO.getAccountNickname()%></span><br>
					
					<span style="color:red">*</span><span>請輸入會員密碼:</span><br>
					<input id="input_box" type="text" name="accountPassword" 
<%-- 					value="<%=(accountInfoVO == null) ? "" : "2"%>" --%>
					placeholder="至少8~16碼任意大小寫英文數字">
					<span style="color:red">${errorMsgs.get("accountPasswordError")}</span><br>

					<span style="color:red">*</span><span>請輸入會員姓名:</span><br> 
					<input id="input_box" type="text" name="accountName" 
<%-- 					value="<%=(accountInfoVO == null) ? "" : accountInfoVORequest.getAccountName()%>" --%>
					placeholder="兩個字以上，任意 中文 英文大小寫">
					<span style="color:red">${errorMsgs.get("accountNameError")}</span><br>
					
					<br>
					
					<span style="color:red">*</span><span>請輸入會員性別 :</span>
					<input type="radio" name="accountGender" value="1" 
<%-- 					${(accountInfoVORequest.accountGender)== 1?"checked":""} --%>
					>男
					<input type="radio" name="accountGender" value="2" 
<%-- 					${(accountInfoVORequest.accountGender)== 2?"checked":""} --%>
					>女 
					<span style="color:red">${errorMsgs.get("accountGenderError")}</span><br>
					
					<br>
					
					<span style="color:red">*</span><span>請輸入會員生日</span><br>
					<input type="date" name="accountBirth" 
<%-- 					value="<%=(accountInfoVO == null) ? "" : accountInfoVORequest.getAccountBirth()%>" --%>
					>
					<span style="color:red">${errorMsgs.get("accountBirthError")}</span><br>
					
					<br>
					
					<span style="color:red">*</span><span>請輸入會員自我介紹:</span><br>
					<textarea id="textarea" name="accountText" rows="5" cols="50" onkeyup="autogrow(this)"><%-- <%=(accountInfoVO == null) ? "" : accountInfoVORequest.getAccountText()%> --%></textarea><br>
					<span style="color:red">${errorMsgs.get("accountTextError")}</span><br>
					
					<span style="color:red">* 為必填欄位，請填妥欄位資訊</span><br>
						
					<input type="hidden" name="action" value="setAccountInfoForRegister"> 
					<input id="register_submit_btn" type="submit" value="提交送出"> 
					<input id="register_reset_btn" type="reset" value="重置">
					
				</form>
			</div>
		</div>
	</div>
	<P>除錯區</P>
	<P>全部的訊息${errorMsgs}</P>
	<P>寄驗證信修改密碼</P>
	<P>備忘錄</P>
	<p>加入會員是否已存在的功能</p>
	<P>會員自我介紹框自動伸縮</P>
	<P>圖片要能夠預覽</P>	

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