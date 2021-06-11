<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
	<table border=1>
			<tr>
				<th>test</th>
				<th>test</th>
			</tr>
			<tr>
				<td>111</td>
				<td>帳號不可為空白</td>
			</tr>
	</table>
	
	<ul>
		<li>1</li>
		<li>2</li>
	</ul>
	
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
			<input type="text" name="adminMail">
		</label>
		
		<label>
			password：
			<input type="password" name="adminPassword">
		</label>
		
		<label>
			管理員暱稱：
			<input type="text" name="adminNickname">
		</label>
		
		<label>
			個人頭像：
			<input type="file" name="adminPic">
		</label>
		
		<input type="hidden" name="action" value="addAdminInfo">
		<button type="submit">送出</button>
	</form>

</body>
</html>