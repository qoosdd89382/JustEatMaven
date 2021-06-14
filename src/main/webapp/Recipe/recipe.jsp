<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="java.util.*"%>
<%@ page import="com.recipe.model.*"%>
<%@ page import="com.recipecuisinecategory.model.*"%>
<%@ page import="com.recipeingredientunit.model.*"%>
<%@ page import="com.recipestep.model.*"%>

<%			
int recipeID = new Integer(request.getParameter("id"));

RecipeService recipeSvc = new RecipeService();
RecipeVO recipeVO = recipeSvc.getOneRecipe(recipeID);
request.setAttribute("recipeVO", recipeVO);

RecipeCuisineCategoryService recipeCatSvc = new RecipeCuisineCategoryService();
List<RecipeCuisineCategoryVO> recipeCatList = recipeCatSvc.getAllByRecipe(recipeID);
request.setAttribute("recipeCatList", recipeCatList);

RecipeIngredientUnitService recipeIngUnitSvc = new RecipeIngredientUnitService();
List<RecipeIngredientUnitVO> recipeIngUnitList = recipeIngUnitSvc.getAllByRecipe(recipeID);
request.setAttribute("recipeIngUnitList", recipeIngUnitList);

RecipeStepService recipeStepSvc = new RecipeStepService();
List<RecipeStepVO> RecipeStepList = recipeStepSvc.getAllByRecipe(recipeID);
request.setAttribute("RecipeStepList", RecipeStepList);

%>

<jsp:useBean id="accountSrv" scope="page" class="com.accountinfo.model.AccountInfoService" />
<jsp:useBean id="CategorySvc" scope="page" class="com.cuisinecategory.model.CuisineCategoryService" />
<jsp:useBean id="IngredientSvc" scope="page" class="com.ingredient.model.IngredientService" />
<jsp:useBean id="UnitSvc" scope="page" class="com.unit.model.UnitService" />
<%-- <jsp:useBean id="recipeCatSvc" scope="page" class="com.recipecuisinecategory.model.RecipeCuisineCategoryService" /> --%>
<%-- <jsp:useBean id="recipeIngUnitSvc" scope="page" class="com.recipeingredientunit.model.RecipeIngredientUnitService" /> --%>

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
<link rel="stylesheet" href="<%=request.getContextPath()%>/Recipe/css/recipe.css">
<title>${recipeVO.recipeName} | 食譜 | Just Eat 揪食</title>
	
</head>
<body>
	<%-- include header --%>
	<header>
		<%@ include file="/common/header.jsp"%>
	</header>
	
	<%-- include navbar --%>
	
	<%-- main --%>
    <main class="row col-12 col-md-10 justify-content-between" style="margin: 0 auto;">
    
		<div class="content col-md-9 col-12">
			<%-- breadcrumbs --%>
			<div class="breadcrumbs" aria-label="breadcrumb">
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>">Just Eat 揪食</a></li>
					<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/Recipe/home.jsp">食譜</a></li>
					<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/Recipe/listAllRecipe.jsp">食譜列表</a></li>
					<li class="breadcrumb-item active" aria-current="page">${recipeVO.recipeName}</li>
				</ol>
			</div> 
			
			<div class="recipe-full row" data-id="${recipeVO.recipeID}">
				

				<div class="title col-12">
					<i class="fas fa-utensils"></i>
					<h4><a href="<%= request.getContextPath() %>/Recipe/recipe.jsp?id=${recipeVO.recipeID}">${recipeVO.recipeName}</a></h4>
				</div>
				<div class="sub-title row col-12">
					<div class="author">
						<i class="fas fa-user"></i><a href="#">${accountSrv.getAccountID(recipeVO.accountID).accountNickname}</a>
					</div>
					<div class="time">
						發表於 <fmt:formatDate value="${recipeVO.recipeTime}" pattern="yyyy.MM.dd a K:mm"/>
					</div>
				</div>
				
				<div class="pic col-12">
					<div class="img-outer clearfix">
						<img src="<%=request.getContextPath()%>/Recipe/Pic/Top/${recipeVO.recipeID}">
					</div>
					<div class="count">
						<span class="viewcount"><i class="fas fa-eye"></i>${recipeVO.recipeViewCount}</span>
						<span class="likecount"><i class="fas fa-thumbs-up"></i>${recipeVO.recipeLikeCount}</span>
						<span class="favcount"><i class="fas fa-heart"></i>${recipeVO.recipeCollectCount}</span>
					</div>
				</div>
				
				<div class="info col-12 col-lg-8 margin-top">
					<div class="head"><h3>食譜介紹</h3></div>
					<div class="intro">
						<div class="intro-text">${recipeVO.recipeIntroduction}</div>
					</div>
				</div>
	
				
				<div class="category col-12 col-lg-4 margin-top">
					<div class="head"><h3>食譜料理分類</h3></div>
					<div class="list">
						<c:forEach var="recipeCatVO" items="${recipeCatList}">
							<span>${CategorySvc.getOneCategory(recipeCatVO.cuisineCategoryID).cuisineCategoryName}</span>
						</c:forEach>
					</div>
				</div>	
				
				<div class="ingredient-unit col-12 margin-top">
					<div class="head"><h3>食譜食材</h3>本食譜可供 <span> ${recipeVO.recipeServe} </span> 人享用</div>
					<div class="list">
						<c:forEach var="recipeIngUnitVO" items="${recipeIngUnitList}">
						<div class="data row">
							<div class="col-6">${IngredientSvc.getOneIngredient(recipeIngUnitVO.ingredientID).ingredientName}</div> 
							<div class="col-3">${recipeIngUnitVO.unitAmount} </div>
							<div class="col-3">${UnitSvc.getOneUnit(recipeIngUnitVO.unitID).unitName}</div>
						</div>
						</c:forEach>
					</div>
				</div>
				
				<div class="step col-12 margin-top">
					<div class="head"><h3>食譜製作步驟</h3></div>
					<c:forEach var="RecipeStepVO" items="${RecipeStepList}">
						<div class="recipe row" data-id="${RecipeStepVO.recipeStepID}">
							<div class="order col-3 col-xl-2 vertical-container"><span>${RecipeStepVO.recipeStepOrder}</span></div>
							<div class="text col-9 col-xl-4 vertical-container">${RecipeStepVO.recipeStepText}</div>
							<div class="img col-12 col-xl-6"><img src="<%=request.getContextPath()%>/Recipe/Pic/Step/${RecipeStepVO.recipeStepID}"></div>
						</div>
					</c:forEach>
				</div>
				
			</div>

		</div>

		<div class="sidebar col-md-3 col-12">
			目前沒東西
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
<%-- 	<script src="<%=request.getContextPath()%>/Recipe/js/recipe.js"></script> --%>
</body>
</html>