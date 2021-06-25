<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="java.util.*"%>
<%@ page import="com.accountinfo.model.*"%>
<%@ page import="com.recipe.model.*"%>
<%@ page import="com.cuisinecategory.model.*"%>
<%@ page import="com.ingredient.model.*"%>
<%@ page import="com.unit.model.*"%>
<%@ page import="com.recipecuisinecategory.model.*"%>
<%@ page import="com.recipeingredientunit.model.*"%>
<%@ page import="com.recipestep.model.*"%>

<jsp:useBean id="accountSrv" scope="page" class="com.accountinfo.model.AccountInfoService" />
<jsp:useBean id="categorySvc" scope="page" class="com.cuisinecategory.model.CuisineCategoryService" />
<jsp:useBean id="reicpeIngUnitSvc" scope="page" class="com.recipeingredientunit.model.RecipeIngredientUnitService" />
<jsp:useBean id="ingredientSvc" scope="page" class="com.ingredient.model.IngredientService" />
<jsp:useBean id="unitSvc" scope="page" class="com.unit.model.UnitService" />
<jsp:useBean id="recipeSvc" scope="page" class="com.recipe.model.RecipeService" />

<%
Map<String, String> errorMsgs = new HashMap<String, String>();
request.setAttribute("errorMsgs", errorMsgs);

List<RecipeIngredientUnitVO> list = null;
String ingredientID = null;

try {
	
	ingredientID = request.getParameter("id");
	if (ingredientID == null) {
		throw new Exception();
	}
	ingredientSvc.updateSearchCount(new Integer(ingredientID));
	list = reicpeIngUnitSvc.getAllByIngredient(new Integer(ingredientID));
	
} catch (Exception e) {
	errorMsgs.put("UnknowErr", "發生錯誤，或您輸入的食譜編號不存在！");
	e.printStackTrace();
	RequestDispatcher failureView = request.getRequestDispatcher("/Recipe/listAllRecipe.jsp");
	failureView.forward(request, response);
	return;
}
	pageContext.setAttribute("ingredientID", ingredientID);
	pageContext.setAttribute("list", list);
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
<link rel="stylesheet" href="<%=request.getContextPath()%>/Recipe/css/listAllRecipe.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/Recipe/css/recipeSidebar.css">
<title>食譜列表 | 食譜 | Just Eat 揪食</title>
	
</head>
<body>
	<%-- include header --%>
	<header>
		<%@ include file="/common/header.jsp"%>
	</header>
	
	<%-- include navbar --%>
	
	<%-- main --%>
    <main class="row col-12 col-md-10 justify-content-between" style="margin: 0 auto;">
    
		<div class="content col-xl-9 col-12">
			<%-- breadcrumbs --%>
			<div class="breadcrumbs" aria-label="breadcrumb">
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>">Just Eat 揪食</a></li>
					<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/Recipe/home.jsp">食譜</a></li>
					<li class="breadcrumb-item active" aria-current="page">食譜列表</li>
				</ol>
			</div> 
    	
<!--     		<section class="error"> -->
<%--     			${errorMsgs.get("UnknowErr")} --%>
<!--     		</section> -->

    		<section class="searchResult">
				<c:if test="${not empty list}">
	    			系統為您尋找 <b>${ingredientSvc.getOneIngredient(ingredientID).ingredientName}</b> 食材，符合條件的食譜共有 <b>${fn:length(list)}</b> 筆：
	    		</c:if>
	    		<c:if test="${empty list}">
	    			系統為您尋找 <b>${ingredientSvc.getOneIngredient(ingredientID).ingredientName}</b> 食材，抱歉，暫時沒有食譜符合條件！
	    		</c:if>
    		</section>
    		
			<div class="list">
    		<%@ include file="pages/page1.file"%>
				<c:forEach var="recipeIngUnit" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				
					<div class="recipe-block row" id="${recipeIngUnit.recipeID}">
					
						<div class="pic col-12 col-lg-5">
							<div class="time">
								<fmt:formatDate value="${recipeSvc.getOneRecipe(recipeIngUnit.recipeID).recipeTime}" pattern="yyyy.MM.dd"/>
							</div>
							<div class="img-outer">
								<img src="<%=request.getContextPath()%>/Recipe/Pic/Top/${recipeIngUnit.recipeID}">
							</div>
							<div class="count">
								<span class="viewcount"><i class="fas fa-eye"></i>${recipeSvc.getOneRecipe(recipeIngUnit.recipeID).recipeViewCount}</span>
								<span class="likecount"><i class="fas fa-thumbs-up"></i>${recipeSvc.getOneRecipe(recipeIngUnit.recipeID).recipeLikeCount}</span>
								<span class="favcount"><i class="fas fa-heart"></i>${recipeSvc.getOneRecipe(recipeIngUnit.recipeID).recipeCollectCount}</span>
							</div>
						</div>
						
						<div class="info col-12 col-lg-7">
							<div class="title"><i class="fas fa-utensils"></i><h4><a href="<%= request.getContextPath() %>/Recipe/recipe.jsp?id=${recipeIngUnit.recipeID}">${recipeSvc.getOneRecipe(recipeIngUnit.recipeID).recipeName}</a></h4></div>
							<div class="author"><i class="fas fa-user"></i><a href="#">${accountSrv.selectOneAccountInfo(recipeSvc.getOneRecipe(recipeIngUnit.recipeID).accountID).accountNickname}</a></div>
							<div class="intro"><div class="intro-text">${recipeSvc.getOneRecipe(recipeCatVO.recipeID).recipeIntroduction}</div></div>
							<div class="change form-group">
								<form class="update" method="post" action="<%=request.getContextPath()%>/Recipe/recipe.do">
									<input type="hidden" name="action" value="getOneForUpdate">
									<input type="hidden" name="recipeID"  value="${recipeIngUnit.recipeID}">
									<button class="btn btn-primary" type="submit">編輯</button>
								</form>
								<form class="delete" method="post" action="<%=request.getContextPath()%>/Recipe/recipe.do">
									<input type="hidden" name="action" value="delete">
									<input type="hidden" name="recipeID"  value="${recipeIngUnit.recipeID}">
									<button class="btn btn-primary" type="submit">刪除</button>
								</form>
							</div>
							<div class="readmore">
								<a href="<%= request.getContextPath() %>/Recipe/recipe.jsp?id=${recipeIngUnit.recipeID}">繼續閱讀 <i class="fas fa-angle-double-right"></i></a>
							</div>
						</div>
						
					</div>
					
				</c:forEach>
			<%@ include file="pages/page2.file"%>
			</div>
			

		</div>

		<%-- include sidebar --%>
		<div class="sidebar col-xl-3 col-12">
			<%@ include file="/Recipe/recipeSidebar.page"%>
		</div>	
    </main>
	
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
	<script src="<%=request.getContextPath()%>/Recipe/js/listAllRecipe.js"></script>
	<script>
	
	</script>
</body>
</html>