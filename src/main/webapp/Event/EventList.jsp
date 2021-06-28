<%@page import="com.accountinfo.model.AccountInfoVO"%>
<%@page import="com.eventcuisinecategory.model.EventCuisineCategoryVO"%>
<%@page import="com.eventcuisinecategory.model.EventCuisineCategoryService"%>
<%@page import="com.cuisinecategory.model.CuisineCategoryService"%>
<%@page import="com.cuisinecategory.model.CuisineCategoryVO"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.eventinfo.model.*"%>
<%
	EventInfoService eventInfoSvc = new EventInfoService();
	EventInfoVO eventInfoVO = null;
	List<EventInfoVO> listAll = eventInfoSvc.getAll();
	List<EventInfoVO> list = new ArrayList<EventInfoVO>();
	
	Date date = new Date();
	Timestamp timestamp = new Timestamp(date.getTime());
	
	for(int i=0;i<listAll.size();i++){
		EventInfoVO eventInfoVOtemp = listAll.get(i);
		if(eventInfoVOtemp.getEventStartTime().after(timestamp)){
			list.add(eventInfoVOtemp);
		}
	}
	
	pageContext.setAttribute("list", list);
	
	CuisineCategoryService cuisineCatSvc = new CuisineCategoryService();
	String cuisineCatID = request.getParameter("cuisineCatID");
	List<CuisineCategoryVO> cuisineCatList = (List<CuisineCategoryVO>) request.getAttribute("cuisineCatList");
	
	AccountInfoVO accountInfoVO = (AccountInfoVO)session.getAttribute("accountInfoVOLogin");
	pageContext.setAttribute("accountInfoVO", accountInfoVO);

	EventCuisineCategoryService eventCuisineCategorySvc = new EventCuisineCategoryService();
	
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>活動列表</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/jquery-ui/css/jquery-ui.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/Event/css/style.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/footer.css">
</head>
<body>

	<header>
		<%@ include file="/common/header.jsp"%>
	</header>

	<nav aria-label="breadcrumb" style="-bs-breadcrumb-divider: '&gt;';">
		<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href=" # ">首頁</a></li>
			<li class="breadcrumb-item active" aria-current="page">活動列表</li>
		</ol>
	</nav>

	<div class="add_Event">
		<div class="Create_or_Join_Event col-10">
			<button type="button" class="col-12 col-sm-3 col-md-2 col-lg-1 createEvent">建立活動</button>
		</div>
	</div>
	<div class="event_list_content row">
		<div class="center_content col-10">
			<table class="filter">
				<tr class="row">
					<td class="col-12 col-sm-3">
					<div>
					<form method="post" action="<%=request.getContextPath() %>/Event/EventInfo.do">
		                類型搜尋:	<input type="text" name="cuisineCatSearch" id="cuisineCatInput" placeholder="請輸入類型名稱  ex:日式 ">
		                <input type="hidden" name="action" value="cuisineCatSearch">
		            </form>	
		            </div>
					</td>
					<td class="col-12 col-sm-3">地區: <br> 
						<form method="post" action="<%=request.getContextPath() %>/Event/EventInfo.do" class="positionSubmit">
							<input type="radio" name="position" id="" value="0" class="col-1">北部 
							<input type="radio" name="position" id="" value="1" class="col-1">中部 
							<input type="radio" name="position" id="" value="2" class="col-1">離島<br> 
							<input type="radio" name="position" id="" value="3" class="col-1">南部 
							<input type="radio" name="position" id="" value="4" class="col-1">東部
							<input type="hidden" name="action" value="searchEventGroupCity">
						</form>
					</td>
					<td class="col-12 col-sm-3">日期: 
						<form method="post" action="<%=request.getContextPath()%>/Event/EventInfo.do" class="dateSubmit">
							<input type="date" name="date" id="" class="date col-12">
							<input type="hidden" name="action" value="searchEventDate">
						</form>
					</td>
					<td class="col-12 col-sm-3">
						<form method="post" action="<%=request.getContextPath()%>/Event/EventInfo.do">
							<input type="text" name="search" placeholder="搜尋活動名稱" class="col-12"> 
							<input type="hidden" name="action" value="searchEventName">
						</form>
					</td>
				</tr>
			</table>
			<%@ include file="/Event/Paging.file" %> 
			<table class="event_list">
				<tr class="row">
					<th class="col-sm-2 col-2">活動圖片</th>
					<th class="col-sm-2 col-2">活動名稱</th>
					<th class="col-sm-2 col-2">活動類型</th>
					<th class="col-sm-2 col-2">活動地區</th>
					<th class="col-sm-2 col-2">活動時間</th>
					<th class="col-sm-2 col-2">點擊率</th>
				</tr>
				<c:choose>
					<c:when test="${not empty listfind}">
						<c:forEach var="eventInfoVO" items="${listfind}" varStatus="i" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
							<c:if test="${eventInfoVO.eventState ==1}">
									<tr class="row">
										<td class="col-sm-2 col-2"><img src="<%=request.getContextPath() %>/Event/EventInfoForOnePic?eventID=${eventInfoVO.eventID}" class="img"></td>
										<td class="col-sm-2 col-2"><a href="<%=request.getContextPath()%>/Event/EventDetailReview.jsp?eventID=${eventInfoVO.eventID}&accountID=${accountInfoVO.accountID}">${eventInfoVO.eventName}</a></td>
										<c:if test="${eventInfoVO.groupType == 1}">
											<td class="col-sm-2 col-2">一人一菜</td>
										</c:if>
										<c:if test="${eventInfoVO.groupType == 2}">
											<td class="col-sm-2 col-2">我當主廚</td>
										</c:if>
										<td class="col-sm-2 col-2">${eventInfoVO.groupCity}</td>
										<td class="col-sm-2 col-2"><fmt:formatDate type="date" value="${eventInfoVO.eventStartTime}" pattern="yyyy-MM-dd HH:mm"/></td>
										<td class="col-sm-2 col-2">1000</td>
									</tr>
							</c:if>
						</c:forEach>
					</c:when>
					<c:when test="${empty listfind && not empty errMsg}">
						<tr class="row">
							<td class="col-sm-12 col-12">${errMsg}</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="eventInfoVO" items="${list}" varStatus="i" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
							<c:if test="${eventInfoVO.eventState ==1}">
									<tr class="row">
										<td class="col-sm-2 col-2"><img src="<%=request.getContextPath() %>/Event/EventInfoForOnePic?eventID=${eventInfoVO.eventID}" class="img"></td>
										<td class="col-sm-2 col-2"><a href="<%=request.getContextPath()%>/Event/EventDetailReview.jsp?eventID=${eventInfoVO.eventID}&accountID=${accountInfoVO.accountID}">${eventInfoVO.eventName}</a></td>
										<c:if test="${eventInfoVO.groupType == 1}">
											<td class="col-sm-2 col-2">一人一菜</td>
										</c:if>
										<c:if test="${eventInfoVO.groupType == 2}">
											<td class="col-sm-2 col-2">我當主廚</td>
										</c:if>
										<td class="col-sm-2 col-2">${eventInfoVO.groupCity}</td>
										<td class="col-sm-2 col-2"><fmt:formatDate type="date" value="${eventInfoVO.eventStartTime}" pattern="yyyy-MM-dd HH:mm"/></td>
										<td class="col-sm-2 col-2">1000</td>
									</tr>
							</c:if>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</table>
			<%@ include file="/Event/SelectPage.file" %> 
		</div>
	</div>
	<footer>
		<%@ include file="/common/footer.jsp"%>
	</footer>
	<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.6.0.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/jquery-ui/js/jquery-ui.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/footer.js"></script>
	<script>

		$("[name=position]").on("change",function(){
			if($(this).is(":checked")){
				$(".positionSubmit").submit();
			}
		});
		
		$("[name=date]").on("change",function(){
			$(".dateSubmit").submit();
		});
		
		$(".createEvent").on("click",function(){
			location.href = "<%=request.getContextPath()%>/Event/CreateEvent.jsp";
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
					return false;
				}
			});
		});
		//================類型儲存========================
// 		$(".confirmCreate").on("click",function(){
// 			var cuisineCatArray = new Array();
// 			$(".cuisineCatAutoOutput").find("ul").each(function(index,element){
// 				var cuisineCatObj = new Object();
// 				var cuisineCatIDArray = new Array();
// 				$(element).find("li").each(function(index,element){
// 					cuisineCatIDArray.push($(element).attr("data-id"));
// 				});
// 				cuisineCatObj["cuisineCatID"] = cuisineCatIDArray;
// 				cuisineCatArray.push(cuisineCatObj);
// 			});
// 			var cuisineCatJson = JSON.stringify(cuisineCatArray);
// 			$(".temp_data").append("<input type='hidden' name='cuisineCatJson' value='"+cuisineCatJson+"'>");
// 		});
	</script>
</body>
</html>