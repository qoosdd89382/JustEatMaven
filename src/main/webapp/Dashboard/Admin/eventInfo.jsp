<%@page import="com.eventcuisinecategory.model.EventCuisineCategoryVO"%>
<%@page import="com.eventcuisinecategory.model.EventCuisineCategoryService"%>
<%@page import="com.eventinfo.model.EventInfoService"%>
<%@page import="java.util.List"%>
<%@page import="com.cuisinecategory.model.CuisineCategoryVO"%>
<%@page import="com.cuisinecategory.model.CuisineCategoryService"%>
<%@page import="com.eventinfo.model.EventInfoVO"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.accountinfo.model.AccountInfoVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<%
	EventInfoService eventInfoSvc = new EventInfoService();
	EventInfoVO eventInfoVO = eventInfoSvc.getEventID(Integer.parseInt(request.getParameter("eventID")));
	pageContext.setAttribute("eventInfoVO", eventInfoVO);
	CuisineCategoryService cuisineCatSvc = new CuisineCategoryService();
	pageContext.setAttribute("cuisineCatSvc",cuisineCatSvc);
// 	String cuisineCatID = request.getParameter("cuisineCatID");
	List<CuisineCategoryVO> cuisineCategoryVOs = (List<CuisineCategoryVO>) request.getAttribute("cuisineCategoryVOs");
	
	EventCuisineCategoryService eventCuisineCatSvc = new EventCuisineCategoryService();
	List<EventCuisineCategoryVO> eventCuisineCategoryVOs = eventCuisineCatSvc.getAllByEventID(Integer.parseInt(request.getParameter("eventID")));
	pageContext.setAttribute("eventCuisineCategoryVOs", eventCuisineCategoryVOs);
	
	if(eventInfoVO!=null){
		if(eventInfoVO.getEventStartTime()!=null){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String formatStartDateTime = df.format(eventInfoVO.getEventStartTime());
			pageContext.setAttribute("formatStartDateTime", formatStartDateTime);
		}
		if(eventInfoVO.getEventEndTime()!=null){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String formatEndDateTime = df.format(eventInfoVO.getEventEndTime());
			pageContext.setAttribute("formatEndDateTime", formatEndDateTime);
		}	
		if(eventInfoVO.getEventRegistartionStartTime()!=null){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String formatRegStartDateTime = df.format(eventInfoVO.getEventRegistartionStartTime());
			pageContext.setAttribute("formatRegStartDateTime", formatRegStartDateTime);
		}
		if(eventInfoVO.getEventRegistartionEndTime()!=null){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String formatRegEndDateTime = df.format(eventInfoVO.getEventRegistartionEndTime());
			pageContext.setAttribute("formatRegEndDateTime", formatRegEndDateTime);
		}
	}
	request.getAttribute("errorMsgs");
	
	AccountInfoVO accountInfoVO = (AccountInfoVO)session.getAttribute("accountInfoVOLogin");
	pageContext.setAttribute("accountInfoVO", accountInfoVO);
	
	System.out.println(eventInfoVO.getGroupType());
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>後台-活動資訊</title>
    <!-- Custom fonts for this template-->
    <link href="<%=request.getContextPath()%>/vendors/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">
    <!-- Custom styles for this template-->
    <link href="<%=request.getContextPath()%>/Dashboard/css/sb-admin-2.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/jquery-ui/css/jquery-ui.css">
    <link href="<%=request.getContextPath()%>/vendors/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/datetimepicker/css/jquery.datetimepicker.css">
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
                    <h1 class="h3 mb-2 text-gray-800">活動資訊</h1>
<!--                     <p class="mb-4"> -->
						
<!-- 					</p> -->

                            <!-- Project Card Example -->
                            <div class="card shadow mb-4">
		                        <div class="card-header py-3">
		                        	<h6 class="m-0 font-weight-bold text-primary">
										活動資訊
									</h6>
		                        </div>
	                        
                                <div class="card-body">

			<form method="post" action="<%=request.getContextPath()%>/Dashboard/Admin/EventInfo.do" enctype="multipart/form-data">
				<div class="form-group">
					<label for="eventName" class="font-weight-bold text-primary">活動名稱：</label>
					<span class="errorSpan">${errorMsgs.get("EventNameIsNull")} </span>
					<input type="text" class="form-control" name="event_name" placeholder="請輸入活動名稱" value="${eventInfoVO.eventName}">
					<input type="hidden" name="eventID" value="${eventInfoVO.eventID}">
				</div>
				
				<div class="form-group">
					<label>
	                    <input type="radio" name="choose_type" value="1" <%= (eventInfoVO==null)?"":(eventInfoVO.getGroupType()==null)?"":(eventInfoVO.getGroupType()==1)?"checked":"" %>>一人一菜
	                    <span class="errorSpan">${errorMsgs.get("GroupTypeIsNull")}</span>
