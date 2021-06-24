<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.admininfo.model.*" %>
<jsp:useBean id="adminSvc" class="com.admininfo.model.AdminInfoService" />

<%
// 驗證有登入就導到後臺畫面
// 沒登入就導去登入
// 所以這支jsp本身也要被filter保護?
	
// 	if (session.getAttribute("adminID") != null) {
		int loginAdminID = (Integer) session.getAttribute("loginAdminID");
		AdminInfoVO adminVO = adminSvc.getOneAdmin(loginAdminID);
// 	}
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
    <!-- Custom fonts for this template-->
    <link href="<%=request.getContextPath()%>/vendors/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">
    <!-- Custom styles for this template-->
    <link href="css/sb-admin-2.css" rel="stylesheet">
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
                    <div class="d-sm-flex align-items-center justify-content-between mb-4">
                        <h1 class="h3 mb-0 text-gray-800">Dashboard</h1>
                    </div>


                    <!-- Content Row -->
                    <div class="row">

                        <!-- Content Column -->
                        <div class="col-lg-6 mb-4">

                            <!-- Project Card Example -->
                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">最近 3 筆待審核商家資料</h6>
                                </div>
                                <div class="card-body">
                                    
		                            <div class="table-responsive">
		                                <table class="table table-borderless" id="dataTable" width="100%" cellspacing="0">
		                                    <thead class="border-bottom">
		                                        <tr>
		                                            <th>會員暱稱</th>
		                                            <th>商家名稱</th>
		                                            <th>商家資訊</th>
		                                            <th colspan="2">審核操作</th>
		                                        </tr>
		                                     </thead>
		                                    <tbody>
		                                        <tr>
		                                            <td>王小華</td>
		                                            <td>華仔蔬果批發</td>
		                                            <td><button type="button" class="btn btn-outline-secondary btn-sm">點此</button></td>
		                                            <td>
														<select class="custom-select custom-select-sm">
														  <option value="0" selected>審核中</option>
														  <option value="1">通過</option>
														  <option value="2">未通過</option>
														</select>
		                                            </td>
		                                             <td>
														<button type="button" class="btn btn-primary btn-sm">確定</button>
		                                            </td>
		                                        </tr>
		                                        <tr>
		                                            <td>王小華</td>
		                                            <td>華仔蔬果批發</td>
		                                            <td><button type="button" class="btn btn-outline-secondary btn-sm">點此</button></td>
		                                            <td>
														<select class="custom-select custom-select-sm">
														  <option value="0" selected>審核中</option>
														  <option value="1">通過</option>
														  <option value="2">未通過</option>
														</select>
		                                            </td>
		                                             <td>
														<button type="button" class="btn btn-primary btn-sm">確定</button>
		                                            </td>
		                                        </tr>
		                                        <tr>
		                                            <td>王小華</td>
		                                            <td>華仔蔬果批發</td>
		                                            <td><button type="button" class="btn btn-outline-secondary btn-sm">點此</button></td>
		                                            <td>
														<select class="custom-select custom-select-sm">
														  <option value="0" selected>審核中</option>
														  <option value="1">通過</option>
														  <option value="2">未通過</option>
														</select>
		                                            </td>
		                                             <td>
														<button type="button" class="btn btn-primary btn-sm">確定</button>
		                                            </td>
		                                        </tr>
		                                    </tbody>
		                                </table>
	                                </div>
	                                
                                    <a target="_blank" rel="nofollow" href="https://undraw.co/">更多審核資料 &rarr;</a>
	                            </div>
                            </div>

                            <!-- Illustrations -->
                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">Illustrations</h6>
                                </div>
                                <div class="card-body">
                                    <div class="text-center">
                                        <img class="img-fluid px-3 px-sm-4 mt-3 mb-4" style="width: 25rem;"
                                            src="img/undraw_posting_photo.svg" alt="...">
                                    </div>
                                    <p>Add some quality, svg illustrations to your project courtesy of <a
                                            target="_blank" rel="nofollow" href="https://undraw.co/">unDraw</a>, a
                                        constantly updated collection of beautiful svg images that you can use
                                        completely free and without attribution!</p>
                                    <a target="_blank" rel="nofollow" href="https://undraw.co/">Browse Illustrations on
                                        unDraw &rarr;</a>
                                </div>
                            </div>

                        </div>

                        <div class="col-lg-6 mb-4">

                            <!-- Project Card Example -->
                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">最近 3 筆待回覆訊息</h6>
                                </div>
                                <div class="card-body">
                                    
		                            <div class="table-responsive">
		                                <table class="table table-borderless" id="dataTable" width="100%" cellspacing="0">
		                                    <thead class="border-bottom">
		                                        <tr>
		                                            <th>會員暱稱</th>
		                                            <th>訊息摘要</th>
		                                            <th>發送時間</th>
		                                            <th colspan="2">回覆操作</th>
		                                        </tr>
		                                     </thead>
		                                    <tbody>
		                                        <tr>
		                                            <td>王小華</td>
		                                            <td>測試測試測試測試...</td>
		                                            <td>6/21 18:30</td>
		                                             <td>
														<button type="button" class="btn btn-primary btn-sm">回覆</button>
		                                            </td>
		                                        </tr>
		                                        <tr>
		                                            <td>王小華</td>
		                                            <td>測試測試測試測試...</td>
		                                            <td>6/21 18:30</td>
		                                             <td>
														<button type="button" class="btn btn-primary btn-sm">回覆</button>
		                                            </td>
		                                        </tr>
		                                        <tr>
		                                            <td>王小華</td>
		                                            <td>測試測試測試測試...</td>
		                                            <td>6/21 18:30</td>
		                                             <td>
														<button type="button" class="btn btn-primary btn-sm">回覆</button>
		                                            </td>
		                                        </tr>
		                                    </tbody>
		                                </table>
	                                </div>
	                                
                                    <a target="_blank" rel="nofollow" href="https://undraw.co/">進入客服聊天室 &rarr;</a>
	                            </div>
                            </div>
                            
                            
                            <!-- Approach -->
                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">Development Approach</h6>
                                </div>
                                <div class="card-body">
                                    <p>SB Admin 2 makes extensive use of Bootstrap 4 utility classes in order to reduce
                                        CSS bloat and poor page performance. Custom CSS classes are used to create
                                        custom components and custom utility classes.</p>
                                    <p class="mb-0">Before working with this theme, you should become familiar with the
                                        Bootstrap framework, especially the utility classes.</p>
                                </div>
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

    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fas fa-angle-up"></i>
    </a>

    <!-- Logout Modal-->
    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                    <a class="btn btn-primary" href="login.html">Logout</a>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap core JavaScript-->
    <script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.6.0.min.js"></script>
    <script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="<%=request.getContextPath()%>/vendors/jquery-easing/jquery.easing.min.js"></script>
    <script src="<%=request.getContextPath()%>/vendors/datatables/jquery.dataTables.min.js"></script>
    <script src="<%=request.getContextPath()%>/vendors/datatables/dataTables.bootstrap4.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin-2.js"></script>
    <script src="js/datatables-demo.js"></script>

</body>
</html>