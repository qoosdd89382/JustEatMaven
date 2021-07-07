<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="java.util.*"%>
<%@ page import="com.accountinfo.model.*"%>
<%@ page import="com.visit.model.*"%>
<%@ page import="com.likeingredient.model.*"%>
<%@ page import="com.dislikeingredient.model.*"%>

<jsp:useBean id="accountSvc" scope="page" class="com.accountinfo.model.AccountInfoService" />
<jsp:useBean id="visitSvc" scope="page" class="com.visit.model.VisitService" />
<jsp:useBean id="ingredientSvc" scope="page" class="com.ingredient.model.IngredientService" />
<jsp:useBean id="likeIngredientSvc" scope="page" class="com.likeingredient.model.LikeIngredientService" />
<jsp:useBean id="dislikeIngredientSvc" scope="page" class="com.dislikeingredient.model.DislikeIngredientService" />

<%
//儲存所有資料的accountInfoVO
AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVOLogin"); 
Integer accountID = accountInfoVO.getAccountID();

pageContext.setAttribute("nowAccount", accountSvc.selectOneAccountInfo(accountID));
session.setAttribute("nowAccount",  accountSvc.selectOneAccountInfo(accountID));
//會員瀏覽紀錄
//上一次瀏覽紀錄
VisitVO lastVisitRecord = visitSvc.getLastVisitRecordByAccountID(accountID);
pageContext.setAttribute("lastVisitRecord",lastVisitRecord);
//這次的瀏覽紀錄
VisitVO latestVisitRecord = visitSvc.getLatestVisitRecordByAccountID(accountID);
pageContext.setAttribute("latestVisitRecord",latestVisitRecord);
//相差的時間
VisitVO periodVisitRecord = visitSvc.getPeriodVisitRecordByAccountID(accountID);
pageContext.setAttribute("periodVisitRecord",periodVisitRecord);

//會員喜歡不喜歡的食材
List<LikeIngredientVO> likeIngredientVOs = likeIngredientSvc.getAccountLikeIngredient(accountID);
pageContext.setAttribute("likeIngredientVOs",likeIngredientVOs);
List<DislikeIngredientVO> dislikeIngredientVOs = dislikeIngredientSvc.getAccountDislikeIngredient(accountID);
pageContext.setAttribute("dislikeIngredientVOs",dislikeIngredientVOs);
%>
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

<title>揪食-會員資料</title>
<style>
body#body_accountInfo{
/* 	background: #ffe259;  */
/* 	background: -webkit-linear-gradient(to left, #ffa751, #ffe259);  */
/* 	background: linear-gradient(to left, #ffa751, #ffe259); */

	background-image:url("<c:url value="/Account/images/AccountInfoPage.jpg" />");
	background-size: cover;
/* 	background-attachment:fixed;  */
/* 	background-repeat: no-repeat; */
}
div#main_block{
	margin-top:150px;
}
/* 歡迎區塊 */
div#account_welcom{
	text-align:center;
	margin:15px;
	font-size:30px;
	
}
/* 用戶名稱 */
div#account_info_area_title{
	margin:20px;
	text-align:center;
}
/* 按鈕位置 */
div#function_select_area{
	text-align:center;
}
/* 會員圖片 */
div#account_picture{
	text-align:center;
}
/* 會員資訊框 */
div#account_info_area{;
	background-color: rgba(0,0,0,0.6);
	color:white;
	
	margin-top:20px;
	
	border-top-left-radius: 10px;
	border-bottom-left-radius: 10px;
	border-top-right-radius: 10px;
	border-bottom-right-radius: 10px;
	
 	box-shadow:0px 1px 2px 1px #aaaaaa, 
 	           inset 0px 1px 1px rgba(255,255,255,0.7); 
	border-radius: 3px solid orange;
 	
}

