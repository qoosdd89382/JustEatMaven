<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.admininfo.model.*" %>

<jsp:useBean id="adminSvc" class="com.admininfo.model.AdminInfoService" />

<%
	AdminInfoVO adminVO = (AdminInfoVO) request.getAttribute("adminVO");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin password reset</title>
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

html, body {
    width: 100%;
    height: 100%;
    font-family: Arial, "微軟正黑體", Verdana, Geneva, Tahoma, sans-serif;
}

body {
	background: #A5D0FF;
	position: relative;
}

.login {
	background: #fff;
	top: 50%;
	left: 50%;
	position: absolute;
	transform: translate(-50%, -50%);
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
	display: inline-block;
	margin-left: 10px;	
}
#submit {
	margin-top: 20px;
}
.visible-pw {
	margin-left: 10px;
}

</style>
</head>
<body>
	
	
	<div class="login rounded-lg col-10 col-md-6 col-lg-5 col-xl-4 shadow-lg">
		<div class="login-title rounded-top">
			<h3>設定管理員密碼</h3>
		</div>
		<div class="login-block row">
			<div class="login-inner col-12 form-group">
				<form class="row" method="post" action="<%= request.getContextPath() %>/Dashboard/admin.do" enctype="multipart/form-data">
				
					<div class="input-label col-12 vertical-container">
						<label for="adminMail">
							管理員信箱
							<span class="errorSpan">${errorMsgs.get("adminEmailErr")}</span>
						</label>
					</div>
					<div class="input-element col-12 input-group mb-3">
						<input id="adminMail" class="form-control form-control-lg rounded-pill" type="text" name="adminMail" value="${adminSvc.getOneAdmin(param.adminID).adminMail}" placeholder="" disabled>
					</div>
					
					<div class="input-label col-12 vertical-container">
						<label for="adminNickname">
							管理員暱稱
							<span class="errorSpan">${errorMsgs.get("adminNicknameErr")}</span>	
						</label>
					</div>
					<div class="input-element col-12 input-group mb-3">
						<input id="adminNickname"  class="form-control form-control-lg rounded-pill" type="text" name="adminNickname" value="${adminSvc.getOneAdmin(param.adminID).adminNickname}" disabled>
					</div>
					
					<div class="input-label col-12 vertical-container">
						<label for="adminPassword">
							密碼
							<span class="errorSpan">${errorMsgs.get("adminPasswordErr")}</span>
						</label>
					</div>
					<div class="input-element col-12 input-group mb-3">
						<input id="adminPassword" class="form-control form-control-lg rounded-pill" type="password" name="adminPassword" aria-describedby="button-addon2">
						<div class="input-group-append">
							<button class="btn btn-primary visible-pw form-control form-control-lg rounded-pill" type="button" id="button-addon2"><i class="fas fa-eye"></i></button>
						</div>
					</div>
					
<!-- 					<div class="input-label col-12 vertical-container"> -->
<!-- 						<label for="adminPasswordRecheck"> -->
<!-- 							請再次輸入密碼 -->
<%-- 							<span class="errorSpan">${errorMsgs.get("adminPasswordRecheckErr")}</span> --%>
<!-- 						</label> -->
<!-- 					</div> -->
<!-- 					<div class="input-element col-12 input-group mb-3"> -->
<!-- 						<input id="adminPasswordRecheck" class="form-control" type="password" name="adminPasswordRecheck" aria-describedby="button-addon2"> -->
<!-- 						<div class="input-group-append"> -->
<!-- 							<button class="btn btn-primary visible-pw" type="button" id="button-addon2"><i class="fas fa-eye"></i></button> -->
<!-- 						</div> -->
<!-- 					</div> -->
					
					<div class="input-element col-12 input-group">
						<input type="hidden" name="action" value="pwreset">
						<button id="submit" class="btn btn-primary rounded-pill btn-lg btn-block" type="submit">設定密碼</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<%-- body 結束標籤之前，載入Bootstrap 的 JS 及其相依性安裝(jQuery、Popper) --%>
	<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.6.0.min.js"></script>
	<script	src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
	<script	src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
	<script	src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/jquery-ui/js/jquery-ui.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/footer.js"></script>
	<script>
		
		$('.visible-pw').on("click", function(e){
			e.preventDefault();
			var type = $(this).parent().prev().attr("type");
			if (type === 'password') {
				$(this).parent().prev().attr("type", "text");
			} else {
				$(this).parent().prev().attr("type", "password");
			}
		});
		
	</script>
</body>
</html>