<%@page import="com.eventinfo.model.EventInfoVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="java.util.List"%>

<jsp:useBean id="eventInfoSvc" class="com.eventinfo.model.EventInfoService" />

<%
	List<EventInfoVO> list =eventInfoSvc.getAll();
	pageContext.setAttribute("list", list);
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>後台-活動列表</title>
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
                    <h1 class="h3 mb-2 text-gray-800">活動列表</h1>
<!--                     <p class="mb-4"> -->
						
<!-- 					</p> -->

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">活動列表</h6>
                        </div>
                        <div class="card-body">
                        本列表共有 ${fn:length(list)} 個活動：<br>
                        	<div class="editBtn row">
		                        <select name="action" class="custom-select col- lg-1 col-md-2 col-3 ml-auto mb-2">
		                        	<option value="delete">刪除</option>
		                       	</select>
		                        <button type="button" class="btn btn-primary col- lg-1 col-md-2 col-3 ml-2 mb-2" id="adminAction">批次操作</button>
							</div>
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>活動編號</th>
                                            <th>活動名稱</th>
                                            <th>活動類型</th>
                                            <th>活動地址</th>
                                            <th>活動時間</th>
											<th>修改/刪除</th>
											<th>成員列表</th>
											<th>批次操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach var="eventInfoVO" items="${list}">
                                        <tr data-id="${eventInfoVO.eventID}">
                                            <td>${eventInfoVO.eventID}</td>
                                            <td class="eventName">
                                            	${eventInfoVO.eventName}
                                            </td>
                                            <c:if test="${eventInfoVO.groupType==1}">
                                            	<td>一人一菜</td>
                                            </c:if>
                                            <c:if test="${eventInfoVO.groupType==2}">
                                            	<td>我當主廚</td>
                                            </c:if>
                                            <td>${eventInfoVO.groupCity}${eventInfoVO.groupAddress}</td>
                                            <td><fmt:formatDate value="${eventInfoVO.eventStartTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                            <td>
                                            	<form action="<%=request.getContextPath()%>/Dashboard/Admin/EventInfo.do" method="post">
													<input type="hidden" name="action" value="getOneForUpdate">
													<input type="hidden" name="eventID" value="${eventInfoVO.eventID}">
													<button type="submit" class="btn btn-primary">修改</button>
												</form>
												<form action="<%=request.getContextPath()%>/Dashboard/Admin/EventInfo.do" method="post">
													<input type="hidden" name="action" value="delete">
													<input type="hidden" name="eventID" value="${eventInfoVO.eventID}">
													<button type="submit" class="btn btn-secondary mt-1">刪除</button>
												</form>
                                            </td>
                                            <td>
	                                            <button type="button" class="btn btn-secondary mt-1 memberList" onclick='location.href = "<%=request.getContextPath()%>/Dashboard/Admin/EventMember.jsp?eventID=${eventInfoVO.eventID}"'>成員列表</button>
                                            </td>
                                            <td>
                                            	<label><input type="checkbox" value="${eventInfoVO.eventID}" name="eventID">選取</label>
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