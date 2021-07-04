<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="java.util.List"%>
<%@ page import="com.admininfo.model.*" %>

<%@ page import="java.util.*"%>
<%@ page import="com.accountinfo.model.*"%>
<%@ page import="com.recipe.model.*"%>
<%@ page import="com.cuisinecategory.model.*"%>
<%@ page import="com.ingredient.model.*"%>
<%@ page import="com.unit.model.*"%>
<%@ page import="com.recipecuisinecategory.model.*"%>
<%@ page import="com.recipeingredientunit.model.*"%>
<%@ page import="com.recipestep.model.*"%>
<%@ page import="com.thumbsuprecipe.model.*"%>
<%@ page import="com.favoriterecipe.model.*"%>

<jsp:useBean id="accountSvc" scope="page" class="com.accountinfo.model.AccountInfoService" />
<jsp:useBean id="recipeSvc" scope="page" class="com.recipe.model.RecipeService" />
<jsp:useBean id="categorySvc" scope="page" class="com.cuisinecategory.model.CuisineCategoryService" />
<jsp:useBean id="ingredientSvc" scope="page" class="com.ingredient.model.IngredientService" />
<jsp:useBean id="unitSvc" scope="page" class="com.unit.model.UnitService" />

<jsp:useBean id="recipeCatSvc" scope="page" class="com.recipecuisinecategory.model.RecipeCuisineCategoryService" />
<jsp:useBean id="recipeIngUnitSvc" scope="page" class="com.recipeingredientunit.model.RecipeIngredientUnitService" />
<jsp:useBean id="recipeStepSvc" scope="page" class="com.recipestep.model.RecipeStepService" />


<%
	RecipeVO recipeVO = (RecipeVO) request.getAttribute("recipeVO");
	if (recipeVO != null) {
		byte[] recipePicTopBuffer = recipeVO.getRecipePicTop();
		request.setAttribute("recipePicTopBuffer", recipePicTopBuffer);
		
	List<RecipeCuisineCategoryVO> recipeCatVOs = (List<RecipeCuisineCategoryVO>) request.getAttribute("recipeCatVOs");
	List<RecipeIngredientUnitVO> recipeIngUnitVOs = (List<RecipeIngredientUnitVO>) request.getAttribute("recipeIngUnitVOs");
	List<RecipeStepVO> recipeStepVOs = (List<RecipeStepVO>) request.getAttribute("recipeStepVOs");
	if (recipeStepVOs == null) {
		request.getSession().removeAttribute("recipePicTopBuffer");
		request.getSession().removeAttribute("recipeStepPicBuffers");
	}
	
	List<RecipeCuisineCategoryVO> orgRecipeCatVOs = recipeCatSvc.getAllByRecipe(recipeVO.getRecipeID());
	List<RecipeIngredientUnitVO> orgRecipeIngUnitVOs = recipeIngUnitSvc.getAllByRecipe(recipeVO.getRecipeID());
	List<RecipeStepVO> orgRecipeStepVOs = recipeStepSvc.getAllByRecipe(recipeVO.getRecipeID());
	request.setAttribute("orgRecipeCatVOs", orgRecipeCatVOs);
	request.setAttribute("orgRecipeIngUnitVOs", orgRecipeIngUnitVOs);
	request.setAttribute("orgRecipeStepVOs", orgRecipeStepVOs);
	}

	String[] unitIDs = request.getParameterValues("unitIDs");
	String[] recipeUnitAmounts = (String[]) request.getAttribute("recipeUnitAmounts");
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin register page</title>
    <!-- Custom fonts for this template-->
    <link href="<%=request.getContextPath()%>/vendors/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">
    <!-- Custom styles for this template-->
    <link href="<%=request.getContextPath()%>/Dashboard/css/sb-admin-2.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/jquery-ui/css/jquery-ui.css">
    <link href="<%=request.getContextPath()%>/vendors/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
<style>

.vertical-container {
	display: -webkit-flex;
	display: flex;
	-webkit-align-items: center;
	align-items: center;
	-webkit-justify-content: center;
	justify-content: center;
}

.ui-autocomplete {
	max-height: 100px;
	overflow-y: auto;
	overflow-x: hidden;
}

.catAutoOutput ul, .ingAutoOutput ul {
	background: #eee;
	min-height: 55px;
	padding: 5px;
	margin-top: 10px;
}

.catAutoOutput ul>li {
	display: inline-block;
	margin: 10px 3px;
	padding: 5px;
	border: 1px solid gray;
	background: #fff;
}
.catAutoOutput ul>li span{
	margin-right: 3px;
}