div#account_info_area form,
div#account_info_area span {
	font-size:25px;
	margin:30px;
}
div#function_select_area_button button,
div#function_select_area_button a {
	margin:10px;
   padding: 16px 24px;
   border: 1px solid #F0C21D;
   background: -webkit-gradient(linear, left top, left bottom, from(#E8CFB7), to(#FFA10A));
   background: -webkit-linear-gradient(top, #E8CFB7, #FFA10A);
   background: -moz-linear-gradient(top, #E8CFB7, #FFA10A);
   background: -ms-linear-gradient(top, #E8CFB7, #FFA10A);
   background: -o-linear-gradient(top, #E8CFB7, #FFA10A);
   background-color: #FFA10A;
   box-shadow: 0px 7px 2px -5px #1A1A1A, inset 0px 0px 5px #C97C38;
   -webkit-box-shadow: 0px 7px 2px -5px #1A1A1A, inset 0px 0px 5px #C97C38;
   -moz-box-shadow: 0px 7px 2px -5px #1A1A1A, inset 0px 0px 5px #C97C38;
   -webkit-border-radius: 34px;
   -moz-border-radius: 34px;
   border-radius: 34px;
   text-shadow: #FFD58C 1px 1px 0px;
   color: #000000;
   font-size: 20px;
   font-family: '微軟正黑體',Arial;
   text-decoration: none;
   font-weight: bold;
   -webkit-transition: 0.4s;
   -moz-transition: 0.4s;
   -o-transition: 0.4s;
   cursor: pointer;
   }
div#function_select_area_button button:hover,
div#function_select_area_button a:hover {
   background: none;
   background-color: #FFA10A;
   box-shadow: 0px 0px 5px 0px #AAAAAA;
   -webkit-box-shadow: 0px 0px 5px 0px #AAAAAA;
   -moz-box-shadow: 0px 0px 5px 0px #AAAAAA;
   border: 1px solid #ffffff;
   color: #000000;
   }
div#function_select_area_button button:active.   
div#function_select_area_button a:active {
   top: 1px;
   position: relative;
   }
/* 修改資料 登出按鈕 */
button#account_change_info,
button#account_logout {
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
button#account_change_info:hover,
button#account_logout:hover {
	background:#FFAA33;
	text-decoration: none;
}
div#account_food_area{
	text-align:center;
	
	margin:20px; 
	
	border:1px solid orange;
	border-top-left-radius: 10px;
	border-bottom-left-radius: 10px;
	border-top-right-radius: 10px;
	border-bottom-right-radius: 10px;
}
div#account_food{
	font-size:20px;
}
div#account_food a{
	font-size:20px;
	color:white;
}
div#account_food a:hover{
	font-size:20px;
	transition: 0.2s;
    color: #F3722C;
}
span#text_title_main{
	color: 	#FF8800;
	font-size:40px !important;
}
span#text_title{
	color: 	#FF8800;
	font-size:25px;
	margin:15px;
	margin-left:100px !important;
}
span#text_content{
	font-size:25px;
	margin:5px !important;
}
span#text_title_2{
	color: 	#FF8800 ;
	font-size:25px;
}

</style>
</head>
<body id="body_accountInfo">

	<header>
		<%@ include file="/common/header.jsp"%>
	</header>
	
	<div class="container" id="main_block">
	
		<div id="account_welcom" class="row justify-content-center" >

<!-- 		暫時封印		 -->
			<div class="col-12 col-md-10">
				<span>歡迎來到揪食JustEat~!</span><br>
