<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%@ include file="/Recipe/recipeImport.jsp"%>

<%
	List<IngredientVO> list = ingredientSvc.getAll();
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

.dataTable thead {
    display: none;
}

.dataTable tr {
    display: inline-block;
}

td.sorting_1 {
    padding: 0;
    border: 0;
}
.table-bordered {
    border: 0;
}
.table-bordered td, .table-bordered th {
    border: 0;
}
.table td, .table th {
    border-top: 0;
}
div#catList_length {
    display: none;
}
ul.pagination {
    display: none;
}
.fontColorWhite {
	color: white;
}
.aIng {
    position: relative;
}
.ingInnerTag {
    font-size: 14px;
    color: black;
    padding: 3px 5px;
    background: #FFAE82;
}
</style>
<title>食譜列表 | 食材 | Just Eat 揪食</title>
	
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
					<li class="breadcrumb-item active" aria-current="page">食材列表</li>
				</ol>
			</div> 
    	

		<div class="table-responsive">
					<table class="table table-bordered" id="catList" width="100%" cellspacing="0" class="display">
						<thead>
							<tr>
								<th>分類名稱</th>
								<th style="visibility: visible">查詢次數</th>
							</tr>
						</thead>
						<tbody>
				<c:forEach var="ingredientVO" items="${list}" varStatus="status">
						<tr data-id="${ingredientVO.ingredientID}" class="aIng">
							<td class="ingName p-0">
								<a class="fontColorWhite btn btn-primary m-1 " href="<%= request.getContextPath() %>/Recipe/recipe.do?action=listAllByIngredient&id=${ingredientVO.ingredientID}" role="button">${ingredientVO.ingredientName}
									<span class="ingInnerTag"><i class="fas fa-search"></i> 被查找 ${ingredientVO.ingredientSearchCount} 次</span>
								</a>
							</td>
							<td class="ingSearchCount"><span style="font-size: 0;">${ingredientVO.ingredientSearchCount}</span></td>
						</tr>
				</c:forEach>
						</tbody>
					</table>
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
	<script src="<%=request.getContextPath()%>/Recipe/js/listAllRecipe.js"></script>
<script src="<%=request.getContextPath()%>/vendors/datatables/jquery.dataTables.min.js"></script>
<script src="<%=request.getContextPath()%>/vendors/datatables/dataTables.bootstrap4.min.js"></script>
	<script>
	<%@ include file="/common/js/scriptFooter.page"%>

	$(function(){
			$("#catList").DataTable({
	  		 	"order": [[ 1, 'desc' ]],
			    "searching": true, //搜尋功能, 預設是開啟
			    "paging": true, //分頁功能, 預設是開啟
			    "ordering": true, //排序功能, 預設是開啟
			    "lengthMenu": [-1],
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
			
		<%@ include file="/Recipe/js/recipeFavThumb.page"%>
		<%@ include file="/Recipe/searchAutoComplIng.file"%>
	});
	
	</script>
</body>
</html>