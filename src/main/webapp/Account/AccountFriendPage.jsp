<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@ page import="java.util.*"%>
<%@ page import="com.accountinfo.model.*"%>
<%@ page import="com.friendship.model.*"%>

<%
FriendshipService friendshipSvc = new FriendshipService();
AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVOLogin");
List<AccountInfoVO> list =  friendshipSvc.getAccountFriendByAccountID(accountInfoVO.getAccountID());
pageContext.setAttribute("list",list);

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

</style>
</head>
<body id="body_friend">
	<header>
		<%@ include file="/common/header.jsp"%>
	</header>
	
	<div class="container" id="main_block">
	
		<div class="row justify-content-center" >

			<div id="account_friend_area" class="col-10 col-sm-10 col-md-8 col-lg-6 col-xl-6 align-self-center" >
				
				<table>
					<tr>
						<th>會員暱稱</th>
					</tr>			
				<%@ include file="ListTop.file"%> 
					<c:forEach var="accountInfoVO" items="${friendshipVO}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
						<tr>
							<td>${accountInfoVO.accountNickname}</td>
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