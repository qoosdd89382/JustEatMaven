

<%@page import="com.productpic.model.ProductPicVO"%>
<%@page import="com.productpic.model.ProductPicJDBCDAO"%>
<%@page import="com.product.model.ProductVO"%>
<%@page import="com.product.model.ProductJDBCDAO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<%-- �����m�߱ĥ� EL ���g�k���� --%>

<%
ProductJDBCDAO dao = new ProductJDBCDAO();
	List<ProductVO> list = dao.getAllByClickCount();
	pageContext.setAttribute("list", list);
	
	
%>




<html>
<head>
<title>�Ҧ����u��� - listAllEmp1_byDAO.jsp</title>

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
        <b>�j�M���� </b>
        <input type="text" name="empno">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="�e�X">
    </FORM>



	<h4>�����m�߱ĥ� EL ���g�k����:</h4>
	<table id="table-1">
		<tr>
			<td>
				<h3>�Ҧ��Ӯa��� - listAllEmp1_byDAO.jsp</h3>
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
			<th>�ӫ~�y����</th>
			<th>�Ӯa�y����</th>
			<th>�ӫ~�W�[���A</th>
			<th>�ӫ~����</th>
			<th>�ӫ~�ƶq</th>
			<th>�ӫ~���</th>
			<th>�ӫ~�W��</th>
			<th>�ӫ~���a</th>
			<th>�ӫ~�O�s�覡</th>
			<th>�ӫ~�W�[�ɶ�</th>
			<th>�ӫ~����ɶ�</th>
			<th>�ӫ~�O�_�馩</th>
			<th>�ӫ~����</th>

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