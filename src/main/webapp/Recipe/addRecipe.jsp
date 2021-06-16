<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="java.util.*"%>
<%@ page import="com.recipe.model.*"%>
<%@ page import="com.cuisinecategory.model.*"%>
<%@ page import="com.recipecuisinecategory.model.*"%>
<%@ page import="com.recipestep.model.*"%>

<%
	RecipeVO recipeVO = (RecipeVO) request.getAttribute("recipeVO");
	if (recipeVO == null) {
		request.getSession().removeAttribute("recipePicTopBuffer");
		request.getSession().removeAttribute("recipeStepPicBuffers");
	}
	List<RecipeStepVO> recipeStepVOs = (List<RecipeStepVO>) request.getAttribute("recipeStepVOs");
	String recipeCategoryIDs = request.getParameter("recipeCategoryIDs");
	String recipeIngredientIDs = request.getParameter("recipeIngredientIDs");
	String[] unitIDs = request.getParameterValues("unitIDs");
	String[] recipeUnitAmounts = (String[]) request.getAttribute("recipeUnitAmounts");
	String[] recipeStepText = request.getParameterValues("recipeStepText");
	// 	String[] recipeUnitIDs = request.getParameterValues("recipeUnitIDs");
	// 	RecipeCuisineCategoryVO recipeCatVO = (RecipeCuisineCategoryVO) request.getAttribute("racipeCatVO");
// 	if (session.getAttribute("recipePicTopBuffer") != null) { System.out.println(session.getAttribute("recipePicTopName")); }
%>

