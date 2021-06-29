<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/Recipe/recipeImport.jsp"%>

<%
Map<String, String> errorMsgs = new HashMap<String, String>();
request.setAttribute("errorMsgs", errorMsgs);

List<RecipeCuisineCategoryVO> list = null;

try {
	
	String cuisineCategoryID = request.getParameter("id");
	if (cuisineCategoryID == null) {
		throw new Exception();
	}
	list = reicpeCatSvc.getAllByCuisineCategory(new Integer(cuisineCategoryID));
	pageContext.setAttribute("cuisineCategoryID", cuisineCategoryID);
	pageContext.setAttribute("list", list);
	
	
} catch (Exception e) {
	errorMsgs.put("UnknowErr", "發生錯誤，或您輸入的食譜編號不存在！");
	e.printStackTrace();
	RequestDispatcher failureView = request.getRequestDispatcher("/Recipe/listAllRecipe.jsp");
	failureView.forward(request, response);
	return;
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
	    			系統為您尋找 <b>${categorySvc.getOneCategory(cuisineCategoryID).cuisineCategoryName}</b> 料理分類，符合條件的食譜共有 <b>${fn:length(list)}</b> 筆：
	    		</c:if>
	    		<c:if test="${empty list}">
	    			系統為您尋找 <b>${categorySvc.getOneCategory(cuisineCategoryID).cuisineCategoryName}</b> 料理分類，抱歉，暫時沒有食譜符合條件！
	    		</c:if>
    		</section>
    		
			<div class="list">
    		<%@ include file="pages/page1.file"%>
				<c:forEach var="recipeCatVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				
					<div class="recipe-block row" id="${recipeCatVO.recipeID}">
					
						<div class="pic col-12 col-lg-5">
							<div class="time">
								<fmt:formatDate value="${recipeSvc.getOneRecipe(recipeCatVO.recipeID).recipeTime}" pattern="yyyy.MM.dd"/>
							</div>
							<div class="img-outer">
								<img src="<%=request.getContextPath()%>/Recipe/Pic/Top/${recipeCatVO.recipeID}">
							</div>
							<div class="count">
								<span class="viewcount"><i class="fas fa-eye"></i>${recipeSvc.getOneRecipe(recipeCatVO.recipeID).recipeViewCount}</span>
								<span class='likecount ${thmupRecipeSvc.isExist(accountInfoVOLogin.accountID, recipeCatVO.recipeID) == null ? "" : "click confirm"}'><i class="fas fa-thumbs-up"></i><span class="num">${thmupRecipeSvc.countAllByRecipe(recipeVO.recipeID)}</span></span>
								<span class='favcount ${favRecipeSvc.isExist(accountInfoVOLogin.accountID, recipeCatVO.recipeID) == null ? "" : "click confirm"}'><i class="fas fa-heart"></i><span class="num">${favRecipeSvc.countAllByRecipe(recipeVO.recipeID)}</span></span>
							</div>
						</div>
						
						<div class="info col-12 col-lg-7">
							<div class="title"><i class="fas fa-utensils"></i><h4><a href="<%= request.getContextPath() %>/Recipe/recipe.jsp?id=${recipeCatVO.recipeID}">${recipeSvc.getOneRecipe(recipeCatVO.recipeID).recipeName}</a></h4></div>
							<div class="author"><i class="fas fa-user"></i><a href="#">${accountSvc.selectOneAccountInfo(recipeSvc.getOneRecipe(recipeCatVO.recipeID).accountID).accountNickname}</a></div>
							<div class="intro"><div class="intro-text">${recipeSvc.getOneRecipe(recipeCatVO.recipeID).recipeIntroduction}</div></div>
							<div class="change form-group">
							<c:if test="${not empty accountInfoVOLogin && accountInfoVOLogin.accountID == recipeSvc.getOneRecipe(recipeCatVO.recipeID).accountID}">
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
							</div>
							<div class="readmore">
								<a href="<%= request.getContextPath() %>/Recipe/recipe.jsp?id=${recipeCatVO.recipeID}">繼續閱讀 <i class="fas fa-angle-double-right"></i></a>
							</div>
						</div>
						
					</div>
					
				</c:forEach>
			<%@ include file="pages/page2.file"%>
			</div>
	
<!-- Modal -->
<div class="modal fade" id="notLogin" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">糟糕，哪裡出錯囉！</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        	本站會員才可使用本功能，請先登入或註冊！
      </div>
      <div class="modal-footer">
<!--         <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button> -->
        <a href="<%= request.getContextPath() %>/Account/AccountLoginPage.jsp" class="btn btn-primary">登入</a>
        <a href="<%= request.getContextPath() %>/Account/AccountRegister/AccountRegisterPage.jsp" class="btn btn-primary">註冊</a>
      </div>
    </div>
  </div>
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

	$(function(){

		<%@ include file="/Recipe/js/recipeFavThumb.page"%>
		<%@ include file="/Recipe/searchAutoComplIng.file"%>
	});
	
	</script>
</body>
</html>