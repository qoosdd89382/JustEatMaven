<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.accountinfo.model.*"%>


<jsp:useBean id="accountSvc" scope="page" class="com.accountinfo.model.AccountInfoService" />
<jsp:useBean id="noticeSvc" scope="page" class="com.notice.model.NoticeService" />
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<style>
* {
	box-sizing: border-box;
}
body {
	background: lightgray;
}
.-none {
	display: none;
}
.-read {
	background: #FDFFD7 !important;
}
#msgsList {
	position: absolute;
	top: 60px;
	right: 120px;
	overflow: hidden;
}
#msgsList > div {
	padding: 10px 15px;
	max-height: 350px;
	width: 250px;
	overflow-y: auto;
	background: #fff;
}

#alertBell {
	cursor: pointer;
	position: relative;
	font-size:24px;
	line-height:40px;
	width: 45px;
	height: 45px;
	border-radius: 50%;
    text-align: center;
	border: 1px solid black;
}
#msgRedDot {
	position: absolute;
	top: 7px;
	right: 7px;
	width: 7px;
	height: 7px;
	border-radius: 50%;
	background: red;
	z-index:1;
}
.msgOne {
/* 	cursor: pointer; */
	border-bottom: 1px lightgray solid;
	padding-bottom: 5px;
	margin-bottom: 10px;
	background: #fff;
	padding: 10px;
}
.msgOne .msgContent {
	word-wrap: break-word;
	word-break: break-all;
}
.msgOne .msgTime {
	font-size:12px;
}
</style>
</head>
<body>

<div id="connectForNotice" style="width: 100px; height: 100px; background: red;">
連線
</div>

<div id="disconnectForNotice" style="width: 100px; height: 100px; background: green;">
中斷連線
</div>

<div id="alertBell" class="">
		<div id="msgRedDot" class="-none"></div>
		<i class="fas fa-bell"></i>
</div>

<section id="msgsList" class="rounded -none">
	<div id="msgsListInner">
	<!-- 	<div class="msgOne" data-id=""> -->
	<!-- 		<div class="msgTitle"><b>系統通知</b></div> -->
	<!-- 		<div class="msgContent">歡迎您註冊成為會員！</div> -->
	<!-- 		<div class="msgTime">2001.1.1</div> -->
	<!-- 	</div> -->
	</div>
</section>



	<%-- body 結束標籤之前，載入Bootstrap 的 JS 及其相依性安裝(jQuery、Popper) --%>
	<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.6.0.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>

	<script src="<%=request.getContextPath()%>/common/js/dateFormat.js"></script>
<script>
window.onload = function() {
	
	$("#alertBell").on("click", function(){
		$("#msgsList").toggleClass("-none");
		$("#msgRedDot").addClass("-none");
		$(".msgOne").each(function(ele, idx){
			console.log(ele);
			if ($(ele).hasClass("-unread")) {
				console.log($(ele));
// 				$(ele).removeClass("-unread");
			}			
		});
// 		$(".msgOne").removeClass("-unread");
	});

	$("#connectForNotice").on("click", function(){
		connectForNotice();
	});

	$("#disconnectForNotice").on("click", function(){
		disconnectForNotice();
	});
	
	var myPointForNotice = "/Notify/${param.accountID}";
	var hostForNotice = window.location.host;
	var pathForNotice = window.location.pathname;
	var webCtxForNotice = pathForNotice.substring(0, pathForNotice.indexOf('/', 1));
	var endPointURLForNotice = "ws://" + hostForNotice + webCtxForNotice + myPointForNotice;
// 	var self = '${accountID}';
	var webSocketForNotice;
	
	function connectForNotice() {
		webSocketForNotice = new WebSocket(endPointURLForNotice);

		webSocketForNotice.onopen = function(event) {
			console.log("Connect Success!");
// 			$("ul").html("");
		};
		
		
		webSocketForNotice.onmessage = function(event) {
			var jsonObj = JSON.parse(event.data);
			if (Array.isArray(jsonObj)) {
				for (var i = 0; i < jsonObj.length; i++) {
					outputMessage(jsonObj[i]);
				}
				$(".msgOne").removeClass("-unread");
				$("#msgRedDot").addClass("-none");
			} else {
				outputMessage(jsonObj);
			}
		};
		
		webSocketForNotice.onclose = function(event) {
			console.log("Disconnected!");
		};
	}
	
	function outputMessage(obj) {
		
		$("#msgsListInner").prepend(
			'<div class="msgOne -unread" data-id="' + obj.noticeID + '">' + 
				'<div class="msgTitle"><b>' + obj.noticeType + '通知</b></div>' + 
				'<div class="msgContent">' + obj.noticeText + '</div>' + 
				'<div class="msgTime">' + obj.noticeTime + '</div>' +
				'</div>' +
			'</div>'
			);

// 		$("ul").prepend(
// 				'<li><${accountSrv.selectOneAccountInfo(param.accountID).accountNickname}收到了['
// 				+ obj.noticeType + ']的消息:'
// 				+ obj.noticeText +
// 				'(' + obj.noticeTime + ')' + 
// 				'</li>');

		$("#msgRedDot").removeClass("-none");
	}
	
	function disconnectForNotice() {
		webSocketForNotice.close();
	}
}
</script>
</body>
</html>