<%-- 	                    <input type="radio" name="choose_type" value="2" <%= (eventInfoVO==null)?"":(eventInfoVO.getGroupType()==null)?"":(eventInfoVO.getGroupType()==2)?"checked":"" %>>我當主廚 --%>
	                </label>
				</div>
				
				<div class="form-group">
					<label for="eventMember" class="font-weight-bold text-primary">活動人數：</label>
					<span class="errorSpan">${errorMsgs.get("EventMemberIsNull")} ${errorMsgs.get("EventMemberMustBeGreaterThanZero")}</span>
					<input type="number" class="form-control" name="event_member" placeholder="請輸入活動人數" value="${eventInfoVO.eventCurrentCount}">
				</div>
				
				<div class="form-group">
					<label for="eventStart" class="font-weight-bold text-primary">活動開始時間：</label>
					<span class="errorSpan">${errorMsgs.get("EventStartTimeIsNull")} ${errorMsgs.get("EventStartTimeNotConform")} ${errorMsgs.get("StartMustAfterRegStart")}</span>
					<input type="text" class="form-control" name="event_start" id="eventStart" placeholder="請輸入活動開始時間" value="<%=pageContext.getAttribute("formatStartDateTime")%>">
				</div>
				
				<div class="form-group">
					<label for="eventEnd" class="font-weight-bold text-primary">活動結束時間：</label>
					<span class="errorSpan">${errorMsgs.get("EventEndTimeIsNull")} ${errorMsgs.get("EventEndTimeNotConform")}${errorMsgs.get("EndMustAfterStart")}</span>
					<input type="text" class="form-control" name="event_end" id="eventEnd" placeholder="請輸入活動結束時間" value="<%=pageContext.getAttribute("formatEndDateTime")%>">
				</div>
				
				<div class="form-group">
					<label for="eventRegStart" class="font-weight-bold text-primary">活動報名開始時間：</label>
					<span class="errorSpan">${errorMsgs.get("EventRegStartTimeIsNull")} ${errorMsgs.get("EventRegStartTimeNotConform")}</span>
					<input type="text" class="form-control" name="event_reg_start" id="eventRegStart" placeholder="請輸入活動報名開始時間" value="<%=pageContext.getAttribute("formatRegStartDateTime")%>">
				</div>
				
				<div class="form-group">
					<label for="eventRegEnd" class="font-weight-bold text-primary">活動報名結束時間：</label>
					<span class="errorSpan">${errorMsgs.get("EventRegEndTimeIsNull")} ${errorMsgs.get("EventRegEndTimeNotConform")} ${errorMsgs.get("RegEndMustAfterRegStart") }</span>
					<input type="text" class="form-control" name="event_reg_end" id="eventRegEnd" placeholder="請輸入活動報名結束時間" value="<%=pageContext.getAttribute("formatRegEndDateTime")%>">
				</div>
				
				<div class="form-group">
					<label for="eventRegEnd" class="font-weight-bold text-primary">地址：</label>
					<span class="errorSpan">${errorMsgs.get("GroupAddressIsNull")}</span>
					<select name="city" class="form-control">
	                <!--北台灣-->
	                <option value="基隆市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("基隆市")?"selected":"" %>>基隆市</option>
	                <option value="新北市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("新北市")?"selected":"" %>>新北市</option>
	                <option value="台北市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("台北市")?"selected":"" %>>台北市</option>
	                <option value="桃園市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("桃園市")?"selected":"" %>>桃園市</option>
	                <option value="宜蘭縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("宜蘭市")?"selected":"" %>>宜蘭縣</option>
	                <option value="新竹縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("新竹縣")?"selected":"" %>>新竹縣</option>
	                <option value="新竹市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("新竹市")?"selected":"" %>>新竹市</option>
	                <!--中台灣-->
	                <option value="苗栗縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("苗栗市")?"selected":"" %>>苗栗縣</option>
	                <option value="台中市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("台中市")?"selected":"" %>>台中市</option>
	                <option value="彰化縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("彰化市")?"selected":"" %>>彰化縣</option>
	                <option value="南投縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("南投市")?"selected":"" %>>南投縣</option>
	                <option value="雲林縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("雲林市")?"selected":"" %>>雲林縣</option>
	                <!--南台灣-->
	                <option value="嘉義縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("嘉義縣")?"selected":"" %>>嘉義縣</option>
	                <option value="嘉義市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("嘉義市")?"selected":"" %>>嘉義市</option>
	                <option value="台南市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("台南市")?"selected":"" %>>台南市</option>
	                <option value="高雄市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("高雄市")?"selected":"" %>>高雄市</option>
	                <option value="屏東縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("屏東市")?"selected":"" %>>屏東縣</option>
	                <!--東台灣-->
	                <option value="花蓮縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("花蓮縣")?"selected":"" %>>花蓮縣</option>
	                <option value="台東縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("台東縣")?"selected":"" %>>台東縣</option>
	                <!--離島區域-->
	                <option value="澎湖縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("澎湖縣")?"selected":"" %>>澎湖縣</option>
	                <option value="金門縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("金門縣")?"selected":"" %>>金門縣</option>
	                <option value="連江縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("連江縣")?"selected":"" %>>連江縣</option>
	            </select>
	            <input type="text" name="address" placeholder="請輸入地址" class="inputAddress form-control" value="<%=(eventInfoVO==null)?"":(eventInfoVO.getGroupAddress()==null)?"":(eventInfoVO.getGroupAddress()) %>">
				</div>
				
				<div class="form-group">
					<label for="cuisineCatName" class="font-weight-bold text-primary">類型：</label>
					<span class="errorSpan">${errorMsgs.get("cuisineCatError")}</span>
					<div class="ui-widget">
						<input type="text"  class="form-control" id="cuisineCatInput" name="" placeholder="請輸入並選擇類型">
					</div>
					<div class="cuisineCatAutoOutput">
						<ul>
				          	<c:if test="${not empty cuisineCategoryVOs}">
				                <c:forEach var="cuisineCatVO" items="${cuisineCategoryVOs}">
				                	<li data-id="${cuisineCatVO.cuisineCategoryID}">
				                		<span>${cuisineCatSvc.getOneCategory(cuisineCatVO.cuisineCategoryID).cuisineCategoryName}</span>
				                		<i class="fas fa-times"></i>
				                	</li>
				                </c:forEach>
				        	</c:if>
				        	<c:if test="${empty cuisineCategoryVOs}">
				                <c:forEach var="eventCuisineCategoryVO" items="${eventCuisineCategoryVOs}">
				                	<li data-id="${eventCuisineCategoryVO.cuisinecategoryID}">
				                		<span>${cuisineCatSvc.getOneCategory(eventCuisineCategoryVO.cuisinecategoryID).cuisineCategoryName}</span>
				                		<i class="fas fa-times"></i>
				                	</li>
				                </c:forEach>
				        	</c:if>
			            </ul>
			            <c:if test="${empty cuisineCategoryVOs}">
							<input class="cuisineCatAutoInput" name="cuisineCatID" type="hidden" value="<c:forEach var="eventCuisineCategoryVO" items="${eventCuisineCategoryVOs}"> ${eventCuisineCategoryVO.cuisinecategoryID}</c:forEach>">
						</c:if>
						<c:if test="${not empty cuisineCategoryVOs}">
							<input class="cuisineCatAutoInput" name="cuisineCatID" type="hidden" value="<c:forEach var="cuisineCategoryVO" items="${cuisineCategoryVOs}"> ${cuisineCategoryVO.cuisineCategoryID}</c:forEach>">
						</c:if>          	
					</div>
				</div>

				<div class="form-group">
					<label for="eventDescription" class="font-weight-bold text-primary">活動說明：</label>
					<textarea class="form-control" name="description" placeholder="請輸入活動說明" rows="10" cols="50">${eventInfoVO.eventDescription}</textarea>
					<input type="hidden" name="event_description" value="<%=(eventInfoVO==null)?"":(eventInfoVO.getEventDescription()==null)?"":(eventInfoVO.getEventDescription()) %>"/>
				</div>

				<div class="form-group">
					<label for="eventPic row" class="font-weight-bold text-primary">活動圖片：</label>
						<label id="picUploadBtn" class="uploadBtn btn btn-secondary col-6">上傳活動圖片
							<input type="file" name="eventPic" id="uploadEventImg" class="form-control-file col-6" style="display:none;">
						</label>
						<div id="preview_img" class="preview col-6"><img src="<%=request.getContextPath()%>/Event/EventInfoForOnePic?eventID=${param.eventID}"></div>
				</div>
				
				<input type="hidden" name="action" value="update">
