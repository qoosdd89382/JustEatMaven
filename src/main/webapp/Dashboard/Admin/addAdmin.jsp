<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.admininfo.model.*" %>
<jsp:useBean id="adminSvc" class="com.admininfo.model.AdminInfoService" />

<%
	AdminInfoVO adminVO = (AdminInfoVO) request.getAttribute("adminVO");
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
<style>

</style>
</head>
<body>
	
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
                    <div class="d-sm-flex align-items-center justify-content-between mb-4">
                        <h1 class="h3 mb-0 text-gray-800">新增管理員</h1>
                    </div>
	
	

                    <!-- Content Row -->
                    <div class="row">
                    
                        <!-- Content Column -->
                        <div class="col-lg-12 mb-4">

                            <!-- Project Card Example -->
                            <div class="card shadow mb-4">
		                        <div class="card-header py-3">
		                        	<h6 class="m-0 font-weight-bold text-primary">新增管理員</h6>
		                        </div>
	                        
                                <div class="card-body">
                                    
		                            <div class="table-responsive">
		                                <form method="post" action="<%= request.getContextPath() %>/Dashboard/admin.do" enctype="multipart/form-data">
		                                <table class="table table-borderless" width="100%" cellspacing="0">
		                                    <thead class="border-bottom">
		                                        <tr>
		                                            <th>管理員信箱</th>
		                                            <th>管理員暱稱</th>
		                                            <th>操作</th>
		                                        </tr>
		                                     </thead>
		                                    <tbody>
		                                        <tr>
		                                            <td>
		                                            	<input id="adminMail"
		                                            		class='form-control rounded-lg ${errorMsgs.get("adminEmailErr") == null ? "": "border-danger"}' 
		                                            		type="text" name="adminMail" value='${errorMsgs.get("adminEmailErr") == null ? adminVO.adminMail : "" }'
		                                            		placeholder='${errorMsgs.get("adminEmailErr") == null ? "請輸入信箱": errorMsgs.get("adminEmailErr")}'>
		                                            </td>
		                                            <td>
														<input id="adminNickname"
															class='form-control rounded-lg ${errorMsgs.get("adminNicknameErr") == null ? "": "border-danger"}'
															type="text" name="adminNickname" value='${errorMsgs.get("adminNicknameErr") == null ? adminVO.adminNickname : "" }'
															placeholder='${errorMsgs.get("adminNicknameErr") == null ? "請輸入暱稱": errorMsgs.get("adminNicknameErr")}'>	
													</td>
		                                            <td>
		                                            	<input type="hidden" name="action" value="insert">
														<button type="submit" class="btn btn-primary rounded-lg">確定新增</button>
														
		                                            </td>
		                                        </tr>
		                                    </tbody>
		                                </table>
		                                </form>
		                            </div>
		                        </div>
                        </div>
                    
<!-- 	<div class="login rounded-lg col-10 col-md-6 col-lg-5 col-xl-4 shadow-lg"> -->
<!-- 		<div class="login-title rounded-top"> -->
<!-- 			<h3>新增管理員</h3> -->
<!-- 		</div> -->
<!-- 		<div class="login-block row"> -->
<!-- 			<div class="login-inner col-12 form-group"> -->
<%-- 				<form class="row" method="post" action="<%= request.getContextPath() %>/Dashboard/admin.do" enctype="multipart/form-data"> --%>
				
<!-- 					<div class="input-label col-12 vertical-container"> -->
<!-- 						<label for="adminMail"> -->
<!-- 							管理員信箱 -->
<%-- 							<span class="errorSpan">${errorMsgs.get("adminEmailErr")}</span> --%>
<!-- 						</label> -->
<!-- 					</div> -->
<!-- 					<div class="input-element col-12 input-group mb-3"> -->
<%-- 						<input id="adminMail" class="form-control form-control-lg rounded-pill" type="text" name="adminMail" value="${adminVO.adminMail}" placeholder=""> --%>
<!-- 					</div> -->
					
<!-- 					<div class="input-label col-12 vertical-container"> -->
<!-- 						<label for="adminNickname"> -->
<!-- 							管理員暱稱 -->
<%-- 							<span class="errorSpan">${errorMsgs.get("adminNicknameErr")}</span>	 --%>
<!-- 						</label> -->
<!-- 					</div> -->
<!-- 					<div class="input-element col-12 input-group mb-3"> -->
<%-- 						<input id="adminNickname" class="form-control form-control-lg rounded-pill" type="text" name="adminNickname" value="${adminVO.adminNickname}"> --%>
<!-- 					</div> -->
					
					
<!-- 					<div class="input-element col-12 input-group"> -->
<!-- 						<input type="hidden" name="action" value="insert"> -->
<!-- 						<button id="submit" class="btn btn-primary rounded-pill btn-lg btn-block" type="submit">註冊</button> -->
<!-- 					</div> -->
<!-- 				</form> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
	
					
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
    <script src="<%=request.getContextPath()%>/Dashboard/js/datatables-demo.js"></script>
</body>
</html>