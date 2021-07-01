<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="java.util.List"%>
<%@ page import="com.admininfo.model.*" %>

<jsp:useBean id="adminSvc" class="com.admininfo.model.AdminInfoService" />

<%
	List<AdminInfoVO> list = adminSvc.getAll();
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
                    <h1 class="h3 mb-2 text-gray-800">管理員列表</h1>
<!--                     <p class="mb-4"> -->
						
<!-- 					</p> -->

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">管理員列表</h6>
                        </div>
                        <div class="card-body">
                        本列表共有 ${fn:length(list)} 名管理員：
	                        <c:if test="${adminSvc.getOneAdmin(loginAdminID).adminState >= 3}">
                        	<div class="editBtn row">
		                        <select name="action" class="custom-select col- lg-1 col-md-2 col-3 ml-auto mb-2">
		                        	<option value="promote">總管理員</option>
		                        	<option value="demote">一般管理員</option>
		                        	<option value="suspend">停權</option>
		                       	</select>
		                        <button type="button" class="btn btn-primary col- lg-1 col-md-2 col-3 ml-2 mb-2" id="adminAction">批次操作</button>
							</div>
							</c:if>
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>暱稱</th>
                                            <th>E-mail</th>
                                            <th>註冊時間</th>
                                            <th>認證狀態</th>
                                            <c:if test="${adminSvc.getOneAdmin(loginAdminID).adminState >= 3}">
                                            	<th>批次操作</th>
                                            </c:if>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach var="adminVO" items="${list}">
                                        <tr data-id="${adminVO.adminID}">
                                            <td>${adminVO.adminID}</td>
                                            <td class="adminNickname">
                                            	${adminVO.adminNickname}
<%--                                             	<c:if test="${adminSvc.getOneAdmin(loginAdminID).adminState >= 3 || loginAdminID == adminVO.adminID }"> --%>
<%--                                             		<button type="button" class="btn btn-primary btn-sm float-right" value="${adminVO.adminID}" name="updateAdminNckname">編輯</button> --%>
<%--                                             	</c:if> --%>
                                            </td>
                                            <td>${adminVO.adminMail}</td>
                                            <td><fmt:formatDate value="${adminVO.adminRegisterTime}" pattern="yyyy.MM.dd a KK:mm"/></td>
                                            <td  class="adminState">${adminVO.adminState == 0 ? "停權" : (adminVO.adminState == 1 ? "審核中" : (adminVO.adminState == 2 ? "已通過" : "總管理員")) }</td>
                                            <c:if test="${adminSvc.getOneAdmin(loginAdminID).adminState >= 3}">
                                            	<td><label><input type="checkbox" value="${adminVO.adminID}" name="adminID">選取</label></td>
                                            </c:if>
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
    <script src="<%=request.getContextPath()%>/Dashboard/js/datatables-demo.js"></script>
    
    <script>
    $(function(){
    	
    	$(document).on("click", "#adminAction", function () {
    		var action = $('select[name="action"]').find("option:selected").val();
    		var actionStr = "";
    		
    		if (action == "promote") { actionStr = "總管理員"; }
    		else if (action == "demote") { actionStr = "已通過"; }
    		else if (action == "suspend") { actionStr = "停權"; }
    		
    		var arr = new Array();
    		$("input[name='adminID']").each(function(index, element) {
    			if ($(element).is(":checked")) {
					arr.push($(element).val());
    			}
    		});
    		
			if (arr.length == 0) {
				return;
			}
			
    		var adminIDs = JSON.stringify(arr);
    		
    		$.ajax({
				type : 'POST',
    			url: '<c:url value="/Dashboard/adminAjax.do" />',
    			data : {
    				'action': action,
    				'adminIDs': adminIDs
    			},
				success: function(data){
					var json = JSON.parse(data);
					$(json).each(function(index, element) {
						$("tr[data-id='" + element + "']").find("td.adminState").text(actionStr);
					});
					$("#successModal").modal();
				},
				
    		});
    		
    	});    	
    	
    	
    });
    </script>
</body>
</html>