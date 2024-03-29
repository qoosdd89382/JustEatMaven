<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String dishAndIngJson = request.getParameter("dishAndIngJson");
	JSONObject jsonObject =null;
	JSONArray  jsonArray = null;
	if(dishAndIngJson ==null || dishAndIngJson.length()==2 || dishAndIngJson.isEmpty()){
		
	}else{
		jsonArray = new JSONArray(dishAndIngJson);
		jsonObject = jsonArray.getJSONObject(0);
	}
	String replaceDishAndIngJson = null;
	if(dishAndIngJson!=null){
		replaceDishAndIngJson = dishAndIngJson.replaceAll("\"","&quot;");
	}
	System.out.println(request.getParameter("accountID"));
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>活動詳情</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/jquery-ui/css/jquery-ui.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/Event/css/style.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/header.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/footer.css">
</head>

<body>
    <header class="header">
    	<%@ include file="/common/header.jsp"%>
    </header>
    <nav aria-label="breadcrumb" style="-bs-breadcrumb-divider: '&gt;';">
		<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/index.jsp">首頁</a></li>
			<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/Event/EventList.jsp">活動列表</a></li>
			<li class="breadcrumb-item active" aria-current="page">活動詳情</li>
		</ol>
	</nav>
	<form action="<%=request.getContextPath()%>/Event/EventInfo.do" method="POST" id="formID">
		<div class="temp_data">
			<input type="hidden" name="accountID" value="${param.accountID}" class="accountIDTemp">
			<input type="hidden" name="eventID" value="${param.eventID}" class="eventIDTemp">
			<input type="hidden" name="choose_type" value="" class="groupTypeTemp">
			<input type="hidden" name="event_name" value="" class="eventNameTemp">
			<input type="hidden" name="event_member" value="" class="eventMemeberTemp">
			<input type="hidden" name="event_start" value="" class="eventStartTemp">
			<input type="hidden" name="event_end" value="" class="eventEndTemp">
			<input type="hidden" name="city" value="" class="cityTemp">
			<input type="hidden" name="address" value="" class="addressTemp">
			<input type="hidden" name="event_description" value="" class="eventDescriptionTemp">
			<input type="hidden" name="dishAndIngJson" value="<%=replaceDishAndIngJson==null?"":replaceDishAndIngJson%>">

		</div>
	    <div class="title">
	        <h2>活動詳情</h2>
	    </div>
	    	<span class="error">${errorMsgs.get("dishAndIngredientIsNull")}</span>
	    <div class="event_content col-12 col-lg-12 row">
	        <div class="event_content_left col-6 col-lg-6">
	        	<div>
	        	揪團類型:<span class="group_type border"></span>
	        	</div>
	            <div>
	                活動名稱:<span class="event_name border"></span> 活動人數:
	                <span class="current_people border"></span>
	            </div>
	            <div>
	                活動報名開始時間:
	                <span class="event_reg_start border"></span>
	            </div>
	            <div>
	                活動報名結束時間:
	                <span class="event_reg_end border"></span>
	            </div>
	            <div>
	                活動開始時間:
	                <span class="event_start border"></span>
	            </div>
	            <div>
	                活動結束時間:
	                <span class="event_end border"></span>
	            </div>
	            <div>
	 	      地址:
	 	      		<span class="address border"></span>
	            </div>
	            <div>
	                類型:
	                <span class="eat_style border"></span>
	            </div>
	            <div>
	                其他人菜色:
	                <span class="dish_name"></span>
	            </div>
	            <%
			    	if(dishAndIngJson==null || dishAndIngJson.length()==2 || dishAndIngJson.isEmpty()){
			    		
			    	}else {
			    		if(jsonObject!=null){
			    %>
				<div>
			    	我的菜色:
			    	<span class="border"><%=jsonObject.getString("dishName") %></span>
			    </div>
			    <%
			    		}
			    	}
			    %>
	            <div>
	            	<%
	            		if(dishAndIngJson==null || dishAndIngJson.length()==2 || dishAndIngJson.isEmpty()){ 
	            	%>
	               			<input type="submit" name="actionJoin" value="新增菜色" class="btn btn-secondary">
	                <%
	            		}else{
	                %>	
	                		<input type="submit" name="actionJoin" value="重新新增菜色" class="btn btn-secondary">
	                <%
	            		}
	                %>
	            </div>
	            <div>
	                <input type="button" value="取消參加" class="returnList btn btn-secondary">
	                <input type="submit" name="action" value="確定參加" class="joinEvent btn btn-secondary">
	            </div>
	            <div id="preview_img">
	            	<img src="<%=request.getContextPath()%>/Event/EventInfoForOnePic?eventID=${param.eventID}">
	            </div>
	        </div>
	        <div class="event_content_right col-6 col-lg-6">
	            <div class="event_description">
	                <textarea name="event_description" cols="60" rows="20" placeholder="活動說明" disabled="disabled" class="event_description_content"></textarea>
	            </div>
	        </div>
	    </div>
    </form>
    <footer>
		<%@ include file="/common/footer.jsp"%>
	</footer>
	
	<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.6.0.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
	<script src="<%=request.getContextPath()%>/Event/js/DateFormat.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/footer.js"></script>
	<script>
	<%@ include file="/common/js/scriptFooter.page"%>
	$(function(){
		$.ajax({
			type:"POST",
			url:"<%=request.getContextPath()%>/Event/EventInfoAJAXServlet.do",
			cache:false,
			data:{"eventID":"<%=request.getParameter("eventID")%>"},
			datatype:"json",
			success:function(data,result){
				if(data.groupType == 1){
					$(".group_type").html("一人一菜");
				}else if(data.groupType==2){
					$(".group_type").html("我當主廚");
				}
				
				var startTime = new Date(""+data.eventStart).format("yyyy-MM-dd hh:mm");
				var endTime = new Date(""+data.eventStart).format("yyyy-MM-dd hh:mm");
				var regStartTime = new Date(""+data.eventRegStart).format("yyyy-MM-dd hh:mm");
				var regEndTime = new Date(""+data.eventRegEnd).format("yyyy-MM-dd hh:mm");
				
				$(".event_name").html(data.eventName);
				$(".current_people").html(data.eventMember);
				$(".event_reg_start").html(regStartTime);
				$(".event_reg_end").html(regEndTime);
				$(".event_start").html(startTime);
				$(".event_end").html(endTime);
				$(".eat_style").html(data.cuisineCatName);
				$(".dish_name").html(data.dishName);
				$(".address").html(data.city+data.address);
				$(".event_description_content").val(data.eventDescription);
				
				
				$(".eventIDTemp").val(data.eventID);
				$(".groupTypeTemp").val(data.groupType);
				$(".eventNameTemp").val(data.eventName);
				$(".eventMemeberTemp").val(data.eventMember);
				$(".eventStartTemp").val(startTime);
				$(".eventEndTemp").val(endTime);
				$(".cityTemp").val(data.city);
				$(".addressTemp").val(data.address);
				$(".eventDescriptionTemp").val(data.eventDescription);
			}
		});
		
		$(".joinEvent").on("click",function(){
		
		});
		$(".returnList").on("click",function(){
			window.location.href = "<%=request.getContextPath()%>/Event/EventList.jsp";
		});
			
	});
	
	</script>
</body>

</html>