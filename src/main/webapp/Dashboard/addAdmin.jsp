<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.admininfo.model.*" %>

<%
	AdminInfoVO adminVO = (AdminInfoVO) request.getAttribute("adminVO");
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

html, body {
    width: 100%;
    height: 100%;
    font-family: Arial, "微軟正黑體", Verdana, Geneva, Tahoma, sans-serif;
}

body {
	background: #4e73df;
	position: relative;
}

.login-block {
	background: white;
	top: 50%;
	left: 50%;
	position: absolute;
	transform: translate(-50%, -50%);
	padding: 20px;
}
.login-block > div {
	display: inline-block;
}
.login-inner label {
	text-align: right;
}
</style>
</head>
<body>
	
	<div class="login-block rounded-lg col-10 col-xl-8 row">
		<div class="login-bg col-lg-3"></div>
		<div class="login-inner col-lg-9 form-group">
			<form class="row" method="post" action="<%= request.getContextPath() %>/Dashboard/admin.do" enctype="multipart/form-data">
				<div class="input-label col-12 col-lg-3">
					<label for="adminMail">e-mail</label>
				</div>
				<div class="input-element col-12 col-lg-9">
					<input class="form-control" type="text" name="adminMail" value="${adminVO.adminMail}" placeholder="123">
				</div>
			
			
			
			
		<label>
			password：
			<input type="password" name="adminPassword" value=""><span class="visible-pw">明碼</span>
		</label>
		
				<label>
					password again：
					<input type="password" name="adminPassword" value=""><span class="visible-pw">明碼</span>
				</label>
				
				<label>
					管理員暱稱：
					<input type="text" name="adminNickname" value="${adminVO.adminNickname}">
				</label>
				
				<label>
					個人頭像：
					<input type="file" accept="image/*" name="adminPic">
				</label>
				
				<input type="hidden" name="action" value="insert">
				<button type="submit">送出</button>
			</form>
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
		
		$('.visible-pw').on("click", function(){
			var type = $(this).prev().attr("type");
			if (type === 'password') {
				$(this).prev().attr("type", "text");
			} else {
				$(this).prev().attr("type", "password");
			}
		});
		
	</script>
</body>
</html>