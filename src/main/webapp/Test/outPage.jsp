<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<%@ page import="java.util.List"%>
<%@ page import="com.admininfo.model.*" %>

<jsp:useBean id="adminChatSvc" class="com.adminchatroom.model.AdminChatroomService" />
<jsp:useBean id="adminSvc" class="com.admininfo.model.AdminInfoService" />
<jsp:useBean id="accounrSvc" class="com.accountinfo.model.AccountInfoService" />

<%

// 	AdminInfoVO loginAdmin = adminSvc.getOneAdmin((int) session.getAttribute("loginAdminID"));

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
        integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">

    <link rel="stylesheet" href="<%= request.getContextPath() %>/common/css/adminChat.css">
<style>

</style>
</head>
<!-- <body onload="connect();" onunload="disconnect();"> -->
<body>


			<%@ include file="/common/adminChat.page"%>



    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <!-- 載入 Font Awesome -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>
<script src="<%=request.getContextPath()%>/common/js/dateFormat.js"></script>
<script>
window.onload = function() {
	<%@ include file="/common/js/adminChatJs.page"%>


}
</script>
</body>
</html>