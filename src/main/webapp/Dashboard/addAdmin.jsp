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
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/footer.css">
<style>
label {
	display: block;
}
</style>
</head>
<body>

	<form method="post" action="<%= request.getContextPath() %>/Dashboard/admin.do">
		<label>
			e-mail：
			<input type="text" name="adminMail" value="${adminVO.adminMail}" placeholder="123">
		</label>
		
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