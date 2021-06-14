<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<% int recipeID = 200001; %>
	<% for (int stepOrdIndex = 1; stepOrdIndex <= 6; stepOrdIndex++) {%>
		<img src="<%= request.getContextPath() %>/TestPic/<%= recipeID %>?step=<%= stepOrdIndex %>">
	<% } %>
	
</body>
</html>