.ingAutoOutput ul > li {
	margin: 10px 3px;
	padding: 3px;
	border: 1px solid gray;
	background: #fff;
	display: flex;
	justify-content: space-between;
	position: relative;
}

.preview {
	border: 1px solid lightgray;
	display: inline-block;
	position: relative;
	min-height: 80px; /* 40px */
	border-radius: .25rem !important;
	margin-top: 10px;
	padding: 3px;
}

.preview span.text {
	position: absolute;
	display: inline-block;
	left: 50%;
	top: 50%;
	transform: translate(-50%, -50%);
	z-index: 1;
	color: lightgray;
}
.preview_img {
    width: 100%;
}
.uploadBtn {
    margin-bottom: 10px;
}
.recipe td:last-child {
    text-align: right;
}
.agreeBox {
	margin-top: 10px;
	margin-bottom: 10px;
}

.errorSpan {
	color: red;
	font-weight: bold;
}
</style>
  <script src="https://cdn.tiny.cloud/1/7cr4ih870sgurlll171zc6ccfd9bh8ylwqjh0slgdx97xyt9/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script>
  <script>

  tinymce.init({
	    selector: 'textarea',
	    menubar: '',
	    toolbar: 'undo redo | bold italic underline strikethrough | fontselect fontsizeselect formatselect | alignleft aligncenter alignright alignjustify | outdent indent |  numlist bullist | forecolor backcolor removeformat | charmap emoticons | ltr rtl',
	  });

  </script>
</head>

<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">

        <!-- Sidebar -->
        <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">
        	<%@include file="/Dashboard/Admin/sidebar.page" %>
        </ul>
        <!-- End of Sidebar -->

        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">

                <!-- Topbar -->
                <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
					<%@include file="/Dashboard/Admin/topbar.page" %>
                </nav>
                <!-- End of Topbar -->

                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <h1 class="h3 mb-2 text-gray-800">食譜資訊</h1>
<!--                     <p class="mb-4"> -->
						
