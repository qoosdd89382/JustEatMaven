<%@page import="java.sql.Timestamp"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="com.eventinfo.model.EventInfoVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %> --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="java.util.*"%>
<%@ page import="com.eventmember.model.*"%>
<%@ page import="com.accountinfo.model.*"%>

<jsp:useBean id="accountSvc" scope="page" class="com.accountinfo.model.AccountInfoService" />
<jsp:useBean id="eventMemberSvc" scope="page" class="com.eventmember.model.EventMemberService" />

<%
	String eventID = request.getParameter("eventID");
	List<EventMemberVO> list = eventMemberSvc.getAllByEventID(new Integer(eventID));
	pageContext.setAttribute("list", list);
//	AccountInfoVO accountInfoVO = (AccountInfoVO) session.getAttribute("accountInfoVOLogin"); 
// 	List<EventMemberVO> list = eventMemberSvc.getAllByEventID(300002);
// 	pageContext.setAttribute("eventID", 300002);
// 	pageContext.setAttribute("list", list);
// 	int accountAvgScore = eventMemberSvc.getAvgScoreByAccountID(100001);
// 	pageContext.setAttribute("accountAvgScore", accountAvgScore);

%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>後台-成員列表</title>
 <link href="<%=request.getContextPath()%>/vendors/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">
    <!-- Custom styles for this template-->
    <link href="<%=request.getContextPath()%>/Dashboard/css/sb-admin-2.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/vendors/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    
</head>
<style>

.vertical-container {
	display: -webkit-flex;
	display: flex;
	-webkit-align-items: center;
	align-items: center;
	-webkit-justify-content: center;
	justify-content: center;
}

.ui-autocomplete {
	max-height: 100px;
	overflow-y: auto;
	overflow-x: hidden;
}

.catAutoOutput ul, .ingAutoOutput ul {
	background: #eee;
	min-height: 55px;
	padding: 5px;
	margin-top: 10px;
}

.catAutoOutput ul>li {
	display: inline-block;
	margin: 10px 3px;
	padding: 5px;
	border: 1px solid gray;
	background: #fff;
}
.catAutoOutput ul>li span{
	margin-right: 3px;
}


.ingAutoOutput ul > li {
	margin: 10px 3px;
	padding: 3px;
	border: 1px solid gray;
	background: #fff;
	display: flex;
	justify-content: space-between;
	position: relative;
}

.preview {
	border: 1px solid lightgray;
	display: inline-block;
	position: relative;
	min-height: 80px; /* 40px */
	border-radius: .25rem !important;
	margin-top: 10px;
	padding: 3px;
}

.preview span.text {
	position: absolute;
	display: inline-block;
	left: 50%;
	top: 50%;
	transform: translate(-50%, -50%);
	z-index: 1;
	color: lightgray;
}
.preview_img {
    width: 100%;
}
.uploadBtn {
    margin-bottom: 10px;
}
.recipe td:last-child {
    text-align: right;
}
.agreeBox {
	margin-top: 10px;
	margin-bottom: 10px;
}

.errorSpan {
	color: red;
	font-weight: bold;
}

#preview_img > img{
	max-width: 400px;
 	max-height: 300px;
}
</style>
 <script src="https://cdn.tiny.cloud/1/7cr4ih870sgurlll171zc6ccfd9bh8ylwqjh0slgdx97xyt9/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script>
  <script>

  tinymce.init({
	    selector: 'textarea',
	    menubar: '',
	    toolbar: 'undo redo | bold italic underline strikethrough | fontselect fontsizeselect formatselect | alignleft aligncenter alignright alignjustify | outdent indent |  numlist bullist | forecolor backcolor removeformat | charmap emoticons | ltr rtl',
	  });

  </script>
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
                    <h1 class="h3 mb-2 text-gray-800">成員列表</h1>
<!--                     <p class="mb-4"> -->
						
<!-- 					</p> -->

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">成員列表</h6>
                        </div>
                      
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>帳號</th>
                                            <th>性別</th>
                                            <th>平均星數</th>
                                            <th>總活動次數</th>
                                            <th>參與次數</th>
                                            <th>狀態</th>
											<th>刪除</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    		
														
							<c:forEach var="eventMemberVO" items="${list}" >
								<tr> 
									<td>${accountSvc.selectOneAccountInfo(eventMemberVO.accountID).accountNickname}</td> 
									<td>
										<c:if test="${accountSvc.selectOneAccountInfo(eventMemberVO.accountID).accountGender == 1}">男</c:if>
										<c:if test="${accountSvc.selectOneAccountInfo(eventMemberVO.accountID).accountGender == 2}">女</c:if>
									</td>
									
									<td>${eventMemberSvc.getAvgScoreByAccountID(eventMemberVO.accountID)}</td> 
									<td>${eventMemberSvc.getTotalEventByAccountID(eventMemberVO.accountID)}</td> 	
									<td>${eventMemberSvc.getTotalAttendanceByAccountID(eventMemberVO.accountID)}</td> 
							
									<td>	
									     <c:if test="${eventMemberSvc.getEventStatusByAccountID(eventMemberVO.accountID, eventMemberVO.eventID) == 1}">審核中</c:if>
									     <c:if test="${eventMemberSvc.getEventStatusByAccountID(eventMemberVO.accountID, eventMemberVO.eventID) == 2}">參與中</c:if>
									      <c:if test="${eventMemberSvc.getEventStatusByAccountID(eventMemberVO.accountID, eventMemberVO.eventID) == 3}">已出席</c:if>
									</td>
									
										<td>
											<form action="<%=request.getContextPath()%>/Event/audit.do">
													<input type="hidden" name="accountID" value="${eventMemberVO.accountID}">
													<input type="hidden" name="action" value="delete">
													<input type="hidden" name="eventID" value="${eventMemberVO.eventID}">
													<button type="submit" class="btn btn-secondary mt-1">刪除</button>
												</form>
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
    $(function() {
		$(".header").load("header.html");
		$(".footer").load("footer.html");
	});
	
	$("[name=position]").on("change",function(){
		if($(this).is(":checked")){
			$(".positionSubmit").submit();
		}
	});
    </script>
</body>
</html>