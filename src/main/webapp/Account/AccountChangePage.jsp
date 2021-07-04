<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.accountinfo.model.*"%>
<!DOCTYPE html>

<%
AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVOLogin"); 
//EmpServlet.java(Concroller), 存入req的VO物件
// String accountMail = request.getParameter("accountMail");
// String accountPassword = request.getParameter("accountPassword");
// String accountNickname = request.getParameter("accountNickname");
// String accountName = request.getParameter("accountName");
// Integer accountGender = Integer.parseInt(request.getParameter("accountGender"));
// String accountBirth = request.getParameter("accountBirth");
// String accountText = request.getParameter("accountText");
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

<title>揪食-修改會員資料</title>

<style>
body#body_change{
/* 	background: #ffe259;  */
/* 	background: -webkit-linear-gradient(to left, #ffa751, #ffe259);  */
/* 	background: linear-gradient(to left, #ffa751, #ffe259); */

	background-image:url("./images/AccountInfoPage.jpg");
	background-size: cover;
}

/*整個區塊 */
div#main_area{
	margin-top:80px;
}
div#change_area{
	text-align:center;
	background-color: rgba(0,0,0,0.6);
	color:white;
	
	width: 200px;
	height: expression(this.height < 100 ? "100px" : this.height "px");
	
 	margin: 35px auto; 
 	padding: 30px; 
	
	border-top-left-radius: 10px;
	border-bottom-left-radius: 10px;
	border-top-right-radius: 10px;
	border-bottom-right-radius: 10px;
	
 	box-shadow:0px 1px 2px 1px #aaaaaa, 
 	           inset 0px 1px 1px rgba(255,255,255,0.7); 
	border-radius: 3px solid orange;
}

div#change_area_title{	
	color: 	#FF8800;
	text-align:center;
	font-size:20px;
	margin-bottom:20px;
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
	border:1px gray dotted;
	height:expression(this.height < 100 ? "100px" : this.height "px");
	width:300px;
}
span.preview_pic{
	margin:0 auto;
}

input#change_submit_btn,
input#change_reset_btn {
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
span#text_title,
span#pic_title{
	color: 	#FF8800;
	font-size:20px;
}
input#change_submit_btn:hover,
input#change_reset_btn:hover {
	background:#FFAA33;
	text-decoration: none;
}
textarea#textarea {
	resize:none;
	overflow:hidden;
}

/* 預覽圖 */
div#account_pic_preview,
div#account_idcardfront_preview,
div#account_idcardback_preview
{
    border: 1px solid lightgray;
    display: inline-block;
    width: 300px;
    min-height: 150px;
    position: relative;
}
div#account_pic_preview span.text,
div#account_idcardfront_preview span.text,
div#account_idcardback_preview span.text{
    position: absolute;
    display: inline-block;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    z-index: -1;
    color: lightgray;
}
div#account_pic_preview img.preview_img,
div#account_idcardfront_preview img.preview_img,
div#account_idcardback_preview img.preview_img
{
   width: 100%;
}

</style>