<jsp:useBean id="categorySvc" scope="page" class="com.cuisinecategory.model.CuisineCategoryService" />
<jsp:useBean id="ingredientSvc" scope="page" class="com.ingredient.model.IngredientService" />
<jsp:useBean id="unitSvc" scope="page" class="com.unit.model.UnitService" />

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap 的 CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/slick/slick.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/slick/slick-theme.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/jquery-ui/css/jquery-ui.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/footer.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/Recipe/css/addRecipe.css">
<style>
        .preview {
            border: 1px solid lightgray;
            display: inline-block; 
            position: relative;
            min-height: 80px;	/* 40px */
            border-radius: .25rem!important;
            margin-top:10px;
            padding: 3px;
        }

        .preview span.text {
            position: absolute;
            display: inline-block;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
            z-index: -1;
            color: lightgray;
        }
        
        .preview_img {
        	width: 100%;
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
	<main class="row col-12 col-md-10 justify-content-between"
		style="margin: 0 auto;">

		<div class="content col-md-9 col-12">
			<%-- breadcrumbs --%>
			<div class="breadcrumbs" aria-label="breadcrumb">
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a
						href="<%=request.getContextPath()%>">Just Eat 揪食</a></li>
					<li class="breadcrumb-item"><a
						href="<%=request.getContextPath()%>/Recipe/home.jsp">食譜</a></li>
					<li class="breadcrumb-item active" aria-current="page">新增食譜</li>
				</ol>
			</div>



			<form method="post"
				action="<%=request.getContextPath()%>/Recipe/recipe.do"
				enctype="multipart/form-data">
				<h2>食譜基本資訊</h2>

				<div class="form-group">
					<label for="recipeName">食譜名稱：</label>
					<input type="text" class="form-control" name="recipeName" placeholder="請輸入食譜名稱" value="<%=(recipeVO == null) ? "" : recipeVO.getRecipeName()%>">
					${errorMsgs.get(recipeNameErrNull)}
					${errorMsgs.get("recipeNameErrNull")}
					${errorMsgs.get("recipeNameErrReg")}
				</div>

				<div class="form-group">
					<label for="recipeCategoryNames">食譜分類：</label><span>${errorMsgs.get("recipeCategoryIDErrNull")}</span><br>
					<span class="ui-widget">
						<input class="form-control" id="catAutoCompl" name="recipeCategoryNames" placeholder="請輸入並選擇料理分類"><br>
					</span>
					<div class="catAutoOutput">
						<ul>
							<c:if test="${not empty recipeCatVOs}">
								<c:forEach var="recipeCatVO" items="${recipeCatVOs}">
									<li data-id='${recipeCatVO.cuisineCategoryID}'>
										<span>${categorySvc.getOneCategory(recipeCatVO.cuisineCategoryID).cuisineCategoryName}</span>
										<i class='fas fa-times'></i>
									</li>
								</c:forEach>
							</c:if>
						</ul>
						<input class="form-control catAutoInput" name="recipeCategoryIDs" type="hidden" value="<%=(recipeCategoryIDs == null) ? "" : recipeCategoryIDs%>">
					</div>

				</div>

				<div class="form-group">
					<label for="recipeIngredientNames">食材標籤與單位：</label>
					<span>${errorMsgs.get("recipeIngredientIDErrNull")}
						${errorMsgs.get("recipeUnitIDErrNull")}
						${errorMsgs.get("recipeUnitAmountErrNull")}
						${errorMsgs.get("recipeunitAmountErrNumber")}</span><br>
						<span class="ui-widget">
						<input id="ingAutoCompl" class="form-control" name="recipeIngredientNames" placeholder="請輸入並選擇食材標籤"><br>
					</span>
					<div class="ingAutoOutput">
						<ul>
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
						<input class="ingAutoInput" name="recipeIngredientIDs" type="hidden" value="<%=(recipeIngredientIDs == null) ? "" : recipeIngredientIDs%>">
					</div>
				</div>

				<div class="form-group">
					<label for="recipeIntroduction">食譜介紹：</label>
						<span>${errorMsgs.get("recipeIntroductionErrNull")}
						${errorMsgs.get("recipeIntroductionErrReg")}</span><br>
					<textarea class="form-control" name="recipeIntroduction" placeholder="請輸入食譜介紹" rows="10" cols="50"><%=(recipeVO == null) ? "" : recipeVO.getRecipeIntroduction()%></textarea>
				</div>

				<div class="form-group">
					<label for="recipeServe">享用人數：</label>
						<input class="form-control" type="number" name="recipeServe" placeholder="請輸入食譜準備的食材可供幾人享用" step="1" min="1" max="20" value="<%=(recipeVO == null) ? "" : recipeVO.getRecipeServe()%>"><br>
					${errorMsgs.get("recipeServeErrWrong")}
					${errorMsgs.get("recipeServeErrRange")}
				</div>

				<div class="form-group">
					<label for="recipePicTop row">食譜完成照：</label>
					${errorMsgs.get("recipePicTopErr")}<br>
						<div id="picTopUploadBtn" class="uploadBtn btn btn-primary col-6">上傳圖片</div>
						<input type="file" name="recipePicTop" class="form-control-file col-6" style="display:none">
						<div id="picTopUploadPreview" class="preview col-6"><span id="picTopUploadText" class="text">預覽圖</span></div>
				</div>

				<h2>食譜步驟</h2>
				${errorMsgs.get("recipeStepErrNull")}
				${errorMsgs.get("recipeStepPicErr")}
				<table class="recipeStepsTable table">
					<tbody>

						<c:if test="${empty recipeStepVOs}">
							<tr class="form-group recipe row">
								<td class="col-6 order-1 col-lg-1 order-lg-1">
									<span class="order">1</span>
									<input name="recipeStepIDs" type="hidden" value="">
									<input name="recipeStepOrders" type="hidden" value="1">
									<input name="oldFileIdentify" type="hidden" value="false">
								</td>
								<td class="col-12 order-3 col-lg-6 order-lg-2">
									<textarea class="form-control" name="recipeStepTexts" placeholder="請輸入步驟說明" rows="5" cols="40"></textarea>
								</td>
								<td class="col-12 order-4 col-lg-4 order-lg-3">
									<div class="picStepUploadBtn uploadBtn btn btn-primary col-12">上傳圖片</div>
									<input type="file" class="form-control-file col-12" name="recipeStepPic" style="display:none" multiple="multiple">
									<div class="picStepPreview preview col-12"><span class="text">預覽圖</span></div>
								</td>
								<td class="col-6 order-2 col-lg-1 order-lg-4">
									<font color="gray"><i class='fas fa-times'></i></font>
								</td>
							</tr>
						</c:if>

						<c:if test="${not empty recipeStepVOs}"> 
							<c:forEach var="recipeStepVO" items="${recipeStepVOs}">
								<tr class="form-group recipe row">
									<td class="col-6 order-1 col-lg-1 order-lg-1">
										<span class="order">${recipeStepVO.recipeStepOrder}</span>
										<input name="recipeStepIDs" type="hidden" value="">
										<input name="recipeStepOrders" type="hidden" value="${recipeStepVO.recipeStepOrder}">
										<input name="oldFileIdentify" type="hidden" value="">
									</td> 
									<td class="col-12 order-3 col-lg-6 order-lg-2">
										<textarea class="form-control" name="recipeStepTexts" placeholder="請輸入步驟說明" rows="5" cols="40">${recipeStepVO.recipeStepText}</textarea>
									</td>
									<td class="col-12 order-4 col-lg-4 order-lg-3">
										<div class="picStepUploadBtn uploadBtn btn btn-primary col-12">上傳圖片</div>
										<input type="file" class="form-control-file col-12" name="recipeStepPic" style="display:none" multiple="multiple">
										<div class="picStepPreview preview col-12"><span class="text">預覽圖</span></div>
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

				<div class="form-check">
						<input type="checkbox" name="agreement" class="form-check-input">
						<label class="form-check-label" for="agreement">同意使用本網站之條款及隱私權政策</label>
						<input type="hidden" name="action" value="insert">
				</div>
				<button id="btnSubmit" class="btn btn-primary" type="submit">送出</button>
			</form>

		</div>

		<div class="sidebar col-md-3 col-12">目前沒東西</div>
	</main>

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
		$(function() {
			<%@ include file="/Recipe/autoComplCat.file"%>
			<%@ include file="/Recipe/autoComplIng.file"%>
			
			var oldFileIdentify = new Array();
			<%
			if (request.getAttribute("oldFileIdentify") != null) {
				String[] oldFileIdentify = (String[])request.getAttribute("oldFileIdentify");
				for (String one : oldFileIdentify ) {	%>
					var tempOD = "<%= one %>";
					if (tempOD != "") { 
						oldFileIdentify.push(tempOD);
					}
			<%	}
			} %>
			
			$('input[name="oldFileIdentify"]').each(function(index, item, array){
				$(item).val(oldFileIdentify[index]);
			});
			
			var isNewRecipe = "${recipeVO}";
			if (isNewRecipe == "") {
				sessionStorage.clear();
			}

            var preview_html = `<span id="preview_text" class="text">預覽圖</span>`;
            var top_img_html = `<img id="top_img" class="preview_img" src="#"></img>`;

            if (sessionStorage.getItem("form_data") != null) {
            	session_history = JSON.parse(sessionStorage.getItem("form_data"));
				
            	if (session_history.top_img != null) {
	                picTopUploadPreview.innerHTML = null;
	                picTopUploadPreview.insertAdjacentHTML('afterbegin', top_img_html);
	
	                document.querySelector("#top_img").src = session_history.top_img;
	                img_base64 = session_history.top_img;
            	}
            	
            	if (session_history.step_pic != null) {
            		session_history.step_pic.forEach(function(element, index) {
            			if (element != null) {
            				$($('.picStepPreview')[index]).empty().append('<img src="'+ element +'" class="step_img preview_img">');
            			}
                	});
            	}
            }
            
			
            function previewerTopPic(file) {
    			let file_reader = new FileReader();
                file_reader.readAsDataURL(file);
                
                file_reader.addEventListener("load", function (e) {
                    // load事件用target
                    var top_preview_text = document.querySelector('#picTopUploadText');
                    var top_img = document.querySelector('#top_img');
                    
                    var img_ele = document.createElement('img');
                    img_ele.id = 'top_img';
                    img_ele.src = e.target.result;
                    img_ele.setAttribute('class', 'preview_img');
                    
//                     picTopUploadName.innerHTML = file.name;	// 這裡的file跟onload事件無關

                    if ((img_ele == null && top_preview_text != null) || top_preview_text != null) {
                    	picTopUploadPreview.replaceChild(img_ele, top_preview_text);
                    } else {
                    	picTopUploadPreview.replaceChild(img_ele, top_img);
                    }
                });
                

//                 file_reader.readAsDataURL(file);
//                 file_reader.addEventListener("loadend", function (e) {
                	
//                 });
                
            };


            document.querySelector("input[name='recipePicTop']").addEventListener("change", function (e) {
                // change事件用target
                previewerTopPic(e.target.files[0]);
            });
            
            $('#picTopUploadBtn').on("click", function() {
            	$('input[name="recipePicTop"]').trigger("click");
            });
            
            function previewerStepPic(event, file) {
            	let file_reader = new FileReader();
                file_reader.readAsDataURL(file);
                
                file_reader.addEventListener("load", function (e) {
                    // load事件用target
                    
//                     let img_ele = document.createElement('img');
//                     img_ele.src = e.target.result;
//                     img_ele.setAttribute('class', 'step_img preview_img');
                    
//                     $(event).closest('td').find('.preview').empty().append(img_ele);
                    $(event).closest('td').find('.preview').empty().append('<img src="'+ e.target.result +'" class="step_img preview_img">');
                    
//                     if ((img_ele == null && step_preview_text != null) || step_preview_text != null) {
//                     	picTopUploadPreview.replaceChild(img_ele, step_preview_text);
//                     } else {
//                     	picTopUploadPreview.replaceChild(img_ele, step_img);
//                     }
                });
            };
            
            $(document).on("change", "input[name='recipeStepPic']", function (e) {
                // change事件用target
                previewerStepPic(this, e.target.files[0]);
            });
            
            $(document).on("click", '.picStepUploadBtn', function() {
            	$(this).closest('td').find('input[name="recipeStepPic"]').trigger("click");
            });
            

            btnSubmit.addEventListener("click", function() {
            	var session_history = new Object();
            	var step_pic = new Array();
            	
                if (document.querySelector('#top_img') != null) {
                    session_history['top_img'] = document.querySelector('#top_img').getAttribute("src");
                }
                if (document.querySelectorAll('.step_img') != null) {
                	document.querySelectorAll('.picStepPreview').forEach(function(element, index) {
                		step_pic.push($(element).find('img.step_img').attr("src"));
                	});
                	session_history.step_pic = step_pic;
                }
                sessionStorage.setItem("form_data", JSON.stringify(session_history));
            });
            
            
            
		});
	</script>
	<script src="<%=request.getContextPath()%>/Recipe/js/addRecipe.js"></script>
</body>
</html>