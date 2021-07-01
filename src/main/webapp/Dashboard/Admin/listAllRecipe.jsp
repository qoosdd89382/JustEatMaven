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
	List<RecipeVO> list = recipeSvc.getAll();
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
                    <h1 class="h3 mb-2 text-gray-800">食譜列表</h1>
<!--                     <p class="mb-4"> -->
						
<!-- 					</p> -->

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">食譜列表</h6>
                        </div>
                        <div class="card-body">
                        
                       	 本列表共有 ${fn:length(list)} 則食譜。
                        	<div class="editBtn row">
		                        <select name="action" class="custom-select col-lg-1 col-md-2 col-3 ml-auto mb-2">
		                        	<option value="delete">刪除</option>
		                       	</select>
		                        <button type="button" class="btn btn-primary col- lg-1 col-md-2 col-3 ml-2 mb-2" id="categoryAction">批次操作</button>
							</div>
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
											<th>編號</th>
											<th>食譜名稱</th>
											<th>作者</th>
											<th>料理分類</th>
											<th>食材</th>
											<th>刪除</th>
											<th>批次操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach var="recipeVO" items="${list}">
                                        <tr data-id="${recipeVO.recipeID}">
                                            <td>${recipeVO.recipeID}</td>
											<td><a href="<%=request.getContextPath()%>/Dashboard/Recipe/recipe.do?action=getOneForUpdate&recipeID=${recipeVO.recipeID}">${recipeVO.recipeName}</a></td>
											<td><a href="<%=request.getContextPath()%>/Dashboard/Account/dashboard.do?action=gotoUpdateAccountInfo&accountID=${recipeVO.accountID}">${accountSvc.selectOneAccountInfo(recipeVO.accountID).accountNickname}</a></td>
											<td>
												<c:forEach var="recipeCatVO" items="${recipeCatSvc.getAllByRecipe(recipeVO.recipeID)}">
													<span>
														${categorySvc.getOneCategory(recipeCatVO.cuisineCategoryID).cuisineCategoryName}
													</span>
												</c:forEach>	
											</td>
											<td>
												<c:forEach var="recipeIngVO" items="${recipeIngUnitSvc.getAllByRecipe(recipeVO.recipeID)}">
													<span>
														${ingredientSvc.getOneIngredient(recipeIngVO.ingredientID).ingredientName}
													</span>
												</c:forEach>	
											</td>
											<td><button class="deleteOne btn btn-primary">刪除</button></td>
                                            <td>
                                            	<label><input type="checkbox" value="${recipeVO.recipeID}" name="recipeID">選取</label>
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

    });
    </script>
</body>
</html>