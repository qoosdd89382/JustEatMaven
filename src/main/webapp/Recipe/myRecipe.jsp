<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%@ include file="/Recipe/recipeImport.jsp"%>

<%
	List<RecipeVO> list = null;
	if (request.getAttribute("list") == null) {
		list = recipeSvc.getAll();
	} else {
		list = (List<RecipeVO>) request.getAttribute("list");
	}
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
<link rel="stylesheet" href="<%=request.getContextPath()%>/Recipe/css/recipeHeader.css">
<link href="<%=request.getContextPath()%>/vendors/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
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
					<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/Recipe">食譜</a></li>
					<li class="breadcrumb-item active" aria-current="page">食譜列表</li>
				</ol>
			</div> 
    	
    		<section class="error">
    			${errorMsgs.get("UnknowErr")}
    		</section>
    		<section class="searchResult">
				${successMsg}
    		</section>
    			
	                        <c:if test="${list.get(0).accountID == accountInfoVOLogin.accountID}">
                        	<div class="editBtn row">
		                        <select name="recipeAction" class="custom-select col- lg-1 col-md-2 col-3 ml-auto mb-2">
		                        	<option value="delete">刪除</option>
		                       	</select>
		                        <button type="button" class="btn btn-primary col- lg-1 col-md-2 col-3 ml-2 mb-2" id="recipeAction">批次操作</button>
							</div>
							</c:if>
				<div class="table-responsive">
					<table class="table table-bordered" id="myRecipeList" width="100%" cellspacing="0" class="display">
						<thead>
							<tr>
<!-- 								<th>編號</th> -->
								<th>標題</th>
								<th>發表時間</th>
								<th>瀏覽人次</th>
								<th>按讚人次</th>
								<th>收藏人次</th>
								<th>批次處理</th>
							</tr>
						</thead>
						<tbody>
				<c:forEach var="recipeVO" items="${list}" varStatus="status">
						<tr data-id="${recipeVO.recipeID}">
<%-- 							<td>${status.count}</td> --%>
							<td><a href="<%=request.getContextPath() %>/Recipe/recipe.jsp?id=${recipeVO.recipeID}">${recipeVO.recipeName}</a></td>
							<td><fmt:formatDate value="${recipeVO.recipeTime}" pattern="yyyy.MM.dd"/></td>
							<td>${recipeVO.recipeViewCount}</span>
							<td>${thmupRecipeSvc.countAllByRecipe(recipeVO.recipeID)}</td>
							<td>${favRecipeSvc.countAllByRecipe(recipeVO.recipeID)}</td>
						<c:if test="${accountInfoVOLogin.accountID == recipeVO.accountID}">
                            <td><label><input type="checkbox" value="${recipeVO.recipeID}" name="recipeID"> 選取</label></td>
                        </c:if>
                        <c:if test="${accountInfoVOLogin.accountID != recipeVO.accountID}">
                            <td></td>
                        </c:if>
						</tr>
				</c:forEach>
						</tbody>
					</table>
				</div>

					
						
							
							
<!-- 							<div class="readmore"> -->
<%-- 								<a href="<%= request.getContextPath() %>/Recipe/recipe.jsp?id=${recipeVO.recipeID}">繼續閱讀 <i class="fas fa-angle-double-right"></i></a> --%>
<!-- 							</div> -->
						
					
			
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
<script src="<%=request.getContextPath()%>/vendors/datatables/jquery.dataTables.min.js"></script>
<script src="<%=request.getContextPath()%>/vendors/datatables/dataTables.bootstrap4.min.js"></script>
	<script>

	$(function(){
		$("#myRecipeList").DataTable({
		    "searching": true, //搜尋功能, 預設是開啟
		    "paging": true, //分頁功能, 預設是開啟
		    "ordering": true, //排序功能, 預設是開啟
		    "lengthMenu": [10, 20],
		    "language": {
		        "processing": "處理中...",
		        "loadingRecords": "載入中...",
		        "lengthMenu": "顯示 _MENU_ 項結果",
		        "zeroRecords": "沒有符合的結果",
		        "info": "顯示第 _START_ 至 _END_ 項結果，共 _TOTAL_ 項",
		        "infoEmpty": "顯示第 0 至 0 項結果，共 0 項",
		        "infoFiltered": "(從 _MAX_ 項結果中過濾)",
		        "infoPostFix": "",
		        "search": "搜尋:",
		        "paginate": {
		            "first": "第一頁",
		            "previous": "上一頁",
		            "next": "下一頁",
		            "last": "最後一頁"
		        },
		        "aria": {
		            "sortAscending": ": 升冪排列",
		            "sortDescending": ": 降冪排列"
		        }
		    }
		});

    	$("#recipeAction").on("click", function () {
    		var action = $('select[name="recipeAction"]').find("option:selected").val();
    		
    		$("input[name='recipeID']").each(function(index, element) {
    			if ($(element).is(":checked")) {
    				var that = this;
					$.ajax({
						type : 'POST',
		    			url: '<c:url value="/Recipe/recipe.do" />',
		    			data : {
		    				'action': action,
		    				'recipeID': $(element).val()
		    			},
						success: function(data){
							$("#actionSuccess").modal();
							$(that).closest("tr[data-id='" + $(element).val() + "']").remove();
						},
						
		    		});
    			}
    		});
    		
    	});    	
    	

		<%@ include file="/Recipe/js/recipeFavThumb.page"%>
		<%@ include file="/Recipe/searchAutoComplIng.file"%>
	});
	
	</script>
</body>
</html>