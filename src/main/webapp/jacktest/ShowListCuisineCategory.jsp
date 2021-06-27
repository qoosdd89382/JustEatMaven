


<%@page import="com.cuisinecategory.model.CuisineCategoryService"%>
<%@page import="com.cuisinecategory.model.CuisineCategoryVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	CuisineCategoryService cuisinecategorysvc = new CuisineCategoryService();
	List<CuisineCategoryVO> list = cuisinecategorysvc.getAll();
	pageContext.setAttribute("list", list);
%>




<html>
<head>
<title>料理分類listAll</title>

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
				<h3>料理分類listAll</h3>
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
			<th>修改</th>

		</tr>



		<c:forEach var="cuisinecategoryVO" items="${list}">
			<tr>
				<td class="id">${cuisinecategoryVO.cuisineCategoryID}</td>
				<td>${cuisinecategoryVO.cuisineCategoryName}</td>
				<td><button class="modify">修改</button></td>
		</c:forEach>
	</table>


	<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.6.0.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
	<%-- <script src="https://cdnjs.cloudflare.com/ajax/libs/skrollr/0.6.30/skrollr.min.js"></script> --%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/vendors/slick/slick.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/footer.js"></script>

<script>

$(document).on("click", "button.modify",function(){
	
		 alert (  $(this).parents("td").prev().text() );
		 console.log("123");
		 var vall=  $(this).parents("td").prev().text();
		 
		 $(this).parents("td").prev().html("<input type='text'>");
		 $(this).parents("td").prev().find("input").val(vall); 
		 $(this).html("確定修改");
		 $(this).attr('class',"confirmModify")
		 
	});
  
  

$(document).on("click", "button.confirmModify", function(){
	$(this).parents("td").prev().text($(this).closest("td").prev().find("input").val());
	 $(this).html("修改");
	 $(this).attr('class',"modify")
	 var modifyname = $(this).parents("td").prev().text();
	 var categoryid = $(this).parent().prev().prev().text();
		console.log(modifyname);
		console.log('test'+categoryid);
		$.ajax({
			type : 'POST',
			url : 'http://localhost:8081/justeat-maven/CuisineCategoryListUpdateServlet',
			data : {
				modifyname : modifyname,
				categoryid :categoryid
			},
			success : function(result) {
				console.log(result);
				
			}

		});
		
	 
});


</script>

</body>
</html>