<!-- 					</p> -->

                            <!-- Project Card Example -->
                            <div class="card shadow mb-4">
		                        <div class="card-header py-3">
		                        	<h6 class="m-0 font-weight-bold text-primary">
										${recipeVO.recipeName}
									</h6>
		                        </div>
	                        
                                <div class="card-body">

			<form method="post" action="<%=request.getContextPath()%>/Dashboard/Recipe/recipe.do" enctype="multipart/form-data">

				<div class="form-group">
					<label for="recipeName" class="font-weight-bold text-primary">食譜名稱：</label>
					<span class="errorSpan">${errorMsgs.get("recipeNameErr")}</span>
					<input type="text" class="form-control" name="recipeName" placeholder="請輸入食譜名稱" value="${recipeVO.recipeName}">
					<input type="hidden" name="recipeID" value="${recipeVO.recipeID}">
				</div>

				<div class="form-group">
					<label for="recipeCategoryNames" class="font-weight-bold text-primary">食譜分類：</label>
					<span class="errorSpan">${errorMsgs.get("recipeCategoryIDErr")}</span>
					<div class="ui-widget">
						<input class="form-control" id="catAutoCompl" name="recipeCategoryNames" placeholder="請輸入並選擇料理分類">
					</div>
					<div class="catAutoOutput">
						<ul>
							<c:if test="${empty recipeCatVOs}">
								<c:forEach var="recipeCatVO" items="${orgRecipeCatVOs}">
									<li data-id='${recipeCatVO.cuisineCategoryID}'>
										<span>${categorySvc.getOneCategory(recipeCatVO.cuisineCategoryID).cuisineCategoryName}</span>
										<i class='fas fa-times'></i>
									</li>
								</c:forEach>
							</c:if>
							<c:if test="${not empty recipeCatVOs}">
								<c:forEach var="recipeCatVO" items="${recipeCatVOs}">
									<li data-id='${recipeCatVO.cuisineCategoryID}'>
										<span>${categorySvc.getOneCategory(recipeCatVO.cuisineCategoryID).cuisineCategoryName}</span>
										<i class='fas fa-times'></i>
									</li>
								</c:forEach>
							</c:if>
						</ul>
						<c:if test="${empty recipeCatVOs}">
							<input class="form-control catAutoInput" name="recipeCategoryIDs" type="hidden" value="<c:forEach var="recipeCatVO" items="${orgRecipeCatVOs}"> ${recipeCatVO.cuisineCategoryID}</c:forEach>">
						</c:if>
						<c:if test="${not empty recipeCatVOs}">
							<input class="form-control catAutoInput" name="recipeCategoryIDs" type="hidden" value="<c:forEach var="recipeCatVO" items="${recipeCatVOs}"> ${recipeCatVO.cuisineCategoryID}</c:forEach>">
						</c:if>
					</div>
				</div>

				<div class="form-group">
					<label for="recipeIngredientNames" class="font-weight-bold text-primary">食材標籤與單位：</label>
					<span class="errorSpan">${errorMsgs.get("recipeIngredientIDErr")}</span>
					<span class="errorSpan">${errorMsgs.get("recipeUnitIDErr")}</span>
					<span class="errorSpan">${errorMsgs.get("recipeUnitAmountErrNull")}</span>
					<span class="errorSpan">${errorMsgs.get("recipeunitAmountErrNumber")}</span>
					<div class="ui-widget">
						<input id="ingAutoCompl" class="form-control" name="recipeIngredientNames" placeholder="請輸入並選擇食材標籤">
					</div>
					<div class="ingAutoOutput">
						<ul>
							<c:if test="${empty recipeIngUnitVOs}">
								<c:forEach var="recipeIngUnitVO" items="${orgRecipeIngUnitVOs}">
									<li class='row' data-id='${recipeIngUnitVO.ingredientID}'>
										<div class='col-4 vertical-container'>${ingredientSvc.getOneIngredient(recipeIngUnitVO.ingredientID).ingredientName}</div>
										<input class='form-control unitAmounts col-2' name='unitAmounts' type='number' min="0.01" max="9999.99" step='0.01' value='${recipeIngUnitVO.unitAmount}'>
										<select class='form-control unitIDs col-3' name='unitIDs'>
											<option value='0'>選擇單位</option>
											<c:forEach var="unitOne" items="${unitSvc.all}">
												<c:if test="${unitOne.unitID != recipeIngUnitVO.unitID}">
													<option value='${unitOne.unitID}'>${unitOne.unitName}</option>
												</c:if>
												<c:if test="${unitOne.unitID == recipeIngUnitVO.unitID}">
													<option value='${unitOne.unitID}' selected>${unitOne.unitName}</option>
												</c:if>
											</c:forEach>
										</select>
										<div class="vertical-container"><i class='fas fa-times'></i></div>
									</li>
								</c:forEach>
							</c:if>
							<c:if test="${not empty recipeIngUnitVOs}">
								<c:forEach var="recipeIngUnitVO" items="${recipeIngUnitVOs}">
									<li class='row' data-id='${recipeIngUnitVO.ingredientID}'>
										<div class='col-4 vertical-container'>${ingredientSvc.getOneIngredient(recipeIngUnitVO.ingredientID).ingredientName}</div>
										<input class='form-control unitAmounts col-2' name='unitAmounts' type='number' min="0.01" max="9999.99" step='0.01' value='${recipeIngUnitVO.unitAmount}'>
										<select class='form-control unitIDs col-3' name='unitIDs'>
											<option value='0'>選擇單位</option>
											<c:forEach var="unitOne" items="${unitSvc.all}">
												<c:if test="${unitOne.unitID != recipeIngUnitVO.unitID}">
													<option value='${unitOne.unitID}'>${unitOne.unitName}</option>
												</c:if>
												<c:if test="${unitOne.unitID == recipeIngUnitVO.unitID}">
													<option value='${unitOne.unitID}' selected>${unitOne.unitName}</option>
												</c:if>
											</c:forEach>
										</select>
										<div class="vertical-container"><i class='fas fa-times'></i></div>
									</li>
								</c:forEach>
							</c:if>
						</ul>
						<c:if test="${empty recipeIngUnitVOs}">
							<input class="ingAutoInput" name="recipeIngredientIDs" type="hidden" value="<c:forEach var="recipeIngUnitVO" items="${orgRecipeIngUnitVOs}"> ${recipeIngUnitVO.ingredientID}</c:forEach>">
						</c:if>
						<c:if test="${not empty recipeIngUnitVOs}">
							<input class="ingAutoInput" name="recipeIngredientIDs" type="hidden" value="<c:forEach var="recipeIngUnitVO" items="${recipeIngUnitVOs}"> ${recipeIngUnitVO.ingredientID}</c:forEach>">
						</c:if>
					</div>
				</div>

				<div class="form-group">
					<label for="recipeIntroduction" class="font-weight-bold text-primary">食譜介紹：</label>
					<span class="errorSpan">${errorMsgs.get("recipeIntroductionErr")}</span>
					<textarea class="form-control" name="recipeIntroduction" placeholder="請輸入食譜介紹" rows="10" cols="50">${recipeVO.recipeIntroduction}</textarea>
				</div>

				<div class="form-group">
					<label for="recipeServe" class="font-weight-bold text-primary">享用人數：</label><span class="errorSpan">${errorMsgs.get("recipeServeErr")}</span>
					<input class="form-control" type="number" name="recipeServe" placeholder="請輸入食譜準備的食材可供幾人享用" step="1" min="1" max="20" value="${recipeVO.recipeServe}">
				</div>

				<div class="form-group">
					<label for="recipePicTop row" class="font-weight-bold text-primary">食譜完成照：</label><span class="errorSpan">${errorMsgs.get("recipePicTopErr")}</span>
						<div id="picTopUploadBtn" class="uploadBtn btn btn-secondary col-6">上傳檔案</div>
						<input type="file" name="recipePicTop" class="form-control-file col-6" style="display:none">
						<div id="picTopUploadPreview" class="preview col-6"><img id="top_img" src="<%=request.getContextPath()%>/Recipe/Pic/Top/${recipeVO.recipeID}" class="preview_img"></div>
				</div>

				<label class="font-weight-bold text-primary">食譜步驟：</label>
				<span class="errorSpan">${errorMsgs.get("recipeStepErr")}</span>
				<span class="errorSpan" style="margin-left: 10px;">${errorMsgs.get("recipeStepPicErr")}</span>
				<table class="recipeStepsTable table">
					<tbody>
						<c:if test="${empty recipeStepVOs}">
							<c:forEach var="recipeStepVO" items="${orgRecipeStepVOs}">
								<tr class="form-group recipe row">
									<td class="col-6 order-1 col-lg-1 order-lg-1">
										<span class="order">${recipeStepVO.recipeStepOrder}</span>
										<input name="recipeStepIDs" type="hidden" value="${recipeStepVO.recipeStepID}">
										<input name="recipeStepOrders" type="hidden" value="${recipeStepVO.recipeStepOrder}">
										<input name="oldFileIdentify" type="hidden" value="true">
									</td>
									<td class="col-12 order-3 col-lg-6 order-lg-2">
										<textarea class="form-control" name="recipeStepTexts" placeholder="請輸入步驟說明" rows="5" cols="40">${recipeStepVO.recipeStepText}</textarea>
									</td>
									<td class="col-12 order-4 col-lg-4 order-lg-3">
										<div class="picStepUploadBtn uploadBtn btn btn-secondary col-12">上傳圖片</div>
										<input type="file" class="form-control-file col-12" name="recipeStepPic" style="display:none" multiple="multiple">
										<div class="picStepPreview preview col-12"><img src="<%=request.getContextPath()%>/Recipe/Pic/Step/${recipeStepVO.recipeStepID}" class="step_img preview_img"></div>
									</td>
									<td class="col-6 order-2 col-lg-1 order-lg-4">
										<c:if test="${fn:length(orgRecipeStepVOs) == 1}">
											<font color="gray"><i class='fas fa-times'></i></font>
										</c:if>
										<c:if test="${fn:length(orgRecipeStepVOs) >= 2}">
											<i class='fas fa-times'></i>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${not empty recipeStepVOs}">
							<c:forEach var="recipeStepVO" items="${recipeStepVOs}">
								<tr class="form-group recipe row">
									<td class="col-6 order-1 col-lg-1 order-lg-1">
										<span class="order">${recipeStepVO.recipeStepOrder}</span>
										<input name="recipeStepIDs" type="hidden" value="${recipeStepVO.recipeStepID}">
										<input name="recipeStepOrders" type="hidden" value="${recipeStepVO.recipeStepOrder}">
										<input name="oldFileIdentify" type="hidden" value="true">
									</td>
									<td class="col-12 order-3 col-lg-6 order-lg-2">
										<textarea class="form-control" name="recipeStepTexts" placeholder="請輸入步驟說明" rows="5" cols="40">${recipeStepVO.recipeStepText}</textarea>
									</td>
									<td class="col-12 order-4 col-lg-4 order-lg-3">
										<div class="picStepUploadBtn uploadBtn btn btn-primary col-12">上傳圖片</div>
										<input type="file" class="form-control-file col-12" name="recipeStepPic" style="display:none" multiple="multiple">
										<div class="picStepPreview preview col-12"><c:if test="${recipeStepVO.recipeStepID != null}"><img src="<%=request.getContextPath()%>/Recipe/Pic/Step/${recipeStepVO.recipeStepID}" class="step_img preview_img"></c:if><c:if test="${recipeStepVO.recipeStepID == null}"><span class="text">預覽圖</span></c:if></div>
									</td>
									<td class="col-6 order-2 col-lg-1 order-lg-4">
										<c:if test="${fn:length(recipeStepVOs) == 1}">
											<font color="gray"><i class='fas fa-times'></i></font>
										</c:if>
										<c:if test="${fn:length(recipeStepVOs) >= 2}">
											<i class='fas fa-times'></i>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>

				<div id="addStepBtn" class="btn btn-secondary btn-block">增加一個步驟</div>
