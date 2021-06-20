<%@page import="com.eventinfo.model.EventInfoJDBCDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<Form method="get" action="/JustEat/EventTestOnePic">
	<input type="number" min="300000" max="309999" value="" placeholder="請輸入要查詢的數字" name="EventID">
	<input type="submit">
	</Form>

</body>
</html>