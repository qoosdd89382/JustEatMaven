<%@page import="com.ingredient.model.IngredientVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="java.util.List"%>
<%@ page import="com.admininfo.model.*" %>
<%@ page import="com.cuisinecategory.model.*" %>

<jsp:useBean id="ingredientSvc" class="com.ingredient.model.IngredientService" />

<%
	List<IngredientVO> list = ingredientSvc.getAll();
	pageContext.setAttribute("list", list);
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
    <link href="<%=request.getContextPath()%>/vendors/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
<style>

</style>
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
                    <h1 class="h3 mb-2 text-gray-800">食材列表</h1>
<!--                     <p class="mb-4"> -->
						
<!-- 					</p> -->

                    <div class="card shadow mb-4 mt-3">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">新增食材</h6>
                        </div>
                        <div class="card-body">
                        	<div id="insertErr"></div>
                        	<input name="ingredientName" class="form-control" style="width: 30%; display:inline-block;" placeholder="請輸入食材名稱">
                        	<button type="button" id="addIngredient" class="btn btn-primary">新增</button>
                        </div>
                    </div>

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">食材列表</h6>
                        </div>
                        <div class="card-body">
                        
	<%-- 錯誤表列 --%>
	<h2 id="error"></h2>
	<h2 id="error2"></h2>
                        
                       	 本列表共有 ${fn:length(list)} 個食材。
                        	<div class="editBtn row">
		                        <select name="action" class="custom-select col-lg-1 col-md-2 col-3 ml-auto mb-2">
		                        	<option value="delete">刪除</option>
		                       	</select>
		                        <button type="button" class="btn btn-primary col- lg-1 col-md-2 col-3 ml-2 mb-2" id="ingredientAction">批次操作</button>
							</div>
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
                                           <th>料理分類</th>
											<th>料理分類名稱</th>
											<th>修改</th>
											<th>刪除</th>
											<th>批次操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach var="ingredientVO" items="${list}">
                                        <tr data-id="${ingredientVO.ingredientID}">
                                            <td class="id">${ingredientVO.ingredientID}</td>
											<td>${ingredientVO.ingredientName}</td>
											<td><button class="modify btn btn-primary">修改</button></td>
											<td><button class="deleteOne btn btn-primary">刪除</button></td>
                                            <td>
                                            	<label><input type="checkbox" value="${ingredientVO.ingredientID}" name="ingredientID">選取</label>
                                            </td>
                                        </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
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

    <!-- Custom scripts for all pages-->
    <script src="<%=request.getContextPath()%>/Dashboard/js/sb-admin-2.js"></script>
    
    <script>

    
    $(function(){
    	
    	$('#dataTable').DataTable({
    		  order: [[ 0, 'desc' ]],
    	});
    	
    	$(document).on("click", "#ingredientAction", function () {
    		// 刪除多筆
    		var action = $('select[name="action"]').find("option:selected").val();
    		$("input[name='ingredientID']").each(function(index, element) {
    			if ($(element).is(":checked")) {
    				var that = element;
					$.ajax({
						type : 'POST',
		    			url: '<c:url value="/Dashboard/Ingredient/ingredient.do" />',
		    			data : {
		    				'action': action,
		    				'ingredientID': $(that).val()
		    			},
						success: function(data){
							if (data != "success") {
								$("#error").html("<font color='red'>" + data + "</font>");
								return;
							}
							$("#successModal").modal();
							$(that).closest("tr").remove();
						},
						
		    		});
    			}
    		});
    		
    	});    	
    	
    	$(document).on("click", ".deleteOne", function () {
    		// 刪除一筆
    		var delIngredientID = $(this).closest("tr").attr("data-id");
    		console.log(delIngredientID);
    		var that = this;
    		$.ajax({
				type : 'POST',
    			url: '<c:url value="/Dashboard/Ingredient/ingredient.do" />',
    			data : {
    				'action': "delete",
    				'ingredientID': delIngredientID
    			},
				success: function(data){
					console.log(data)
					if (data != "success") {
						$("#error").html("<font color='red'>" + data + "</font>");
						return;
					}
					$("#successModal").modal();
					$(that).closest("tr").remove();
				}
    		});
    	});
    	
    	$("#addIngredient").on("click", function (){
    		var ingredientName = $("input[name='ingredientName']").val();
    		$.ajax({
				type : 'POST',
    			url: '<c:url value="/Dashboard/Ingredient/ingredient.do" />',
    			data : {
    				'action': "insert",
    				'ingredientName': ingredientName
    			},
				success: function(data){
					if (data != "success") {
					console.log(data);
						$("#insertErr").html("<font color='red'>" + data + "</font>");
						return;
					}
// 					$("#successModal").modal();
					location.reload();
				},
				
    		});
    	});
    	


    	$(document).on("click", "button.modify",function(){
    	
    		 var vall=  $(this).parents("td").prev().text();
    		 
			 $(this).parents("td").prev().html("<input type='text' class='form-control'>");
    		 $(this).parents("td").prev().find("input").val(vall); 
    		 $(this).html("確定修改");
			 $(this).attr('class',"confirmModify btn btn-primary")
			 $('#error').html("");
    	});
      
      

    $(document).on("click", "button.confirmModify", function(){
    	var that = this;
    	$(this).parents("td").prev().text($(this).closest("td").prev().find("input").val());
	   	 $(this).html("修改");
	   	 $(this).attr('class',"modify btn btn-primary")
	   	 var modifyname = $(this).parents("td").prev().text();
	   	 var ingredientid = $(this).parent().prev().prev().text();
    		$.ajax({
    			type : 'POST',
    			url : '<c:url value="/IngredientListUpdateServlet" />',
    			data : {
    				modifyname : modifyname,
    				ingredientid :ingredientid
    			},
    			success : function(result) {
					var aa= result.split('@');
					console.log(aa[1]+"aa");
					$('#error').html(aa[0]);
					$(that).parents("td").prev().html(aa[1]);
					console.log(result);
					$("#successModal").modal();
				}

    		});
    });


    	


    });
    </script>
</body>
</html>