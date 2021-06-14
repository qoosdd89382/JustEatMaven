<%@page import="com.eventinfo.model.EventInfoJDBCDAO"%>
<%@page import="com.eventinfo.model.EventInfoVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>測試顯示全部圖片</title>
</head>
<body>
	<%
		EventInfoJDBCDAO dao = new EventInfoJDBCDAO();
		for (int i = 0; i <= dao.getAll().size() - 1; i++) {
	%>
	<img src="<%=request.getContextPath()%>/EventTestAllPic?image=<%=i%>">
	<%
		}
	%>
</body>
</html>