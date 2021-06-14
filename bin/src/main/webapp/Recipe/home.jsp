<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="java.util.*"%>
<%-- <%@ page import="com.*"%> --%>
	
	
<%
// 	Map<String, String> allPageNames = (HashMap) application.getAttribute("allPageNames");
// 	allPageNames.put("recipeHome", "食譜");
// 	application.setAttribute("allPageNames", allPageNames);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${allPageNames.recipeHome} | ${allPageNames.webIndex}</title>
</head>
<body>

	<ul>
		<li><a href="<%=request.getContextPath()%>/Recipe/addRecipe.jsp">新增食譜</a></li>
		<li><a href="<%=request.getContextPath()%>/Recipe/listAllRecipe.jsp">食譜列表</a></li>
	</ul>
	
</body>
</html>