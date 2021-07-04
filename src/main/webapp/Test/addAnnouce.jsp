<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form action="<%=request.getContextPath() %>/Account/AnnouceTest">
		<input type="text" name="annouceText">
		<button type="submit">新增一筆公告</button>
	</form>

</body>
</html>