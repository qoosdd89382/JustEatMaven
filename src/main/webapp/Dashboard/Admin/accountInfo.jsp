<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.admininfo.model.*" %>
<%@ page import="com.accountinfo.model.*" %>

<jsp:useBean id="accountInfoSvc" class="com.accountinfo.model.AccountInfoService" />

<%
AccountInfoVO accountInfoVO = (AccountInfoVO) request.getAttribute("accountInfoVO"); 
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
                        <h1 class="h3 mb-0 text-gray-800">會員資訊</h1>
                    </div>
	
                    <!-- Content Row -->
                    <div class="row">
                    
                        <!-- Content Column -->
                        <div class="col-lg-12 mb-4">

                            <!-- Project Card Example -->
                            <div class="card shadow mb-4">
		                        <div class="card-header py-3">
		                        	<h6 class="m-0 font-weight-bold text-primary">
										會員 ${accountInfoVO.accountNickname} 的個人資訊
									</h6>
		                        </div>
	                        
                                <div class="card-body">
                                
                                <h4 style="text-align: center; color: #4e73df;">${updateSuccess}</h4>
 <form action="<%=request.getContextPath()%>/Dashboard/Account/dashboard.do" method="post" enctype="multipart/form-data">
  <div class="form-row">
  
    <div class="form-group col-md-6">
      <label for="accountID">ID</label>
      <input type="text" class="form-control" value="${accountInfoVO.accountID}" disabled>
    </div>
    
    <div class="form-group col-md-6">
      <label for="adminNickname">暱稱
			<span class="errorSpan"><font color="red">${errorMsgs.get("adminNicknameErr")}</font></span>
       </label>
      <input type="text"
	      	class='form-control ${errorMsgs.get("adminNicknameErr") == null ? "": "border-danger"}'
	      	id="accountNickname"
	      	value="${accountInfoVO.accountNickname}"
	      	name="accountNickname">
    </div>
    
   <div class="form-group col-md-6">
    <label for="accountMail">E-mail</label>
    <input type="email" class="form-control" id="accountMail"
    		value="${accountInfoVO.accountMail}"
    		name="accountMail">
   </div>
  
    <div class="form-group col-md-6">
      <label for="accountPassword">密碼
			<span class="errorSpan"><font color="red">${errorMsgs.get("newPasswordErr")}</font></span>
      </label>
      <input type="password" autocomplete="accountPassword" 
	      	class='form-control ${errorMsgs.get("newPasswordErr") == null ? "": "border-danger"}'
	      	id="accountPassword" name="accountPassword">
    </div>
    
  
   <div class="form-group col-md-6">
    <label for="accountName">姓名</label>
    <input type="text" class="form-control" id="accountName"
    		value="${accountInfoVO.accountName}"
    		name="accountName">
   </div>
  
   <div class="form-group col-md-6">
    <label for="accountGender">性別</label>
    <select name="accountGender" class="custom-select">
	    <option value='1' ${accountInfoVO.accountGender == 1 ? "selected":""}>男</option>
	    <option value='0' ${accountInfoVO.accountGender == 0 ? "selected":""}>女</option>
    </select>
   </div>
   
   <div class="form-group col-md-6">
     <label for="accountState">狀態</label>
    <select name="accountState" class="custom-select">
	    <option value='1' ${accountInfoVO.accountState == true ? "selected":""}>啟用中</option>
	    <option value='0' ${accountInfoVO.accountState == false ? "selected":""}>停權中</option>
    </select>
   </div>
   
<!--    <div class="form-group col-md-6"> -->
<!--      <label for="accountLevel">目前狀態</label> -->
<!--       <input type="text" class="form-control" -->
<%--       		value='${accountInfoVO.accountLevel == 1 ? "一般會員" : "特權會員" }' disabled> --%>
<!--    </div> -->
   
    <div class="col-md-6"></div>
    
    <div class="col-md-6 vertical-container">
		<label for="accountPic">頭像上傳
			<span class="errorSpan"><font color="red">${errorMsgs.get("adminPicErr")}</font></span>
		</label>
		<input id="accountPic" accept="image/*" type="file" name="accountPic" style="display:none">
		<button id="upload-btn" class="btn btn-secondary form-control btn-block" type="button" id="button-addon2">上傳檔案</button>
		<div class="input-element col-12 input-group mb-3 upload-block">
			<div class="col-12 preview text-center rounded">
				<img src="<%= request.getContextPath() %>/Account/Pic/Pic/${accountInfoVO.accountID}" class="preview_img">
			</div>
		</div>
    </div>
    
    <div class="col-md-6"></div>
	<input type="hidden" name="action" value="updateAccountInfoFromDashboard">
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
    	$('input[name="accountPic"]').trigger("click");
	});
	


	$('input[name="accountPic"]').on("change", function(e) {
        	previewerPic(e.target.files[0]);
    });
    

    function previewerPic(file) {
    	let file_reader = new FileReader();
        file_reader.readAsDataURL(file);
        
        file_reader.addEventListener("load", function (e) {
            $('.preview').empty().append('<img src="'+ e.target.result +'" class="preview_img">');
            
        });
    };
    
    
    </script>
</body>
</html>