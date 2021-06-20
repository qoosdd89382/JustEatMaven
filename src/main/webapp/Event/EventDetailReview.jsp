<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
			<li class="breadcrumb-item"><a href=" # ">首頁</a></li>
			<li class="breadcrumb-item"><a href=" # ">活動列表</a></li>
			<li class="breadcrumb-item active" aria-current="page">活動詳情</li>
		</ol>
	</nav>
	<form action="<%=request.getContextPath()%>/Event/EventInfo.do" method="POST" id="formID">
	    <div class="title">
	        <h2>活動詳情</h2>
	    </div>
	    <div class="temp_data">
	    	<input type="hidden" name="eventID" value="${param.eventID}">
	    </div>
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
	                活動開始日期:
	                <span class="event_start border"></span>
	            </div>
	            <div>
	                活動結束日期:
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
	                菜色:
	                <span class="dish_name"></span>
	            </div>
	            <div>
	                <input type="submit" name="action" value="成員菜單">
	            </div>
	            <div>
	                <input type="button" value="上一頁" class="returnList">
	                <input type="submit" name="action" value="加入活動" class="joinEvent">
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
	<script src="<%=request.getContextPath()%>/Event/js/DateFormat.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/footer.js"></script>
	<script>

	$(function(){
		$.ajax({
			type:"POST",
			url:"<%=request.getContextPath()%>/Event/EventInfoAJAXServlet.do",
			cache:false,
			data:{"eventID":"<%=request.getParameter("eventID")%>"},
			datatype:"json",
			success:function(data,result){
				console.log(data);
				if(data.groupType == 1){
					$(".group_type").html("一人一菜");
				}else if(data.groupType==2){
					$(".group_type").html("我當主廚");
				}
				var startTime = new Date(""+data.eventStart).format("yyyy-MM-dd hh:mm");
				var endTime = new Date(""+data.eventStart).format("yyyy-MM-dd hh:mm");
				
				$(".event_name").html(data.eventName);
				$(".current_people").html(data.eventMember);
				$(".event_start").html(startTime);
				$(".event_end").html(endTime);
				$(".eat_style").html("");
				$(".dish_name").html(data.dishName);
				$(".address").html(data.city+data.address);
				$(".event_description_content").val(data.eventDescription);
			}
		});
		
		$(".returnList").on("click",function(){
			window.location.href = "<%=request.getContextPath()%>/Event/EventList.jsp";
		});
			
	});
	
	</script>
</body>

</html>