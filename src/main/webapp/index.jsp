<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="java.util.*"%>
<%@ page import="com.accountinfo.model.*"%>
<%@ page import="com.eventinfo.model.*"%>

<jsp:useBean id="eventInfoSvc" scope="page" class="com.eventinfo.model.EventInfoService" />

<%-- <%@ page import="com.*"%> --%>

<%
// 	Map<String, String> allPageNames = new HashMap<String, String>();
// 	allPageNames.put("webIndex", "Just Eat 揪食");
// 	application.setAttribute("allPageNames", allPageNames);
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
<link rel="stylesheet" href="<%= request.getContextPath() %>/common/css/adminChat.css">
<title>Just Eat 揪食 - 揪團共享美食</title>
</head>
<body>
	<%-- include header --%>
	<header>
		<%@ include file="/common/header.jsp"%>
	</header>
	
    <!-- search bar -->
    <div class="search">
    <form action="<%= request.getContextPath() %>/Index/search" method="post">
        <div class="search_inner">
            <h1>你空虛的胃，用揪團填滿</h1>
            <div id="search_bar" class="input-group col-lg-6 col-md-8 col-10">	<!-- boostrap v5 "input-group" class 標籤有長度衝突問題  -->
                <select class="custom-select col-4" name="searchFor" class=''>		<!-- boostrap v5: "from-select" -->
                    <option value="event">找活動</option>
                    <option value="recipe">找食譜</option>
<!--                     <option value="3">找食材</option> -->
                </select>
                <input type="text" name="searchString" class='form-control col-8' placeholder="請輸入搜尋內容"
                    aria-label="Text input with segmented dropdown button">
                <div class="input-group-append">
                    <button class="btn btn-outline-secondary" type="submit"><i class="fas fa-search"></i></button>
                </div>
                
            </div>
        </div>
    </form>
        <!-- <form class="search_form" action="#" method="GET">
            <div class="select_outline">
                <select class="search_type" name="search_type">
                    <option value="seach_event" selected>找活動</option>
                    <option value="seach_recipe">找食譜</option>
                    <option value="seach_ingredient">找食材</option>
                </select><input class="search_input" value="" placeholder="請輸入搜尋內容"><div class="search_icon">
                    <i class="fas fa-search"></i></div>
            </div>
            <input class="search_submit_btn" type="submit" name="" style="display: none">
        </form> -->
    </div>

    <!-- main -->
    <main class="col-10" style="margin: 0 auto;">
        <div class="hot_events">
            <h3>熱門活動</h3>
            <section class="slider_box multiple-item responsive">
            <c:forEach var="eventInfoVO" items="${eventInfoSvc.getSomeNew()}">
                <div>
                    <div class="img_outer"><img src="<%= request.getContextPath() %>/Event/EventInfoForOnePic?eventID=${eventInfoVO.eventID}"></div>
                    <span class="popular">${eventInfoVO.eventCurrentCount} 人</span>
                    <div class="title"><a href="<%= request.getContextPath() %>/Event/EventDetailReview.jsp?eventID=${eventInfoVO.eventID}&accountID=${accountInfoVOLogin.accountID}">${eventInfoVO.eventName}</a></div>
                    <div class="datetime"><span class="date">${eventInfoVO.eventStartTime}</span><span class="time">下午 2 時 30 分</span></div>
                </div>
            </c:forEach>
            </section>
        </div>
    </main>

	<%-- include chatbox --%>
	<%@ include file="/common/adminChat.page"%>

	<%-- include footer --%>
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
	<script src="<%=request.getContextPath()%>/common/js/dateFormat.js"></script>
	<script>
	<c:if test="${not empty sessionScope.accountInfoVOLogin}">

	$(function(){

			<%@ include file="/common/js/adminChatJs.page"%>
			<%@ include file="/common/js/noticeWebSocketJs.page"%>
		
	});
	
	$(window).on("unload", function(e) {
		disconnectForNotice();
		disconnect();
	});
	
	</c:if>
	</script>
</body>
</html>