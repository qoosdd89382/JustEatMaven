<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.accountinfo.model.*"%>
<!DOCTYPE html>

<%
AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVO"); 
//EmpServlet.java(Concroller), 存入req的VO物件
// String accountMail = request.getParameter("accountMail");
// String accountPassword = request.getParameter("accountPassword");
// String accountNickname = request.getParameter("accountNickname");
// String accountName = request.getParameter("accountName");
// Integer accountGender = Integer.parseInt(request.getParameter("accountGender"));
// String accountBirth = request.getParameter("accountBirth");
// String accountText = request.getParameter("accountText");
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

<title>AccountChangePage</title>

<style>

div#change {
	list-style: none;
	font-size: 25px;
}
div#change_area{
	margin-top:150px;
	border:1px solid gray;
	margin-right:300px;
}
div#second_level_register_area{
	margin-top:5px;
	border:1px solid gray;	
}

div#third_level_register_area{
	margin-top:5px;
	border:1px solid gray;
}
div#account_pic_preview,
div#account_idcardfront_preview,
div#account_idcardback_preview {
	border:1px gray dashed;
	height:120px;
	width:170px;
}
span.preview_pic{
	margin:0 auto;
}

input#change_submit_btn,
input#change_reset_btn {
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

input#change_submit_btn:hover,
input#change_reset_btn:hover {
	background:#FFAA33;
	text-decoration: none;
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
	
		<div class="row">
		
			<div id="change_area" class="col-6">
			<span>${errorMsgs}</span><br>
<%-- 			<span>EL${accountMail}</span><br> --%>
<%-- 			<span>JAVA<%=accountMail %></span><br> --%>
<%-- 			<span>JAVAVO<%=accountInfoVO.getAccountMail() %></span> --%>
				<span>請輸入您的會員資料</span><br>
				<form id="change" method="post" action="<%=request.getContextPath()%>/Account/accountInfo.do" enctype="multipart/form-data">
					<span>請輸入會員信箱 (如JerryMouse@gmail.com):</span><br> 
					
<%-- 					<input type="text" name="accountMail" value="<%=(accountMail == null) ? "" : accountMail%>"> --%>
					
					<input type="text" name="accountMail" value="${accountInfoVO.accountMail}">
					
					<span style="color:red">${errorMsgs.get("accountMailError")}</span><br> 
					
					<span>請輸入會員暱稱 (如王陽明):</span><br> 
					<input type="text" name="accountNickname" value="${accountInfoVO.accountNickname}">
					<span style="color:red">${errorMsgs.get("accountNicknameError")}</span><br> 
					
					<span>請輸入會員密碼(如1q2w3e4r5t):之後改寄驗證信修改密碼</span><br> 
					<input type="text" name="accountPassword" value="${accountInfoVO.accountPassword}"><br>
					<!-- 			<b>請輸入會員狀態 (如1):</b> -->
					<!-- 			<input type="text" name="accountState"><br> -->
					<!-- 			<b>請輸入會員層級 (如1):</b> -->
					<!-- 			<input type="text" name="accountLevel"><br> -->

					<span>請輸入會員姓名 (如王曉明):</span><br>
					<input type="text" name="accountName" value="${accountInfoVO.accountName}"><br>
					<span>請輸入會員性別 :</span> 
					<input type="radio" name="accountGender" value="1" ${(accountInfoVO.accountGender)== 1?"checked":""}>男
					<input type="radio" name="accountGender" value="2" ${(accountInfoVO.accountGender)== 2?"checked":""}>女 <br>
					<span>請輸入會員生日</span><br>
					<input type="date" name="accountBirth" value="${accountInfoVO.accountBirth}"> <br>
					<span>請輸入會員電話 (如0912345678):</span><br>
					<input type="text" name="accountPhone" value="${accountInfoVO.accountPhone}"><br> 
					<span>請輸入會員照片 :照片要用JSP照片要用JSP照片要用JSP</span>
					<input type="file" name="accountPic" value="${accountInfoVO.accountPic}"><br> 
					<span>請輸入會員身分證正面:</span>
					<input type="file" name="accountIDcardFront"><br> 
					<span>請輸入會員身分證背面:</span>
					<input type="file" name="accountIDcardBack"><br> 
					<span>請輸入會員自我介紹:(必填)</span><br>
					<textarea id="textarea" name="accountText" rows="5" cols="50" onkeyup="autogrow(this)">${accountInfoVO.accountText}</textarea><br>
					<span style="color:red">${errorMsgs.get("accountTextError")}</span><br>
					<!-- <b>請輸入會員註冊時間</b> -->
					<!-- <input type="text" name="accountPhone"><br> -->

					<input type="hidden" name="action" value="setAccountInfo_For_Change"> 
					<input id="change_submit_btn" type="submit" value="提交送出"> 
					<input id="change_reset_btn" type="reset" value="重置">
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