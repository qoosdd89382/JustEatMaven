<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%@ include file="/Recipe/recipeImport.jsp"%>

<%
	List<RecipeVO> list = recipeSvc.getAll();
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
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/jquery-ui/css/jquery-ui.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/footer.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/Recipe/css/listAllRecipe.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/Recipe/css/recipeSidebar.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/Recipe/css/recipeSearchbar.css">
<style>
.ui-autocomplete {
	max-height: 100px;
	overflow-y: auto;
	overflow-x: hidden;
}
</style>
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
					<li class="breadcrumb-item active" aria-current="page">食譜列表</li>
				</ol>
			</div> 
    	
    		<section class="error">
    			${errorMsgs.get("UnknowErr")}
    		</section>
    		
			<div class="list">
    		<%@ include file="pages/page1.file"%>
				<c:forEach var="recipeVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				
					<div class="recipe-block row" id="${recipeVO.recipeID}">
					
						<div class="pic col-12 col-lg-5">
							<div class="time">
								<fmt:formatDate value="${recipeVO.recipeTime}" pattern="yyyy.MM.dd"/>
								<%-- yyyy.MM.dd a KK:mm --%>
							</div>
							<div class="img-outer">
								<img src="<%=request.getContextPath()%>/Recipe/Pic/Top/${recipeVO.recipeID}">
							</div>
							<div class="count">
								<span class="viewcount"><i class="fas fa-eye"></i>${recipeVO.recipeViewCount}</span>
								<span class='likecount ${accountInfoVOLogin == null ? "" : (thmupRecipeSvc.isExist(accountInfoVOLogin.accountID, recipeVO.recipeID) == null ? "" : "click confirm")}'><i class="fas fa-thumbs-up"></i><span class="num">${thmupRecipeSvc.countAllByRecipe(recipeVO.recipeID)}</span></span>
								<span class='favcount ${accountInfoVOLogin == null ? "" : (favRecipeSvc.isExist(accountInfoVOLogin.accountID, recipeVO.recipeID) == null ? "" : "click confirm")}'><i class="fas fa-heart"></i><span class="num">${favRecipeSvc.countAllByRecipe(recipeVO.recipeID)}</span></span>
							</div>
						</div>
						<!-- 應該要找時間處理驗證，若非本人不得刪除 -->
						<div class="info col-12 col-lg-7">
							<div class="title"><i class="fas fa-utensils"></i><h4><a href="<%= request.getContextPath() %>/Recipe/recipe.jsp?id=${recipeVO.recipeID}">${recipeVO.recipeName}</a></h4></div>
<!-- 							<div class="row"> -->
							<div class="author"><i class="fas fa-user"></i><a href="#">${accountSvc.selectOneAccountInfo(recipeVO.accountID).accountNickname}</a></div>
<!-- 							<div class="col-6">test</div> -->
<!-- 							</div> -->
							<div class="intro"><div class="intro-text">${recipeVO.recipeIntroduction}</div></div>
							
							<c:if test="${not empty accountInfoVOLogin && accountInfoVOLogin.accountID == recipeVO.accountID}">
								<div class="change form-group">
									<form class="update" method="post" action="<%=request.getContextPath()%>/Recipe/recipe.do">
										<input type="hidden" name="action" value="getOneForUpdate">
										<input type="hidden" name="recipeID"  value="${recipeVO.recipeID}">
										<button class="btn btn-primary" type="submit">編輯</button>
									</form>
									<form class="delete" method="post" action="<%=request.getContextPath()%>/Recipe/recipe.do">
										<input type="hidden" name="action" value="delete">
										<input type="hidden" name="recipeID"  value="${recipeVO.recipeID}">
										<button class="btn btn-primary" type="submit">刪除</button>
									</form>
								</div>
							</c:if>
							
							
							<div class="readmore">
								<a href="<%= request.getContextPath() %>/Recipe/recipe.jsp?id=${recipeVO.recipeID}">繼續閱讀 <i class="fas fa-angle-double-right"></i></a>
							</div>
						</div>
						
					</div>
					
				</c:forEach>
			<%@ include file="pages/page2.file"%>
			</div>
			
			<%-- include notMemberAlertModal --%>
			<%@ include file="/Recipe/notMemberAlertModal.page"%>
			
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
	<script src="<%=request.getContextPath()%>/vendors/jquery-ui/js/jquery-ui.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/footer.js"></script>
	<script src="<%=request.getContextPath()%>/Recipe/js/listAllRecipe.js"></script>
	<script>

	$(function(){

		<%@ include file="/Recipe/js/recipeFavThumb.page"%>
		<%@ include file="/Recipe/searchAutoComplIng.file"%>
	});
	
	</script>
</body>
</html>