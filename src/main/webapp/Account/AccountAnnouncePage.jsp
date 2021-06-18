<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@ page import="java.util.*"%>
<%@ page import="com.accountinfo.model.*"%>
<%@ page import="com.announce.model.*"%>

<%
AnnounceService announceSvc = new AnnounceService();
String accountMail = (String) session.getAttribute("accountMail");
List<AnnounceVO> announceVO =  announceSvc.getAnnounce();
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

<title>AccountAnnouncePage</title>
<style>
div.container{
margin-top:100px;
}


</style>
</head>
<body>
	<header>
		<%@ include file="/common/header.jsp"%>
	</header>
	
	<div class="container">
		<div class="row">
			<div class="col-4" id="function_select_area">
					<ul>
<!-- 						<li><a href='AccountInfoPage.jsp'>會員資料</a></li> -->
						<li>
						<form method="post" action="accountInfo.do">
								<input type="hidden" name="action" value="gotoAccountInfoPage">
								<input type="submit" value="會員資料">
						</form>
						</li>
						<li><a href='#'>我的活動</a></li>
						<li><a href='#'>我的評價</a></li>
						<li>
						<form method="post" action="friendship.do">
								<input type="hidden" name="action" value="getAccount_Friendship">
								<input type="submit" value="我的好友">
						</form>
						</li>
						<li><a href='#'>我的文章</a></li>
						<li><a href='#'>我的收藏</a></li>
						<li><a href='#'>我的訂單</a></li>
						<li><a href='#'>成為商家(這裡記得要加判斷)</a></li>
					<li>
						<form method="post" action="notice.do">
							<input type="hidden" name="action" value="getAccount_Notice">
							<input type="submit" value="查看我的通知">
						</form>
					</li>
					<li>
						<form method="post" action="announce.do">
							<input type="hidden" name="action" value="getAccount_Announce">
							<input type="submit" value="查看我的公告">
						</form>
					</li>	
					</ul>
			</div>
			<div class="col">
				<p>我是公告頁面</p>
				<p>借我標記一下session=>${accountMail}</p>	
				<p>我是你的公告集合${announceVO}</p>
				<p>以下是你的公告</p>

				<c:forEach var="announceVO" items="${announceVO}" varStatus="i">
					<div>
						<tr>
							<td>${announceVO.announceText}</td>
							<td>${announceVO.announceTime}</td>
						</tr>
					</div>
				</c:forEach>
				
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