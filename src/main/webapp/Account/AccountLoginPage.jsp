<!-- 
//郭建巖
//待修: 
-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap 的 CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/vendors/slick/slick.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/vendors/slick/slick-theme.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/footer.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/index.css">

<title>AccountLoginPage</title>
</head>

<style>
li#login{ 
 margin:200px; 
 list-style:none;
} 
input#test{ 

border:none;

  width：200px;  height:80px;

  box-radius:25%;

  outline:medium;

  text-align:center;

  }

</style>

<body>

	<header>
		<%@ include file="/common/header.jsp"%>
	</header>
	
	
	<li id="login">
	
		<%-- 錯誤表列 --%>
		<c:if test="${not empty errorMsgs}">
			<font style="color: red">請修正以下錯誤:</font>
			<ul>
				<c:forEach var="message" items="${requestScope.errorMsgs}">
					<li style="color: red">${message}</li>
				</c:forEach>
			</ul>
		</c:if>
		
		<b>您好，歡迎登入</b>
		
		<form method="post" action="accountInfo.do" name="LoginInfo">
			<b>請輸入會員信箱 (如JerryMouse@gmail.com):</b> <br>
			<input type="text" name="accountMail"> <br>
			<b>記得改TYPE</b>
			<b>請輸入會員密碼 (如1q2w3e4r5t):</b> <br>
			<input type="text" name="accountPassword"><br>
<!-- 			<input type="hidden" name="action" value="getOne_For_Display"> -->

	    <%--點選圖片可進行驗證碼重新整理--%>
	    ===${RandomNumber}===
	    <input type="text" name="RandomNumberInput">
	    
	    <img name="imgValidate" src = "RandomNumber.jsp" onclick="refresh()" ><br>
    
			<input type="hidden" name="action" value="getAccountInfo_For_Login">
			<input type="submit" value="登入">
			<input type="reset" value="重置">
		</form>
		
		<form method="post" action="accountInfo.do">
			<input type="hidden" name="action" value="getAccountInfo_For_Login">
			<u><b><input name="test" type="submit" value="忘記密碼了嗎?"></b></u>
		</form>
		
		<form method="post" action="accountInfo.do">
			<input type="hidden" name="action" value="setAccountInfo_For_Register">
			<u><b><input name="test" type="submit" value="還不是會員?"></b></u>
		</form>
		
		<h3><a id="AccountLogin" href='AccountPage.jsp'>回到會員中心</a></h3>
		
	</li>

	
	
	
	<footer>
		<%@ include file="/common/footer.jsp"%>
	</footer>
	
	<script type="text/javascript">
	
	    function refresh() {
	    	LoginInfo.imgValidate.src="RandomNumber.jsp?id="+Math.random();
	    }
	
	
	</script>
	
	<%-- body 結束標籤之前，載入Bootstrap 的 JS 及其相依性安裝(jQuery、Popper) --%>
	<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.6.0.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
	<%-- <script src="https://cdnjs.cloudflare.com/ajax/libs/skrollr/0.6.30/skrollr.min.js"></script> --%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/vendors/slick/slick.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/footer.js"></script>
	<script src="<%=request.getContextPath()%>/js/index.js"></script>
</body>
</html>