<%-- 				<input type="hidden" name="accountID" value="${recipeVO.accountID}"> --%>
				<button id="btnSubmit" class="btn btn-primary btn-block" type="submit">送出</button>
			</form>
				<a href="<%=request.getContextPath()%>/Dashboard/Admin/listAllEvent.jsp" class="btn btn-light btn-block mt-1">返回列表</a>

                                
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
	<script	src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/jquery-ui/js/jquery-ui.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/datetimepicker/js/jquery.datetimepicker.full.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="<%=request.getContextPath()%>/Dashboard/js/sb-admin-2.js"></script>
    <script>
    $.datetimepicker.setLocale('zh'); // kr ko ja en
    $('#eventStart').datetimepicker({
       theme: '',          //theme: 'dark',
       timepicker: true,   //timepicker: false,
       step: 15,            //step: 60 (這是timepicker的預設間隔60分鐘)
	    format: 'Y-m-d H:i',
// 	    minDate:'-1970-01-01'
    });
    $('#eventEnd').datetimepicker({
	        theme: '',          //theme: 'dark',
	        timepicker: true,   //timepicker: false,
	        step: 15,            //step: 60 (這是timepicker的預設間隔60分鐘)
		    format: 'Y-m-d H:i',
// 		    minDate:'-1970-01-01'
	 });
    $('#eventRegStart').datetimepicker({
	        theme: '',          //theme: 'dark',
	        timepicker: true,   //timepicker: false,
	        step: 15,            //step: 60 (這是timepicker的預設間隔60分鐘)
		    format: 'Y-m-d H:i',
// 		    minDate:'-1970-01-01'
	 });
    $('#eventRegEnd').datetimepicker({
	        theme: '',          //theme: 'dark',
	        timepicker: true,   //timepicker: false,
	        step: 15,            //step: 60 (這是timepicker的預設間隔60分鐘)
		    format: 'Y-m-d H:i',
// 		    minDate:'-1970-01-01'
	 });
    
    $("#uploadEventImg").on("change", function() {
        console.log(this.files);
        var reader = new FileReader();
        reader.readAsDataURL(this.files[0]);
        $(reader).on("load", function() {
        	$("#preview_img").html("");
            $("#preview_img").append("<img src="+reader.result+">");
        });
    });
    
    $(function(){
		var cuisineCatArray = new Array();
		<%
			for(CuisineCategoryVO tempVO:cuisineCatSvc.getAll()){
		%>
			var cuiCatObj = new Object();
			cuiCatObj['id'] = <%=tempVO.getCuisineCategoryID() %>;
			cuiCatObj['value'] = "<%=tempVO.getCuisineCategoryName() %>";
			cuisineCatArray.push(cuiCatObj);
		<%		
			}
		%>
		
		//================類型儲存========================
		var cuisineCatIDsEle = $('input[name="cuisineCatID"]').val().trim();
		if (cuisineCatIDsEle != "") {
			var cuisineCatIDs = cuisineCatIDsEle.split(" ");
			console.log(cuisineCatIDs);
			cuisineCatIDs.forEach(function(itm, ind, arr){
				cuisineCatArray.forEach(function(item, index, array){
		        	if (parseInt(cuisineCatIDs[ind]) == cuisineCatArray[index].id) {
		        		cuisineCatArray.splice(index, 1);
		        	}
		    	});
		    });
		}
		
		function putCuisineCatInID(id,name){
			var tempCuisineCatHTML = "<li data-id='"+id+"'><span>" +name + "</span><i class='fas fa-times'></i></li>";		
			$(".cuisineCatAutoOutput").find("ul").append(tempCuisineCatHTML);
			
			if($(".cuisineCatAutoInput").val()==""){
				$(".cuisineCatAutoInput").val(" "+id);
			}else{
				var tempCuisineCatStr = $(".cuisineCatAutoInput").val();
				tempCuisineCatStr += " "+id;
				$(".cuisineCatAutoInput").val(tempCuisineCatStr);
			}
		}

		$("#cuisineCatInput").on("keydown",function(event){
			if(event.keyCode === $.ui.keyCode.Enter){
				event.preventDefault();
			}
		}).autocomplete({
			minLength: 0,
			source: cuisineCatArray,
			select: function(event,ui){
				cuisineCatArray.forEach(function(item,index,array){
					if(ui.item.value == array[index]['value']){
						array.splice(index,1);
					}
				});
				putCuisineCatInID(ui.item.id , ui.item.value);
				$("#cuisineCatInput").val("");
				return false;
			}
		});
		
		$(".cuisineCatAutoOutput").on("click","svg",function(event){
			var selectID = $(this).parent("li").attr("data-id");
			var selectName =$(this).parent("li").find("span").html();

			var tempID = $(".cuisineCatAutoInput").val();
			var newID = tempID.replace(" "+selectID,"");
			
			$(".cuisineCatAutoInput").val(newID);
			
			var addBackCuisineCatObj = new Object();
			addBackCuisineCatObj['id'] = selectID;
			addBackCuisineCatObj['value'] = selectName;
			cuisineCatArray.push(addBackCuisineCatObj);
			
			$(this).closest("li").remove();
		});
	});
	</script>
</body>
</html>