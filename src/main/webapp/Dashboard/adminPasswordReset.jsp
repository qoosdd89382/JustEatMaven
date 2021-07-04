<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.admininfo.model.*" %>

<jsp:useBean id="adminSvc" class="com.admininfo.model.AdminInfoService" />

<%  
	if (request.getParameter("adminID") == null || request.getParameter("authCode") == null) {
		RequestDispatcher successView = 
				request.getRequestDispatcher("/Dashboard/adminRegisterAuth.jsp");
		return;
	}

	AdminInfoVO adminVO = adminSvc.getOneAdmin(new Integer((String) request.getParameter("adminID")));
	if (request.getParameter("adminVO") != null) {
		adminVO = (AdminInfoVO) request.getAttribute("adminVO");
	}
// 	else {
// 		session.removeAttribute("adminPicBuffer");
// 		pageContext.setAttribute("isNewAdmin", "yes");
// 	}
	pageContext.setAttribute("adminVO", adminVO);
	
	String authCode = (String) request.getParameter("authCode");
	if (!authCode.equals(adminVO.getAdminPassword())) {
		RequestDispatcher successView = 
				request.getRequestDispatcher("/Dashboard/adminResendAuth.jsp?adminID=" + adminVO.getAdminID());
		return;
	}
	pageContext.setAttribute("authCode", authCode);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin password reset</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/slick/slick.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/slick/slick-theme.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/jquery-ui/css/jquery-ui.css">
<style>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}


body {
	background: #A5D0FF;
    font-family: Arial, "微軟正黑體", Verdana, Geneva, Tahoma, sans-serif;
    height: 100%;
  display: flex;
  justify-content: center;
  align-items: center; 
  padding-top: 50px;
  padding-bottom: 50px;
  
}

.login {
	background: #fff;
	padding: 0!important;
	margin: 0!important;
}

.login-title {
	background: #007BFF;
	padding: 20px;
}

.login-title h3 {
	text-align: center;
	color: #fff;
	margin-bottom: 0!important;
}

.login-block {
	padding: 30px;
}

.login-block .form-group {
	margin-bottom: 0!important;
}

.login-block > div {
	display: inline-block;
}

.login-inner label {
	display: inline-block;
	width: 100%;
	color: #777;
	font-size: 16px;
}

.vertical-container {
	display: -webkit-flex;
	display: flex;
	-webkit-align-items: center;
	align-items: center;
	-webkit-justify-content: center;
	justify-content: center;
}

.errorSpan {
	font-size: 14px;
	color: red;
	display: block;
	margin-left: 10px;	
}
/* #submit { */
/* 	margin-top: 20px; */
/* } */
.visible-pw {
	margin-left: 10px;
}

.pre-login {
	text-align: center;
	padding: 10px;
	margin-bottom: 30px;
}
.upload-block {
	padding-bottom: 30px!important;
	margin-bottom: 30px!important;
}
.pre-login h4 {
	font-size: 22px;
}
.pre-login p {
	margin-top: 20px;
	font-size: 16px;
	
}


.preview {
	border: 1px solid lightgray;
	display: inline-block;
	position: relative;
	min-height: 100px;
/* 	border-radius: .25rem !important; */
	margin-top: 10px;
/* 	padding: 3px; */
  display: flex;
  justify-content: center;
  align-items: center; 
  overflow: hidden;
  padding: 0!important;
}

.preview span.text {
	display: inline-block;
	color: lightgray;
}

