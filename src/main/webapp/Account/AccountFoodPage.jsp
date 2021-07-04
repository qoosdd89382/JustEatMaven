<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.accountinfo.model.*"%>
<%@ page import="com.ingredient.model.*"%>
<%@ page import="com.likeingredient.model.*"%>

<jsp:useBean id="ingredientSvc" scope="page" class="com.ingredient.model.IngredientService" />


<!DOCTYPE html>
<% 
//會員登入頁面紀錄
// Integer accountID = (Integer) request.getAttribute("accountID");



%>
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
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/index.css">

<title>揪食-會員挑選食材</title>

<style>
body#body_food{
	background-image:url("./images/LoginBackGround.jpg");
	background-size: cover;
	background-attachment:fixed; 
	background-repeat: no-repeat;
}

div#main_area{
	margin-top:80px;
}
div#food_area{
	text-align:center;
	background-color: rgba(0,0,0,0.6);
	color:white;
	
	width: 200px;
	height: expression(this.height < 100 ? "100px" : this.height "px");
	
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
input#ingAutoCompl{
	border-top-left-radius: 10px;
	border-bottom-left-radius: 10px;
	border-top-right-radius: 10px;
	border-bottom-right-radius: 10px;
}
div#food_area_title{
	color: 	#FF8800;
	font-size:20px;
}

div#userIngredient{
	border:1px solid white;
	margin:5px;
	border-top-left-radius: 10px;
	border-bottom-left-radius: 10px;
	border-top-right-radius: 10px;
	border-bottom-right-radius: 10px;
}

input#food_submit_btn,
input#food_reset_btn {
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

input#food_submit_btn:hover,
input#food_reset_btn:hover {
	background:#FFAA33;
	text-decoration: none;
}


</style>

</head>
<body id="body_food">
	<header>
		<%@ include file="/common/header.jsp"%>
	</header>
	
	<div class="container">
	
		<div id="main_area" class="row">
		
			<div id="food_area" class="col-10 col-sm-10 col-md-8 col-lg-6 col-xl-6 align-self-center">
			
				<div id="food_area_title">
					<span>在審核資料期間，先去看食譜如何?</span><br>
					<span>請填入您對食材的喜好</span>
				</div>
				
				<form method="post" action="<%=request.getContextPath()%>/Account/accountInfo.do">
				
					<div class="form-group">
						<span style="color:red">*</span><span>請輸入您喜愛的食材:</span>
						<span style="color:red">${errorMsgs.get("likeIngredientIDError")}</span><br>
						<div class="ui-widget">
							<input id="ingAutoCompl_like" class="form-control" type="text" name="likeIngredientNames" placeholder="輸入或選擇食材標籤">
						</div>
						<!--使用者原本有輸入的話 -->
						<div id="userSelectFood" class="ingAutoOutput_like">
							<ul>
								<c:if test="${not empty likeIngredientVOs}">
									<c:forEach var="likeIngredientVOs" items="${likeIngredientVOs}">
										<li class='row' data-id='${likeIngredientVO.ingredientID}'>
											<div id="userIngredient" class='col-4 vertical-container'>${ingredientSvc.getOneIngredient(likeIngredientVO.ingredientID).ingredientName}</div>
											<div class="vertical-container"><i class='fas fa-times'></i></div>
										</li>
									</c:forEach>
								</c:if>
							</ul>
							<input class="ingAutoInput_like" name="likeIngredientIDs" type="hidden" 
							<%-- value="<%=(likeIngredientIDs == null) ? "" : likeIngredientIDs%>" --%>
							>
						</div>
					</div>
					
					<div class="form-group">
						<span style="color:red">*</span><span>請輸入您討厭的食材:</span>
						<span style="color:red">${errorMsgs.get("dislikeIngredientIDError")}</span><br>	
						<div class="ui-widget">
							<input id="ingAutoCompl_dislike" class="form-control" type="text" name="dislikeIngredientID" placeholder="輸入或選擇食材標籤">
						</div>
						<!--使用者原本有輸入的話 -->
						<div class="ingAutoOutput_dislike">
							<ul>
								<c:if test="${not empty dislikeIngredientVOs}">
									<c:forEach var="dislikeIngredientVOs" items="${dislikeIngredientVOs}">
										<li class='row' data-id='${dislikeIngredientVO.ingredientID}'>
											<div class='col-4 vertical-container'>${ingredientSvc.getOneIngredient(dislikeIngredientVO.ingredientID).ingredientName}</div>
											<div class="vertical-container"><i class='fas fa-times'></i></div>
										</li>
									</c:forEach>
								</c:if>
							</ul>
							<input class="ingAutoInput_dislike" name="dislikeIngredientIDs" type="hidden" 
							<%-- value="<%=(likeIngredientIDs == null) ? "" : likeIngredientIDs%>" --%>
							>
						</div>
					</div>		
<%-- 					<input type="hidden" name="accountID" value="<%=accountID%>>"> --%>
					<input type="hidden" name="action" value="getAccountFood"> 
					<input id="food_submit_btn" type="submit" value="確認送出前往食譜專區"> 
					<input id="food_reset_btn" type="reset" value="重置">
					<br>
				</form>
				<a href="<%=request.getContextPath()%>/Account/AccountLoginPage.jsp">跳過此步驟前往會員中心</a>
				
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
	<script src="<%=request.getContextPath()%>/vendors/jquery-ui/js/jquery-ui.js"></script>
	<%-- <script src="https://cdnjs.cloudflare.com/ajax/libs/skrollr/0.6.30/skrollr.min.js"></script> --%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/vendors/slick/slick.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/footer.js"></script>
	<script src="<%=request.getContextPath()%>/js/index.js"></script>
	<script>
		$(function() {
			<%@ include file="/Account/foodAutoCompiling.file"%>
		});
	</script>
</body>
</html>