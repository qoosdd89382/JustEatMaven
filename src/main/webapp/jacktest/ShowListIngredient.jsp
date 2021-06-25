


<%@page import="com.ingredient.model.IngredientVO"%>
<%@page import="com.ingredient.model.IngredientService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
IngredientService ingredientsvc = new IngredientService();
	List<IngredientVO> list = ingredientsvc.getAll();
	pageContext.setAttribute("list", list);
	
	
%>




<html>
<head>
<title>食材標籤listAll</title>

<style>
table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}
</style>

<style>
table {
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 5px;
	text-align: center;
}
</style>

</head>
<body bgcolor='white'>


 



	<h4>此頁練習採用 EL 的寫法取值:</h4>
	<table id="table-1">
		<tr>
			<td>
				<h3>食材標籤listAll</h3>
				<h4>
					<a href="select_page.jsp"><img src="images/back1.gif"
						width="100" height="32" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<h1>熱門食材</h1>

	<table>
		<tr>
			<th>料理分類</th>
			<th>料理分類名稱</th>
			

		</tr>



		<c:forEach var="ingredientVO" items="${list}">
			<tr>
				<td>${ingredientVO.ingredientID}</td>  
				<td>${ingredientVO.ingredientName}</td>  
				

		</c:forEach>
		
	
	
			
				

		
		
		
		
	</table>
	
	


</body>
</html>