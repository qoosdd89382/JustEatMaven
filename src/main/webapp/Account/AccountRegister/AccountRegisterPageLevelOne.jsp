<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.accountinfo.model.*"%>

<jsp:useBean id="accountInfoSvc" scope="page" class="com.accountinfo.model.AccountInfoService" />


<!DOCTYPE html>

<%
AccountInfoVO accountInfoVO = null;

//用來返回錯誤訊息
if(request.getAttribute("accountInfoVO") != null){
	accountInfoVO = (AccountInfoVO) request.getAttribute("accountInfoVO");
//從信箱過來的一定有SESSION 前面網頁也會存SESSION
}else if (session.getAttribute("accountInfoVO") != null){
// 	//取得信箱中的ID 並且取得信箱
	accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVO");
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

<title>揪食-一般會員註冊</title>

<style>
body#body_register{
/* 	background: #ffe259;  */
/* 	background: -webkit-linear-gradient(to left, #ffa751, #ffe259);  */
/* 	background: linear-gradient(to left, #ffa751, #ffe259); */

	background-image:url("./images/LoginBackGround.jpg");
	background-size: cover;
	background-attachment:fixed; 
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
	height: expression(this.height < 100 ? "100px" : this.height "px");
	
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
div#register_area_title{
	color: 	#FF8800;
	font-size:20px;
}
input#input_box{
	border-top-left-radius: 10px;
	border-bottom-left-radius: 10px;
	border-top-right-radius: 10px;
	border-bottom-right-radius: 10px;
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
		
			<div id="register_area" class="col-10 col-sm-10 col-md-8 col-lg-6 col-xl-6 align-self-center">
			<div id="register_area_title">
			<Strong>您好~歡迎來到揪食!</Strong><br>
			<strong>請輸入以下資料協助您成為我們的一員</strong>
			</div>
			
				<form id="register_area" method="post" action="<%=request.getContextPath()%>/Account/accountInfo.do" enctype="multipart/form-data">
				
					<span>會員信箱 :</span>
					<span><%=(accountInfoVO.getAccountMail() == null) ? "" : accountInfoVO.getAccountMail()%></span><br>
					
					<span style="color:red">*</span><span>請輸入會員暱稱:</span><br>
					<input id="input_box" type="text" name="accountNickname" 
					class='${errorMsgs.get("accountNicknameError") == null ? "": "border-danger"}'					
					value="<%=(accountInfoVO.getAccountNickname() == null) ? "" : accountInfoVO.getAccountNickname()%>"
					placeholder="至少兩個字以上，任意 中文 數字 英文大小寫"><br>
					<span style="color:red">${errorMsgs.get("accountNicknameError")}</span><br>
					
					
					<span style="color:red">*</span><span>請輸入會員密碼:</span><br>
					<input id="input_box" type="password" name="accountPassword" 
					class='${errorMsgs.get("accountPasswordError") == null ? "": "border-danger"}'
					value="<%=(accountInfoVO.getAccountPassword() == null) ? "" : accountInfoVO.getAccountPassword()%>"
					placeholder="至少8~16碼任意大小寫英文數字"><br>
					<span style="color:red">${errorMsgs.get("accountPasswordError")}</span><br>

					<span style="color:red">*</span><span>請再次輸入會員密碼:</span><br>
					<input id="input_box" type="password" name="accountPasswordRepeat" 
					class='${errorMsgs.get("accountPasswordRepeatError") == null ? "": "border-danger"}'
					value="<%=(accountInfoVO.getAccountPassword() == null) ? "" : accountInfoVO.getAccountPassword()%>"
					placeholder="至少8~16碼任意大小寫英文數字"><br>
					<span style="color:red">${errorMsgs.get("accountPasswordRepeatError")}</span><br>
					
					<span style="color:red">*</span><span>請輸入會員姓名:</span><br> 
					<input id="input_box" type="text" name="accountName" 
					class='${errorMsgs.get("accountNameError") == null ? "": "border-danger"}'
					value="<%=(accountInfoVO.getAccountName() == null) ? "" : accountInfoVO.getAccountName()%>"
					placeholder="兩個字以上，任意 中文 英文大小寫"><br>
					<span style="color:red">${errorMsgs.get("accountNameError")}</span><br>
					
					<br>
					
					<span style="color:red">*</span><span>請輸入會員性別 :</span>
					<input type="radio" name="accountGender" value="1" 
					${(accountInfoVO.accountGender)== 1?"checked":""}
					>男
					<input type="radio" name="accountGender" value="2" 
					${(accountInfoVO.accountGender)== 2?"checked":""}
					>女 
					<span style="color:red">${errorMsgs.get("accountGenderError")}</span><br>
					
					<br>
					
					<span style="color:red">*</span><span>請輸入會員生日</span><br>
					<input type="date" name="accountBirth" 
					value="<%=(accountInfoVO.getAccountBirth() == null) ? "" : accountInfoVO.getAccountBirth()%>"
					>
					<span style="color:red">${errorMsgs.get("accountBirthError")}</span><br>
					
					<br>
					
					<span style="color:red">*</span><span>請輸入會員自我介紹:</span><br>
					<textarea id="textarea" name="accountText" rows="5" cols="50" onkeyup="autogrow(this)"><%=(accountInfoVO.getAccountText() == null) ? "" : accountInfoVO.getAccountText()%></textarea><br>
					<span style="color:red">${errorMsgs.get("accountTextError")}</span><br>
					
					<span style="color:red">* 為必填欄位，請填妥欄位資訊</span><br>
					
					<input type="hidden" name="action" value="setAccountInfoForRegister"> 
					<input id="register_submit_btn" type="submit" value="提交送出"> 
					<input id="register_reset_btn" type="reset" value="重置">
					
				</form>
			</div>
		</div>
	</div>

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