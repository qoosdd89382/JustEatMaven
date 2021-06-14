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
<style>
label {
	display: block;
}
</style>
</head>
<body>
	
	<c:if test="${not empty errorMsgs}">
		<ul>
			<c:forEach var="msg" items="${errorMsgs}">
				<li>${msg}</li>
			</c:forEach>
		</ul>
	</c:if>
	
	<form method="post" action="admin.do">
		<label>
			e-mail：
			<input type="text" name="adminMail" value="<%= (adminVO==null) ? "" : adminVO.getAdminMail() %>" placeholder="123">
		</label>
		
		<label>
			password：
			<input type="password" name="adminPassword" value="<%= (adminVO==null) ? "" : adminVO.getAdminPassword() %>">
		</label>
		
		<label>
			管理員暱稱：
			<input type="text" name="adminNickname" value="<%= (adminVO==null) ? "" : adminVO.getAdminNickname() %>">
		</label>
		
		<label>
			個人頭像：
			<input type="file" accept="image/*" name="adminPic">
		</label>
		
		<input type="hidden" name="action" value="insert">
		<button type="submit">送出</button>
	</form>

</body>
</html>