<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.accountinfo.model.*"%>


<jsp:useBean id="accountSrv" scope="page" class="com.accountinfo.model.AccountInfoService" />
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
        integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">

</head>
<body>

<div id="connect" style="width: 100px; height: 100px; background: red;">
連線
</div>

<div id="disconnect" style="width: 100px; height: 100px; background: green;">
中斷連線
</div>

<ul>

</ul>



<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>
<script src="<%=request.getContextPath()%>/common/js/dateFormat.js"></script>
<script>
window.onload = function() {

	$("#connect").on("click", function(){
		connect()
	});

	$("#disconnect").on("click", function(){
		disconnect()
	});
	
	
	
	var MyPoint = "/Notify/${param.accountID}";
	var host = window.location.host;
	var path = window.location.pathname;
	var webCtx = path.substring(0, path.indexOf('/', 1));
	var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;

	var statusOutput = document.getElementById("statusOutput");
	var messagesArea = document.getElementById("messagesArea");
	var self = '${accountID}';
	var webSocket;
	
	function connect() {
		webSocket = new WebSocket(endPointURL);

		webSocket.onopen = function(event) {
			console.log("Connect Success!");
			$("ul").html("");
		};
		
		
		webSocket.onmessage = function(event) {
			var jsonObj = JSON.parse(event.data);
			if (Array.isArray(jsonObj)) {
				for (var i = 0; i < jsonObj.length; i++) {
					console.log(jsonObj[i]);
					outputMessage(jsonObj[i]);
				}
			} else {
				console.log(jsonObj);
				outputMessage(jsonObj);
			}
		};
		
		webSocket.onclose = function(event) {
			console.log("Disconnected!");
		};
	}
	
	function outputMessage(obj) {
		$("ul").prepend(
				'<li>${accountSrv.selectOneAccountInfo(param.accountID).accountNickname}收到了[類型'
				+ obj.noticeType + ']的消息:'
				+ obj.noticeText +
				'(' + obj.noticeTime + ')' + 
				'</li>');
	}
	
	function disconnect() {
		webSocket.close();
	}
}
</script>
</body>
</html>