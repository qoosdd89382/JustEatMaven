<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="java.util.*"%>
<%@ page import="com.accountinfo.model.*"%>
<%@ page import="com.eventinfo.model.*"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@ page import="java.util.*"%>


<jsp:useBean id="thmupRecipeSvc" scope="page" class="com.thumbsuprecipe.model.ThumbsupRecipeService" />
<jsp:useBean id="favRecipeSvc" scope="page" class="com.favoriterecipe.model.FavoriteRecipeService" />
<jsp:useBean id="recipeSvc" scope="page" class="com.recipe.model.RecipeService" />
<jsp:useBean id="eventInfoSvc" scope="page" class="com.eventinfo.model.EventInfoService" />
<jsp:useBean id="now" class="java.util.Date" scope="page"/>

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
<style>

.popular > span {
	margin-right: 10px;
}

.popular > span svg {
	margin-right: 10px;
}

.popular > span {
	margin-right: 10px;
}

.popular > span svg {
	margin-right: 10px;
}
.popular span.favcount.click svg {
	color: #FF79C6;	
}
.popular span.likecount.click svg {
	color: #435DFF;
}



</style>
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
            <h2><i class="fas fa-pizza-slice"></i> 最新活動</h2>
            <section class="slider_box multiple-item responsive">
            <c:forEach var="eventInfoVO" items="${eventInfoSvc.getSomeNew()}" begin="0" end="5">
                <div>
                    <div class="img_outer"><img src="<%= request.getContextPath() %>/Event/EventInfoForOnePic?eventID=${eventInfoVO.eventID}"></div>
                    <span class="popular shadow">${eventInfoVO.eventCurrentCount} 人</span>
                    <span class="place shadow">${eventInfoVO.groupCity}</span>
                    <div class="title">
                    	<a href="<%= request.getContextPath() %>/Event/EventDetailReview.jsp?eventID=${eventInfoVO.eventID}&accountID=${accountInfoVOLogin.accountID}">${eventInfoVO.eventName}</a>
						<c:if test="${now < eventInfoVO.eventRegistartionStartTime}">
							<span class="badge badge-primary">未開始報名</span>
						</c:if>	
						<c:if test="${now > eventInfoVO.eventRegistartionStartTime && now < eventInfoVO.eventRegistartionEndTime}">
							<span class="badge badge-warning">報名中</span>
						</c:if>	
						<c:if test="${now > eventRegistartionEndTime && now < eventInfoVO.eventStartTime}">
							<span class="badge badge-info">等待進行</span>
						</c:if>	
						<c:if test="${now > eventInfoVO.eventStartTime && now < eventInfoVO.eventEndTime}">
							<span class="badge badge-success">進行中</span>
						</c:if>	
                    	<c:if test="${now > eventInfoVO.eventEndTime}">
							<span class="badge badge-secondary">已結束</span>
						</c:if>
                    </div>
                    <div class="datetime">
                    	<span class="date"><fmt:formatDate value="${eventInfoVO.eventStartTime}" pattern="yyyy/MM/dd KK:mm"/></span>
                    </div>
                </div>
            </c:forEach>
            </section>
        </div>
        
        
        <div class="hot_events mt-5">
            <h2><i class="fas fa-hamburger"></i> 最新食譜</h2>
            <section class="slider_box multiple-item responsive">
            <c:forEach var="recipeVO" items="${recipeSvc.getSomeNew()}" begin="0" end="5">
                <div class="recipeInfo" data-id="${recipeVO.recipeID}">
                    <div class="img_outer"><img src="<%= request.getContextPath() %>/Recipe/Pic/Top/${recipeVO.recipeID}"></div>
                    <span class="popular shadow">
						<span class="viewcount"><i class="fas fa-eye"></i>${recipeVO.recipeViewCount}</span>
						<span class='likecount ${accountInfoVOLogin == null ? "" : (thmupRecipeSvc.isExist(accountInfoVOLogin.accountID, recipeVO.recipeID) == null ? "" : "click confirm")}'><i class="fas fa-thumbs-up"></i><span class="num">${thmupRecipeSvc.countAllByRecipe(recipeVO.recipeID)}</span></span>
						<span class='favcount ${accountInfoVOLogin == null ? "" : (favRecipeSvc.isExist(accountInfoVOLogin.accountID, recipeVO.recipeID) == null ? "" : "click confirm")}'><i class="fas fa-heart"></i><span class="num">${favRecipeSvc.countAllByRecipe(recipeVO.recipeID)}</span></span>
					</span>
                    <div class="title">
                    	<a href="<%= request.getContextPath() %>/Recipe/recipe.jsp?id=${recipeVO.recipeID}">${recipeVO.recipeName}</a>
                    </div>
                    <div class="datetime">
                    	<span class="date"><fmt:formatDate value="${recipeVO.recipeTime}" pattern="yyyy/MM/dd KK:mm"/></span>
                    </div>
                </div>
            </c:forEach>
            </section>
        </div>
        
		<%-- include notMemberAlertModal --%>
		<%@ include file="/Recipe/notMemberAlertModal.page"%>
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
		<%@ include file="/common/js/scriptFooter.page"%>
		$(function(){

			$("span.likecount").on("mouseover", "svg", function() {
				$(this).css('cursor', 'pointer'); 
				$(this).parent().addClass("click");
			});
			
			$("span.likecount").on("mouseout", "svg", function() {
				$(this).css('cursor', 'auto');
				if (!$(this).parent().hasClass("confirm")) {
					$(this).parent().removeClass("click");
				}
			});
				
			$("span.likecount").on("click", "svg", function() {
				var that = this;
				var accountID = "${accountInfoVOLogin.accountID}";
				var recipeID = $(that).closest(".recipeInfo").attr("data-id");
				
				if (accountID == "") {
					$('#notLogin').modal();
					return;
				}
				
				$.ajax({
					type : 'POST',
					url : '<c:url value="/Recipe/recipeFavThumb.do"/>',
					data : {
						'action': "insertThumbsupRecipe",
						'accountID': accountID,
						'recipeID': recipeID
					},
					success : function(data) {
						var jsonObj = JSON.parse(data);
						$(that).next().text(jsonObj.count);
						if ("insertSucess" == jsonObj.status) {
							$(that).parent().addClass("click");
							$(that).parent().addClass("confirm");
						} else if ("deleteSucess" == jsonObj.status) {
							$(that).parent().removeClass("click");
							$(that).parent().removeClass("confirm");
						}
					}
				});
			});

			

			$("span.favcount").on("mouseover", "svg", function() {
				$(this).css('cursor', 'pointer'); 
				$(this).parent().addClass("click");
			});
			
			$("span.favcount").on("mouseout", "svg", function() {
				$(this).css('cursor', 'auto'); 
				if (!$(this).parent().hasClass("confirm")) {
					$(this).parent().removeClass("click");
				}
			});
				
			$("span.favcount").on("click", "svg", function() {
				var that = this;
				var accountID = "${accountInfoVOLogin.accountID}";
				var recipeID = $(that).closest(".recipeInfo").attr("data-id");
				
				if (accountID == "") {
					$('#notLogin').modal();
					return;
				}
				
				$.ajax({
					type : 'POST',
					url : '<c:url value="/Recipe/recipeFavThumb.do"/>',
					data : {
						'action': "insertFavoriteRecipe",
						'accountID': accountID,
						'recipeID': recipeID
					},
					success : function(data) {
						var jsonObj = JSON.parse(data);
						$(that).next().text(jsonObj.count);
						if ("insertSucess" == jsonObj.status) {
							$(that).parent().addClass("click");
							$(that).parent().addClass("confirm");
						} else if ("deleteSucess" == jsonObj.status) {
							$(that).parent().removeClass("click");
							$(that).parent().removeClass("confirm");
						}
					}
				});
			});			
		});
	</script>
</body>
</html>