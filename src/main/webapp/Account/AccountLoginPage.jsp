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
div#login {
	margin: 200px;
	list-style: none;
}

input#account_login_btn,
input#account_reset_btn {
	margin:5px;
	border:none;
	-webkit-border-radius: 20;
	-moz-border-radius: 20;
	border-radius: 20px;
	color: #ffffff;
	font-size: 15px;
	background: 	#FF8800;
	padding: 5px 15px 5px 15px;
	text-decoration: none;
}

input#account_login_btn:hover,
input#account_reset_btn:hover {
	background:#FFAA33;
	text-decoration: none;
}

input#account_forget_code,
input#account_register_info {
	border:none;
	font-family: Arial;
	color:#0000FF;
	font-size: 20px;
	padding: 10px 20px 10px 20px;
	background: #FFFFFF;
	text-decoration: underline;
}

</style>

<body>

	<header>
		<%@ include file="/common/header.jsp"%>
	</header>
	
	<div class="container">
		<div class="row">
			<div id="login" class="col">
				
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
					<p>借我標記一下session(EL)=>${accountMail}</p>
					<form method="post" action="accountInfo.do" name="LoginInfo">
						<b>請輸入會員信箱 (如JerryMouse@gmail.com):</b> <br>
						<input type="text" name="accountMail"> <br>
						<b>請輸入會員密碼 (如1q2w3e4r5t):(記得改TYPE))</b> <br>
						<input type="text" name="accountPassword"><br>
			
					    <%--點選圖片可進行驗證碼重新整理--%>
					    <b>請輸入驗證碼 (點擊圖片可刷新):</b> <br>
					    <input type="text" name="RandomNumberInput">
					    
					    <img name="imgValidate" src = "RandomNumber.jsp" onclick="refresh()"><br>
			    
						<input type="hidden" name="action" value="getAccountInfo_For_Login">
						<input id="account_login_btn" type="submit" value="登入">
						<input id="account_reset_btn" type="reset" value="重置">
					</form>
					
<!-- 					<form method="post" action="accountInfo.do"> -->
<!-- 						<input type="hidden" name="action" value="getAccountInfo_For_Login"> -->
<!-- 						<input id="account_forget_code" type="submit" value="忘記密碼了嗎?"> -->
<!-- 						<p>連結到寄送信箱頁面(還沒做)</p> -->
<!-- 					</form> -->
					
					<h3><a id="account_forget_code" href='#'>忘記密碼了嗎?</a></h3>
					
<!-- 					<form method="post" action="accountInfo.do"> -->
<!-- 						<input type="hidden" name="action" value="gotoAccountInfo_For_Register"> -->
<!-- 						<input id="account_register_info" type="submit" value="還不是會員?"> -->
<!-- 						<p>連結到註冊會員資料頁面(施工中)</p> -->
<!-- 					</form> -->
					
					<h3><a id="account_register_info" href='AccountRegisterPage.jsp'>還不是會員?</a></h3>
					
					<h3><a id="AccountLogin" href='AccountPage.jsp'>回到會員中心</a></h3>
					
			</div>
		</div>
	</div>
	
	<footer>
		<%@ include file="/common/footer.jsp"%>
	</footer>
	
	<script type="text/javascript">
	//頁面載入執行
// 		function window.onload(){
// 			if(window.sessionStorage){
// 				var 
// 			}
		
// 		}
	
	//驗證碼點擊更新
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