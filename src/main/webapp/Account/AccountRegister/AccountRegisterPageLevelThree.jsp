<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.accountinfo.model.*"%>
<!DOCTYPE html>

<%
//先不用
// AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVO"); 
//EmpServlet.java(Concroller), 存入req的VO物件

// String accountMail = request.getParameter("accountMail");
// String accountPassword = request.getParameter("accountPassword");
// String accountNickname = request.getParameter("accountNickname");
// String accountName = request.getParameter("accountName");
// // Integer accountGender =request.getParameter("accountGender");
// String accountBirth = request.getParameter("accountBirth");
// String accountText = request.getParameter("accountText");

//當 確認註冊 取得 使用者存在頁面中的數值
AccountInfoVO accountInfoVO = (AccountInfoVO) request.getAttribute("accountInfoVO"); 

%>

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

<title>揪食-會員註冊</title>

<style>
div#register_area{
	margin-top:150px;
	border:1px solid gray;
	margin-right:300px;
}
div#second_level_register_area{
	margin-top:5px;
	border:1px solid gray;	
}

div#third_level_register_area{
	margin-top:5px;
	border:1px solid gray;
}
div#account_pic_preview,
div#account_idcardfront_preview,
div#account_idcardback_preview {
	border:1px gray dashed;
	height:120px;
	width:170px;
}
span.preview_pic{
	margin:0 auto;
}

input#register_submit_btn,
input#register_reset_btn {
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

input#register_submit_btn:hover,
input#register_reset_btn:hover {
	background:#FFAA33;
	text-decoration: none;
}

textarea#textarea {
	resize:none;
	overflow:hidden;
}

</style>

</head>
<body>
	<header>
		<%@ include file="/common/header.jsp"%>
	</header>

	<div class="container">
	
		<div id="main_area" class="row">
		
			<div id="register_area" class="col offset-3">
			
			<Strong>您好~歡迎來到揪食!</Strong><br>
			<strong>請輸入您的資料協助您成為我們的一員</strong>
			
				<form method="post" action="<%=request.getContextPath()%>/Account/accountInfo.do" enctype="multipart/form-data">
					<strong>如要參加活動請提供以下資料</strong>
				
					<div id="second_level_register_area">
					<span>請輸入會員電話 (如0912345678):</span><br>
					<input type="text" name="accountPhone">
					<span style="color:red">${errorMsgs.get("accountPhoneError")}</span><br> 
					</div>

					<div id="third_level_register_area">
					<span>請輸入會員照片:</span><br> 
					<input type="file" name="accountPic"><br> 
					<div id="account_pic_preview"><span class="preview_pic">會員照片預覽圖</span></div>
					
					<span>請輸入會員身分證正面:</span><br>
					<input type="file" name="accountIDcardFront"><br> 
					<div id="account_idcardfront_preview"><span class="preview_pic">會員身分證正面預覽圖</span></div>
					
					<span>請輸入會員身分證背面:</span><br>
					<input type="file" name="accountIDcardBack"><br> 
					<div id="account_idcardback_preview"><span class="preview_pic">會員身分證背面預覽圖</span></div>
					</div>
					

					<input type="hidden" name="action" value="setLevelThreeAccountInfoForRegister"> 
					<input id="register_submit_btn" type="submit" value="提交送出"> 
					<input id="register_reset_btn" type="reset" value="重置">
				</form>
			</div>
		</div>
	</div>
	<P>除錯區</P>
	全部的訊息${errorMsgs}
	<P>備忘錄</P>
	<p>加入會員是否已存在的功能</p>
	<P>會員自我介紹框自動伸縮</P>
	<P>圖片要能夠預覽</P>	

	<h3><a id="AccountLogin" href='AccountPage.jsp'>回到會員中心</a></h3>

	<footer>
		<%@ include file="/common/footer.jsp"%>
	</footer>
	
	<script type="text/javascript">
	//功能區
		function autogrow(textarea){
		var adjustedHeight=textarea.clientHeight;
		
		    adjustedHeight=Math.max(textarea.scrollHeight,adjustedHeight);
		    
		    if (adjustedHeight>textarea.clientHeight){
		        textarea.style.height=adjustedHeight+'px';
		    }
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