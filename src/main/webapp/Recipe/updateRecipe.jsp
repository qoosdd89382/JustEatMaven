<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/Recipe/recipeImport.jsp"%>

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
	// 驗證新資料帶回
// 	String recipeCategoryIDs = request.getParameter("recipeCategoryIDs");
// 	String recipeIngredientIDs = request.getParameter("recipeIngredientIDs");
	String[] unitIDs = request.getParameterValues("unitIDs");
	String[] recipeUnitAmounts = (String[]) request.getAttribute("recipeUnitAmounts");
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap 的 CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<%-- <link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/slick/slick.css" /> --%>
<%-- <link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/slick/slick-theme.css" /> --%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/jquery-ui/css/jquery-ui.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/footer.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/Recipe/css/addRecipe.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/Recipe/css/recipeSidebar.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/Recipe/css/recipeSearchbar.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/Recipe/css/recipeHeader.css">
<title>${recipeVO.recipeName} [編輯] | 食譜 | Just Eat 揪食</title>
  <script src="https://cdn.tiny.cloud/1/7cr4ih870sgurlll171zc6ccfd9bh8ylwqjh0slgdx97xyt9/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script>
  <script>

  tinymce.init({
	    selector: 'textarea',
	    menubar: '',
	    toolbar: 'undo redo | bold italic underline strikethrough | fontselect fontsizeselect formatselect | alignleft aligncenter alignright alignjustify | outdent indent |  numlist bullist | forecolor backcolor removeformat | charmap emoticons | ltr rtl',
	  });

  </script>
<style>
</style>
</head>
<body>
	
	<%-- include header --%>
	<header>
		<%@ include file="/common/header.jsp"%>
	</header>

	<%-- include navbar --%>
	<%@ include file="/Recipe/recipeHeader.page"%>

	<%-- main --%>
	<main class="row col-12 col-xl-10 justify-content-between" style="margin: 0 auto;">
	<%-- include searchbar --%>
	<div class="searchbar col-12">
		<%@ include file="/Recipe/recipeSearchbar.page"%>
	</div>

		<div class="content col-md-9 col-12">
			<%-- breadcrumbs --%>
			<div class="breadcrumbs" aria-label="breadcrumb">
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>">Just Eat 揪食</a></li>
					<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/Recipe/home.jsp">食譜</a></li>
					<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/Recipe/listAllRecipe.jsp">食譜列表</a></li>
					<li class="breadcrumb-item active" aria-current="page">${recipeVO.recipeName} [編輯]</li>
				</ol>
			</div>

			<form method="post" action="<%=request.getContextPath()%>/Recipe/recipe.do" enctype="multipart/form-data">
				<h3>食譜基本資訊</h3>

				<div class="form-group">
					<label for="recipeName">食譜名稱：</label>
					<span class="errorSpan">${errorMsgs.get("recipeNameErr")}</span>
					<input type="text" class="form-control" name="recipeName" placeholder="請輸入食譜名稱" value="${recipeVO.recipeName}">
					<input type="hidden" name="recipeID" value="${recipeVO.recipeID}">
				</div>

				<div class="form-group">
					<label for="recipeCategoryNames">食譜分類：</label>
					<span class="errorSpan">${errorMsgs.get("recipeCategoryIDErr")}</span>
					<div class="ui-widget">
						<input class="form-control" id="catAutoCompl" name="recipeCategoryNames" placeholder="請輸入並選擇料理分類">
					</div>
					<div class="catAutoOutput">
						<ul>
							<c:if test="${empty recipeCatVOs}">