<%-- 				<fmt:formatDate value="${periodVisitRecord.visitRecord}" pattern="dd.hh.mm.ss"/> --%>
			</div>
			
		</div>
		
		<div id="function_select_area" class="row justify-content-center">
		
			<div id="function_select_area_button" class="col-6 col-md-2">
				<form method="post" action="<%=request.getContextPath()%>/Account/accountInfo.do">
					<input type="hidden" name="action" value="gotoAccountInfoPage">
					<button id="btn_submit" type="submit">會員資料</button>
				</form>		
			</div>
			
			<div id="function_select_area_button" class="col-6 col-md-2">
				<form method="post" action="<%=request.getContextPath()%>/Account/friendship.do">
					<input type="hidden" name="action" value="getAccount_Friendship">
					<button id="btn_submit" type="submit">我的好友</button>
				</form>
			</div>
			
			<div id="function_select_area_button" class="col-6 col-md-2 align-self-center">
				<a href="<%=request.getContextPath()%>/Event/MyEvent.jsp?accountID=${nowAccount.accountID}">我的活動</a>
			</div>
			
			<div id="function_select_area_button" class="col-6 col-md-2 align-self-center">
				<a href='<%=request.getContextPath()%>/Recipe/recipe.do?action=myRecipe&id=${nowAccount.accountID}'>我的食譜</a>
			</div>
	
		</div>
			
		<div class="row justify-content-center">
			
			<div id="account_info_area" class="col-10 col-sm-10 col-md-8 col-lg-8 col-xl-8 align-self-center" >
			
				<div id="account_info_area_title">
					<span style="text-align:center">用戶</span><span id="text_title_main">${nowAccount.accountNickname}</span>
					<span style="text-align:center">以下是您的資料</span><br>				
				</div>
				
				<span id="text_title">用戶信箱:</span><span id="text_content">${nowAccount.accountMail}</span><br>
				<span id="text_title">用戶暱稱:</span><span id="text_content">${nowAccount.accountNickname}</span><br>
				
				<c:if test="${nowAccount.accountLevel==1}">
					<span id="text_title">用戶層級:</span><span id="text_content">一般會員</span><br>				
				</c:if>
				<c:if test="${nowAccount.accountLevel==2}">
					<span id="text_title">用戶層級:</span><span id="text_content">一般會員(待審核)</span><br>				
				</c:if>
				<c:if test="${nowAccount.accountLevel==3}">
					<span id="text_title">用戶層級:</span><span id="text_content">特權會員</span><br>				
				</c:if>
				<c:if test="${nowAccount.accountLevel<3}">
					<span id="text_title" style="color:red">${errorMsgsLevel.get("accountLevelFromEventError")}</span><br>
				</c:if>
				
				<br>				
				<span id="text_title">用戶名稱:</span><span id="text_content">${nowAccount.accountName}</span><br>
				<span id="text_title">用戶性別:</span><span id="text_content">${(nowAccount.accountGender==1?"男":"女")}</span><br>
				<span id="text_title">用戶生日:</span><span id="text_content">${nowAccount.accountBirth}</span><br>
				<span id="text_title">用戶電話:</span><span id="text_content">${nowAccount.accountPhone}</span><br>
				
				<br>
				
				<div id="account_picture">
					<span id="text_title_2">用戶照片:</span><br>					
					<img src="<%=request.getContextPath()%>/Account/Pic/Pic/${nowAccount.accountID}" width="300px" height="150px"><br>
						
					<span id="text_title_2">用戶身分證正面:</span><br>
					<img src="<%=request.getContextPath()%>/Account/Pic/Front/${nowAccount.accountID}" width="300px" height="150px"><br>
					
					<span id="text_title_2">用戶身分證背面:</span><br>
					<img src="<%=request.getContextPath()%>/Account/Pic/Back/${nowAccount.accountID}" width="300px" height="150px"><br>
				</div>
				
				<div id="account_food_area">
				<span id="text_title_2">請點選您喜歡的食材</span>
					<c:forEach var="likeIngredientVO" items="${likeIngredientVOs}">
						<div id="account_food">
						<a href="<%= request.getContextPath() %>/Recipe/recipe.do?action=listAllByIngredient&id=${likeIngredientVO.likeIngredientID}">
						${ingredientSvc.getOneIngredient(likeIngredientVO.likeIngredientID).ingredientName}
						</a>
						</div>
					</c:forEach>
				</div>
				
				<div id="account_food_area">
				<span id="text_title_2">以下是您討厭的食材</span>
					<c:forEach var="dislikeIngredientVO" items="${dislikeIngredientVOs}">
						<div id="account_food">
						<a href="<%= request.getContextPath() %>/Recipe/recipe.do?action=listAllByIngredient&id=${dislikeIngredientVO.dislikeIngredientID}">
						${ingredientSvc.getOneIngredient(dislikeIngredientVO.dislikeIngredientID).ingredientName}
						</a>
						</div>
					</c:forEach>
				</div>
							
				<div id="account_food_area">
					<span id="text_title_2">用戶自我介紹:<br></span>${nowAccount.accountText}<br>
				</div>
				
				<div id="account_food_area">
				<span id="text_title_2">用戶註冊時間:</span><br>${nowAccount.accountRegisterTime}<br>
				</div>
				
			<div id="accountInfo_btn" style="text-align:center;">
				<form method="post" action="<%=request.getContextPath()%>/Account/accountInfo.do">
					<input type="hidden" name="action" value="gotoAccountChangePage">
					<button id="account_change_info" type="submit">修改我的會員資料</button>	
				</form>

				<form method="post" action="<%=request.getContextPath()%>/Account/accountInfo.do" >
					<input type="hidden" name="action" value="getAccountLogout">
					<button id="account_logout" type="submit">登出帳戶</button>						
				</form>
			</div>				
			
			</div>
		
		</div>	
	
	</div>

	<footer>
		<%@ include file="/common/footer.jsp"%>
	</footer>

<!-- 用不到的扣 -->
<!-- 				<form method="post" action="notice.do"> -->
<!-- 					<input type="hidden" name="action" value="getAccount_Notice"> -->
<!-- 					<input type="submit" value="查看我的通知"> -->
<!-- 				</form> -->
				
<!-- 				<form method="post" action="announce.do"> -->
<!-- 					<input type="hidden" name="action" value="getAccount_Announce"> -->
<!-- 					<input type="submit" value="查看我的公告"> -->
<!-- 				</form> -->

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