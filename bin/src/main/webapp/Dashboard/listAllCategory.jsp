<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="java.util.*"%>
<%@ page import="com.cuisinecategory.model.*"%>

<%
CuisineCategoryService catSvc = new CuisineCategoryService();
List<CuisineCategoryVO> list = catSvc.getAll();
pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

		<table border=1>
			<tr>
				<th>料理分類編號</th>
				<th>料理分類名稱</th>
			</tr>
			
	<c:forEach var="cuisineCategoryVO" items="${list}">
			<tr>
				<td>${cuisineCategoryVO.cuisineCategoryID}</td>
				<td>${cuisineCategoryVO.cuisineCategoryName}</td>
			</tr>
	</c:forEach>
		</table>







</body>
</html>