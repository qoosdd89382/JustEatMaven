<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%@ include file="/Recipe/recipeImport.jsp"%>

<%			
	Map<String, String> errorMsgs = new HashMap<String, String>();
	request.setAttribute("errorMsgs", errorMsgs);
	
	try {
	
		int recipeID = new Integer(request.getParameter("id"));
			
		recipeSvc.updateViewCount(new Integer(recipeID));
		
		RecipeVO recipeVO = recipeSvc.getOneRecipe(recipeID);
		
		if (recipeVO == null) {
			throw new Exception();
		}
		
		request.setAttribute("recipeVO", recipeVO);
		
// 		RecipeCuisineCategoryService recipeCatSvc = new RecipeCuisineCategoryService();
		List<RecipeCuisineCategoryVO> recipeCatList = recipeCatSvc.getAllByRecipe(recipeID);
		request.setAttribute("recipeCatList", recipeCatList);
		
		List<RecipeIngredientUnitVO> recipeIngUnitList = recipeIngUnitSvc.getAllByRecipe(recipeID);
		request.setAttribute("recipeIngUnitList", recipeIngUnitList);
		
		List<RecipeStepVO> RecipeStepList = recipeStepSvc.getAllByRecipe(recipeID);
		request.setAttribute("RecipeStepList", RecipeStepList);

	
	} catch (Exception e) {
		errorMsgs.put("UnknowErr", "發生錯誤，或您輸入的食譜編號不存在！");
		e.printStackTrace();
		RequestDispatcher failureView = request.getRequestDispatcher("/Recipe/listAllRecipe.jsp");
		failureView.forward(request, response);
	}
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
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/jquery-ui/css/jquery-ui.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/footer.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/Recipe/css/recipe.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/Recipe/css/recipeSidebar.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/Recipe/css/recipeSearchbar.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/Recipe/css/recipeHeader.css">
<title>${recipeVO.recipeName} | 食譜 | Just Eat 揪食</title>
	
</head>
<body>
	<%-- include header --%>
	<header>
		<%@ include file="/common/header.jsp"%>
	</header>
	
	<%-- include navbar --%>
	<%@ include file="/Recipe/recipeHeader.page"%>
	
	<%-- main --%>
    <main class="row col-12 col-md-10 justify-content-between" style="margin: 0 auto;">
	
	<%-- include searchbar --%>
	<div class="searchbar col-12">
		<%@ include file="/Recipe/recipeSearchbar.page"%>
	</div>
    
		<div class="content col-xl-9 col-12">
			<%-- breadcrumbs --%>
			<div class="breadcrumbs" aria-label="breadcrumb">
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>">Just Eat 揪食</a></li>
					<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/Recipe/home.jsp">食譜</a></li>
					<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/Recipe/listAllRecipe.jsp">食譜列表</a></li>
					<li class="breadcrumb-item active" aria-current="page">${recipeVO.recipeName}</li>
				</ol>
			</div> 
			
			<div class="recipe-full row" id="${recipeVO.recipeID}">

				<div class="title col-12">
					<i class="fas fa-utensils"></i>
					<h4><a href="<%= request.getContextPath() %>/Recipe/recipe.jsp?id=${recipeVO.recipeID}">${recipeVO.recipeName}</a></h4>
				</div>
				<div class="sub-title col-12">
					<div class="author">
						<i class="fas fa-user"></i><a href="${pageContext.request.contextPath}/Recipe/recipe.do?action=myRecipe&id=${recipeVO.accountID}">${accountSvc.selectOneAccountInfo(recipeVO.accountID).accountNickname}</a>
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
						<span class='likecount ${accountInfoVOLogin == null ? "" : (thmupRecipeSvc.isExist(accountInfoVOLogin.accountID, recipeVO.recipeID) == null ? "" : "click confirm")}'><i class="fas fa-thumbs-up"></i><span class="num">${thmupRecipeSvc.countAllByRecipe(recipeVO.recipeID)}</span></span>
						<span class='favcount ${accountInfoVOLogin == null ? "" : (favRecipeSvc.isExist(accountInfoVOLogin.accountID, recipeVO.recipeID) == null ? "" : "click confirm")}'><i class="fas fa-heart"></i><span class="num">${favRecipeSvc.countAllByRecipe(recipeVO.recipeID)}</span></span>
					</div>
							<c:if test="${not empty accountInfoVOLogin && accountInfoVOLogin.accountID == recipeVO.accountID}">
								<div class="change form-group">
									<form class="update" method="post" action="<%=request.getContextPath()%>/Recipe/recipe.do">
										<input type="hidden" name="action" value="getOneForUpdate">
										<input type="hidden" name="recipeID"  value="${recipeVO.recipeID}">
										<button class="btn btn-secondary" type="submit">編輯</button>
									</form>
									<form class="delete" method="post" action="<%=request.getContextPath()%>/Recipe/recipe.do">
										<input type="hidden" name="action" value="delete">
										<input type="hidden" name="recipeID"  value="${recipeVO.recipeID}">
										<button class="btn btn-secondary" type="submit">刪除</button>
									</form>
								</div>
							</c:if>
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
							<span><a href="<%= request.getContextPath() %>/Recipe/recipe.do?action=listAllByCategory&id=${recipeCatVO.cuisineCategoryID}">${categorySvc.getOneCategory(recipeCatVO.cuisineCategoryID).cuisineCategoryName}</a></span>
						</c:forEach>
					</div>
				</div>	
				
				<div class="ingredient-unit col-12 margin-top">
					<div class="head"><h3>食譜食材</h3>本食譜可供 <span> ${recipeVO.recipeServe} </span> 人享用</div>
					<div class="list">
						<c:forEach var="recipeIngUnitVO" items="${recipeIngUnitList}">
						<div class="data row">
							<div class="col-6">
								<a class="" href="<%= request.getContextPath() %>/Recipe/recipe.do?action=listAllByIngredient&id=${recipeIngUnitVO.ingredientID}">${ingredientSvc.getOneIngredient(recipeIngUnitVO.ingredientID).ingredientName}</a></div> 
