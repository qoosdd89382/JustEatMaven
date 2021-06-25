


<%@page import="com.recipe.model.RecipeVO"%>
<%@page import="com.recipe.model.RecipeService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<%-- �����m�߱ĥ� EL ���g�k���� --%>

<%
RecipeService recipesvc = new RecipeService();
	List<RecipeVO> list = recipesvc.getAll();
	pageContext.setAttribute("list", list);
	
	
%>




<html>
<head>
<title>����listAll</title>

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





	<h4>�����m�߱ĥ� EL ���g�k����:</h4>
	<table id="table-1">
		<tr>
			<td>
				<h3>����listAll</h3>
				<h4>
					<a href="select_page.jsp"><img src="images/back1.gif"
						width="100" height="32" border="0">�^����</a>
				</h4>
			</td>
		</tr>
	</table>

	<%-- ���~��C --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">�Эץ��H�U���~:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<h1>��������</h1>

	<table>
		<tr>
			<th>���Ьy����</th>
			<th>���ЦW��</th>
			<th>����²��</th>
			<th>���иm����</th>
			<th>���ФH��</th>
			<th>���еo��ɶ�</th>
			<th>�����s���H��</th>
			<th>���g�H��</th>
			<th>���äH��</th>
			<th>�|���s��</th>
			<th>���ʸԱ�</th>

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