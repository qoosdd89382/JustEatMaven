


<%@page import="com.recipe.model.RecipeVO"%>
<%@page import="com.recipe.model.RecipeService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
RecipeService recipesvc = new RecipeService();
	List<RecipeVO> list = recipesvc.getAll();
	pageContext.setAttribute("list", list);
	
	
%>




<html>
<head>
<title>食譜listAll</title>

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
				<h3>食譜listAll</h3>
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
			<th>食譜流水號</th>
			<th>食譜名稱</th>
			<th>食譜簡介</th>
			<th>食譜置頂照</th>
			<th>食譜人份</th>
			<th>食譜發表時間</th>
			<th>食譜瀏覽人次</th>
			<th>按讚人數</th>
			<th>收藏人數</th>
			<th>會員編號</th>
			<th>活動詳情</th>

		</tr>



		<c:forEach var="recipeVO" items="${list}">
			<tr>
				<td>${recipeVO.recipeID}</td>  
				<td>${recipeVO.recipeName}</td>  
				<td>${recipeVO.recipeIntroduction}</td>  
				<td>${recipeVO.recipePicTop}</td>  
				<td>${recipeVO.recipeServe}</td>  
				<td>${recipeVO.recipeTime}</td>  
				<td>${recipeVO.recipeViewCount}</td>  
				<td>${recipeVO.recipeLikeCount}</td>  
				<td>${recipeVO.recipeCollectCount}</td>  
				<td>${recipeVO.accountID}</td>  

		</c:forEach>
		
	
	
			
				

		
		
		
		
	</table>
	
	


</body>
</html>