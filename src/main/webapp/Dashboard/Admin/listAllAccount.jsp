<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="java.util.List"%>
<%@ page import="com.admininfo.model.*" %>
<%@ page import="com.accountinfo.model.*" %>

<jsp:useBean id="accountInfoSvc" class="com.accountinfo.model.AccountInfoService" />

<%
	List<AccountInfoVO> list = accountInfoSvc.selectAllAccountInfo();
	pageContext.setAttribute("list", list);
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin listAllAccount page</title>
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
                    <h1 class="h3 mb-2 text-gray-800">會員列表</h1>
<!--                     <p class="mb-4"> -->
						
<!-- 					</p> -->

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">會員列表</h6>
                        </div>
                        <div class="card-body">
                       	 本站共有 ${fn:length(list)} 名會員：
                       	 ${ errorMsgs.get("UnexceptionError") }
                        	<div class="editBtn row">
		                        <select name="action" class="custom-select col-lg-1 col-md-2 col-3 ml-auto mb-2">
		                        	<option value="activeAccountInfo">啟用</option>
		                        	<option value="freezeAccountInfo">停權</option>
		                       	</select>
		                        <button type="button" class="btn btn-primary col- lg-1 col-md-2 col-3 ml-2 mb-2" id="accountAction">批次操作</button>
							</div>
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
                                           <th>編號</th>
                                           <th>信箱</th>
                                           <th>暱稱</th>
                                           <th>註冊時間</th>
                                           <th>狀態</th>
                                           <th>操作</th>
                                           <th>批次操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach var="accountInfoVO" items="${list}">
                                        <tr data-id="${accountInfoVO.accountID}">
                                            <td>${accountInfoVO.accountID}</td>
                                            <td>${accountInfoVO.accountMail}</td>
                                            <td>
                                            	<a href="<%=request.getContextPath()%>/Dashboard/Account/dashboard.do?action=gotoUpdateAccountInfo&accountID=${accountInfoVO.accountID}">
                                            		${accountInfoVO.accountNickname}
                                            	</a>
                                            </td>
                                            <td><fmt:formatDate value="${accountInfoVO.accountRegisterTime}" pattern="yyyy.MM.dd a KK:mm"/></td>
                                            <td>
                                            	${accountInfoVO.accountState == true ? "啟用中" : "停權中" }
                                            </td>
                                            <td>

							<c:if test="${accountInfoVO.accountState == false}">
							  <form method="post" action="<%=request.getContextPath()%>/Dashboard/Account/dashboard.do" style="margin-bottom: 0px;">
							     <input type="hidden" name="accountID"  value="${accountInfoVO.accountID}">
							     <input type="hidden" name="action" value="activeAccountInfo">
							     <button type="submit" class="actionBtn btn btn-sm btn-danger">啟用</button>
							  </form>
							</c:if>
							<c:if test="${accountInfoVO.accountState == true}">
							  <form method="post" action="<%=request.getContextPath()%>/Dashboard/Account/dashboard.do" style="margin-bottom: 0px;">
							     <input type="hidden" name="accountID"  value="${accountInfoVO.accountID}">
							     <input type="hidden" name="action" value="freezeAccountInfo">
							     <button type="submit" class="actionBtn btn btn-sm btn-primary">停權</button>
							  </form>
							</c:if>
											</td>
                                            <td>
                                            	<label><input type="checkbox" value="${accountInfoVO.accountID}" name="accountIDforSelect">選取</label>
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
    <script src="<%=request.getContextPath()%>/Dashboard/js/datatables-demo.js"></script>
    
    <script>
    $(function(){
    	
    	$(document).on("click", "#accountAction", function () {
    		var action = $('select[name="action"]').find("option:selected").val();
    		var actionToggleStr = "";
    		var actionToggleAction = "";
    		
    		if (action == "freezeAccountInfo") { actionToggleStr = "啟用"; actionToggleAction = "activeAccountInfo"; }
    		else if (action == "activeAccountInfo") { actionToggleStr = "停權"; actionToggleAction = "freezeAccountInfo"; }
    		
//     		var arr = new Array();
    		$("input[name='accountIDforSelect']").each(function(index, element) {
    			if ($(element).is(":checked")) {
// 					arr.push($(element).val());
					$.ajax({
						type : 'POST',
		    			url: '<c:url value="/Dashboard/Account/dashboard.do" />',
		    			data : {
		    				'action': action,
		    				'accountID': $(element).val()
		    			},
						success: function(data){
							console.log($("tr[data-id='" + $(element).val() + "']").find("button[type='submit']").text());
							$("tr[data-id='" + $(element).val() + "']").find("button[type='submit']").text(actionToggleStr);
							$("tr[data-id='" + $(element).val() + "']").find("input[name='action']").val(actionToggleAction);
							$("#successModal").modal();
							if (actionToggleStr == "啟用") {
								$(element).closest("tr").find(".actionBtn").removeClass("btn-primary");
								$(element).closest("tr").find(".actionBtn").addClass("btn-danger");
							} else {
								$(element).closest("tr").find(".actionBtn").removeClass("btn-danger");
								$(element).closest("tr").find(".actionBtn").addClass("btn-primary");
							}
						},
						
		    		});
    			}
    		});
    		
    	});    	
    	
    	
    });
    </script>
</body>
</html>