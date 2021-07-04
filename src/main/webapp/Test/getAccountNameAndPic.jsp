<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<h1>100001</h1>
	
	<%-- body 結束標籤之前，載入Bootstrap 的 JS 及其相依性安裝(jQuery、Popper) --%>
	<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.6.0.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
	<script type="text/javascript">
	
	$(function(){
		
		
		$("h1").on("click", function(){
			console.log($("h1").text());

			$.ajax({
				type : 'POST',
				url: '<c:url value="/Dashboard/getAccountInfo.do" />',
				data: {	
					'accountID': $("h1").text()
				},
				success: function (data) {
					var obj = JSON.parse(data);
					if (data != "") {
						console.log(obj.accountName);
						console.log('<c:url value="/Account/Pic/Pic" />' + "/" + obj.accountID)
					}
				}
			});
		});
		
		
	});
	
	
	</script>
</body>
</html>