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


.preview {
	border: 1px solid lightgray;
	display: inline-block;
	position: relative;
	min-height: 100px;
	margin-top: 10px;
  display: flex;
  justify-content: center;
  align-items: center; 
  overflow: hidden;
  padding: 0!important;
}

.preview span.text {
	display: inline-block;
	color: lightgray;
}

.preview_img {
	width: 100%;
}
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
                        <h1 class="h3 mb-0 text-gray-800">管理員資訊</h1>
                    </div>
	
                    <!-- Content Row -->
                    <div class="row">
                    
                        <!-- Content Column -->
                        <div class="col-lg-12 mb-4">

                            <!-- Project Card Example -->
                            <div class="card shadow mb-4">
		                        <div class="card-header py-3">
		                        	<h6 class="m-0 font-weight-bold text-primary">
										管理員 ${adminSvc.getOneAdmin(loginAdminID).adminNickname} 的個人資訊
									</h6>
		                        </div>
	                        
                                <div class="card-body">
 <form action="<%= request.getContextPath()%>/Dashboard/admin.do">
  <div class="form-row">
    <div class="form-group col-md-6">
      <label for="adminID">ID</label>
      <input type="text" class="form-control" id="adminID" value="${adminSvc.getOneAdmin(loginAdminID).adminID}" disabled>
    </div>
    <div class="form-group col-md-6">
      <label for="adminNickname">暱稱</label>
      <input type="text" class="form-control" id="adminNickname" value="${adminSvc.getOneAdmin(loginAdminID).adminNickname}" name="adminNickname">
    </div>
  <div class="form-group col-md-12">
    <label for="adminMail">E-mail</label>
    <input type="email" class="form-control" id="adminMail" name="adminMail" value="${adminSvc.getOneAdmin(loginAdminID).adminMail}">
  </div>
    <div class="form-group col-md-6">
      <label for="newPassword">新密碼</label>
      <input type="password" autocomplete="new-password" class="form-control" id="newPassword" name="newPassword">
    </div>
    <div class="form-group col-md-6">
      <label for="newPasswordRecheck">再次輸入新密碼</label>
      <input type="password" autocomplete="new-password" class="form-control" id="newPasswordRecheck" name="newPasswordRecheck">
    </div>
    <hr>
    <div class="col-md-6 vertical-container">
		<label for="adminPic">頭像上傳
			<span class="errorSpan">${errorMsgs.get("adminPicErr")}</span>
		</label>
		<input id="adminPic" type="file" name="adminPic" style="display:none">
		<button id="upload-btn" class="btn btn-secondary btn-lg form-control btn-block" type="button" id="button-addon2">上傳檔案</button>
		<div class="input-element col-12 input-group mb-3 border-bottom upload-block">
			<div class="col-12 preview text-center rounded">
				<img src="<%= request.getContextPath() %>/Dashboard/Admin/Pic?adminID=${loginAdminID}" class="preview_img">
			</div>
		</div>
    </div>
    <div class="col-md-6"></div>
    
	<div class="form-group col-md-6">
	 <label for="adminPassword">修改資料，請輸入目前密碼 <font color="red">*</font></label>
	 <input type="password" autocomplete="new-password" class="form-control" id="adminPassword" name="adminPassword">
	</div>
    <div class="col-md-6"></div>
	<input type="hidden" name="action" value="update">
  	<button id="submit" type="submit" class="col-md-6 btn btn-primary">送出修改</button>
  </div>
</form>

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
    <script src="<%=request.getContextPath()%>/Dashboard/js/datatables-demo.js"></script>
    
    <script>


    if (sessionStorage.getItem("form_data") != null) {
    	session_history = JSON.parse(sessionStorage.getItem("form_data"));
		
    	if (session_history.preview_img != null) {
            $('.preview').empty().append('<img src="'+ session_history.preview_img +'" class="preview_img">');
    	}
    	
    }
    
	var isNewAdmin = "${isNewAdmin}";
	if (isNewAdmin == "yes") {
		sessionStorage.clear();
	}
    
	$('.visible-pw').on("click", function(e){
		e.preventDefault();
		var type = $(this).parent().prev().attr("type");
		if (type === 'password') {
			$(this).parent().prev().attr("type", "text");
		} else {
			$(this).parent().prev().attr("type", "password");
		}
	});
	

	$('#upload-btn').on("click", function(e){
		e.preventDefault();
    	$('input[name="adminPic"]').trigger("click");
	});
	

	$('input[name="adminPic"]').on("change", function(e) {
        previewerPic(e.target.files[0]);
    });
    

    function previewerPic(file) {
    	let file_reader = new FileReader();
        file_reader.readAsDataURL(file);
        
        file_reader.addEventListener("load", function (e) {
            $('.preview').empty().append('<img src="'+ e.target.result +'" class="preview_img">');
            
        });
    };
    

    $("#submit").on("click", function() {
    	var session_history = new Object();
    	var step_pic = new Array();
    	
        if ($('.preview_img') != null) {
            session_history['preview_img'] = $(".preview_img").attr("src");
        }
        sessionStorage.setItem("form_data", JSON.stringify(session_history));
        
    });
    
    </script>
</body>
</html>