</head>
<body id="body_change">
	<header>
		<%@ include file="/common/header.jsp"%>
	</header>

	<div class="container">
	
		<div class="row justify-content-center">
		
			<div id="change_area" class="col-10 col-sm-10 col-md-8 col-lg-6 col-xl-6 align-self-center">
				<div id="change_area_title">			
					<span>揪食-修改會員資料</span><br>
					<span>請修改您想更動的資料</span>
				</div>
				<form id="change" method="post" action="<%=request.getContextPath()%>/Account/accountInfo.do" enctype="multipart/form-data">
					
					<span id="text_title">會員信箱 :${accountInfoVOLogin.accountMail}</span><br> 
					<br>
					<span id="text_title">會員暱稱:${accountInfoVOLogin.accountNickname}</span><br> 
					<br>
					
					<span id="text_title">會員密碼:</span><br> 
					<input type="text" name="accountPassword" value="${accountInfoVOLogin.accountPassword}"><br>
					<span style="color:red">${errorMsgs.get("accountPasswordError")}</span><br>

					<span id="text_title">會員姓名:</span><br>
					<input type="text" name="accountName" value="${accountInfoVOLogin.accountName}"><br>
					<span style="color:red">${errorMsgs.get("accountNameError")}</span><br> 
					
					<span>會員性別 :</span> 
					<input type="radio" name="accountGender" value="1" ${(accountInfoVOLogin.accountGender)== 1?"checked":""}>男
					<input type="radio" name="accountGender" value="2" ${(accountInfoVOLogin.accountGender)== 2?"checked":""}>女 <br>
					<span style="color:red">${errorMsgs.get("accountGenderError")}</span><br> 
					
					<span id="text_title">會員生日:</span><br>
					<input type="date" name="accountBirth" value="${accountInfoVOLogin.accountBirth}"><br>
					<span style="color:red">${errorMsgs.get("accountBirthError")}</span><br> 
					
					<span id="text_title">會員電話 :</span><br>
					<input type="text" name="accountPhone" value="${accountInfoVOLogin.accountPhone}"><br> 
					<span style="color:red">${errorMsgs.get("accountPhoneError")}</span><br> 
					
					<span id="pic_title">請輸入會員照片:</span><br> 
					<input type="file" id="account_pic" name="accountPic" value="${accountInfoVOLogin.accountPic}"><br> 
					<div id="account_pic_preview">
					<img src="<%=request.getContextPath()%>/Account/Pic/Pic/<%=accountInfoVO.getAccountID()%>" width="300px" height="150px"><br>
					<span class="preview_pic">會員照片預覽圖</span></div><br>
					
					<span id="pic_title">請輸入會員身分證正面:</span><br>
					<input type="file" id="account_front" name="accountIDcardFront" value="${accountInfoVOLogin.accountIDcardFront}"><br> 
					<div id="account_idcardfront_preview">
					<img src="<%=request.getContextPath()%>/Account/Pic/Front/<%=accountInfoVO.getAccountID()%>" width="300px" height="150px"><br>						
					<span class="preview_pic">會員身分證正面預覽圖</span></div><br>
					
					<span id="pic_title">請輸入會員身分證背面:</span><br>
					<input type="file" id="account_back" name="accountIDcardBack" value="${accountInfoVOLogin.accountIDcardBack}"><br> 
					<div id="account_idcardback_preview">
					<img src="<%=request.getContextPath()%>/Account/Pic/Back/<%=accountInfoVO.getAccountID()%>" width="300px" height="150px"><br>
					<span class="preview_pic">會員身分證背面預覽圖</span></div><br>

					<span id="text_title">會員自我介紹:</span><br>
					<textarea id="textarea" name="accountText" rows="5" cols="50" onkeyup="autogrow(this)">${accountInfoVOLogin.accountText}</textarea><br>
					<span style="color:red">${errorMsgs.get("accountTextError")}</span><br>
					
					<input type="hidden" name="action" value="setAccountInfoForChange"> 
					<input id="change_submit_btn" type="submit" value="提交送出"> 
					<input id="change_reset_btn" type="reset" value="重置">
				</form>
			</div>
		</div>
	</div>

	<footer>
		<%@ include file="/common/footer.jsp"%>
	</footer>

	<script type="text/javascript">
	var account_pic_file_el = document.getElementById("account_pic");
	var account_pic_preview_el = document.getElementById("account_pic_preview");
	account_pic_file_el.addEventListener("change",function(){//file標籤物件改變
        if(this.files.length > 0){
            preview_pic_img(this.files[0]);//產生檔案物件預覽圖
        }else{
        	account_pic_preview_el.innerHTML = '<span class="text">預覽圖</span>';
        }
 	});
	
	//取得預覽圖方法
    //此方法將傳入的物件進行儲存於節點的動作
    var preview_pic_img = function(e){
        var reader = new FileReader();//建立檔案物件
        reader.readAsDataURL(e);//將傳入的物件讀取
        reader.addEventListener("load",function(){
            //產生檔案節點物件
            var img_node = document.createElement("img");
            img_node.setAttribute("src",reader.result);
            img_node.setAttribute("class","preview_img");
            //清空預覽區文字，並放入檔案節點
            account_pic_preview_el.innerHTML = "";
            account_pic_preview_el.append(img_node);
        })
    }

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