<%-- 							<div class="col-3">${recipeIngUnitVO.unitAmount} </div> --%>
							<div class="col-3">
								<fmt:formatNumber value="${recipeIngUnitVO.unitAmount}" type="currency" pattern="#.##"/>
							</div>
							<div class="col-3">${unitSvc.getOneUnit(recipeIngUnitVO.unitID).unitName}</div>
						</div>
						</c:forEach>
					</div>
				</div>
				
				<div class="step col-12 margin-top">
					<div class="head"><h3>食譜製作步驟</h3></div>
					<c:forEach var="RecipeStepVO" items="${RecipeStepList}">
						<div class="recipe row" data-id="${RecipeStepVO.recipeStepID}">
							<div class="order col-3 col-xl-2 vertical-container"><span>${RecipeStepVO.recipeStepOrder}</span></div>
<%-- 							<div class="text col-9 col-xl-4 vertical-container">${RecipeStepVO.recipeStepText}</div> --%>
							<div class="text col-9 col-xl-4 border rounded m-0 p-2">${RecipeStepVO.recipeStepText}</div>
							<div class="img col-12 col-xl-6 p-0 pl-2"><img class="rounded" src="<%=request.getContextPath()%>/Recipe/Pic/Step/${RecipeStepVO.recipeStepID}"></div>
						</div>
					</c:forEach>
				</div>
				
			</div>

			<%-- include notMemberAlertModal --%>
			<%@ include file="/Recipe/notMemberAlertModal.page"%>
		</div>

		<%-- include sidebar --%>
		<div class="sidebar col-xl-3 col-12">
			<%@ include file="/Recipe/recipeSidebar.page"%>
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
	<script src="<%=request.getContextPath()%>/vendors/jquery-ui/js/jquery-ui.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/footer.js"></script>
<%-- 	<script src="<%=request.getContextPath()%>/Recipe/js/listAllRecipe.js"></script> --%>

	<script>
	<%@ include file="/common/js/scriptFooter.page"%>

	$(function(){

		<%@ include file="/Recipe/js/recipeFavThumb.page"%>
		<%@ include file="/Recipe/searchAutoComplIng.file"%>
	});
	
	</script>
</body>
</html>