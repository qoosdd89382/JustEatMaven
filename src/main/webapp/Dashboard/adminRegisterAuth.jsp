<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.admininfo.model.*" %>

<jsp:useBean id="adminSvc" scope="page" class="com.admininfo.model.AdminInfoService" />

<%

try {
	AdminInfoVO adminVO = adminSvc.getOneAdmin(new Integer(request.getParameter("adminID")));
	request.setAttribute("adminVO", adminVO);
	if (adminVO == null) {
		throw new Exception();
	}
} catch (Exception e) {
	response.sendRedirect(request.getContextPath());
	return;
}

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin register page</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/slick/slick.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/slick/slick-theme.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/jquery-ui/css/jquery-ui.css">
<style>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}


body {
	background: #A5D0FF;
    font-family: Arial, "微軟正黑體", Verdana, Geneva, Tahoma, sans-serif;
    height: 100%;
  display: flex;
  justify-content: center;
  align-items: center; 
  padding-top: 50px;
  padding-bottom: 50px;
  
}

.login {
	background: #fff;
	padding: 0!important;
	margin: 0!important;
}


.login-title {
	background: #007BFF;
	padding: 30px;
}

.login-title h3 {
	text-align: center;
	color: #fff;
	margin-bottom: 0!important;
}

.login-block {
	padding: 30px;
}

.login-block .form-group {
	margin-bottom: 0!important;
}

.login-block > div {
	display: inline-block;
}

.login-inner label {
	display: inline-block;
	width: 100%;
	color: #777;
	font-size: 16px;
}

.vertical-container {
	display: -webkit-flex;
	display: flex;
	-webkit-align-items: center;
	align-items: center;
	-webkit-justify-content: center;
	justify-content: center;
}

.errorSpan {
	font-size: 14px;
	color: red;
	display: block;
	margin-left: 10px;	
}
#submit {
	margin-top: 20px;
}
.pre-login {
	text-align: center;
	margin-bottom: 30px;
	padding: 10px;
}
.pre-login h4 {
	font-size: 22px;
}
.pre-login p {
	margin-top: 20px;
	font-size: 16px;
	
}
</style>
</head>
<body>
	
	<div class="login rounded-lg col-10 col-md-6 col-lg-5 col-xl-4 shadow-lg">
		<div class="login-title rounded-top">
			<h3>管理員 e-mail 認證</h3>
		</div>
		<div class="login-block row">
			<div class="login-inner col-12 form-group">
				<form method="post" action="<%= request.getContextPath() %>/Dashboard/admin.do?action=auth&adminID=${param.adminID}" >
					
					<div class="pre-login border-bottom">
						<h4>${adminVO.adminNickname}，您好！</h4>
						<p>您註冊的信箱為：<br>
							${adminVO.adminMail}<br>
							請前往收取驗證碼，或於下方輸入驗證碼。</p>
					</div>
				
<!-- 					<div class="input-label col-12 vertical-container"> -->
<!-- 						<label for="authCode"> -->
<!-- 							驗證碼 -->
<%-- 							<span class="errorSpan">${errorMsgs.get("param.")}</span> --%>
<!-- 						</label> -->
<!-- 					</div> -->
					<span class="errorSpan col-12">${errorMsgs.get("authWrongErr")}</span>
					<div class="input-element col-12 input-group mb-3">
						<input id="authCode" class="form-control form-control-lg rounded-pill" type="text" name="authCode" value="" placeholder="請輸入驗證碼">
					</div>
					
					<div class="input-element col-12 input-group">
						<input type="hidden" name="action" value="insert">
						<button id="submit" class="btn btn-primary rounded-pill btn-lg btn-block" type="submit">送出驗證</button>
					</div>
				</form>
	
			</div>
		</div>
	</div>
</body>
</html>