.preview_img {
	width: 100%;
}
</style>
</head>
<body>
	
	
	<div class="login rounded-lg col-10 col-md-6 col-lg-5 col-xl-4 shadow-lg">
		<div class="login-title rounded-top">
			<h3>設定管理員密碼</h3>
		</div>
		<div class="login-block row">
			<div class="login-inner col-12 form-group">
				<form method="post" action="<%= request.getContextPath() %>/Dashboard/admin.do" enctype="multipart/form-data">
				
					<div class="pre-login border-bottom">
						<h4>${adminVO.adminNickname}，您好！</h4>
						<p>認證已成功，請設定您的密碼。</p>
					</div>
					
					<div class="input-label col-12 vertical-container">
						<label for="adminMail">
							管理員信箱
							<span class="errorSpan">${errorMsgs.get("adminEmailErr")}</span>
						</label>
					</div>
					<div class="input-element col-12 input-group mb-3">
						<input id="adminMail" class="form-control form-control-lg rounded-pill" type="text" name="adminMail" value="${adminVO.adminMail}" placeholder="" disabled>
					</div>
					
					<div class="input-label col-12 vertical-container">
						<label for="adminNickname">
							管理員暱稱
							<span class="errorSpan">${errorMsgs.get("adminNicknameErr")}</span>	
						</label>
					</div>
					<div class="input-element col-12 input-group mb-3">
						<input id="adminNickname"  class="form-control form-control-lg rounded-pill" type="text" name="adminNickname" value="${adminVO.adminNickname}" disabled>
					</div>
					
					<div class="input-label col-12 vertical-container">
						<label for="adminPassword">
							密碼
							<span class="errorSpan">${errorMsgs.get("adminPasswordErr")}</span>
						</label>
					</div>
					<div class="input-element col-12 input-group mb-3">
						<input id="adminPassword" class="form-control form-control-lg rounded-pill" type="password" autocomplete="new-password" name="adminPassword">
						<div class="input-group-append">
							<button class="btn btn-secondary visible-pw form-control form-control-lg rounded-pill" type="button"><i class="fas fa-eye"></i></button>
						</div>
					</div>
					
					<div class="input-label col-12 vertical-container">
						<label for="adminPasswordRecheck">
							再次輸入密碼
							<span class="errorSpan">${errorMsgs.get("adminPasswordRecheckErr")}</span>
						</label>
					</div>
					<div class="input-element col-12 input-group mb-3">
						<input id="adminPasswordRecheck" class="form-control form-control-lg rounded-pill" type="password" autocomplete="new-password" name="adminPasswordRecheck">
						<div class="input-group-append">
							<button class="btn btn-secondary visible-pw form-control form-control-lg rounded-pill" type="button"><i class="fas fa-eye"></i></button>
						</div>
					</div>
					
					<div class="input-element col-12 input-group">
						<input type="hidden" name="adminID" value="${adminVO.adminID}">
						<input type="hidden" name="authCode" value="${authCode}">
						<input type="hidden" name="action" value="resetpwd">
						<button id="submit" class="btn btn-primary rounded-pill btn-lg btn-block" type="submit">重新設定密碼</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<%-- body 結束標籤之前，載入Bootstrap 的 JS 及其相依性安裝(jQuery、Popper) --%>
	<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.6.0.min.js"></script>
	<script	src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
	<script	src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
	<script	src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/jquery-ui/js/jquery-ui.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/footer.js"></script>
	<script>

// 	    if (sessionStorage.getItem("form_data") != null) {
// 	    	session_history = JSON.parse(sessionStorage.getItem("form_data"));
			
// 	    	if (session_history.preview_img != null) {
// 	            $('.preview').empty().append('<img src="'+ session_history.preview_img +'" class="preview_img">');
// 	    	}
	    	
// 	    }
	    
// 		var isNewAdmin = "${isNewAdmin}";
// 		if (isNewAdmin == "yes") {
// 			sessionStorage.clear();
// 		}
	    
// 		$('.visible-pw').on("click", function(e){
// 			e.preventDefault();
// 			var type = $(this).parent().prev().attr("type");
// 			if (type === 'password') {
// 				$(this).parent().prev().attr("type", "text");
// 			} else {
// 				$(this).parent().prev().attr("type", "password");
// 			}
// 		});
		

// 		$('#upload-btn').on("click", function(e){
// 			e.preventDefault();
// 	    	$('input[name="adminPic"]').trigger("click");
// 		});
		

// 		$('input[name="adminPic"]').on("change", function(e) {
// 	        previewerPic(e.target.files[0]);
// 	    });
	    

// 	    function previewerPic(file) {
// 	    	let file_reader = new FileReader();
// 	        file_reader.readAsDataURL(file);
	        
// 	        file_reader.addEventListener("load", function (e) {
// 	            $('.preview').empty().append('<img src="'+ e.target.result +'" class="preview_img">');
	            
// 	        });
// 	    };
	    

// 	    $("#submit").on("click", function() {
// 	    	var session_history = new Object();
// 	    	var step_pic = new Array();
	    	
// 	        if ($('.preview_img') != null) {
// 	            session_history['preview_img'] = $(".preview_img").attr("src");
// 	        }
// 	        sessionStorage.setItem("form_data", JSON.stringify(session_history));
	        
// 	    });
		
	</script>
</body>
</html>