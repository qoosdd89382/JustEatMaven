<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@ page import="java.util.*"%>
<%@ page import="com.accountinfo.model.*"%>
<%@ page import="com.recipe.model.*"%>
<%@ page import="com.friendship.model.*"%>

<jsp:useBean id="recipeSvc" scope="page" class= "com.recipe.model.RecipeService"/>
<jsp:useBean id="accountInfoSvc" scope="page" class= "com.accountinfo.model.AccountInfoService"/>
<jsp:useBean id="friendshipSvc" scope="page" class= "com.friendship.model.FriendshipService"/>

<%
AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVOLogin");
List<AccountInfoVO> list =  friendshipSvc.getAccountFriendByAccountID(accountInfoVO.getAccountID());
pageContext.setAttribute("list",list);

Integer accountID = accountInfoVO.getAccountID();

List<RecipeVO> listRecipe = recipeSvc.getAllByWriter(accountInfoVO.getAccountID());


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

<title>AccountFriendPage</title>
<style>
body#body_friend{
/* 	background: #ffe259;  */
/* 	background: -webkit-linear-gradient(to left, #ffa751, #ffe259);  */
/* 	background: linear-gradient(to left, #ffa751, #ffe259); */

	background-image:url("./images/AccountInfoPage.jpg");
	background-size: cover;
/* 	background-attachment:fixed;  */
/* 	background-repeat: no-repeat; */
}
div#main_block{
	margin-top:150px;
}
div#account_friend_area table{
	text-align:center;
	border:2px solid white;
}
div#account_friend_area table tbody th{
	text-align:center;
	padding:15px;
	font-size:15px;
	
}
div#account_friend_area table tbody tr{
	text-align:center;
	border:1px solid white;
	padding:20px;
	font-size:20px;
}

div#account_friend_area{;
	background-color: rgba(0,0,0,0.6);
	color:white;
	
	width: 200px;
	height:  expression(this.height < 200 ? "200px" : this.height "px");
	
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

button#friend_delete_btn{
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
button#friend_delete_btn:hover{
	background:#FFAA33;
	text-decoration: none;
}


</style>
</head>
<body id="body_friend">
	<header>
		<%@ include file="/common/header.jsp"%>
	</header>
	
	<div class="container" id="main_block">
	
		<div id="function_select_area" class="row justify-content-center">
		
			<div id="function_select_area_button" class="col-6 col-md-2">
				<form method="post" action="accountInfo.do">
					<input type="hidden" name="action" value="gotoAccountInfoPage">
					<button id="btn_submit" type="submit">會員資料</button>
				</form>		
			</div>
			
			<div id="function_select_area_button" class="col-6 col-md-2">
				<form method="post" action="friendship.do">
					<input type="hidden" name="action" value="getAccount_Friendship">
					<button id="btn_submit" type="submit">我的好友</button>
				</form>
			</div>
			
			<div id="function_select_area_button" class="col-6 col-md-2 align-self-center">
				<a href="<%=request.getContextPath()%>/Event/MyEvent.jsp?accountID=<%=accountInfoVO.getAccountID()%>">我的活動</a>
			</div>
			
			<div id="function_select_area_button" class="col-6 col-md-2 align-self-center">
				<a href='<%=request.getContextPath()%>/Recipe/recipe.do?action=myRecipe&id=<%=accountID%>'>我的食譜</a>
			</div>
	
		</div>
	
		<div class="row justify-content-center" >

			<div id="account_friend_area" class="col-10 col-sm-10 col-md-8 col-lg-6 col-xl-6 align-self-center" >
				
				<table>
					<tr>
						<th>會員暱稱</th>
						<th>會員食譜發布數</th>
						<th>會員活動參加數</th>
					</tr>			
					
				<%@ include file="ListTop.file"%> 
					<c:forEach var="accountInfoVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
						<tr>
							<td>${accountInfoVO.accountNickname}</td>
							<td>${recipeSvc.getAllByWriter(accountInfoVO.accountID).size()}</td>
							<td>${accountInfoSvc.getParticipationByAccountID(accountInfoVO.accountID)}</td>
							<td>
							<form method="post" action="<%=request.getContextPath()%>/Account/friendship.do" style="margin-bottom: 0px;">
							     <input type="hidden" name="accountID"  value="<%=accountInfoVO.getAccountID()%>">
							     <input type="hidden" name="friendID"  value="${accountInfoVO.accountID}">
							     <input type="hidden" name="action" value="removeFriendship">
							     <button id="friend_delete_btn" type="submit">刪除</button>
							</form>
							</td>
						</tr>
					</c:forEach>
				</table>
				<%@ include file="ListBottom.file" %>	
				<br>
				<br>
				<br>
				<br>
				
			</div>
		</div>
	</div>
	
	<footer>
		<%@ include file="/common/footer.jsp"%>
	</footer>

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