<!-- 				<div class="agreeBox"> -->
<!-- 					<label> -->
<!-- 						<input type="checkbox" name="agreement" class="styled-checkbox" value="agree"> -->
<!-- 							同意使用本網站之條款及隱私權政策 -->
<!-- 					</label> -->
<!-- 				</div> -->
				<hr />
				<span class="errorSpan">${errorMsgs.get("agreementErr")}</span>
				<input type="hidden" name="action" value="update">
				<input type="hidden" name="accountID" value="${recipeVO.accountID}">
				<button id="btnSubmit" class="btn btn-primary btn-block" type="submit">送出</button>
			</form>
				<a href="<%=request.getContextPath()%>/Dashboard/Admin/listAllRecipe.jsp" class="btn btn-light btn-block mt-1">返回列表</a>

                                
		                        </div>
		                    </div>

                </div>
                <!-- /.container-fluid -->

            </div>
            <!-- End of Main Content -->


            <!-- Footer -->
            <footer class="sticky-footer bg-white">
                <%@include file="/Dashboard/Admin/footer.page" %>
            </footer>
            <!-- End of Footer -->

        </div>
        <!-- End of Content Wrapper -->

    </div>
    <!-- End of Page Wrapper -->
	<%@include file="/Dashboard/Admin/endActive.page" %>

    <!-- Bootstrap core JavaScript-->
    <script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.6.0.min.js"></script>
    <script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="<%=request.getContextPath()%>/vendors/jquery-easing/jquery.easing.min.js"></script>
    <script src="<%=request.getContextPath()%>/vendors/datatables/jquery.dataTables.min.js"></script>
    <script src="<%=request.getContextPath()%>/vendors/datatables/dataTables.bootstrap4.min.js"></script>
	<script	src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/jquery-ui/js/jquery-ui.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="<%=request.getContextPath()%>/Dashboard/js/sb-admin-2.js"></script>
    <script>
		$(function() {

			<%@ include file="/Recipe/autoComplCat.file"%>
			<%@ include file="/Recipe/autoComplIng.file"%>
			
			var oldFileIdentifyArr = new Array();
			<%
			if (request.getAttribute("oldFileIdentify") != null) {
				String[] oldFileIdentify = (String[])request.getAttribute("oldFileIdentify");
				for (String one : oldFileIdentify ) {	%>
					var tempOD = "<%= one %>";
					if (tempOD != "") { 
						oldFileIdentifyArr.push(tempOD);
					}
			<%	} %>

			$('input[name="oldFileIdentify"]').each(function(index, item, array){
				$(item).val(oldFileIdentifyArr[index]);
			});
			
		<%
			} %>
			
			var isNewRecipe = "${recipeStepVOs}";
			if (isNewRecipe == "") {
				sessionStorage.clear();
			}
	
			$("table").on("click", "svg", function(){
				if ($("table").find("tr.recipe").length != 1) {
					var that = this;
					var delOrder = $(this).closest("tr.recipe").find("span.order").html();
					var recipeID = $('input[name="recipeID"]').val();
			  		console.log(recipeID);
					$.ajax({
						  url: '<c:url value="/Dashboard/Recipe/deleteRecipeStep.do?update="/>' + recipeID + "&delOrder=" + delOrder.toString(),
						  type: "GET",
						  success: function(data){
							$(that).closest("tr.recipe").remove();
							var stepOrderSpan = $("table").find("tr.recipe").find("td").find("span.order");
							var stepOrderInput = $("table").find("tr.recipe").find("td").find("input[name='recipeStepOrders']");
							$(stepOrderSpan).each(function(index, element) {
								$(element).html(index + 1);
							});
							$(stepOrderInput).each(function(index, element) {
								$(element).val(index + 1);
							});
							if ($("table").find("tr.recipe").length == 1) {
								var lastStepOrderDel = $("table").find("tr").find("td").find("svg").first().closest("td");
								lastStepOrderDel.html("<font color='gray'><i class='fas fa-times'></i></font>");
							}
						  },
						});
	
				}
			});
		});
	</script>
	<script src="<%=request.getContextPath()%>/Recipe/js/addRecipe.js"></script>


</body>
</html>