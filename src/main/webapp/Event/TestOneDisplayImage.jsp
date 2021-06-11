<%@page import="com.eventinfo.model.EventInfoVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	EventInfoVO eventInfoVO = (EventInfoVO) request.getAttribute("eventInfoVO");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<img src="<%=request.getContextPath()%>/EventTestOnePic?ID=<%=eventInfoVO.getEventID()%>">
<%-- 	<img src="data:image/jpg;base64,<%= picBuffer %>"> --%>
</body>
</html>