<%-- 								<c:forEach var="recipeCatVO" items="${recipeCatSvc.getAllByRecipe(recipeVO.recipeID)}"> --%>
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
<%-- 							<input class="form-control catAutoInput" name="recipeCategoryIDs" type="hidden" value="<c:forEach var="recipeCatVO" items="${recipeCatSvc.getAllByRecipe(recipeVO.recipeID)}"> ${recipeCatVO.cuisineCategoryID}</c:forEach>"> --%>
							<input class="form-control catAutoInput" name="recipeCategoryIDs" type="hidden" value="<c:forEach var="recipeCatVO" items="${orgRecipeCatVOs}"> ${recipeCatVO.cuisineCategoryID}</c:forEach>">
						</c:if>
						<c:if test="${not empty recipeCatVOs}">
							<input class="form-control catAutoInput" name="recipeCategoryIDs" type="hidden" value="<c:forEach var="recipeCatVO" items="${recipeCatVOs}"> ${recipeCatVO.cuisineCategoryID}</c:forEach>">
						</c:if>
					</div>
				</div>

				<div class="form-group">
					<label for="recipeIngredientNames">食材標籤與單位：</label>
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
<%-- 								<c:forEach var="recipeIngUnitVO" items="${recipeIngUnitSvc.getAllByRecipe(recipeVO.recipeID)}"> --%>
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
<%-- 							<input class="ingAutoInput" name="recipeIngredientIDs" type="hidden" value="<c:forEach var="recipeIngUnitVO" items="${recipeIngUnitSvc.getAllByRecipe(recipeVO.recipeID)}"> ${recipeIngUnitVO.ingredientID}</c:forEach>"> --%>
							<input class="ingAutoInput" name="recipeIngredientIDs" type="hidden" value="<c:forEach var="recipeIngUnitVO" items="${orgRecipeIngUnitVOs}"> ${recipeIngUnitVO.ingredientID}</c:forEach>">
						</c:if>
						<c:if test="${not empty recipeIngUnitVOs}">
							<input class="ingAutoInput" name="recipeIngredientIDs" type="hidden" value="<c:forEach var="recipeIngUnitVO" items="${recipeIngUnitVOs}"> ${recipeIngUnitVO.ingredientID}</c:forEach>">
						</c:if>
					</div>
				</div>

				<div class="form-group">
					<label for="recipeIntroduction">食譜介紹：</label>
					<span class="errorSpan">${errorMsgs.get("recipeIntroductionErr")}</span>
					<textarea class="form-control" name="recipeIntroduction" placeholder="請輸入食譜介紹" rows="10" cols="50">${recipeVO.recipeIntroduction}</textarea>
				</div>

				<div class="form-group">
					<label for="recipeServe">享用人數：</label><span class="errorSpan">${errorMsgs.get("recipeServeErr")}</span>
					<input class="form-control" type="number" name="recipeServe" placeholder="請輸入食譜準備的食材可供幾人享用" step="1" min="1" max="20" value="${recipeVO.recipeServe}">
				</div>

				<div class="form-group">
					<label for="recipePicTop row">食譜完成照：</label><span class="errorSpan">${errorMsgs.get("recipePicTopErr")}</span>
						<div id="picTopUploadBtn" class="uploadBtn btn btn-primary col-6">上傳檔案</div>
						<input type="file" name="recipePicTop" class="form-control-file col-6" style="display:none">
						<div id="picTopUploadPreview" class="preview col-6"><img id="top_img" src="<%=request.getContextPath()%>/Recipe/Pic/Top/${recipeVO.recipeID}" class="preview_img"></div>
				</div>

				<h2>食譜步驟</h2>
				<span class="errorSpan">${errorMsgs.get("recipeStepErr")}</span>
				<span class="errorSpan" style="margin-left: 10px;">${errorMsgs.get("recipeStepPicErr")}</span>
				<table class="recipeStepsTable table">
					<tbody>
						<c:if test="${empty recipeStepVOs}">
<%-- 							<c:forEach var="recipeStepVO" items="${recipeStepSvc.getAllByRecipe(recipeVO.recipeID)}"> --%>
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
										<div class="picStepUploadBtn uploadBtn btn btn-primary col-12">上傳圖片</div>
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

				<div id="addStepBtn" class="btn btn-primary">增加一個步驟</div>

				<label>
					<input type="checkbox" name="agreement" class="styled-checkbox" value="agree">
						同意使用本網站之條款及隱私權政策
				</label>
				<span class="errorSpan">${errorMsgs.get("agreementErr")}</span>
				<input type="hidden" name="action" value="update">
				<input type="hidden" name="accountID" value="update">
				<button id="btnSubmit" class="btn btn-primary" type="submit">送出</button>
			</form>

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
	<script	src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
	<script	src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
	<script	src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
	<%-- <script src="https://cdnjs.cloudflare.com/ajax/libs/skrollr/0.6.30/skrollr.min.js"></script> --%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/vendors/slick/slick.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/jquery-ui/js/jquery-ui.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/footer.js"></script>
	<script>
	<%@ include file="/common/js/scriptFooter.page"%>
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
						  url: '<c:url value="/Recipe/deleteRecipeStep.do?update="/>' + recipeID + "&delOrder=" + delOrder.toString(),
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
// 								var lastStepOrderDel = $("table").find("tr").find("td").find("svg").first().closest("td");
// 								lastStepOrderDel.html("<font color='gray'><i class='fas fa-times'></i></font>");
								var lastStepOrderDel = $("table").find("tr").find("td").last();
								$(lastStepOrderDel).html("<font color='gray'><i class='fas fa-times'></i></font>");
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