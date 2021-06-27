

<%@page import="com.productpic.model.ProductPicVO"%>
<%@page import="com.productpic.model.ProductPicJDBCDAO"%>
<%@page import="com.product.model.ProductVO"%>
<%@page import="com.product.model.ProductJDBCDAO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
ProductJDBCDAO dao = new ProductJDBCDAO();
	List<ProductVO> list = dao.getAllByClickCount();
	pageContext.setAttribute("list", list);
	
	
%>




<html>
<head>
<title>所有員工資料 - listAllEmp1_byDAO.jsp</title>

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


   <FORM METHOD="post" ACTION="emp.do" >
        <b>搜尋食材 </b>
        <input type="text" name="empno">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>



	<h4>此頁練習採用 EL 的寫法取值:</h4>
	<table id="table-1">
		<tr>
			<td>
				<h3>所有商家資料 - listAllEmp1_byDAO.jsp</h3>
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
			<th>商品流水號</th>
			<th>商家流水號</th>
			<th>商品上架狀態</th>
			<th>商品價格</th>
			<th>商品數量</th>
			<th>商品單位</th>
			<th>商品規格</th>
			<th>商品產地</th>
			<th>商品保存方式</th>
			<th>商品上架時間</th>
			<th>商品到期時間</th>
			<th>商品是否折扣</th>
			<th>商品說明</th>

		</tr>



		<c:forEach var="productVO" items="${list}">
			<tr>
				<td>${productVO.productID}</td>  
				<td>${productVO.sellerID}</td>
				<td>${productVO.productState}</td>
				<td>${productVO.productPrice}</td>
				<td>${productVO.productAmount}</td>
				<td>${productVO.productUnit}</td>
				<td>${productVO.productSpecification}</td>
				<td>${productVO.productOrigin}</td>
				<td>${productVO.productStorageMethod}</td>
				<td>${productVO.productReleaseTime}</td>
				<td>${productVO.productExpireTime}</td>
				<td>${productVO.productDiscount}</td>
				<td>${productVO.productText}</td>
				

		</c:forEach>
		
	
	
			
				

		
		
		
		
	</table>
	
	


</body>
</html>