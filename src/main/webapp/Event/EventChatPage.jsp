<%@page import="com.eventinfo.model.EventInfoService"%>
<%@page import="com.eventinfo.model.EventInfoVO"%>
<%@page import="com.accountinfo.model.AccountInfoVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	AccountInfoVO accountInfoVO = (AccountInfoVO)session.getAttribute("accountInfoVOLogin");
	pageContext.setAttribute("accountInfoVO", accountInfoVO);
	EventInfoService eventInfoSvc = new EventInfoService();
	EventInfoVO eventInfoVO = eventInfoSvc.getEventID(Integer.parseInt(request.getParameter("eventID")));
	pageContext.setAttribute("eventInfoVO", eventInfoVO);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>活動聊天室</title>
<style>
* {
	margin: auto;
	padding: 0px;
}

html, body {
	font: 15px verdana, Times New Roman, arial, helvetica, sans-serif,
		Microsoft JhengHei;
	width: 90%;
	height: 90%;
	background: #eeeeda;
}

.panel {
	border: 2px solid #0078ae;
	border-radius: 5px;
	width: 100%;
}

.message-area {
	height: 70%;
	resize: none;
	box-sizing: border-box;
	overflow: auto;
}

.input-area {
	background: #0078ae;
	box-shadow: inset 0 0 10px #00568c;
}

.input-area input {
	margin: 0.5em 0em 0.5em 0.5em;
}

.text-field {
	border: 1px solid grey;
	padding: 0.2em;
	box-shadow: 0 0 5px #000000;
}

h1 {
	font-size: 1.5em;
	padding: 5px;
	margin: 5px;
}

#userName {
	width: 20%;
}

#message {
	min-width: 50%;
	max-width: 60%;
}

.statusOutput {
	background: #0078ae;
	text-align: center;
	color: #ffffff;
	border: 1px solid grey;
	padding: 0.2em;
	box-shadow: 0 0 5px #000000;
	width: 30%;
}

</style>
</head>
<body>
	<h3 id="statusOutput" class="statusOutput">${eventInfoVO.eventName}</h3>
	<div id="row"></div>
	<textarea id="messagesArea" class="panel message-area" readonly></textarea>
	<div class="panel input-area">
		<span id="userName">${accountInfoVO.accountNickname}</span>
		<input type="hidden" id="accountID" value="${accountInfoVO.accountID}"/>
		<input id="message" class="text-field" type="text" placeholder="Message"/> 
		<input type="submit" id="sendMessage" class="button" value="Send"/> 
		<input type="button" id="connect" class="button" value="Connect"/> 
		<input type="button" id="disconnect" class="button" value="Disconnect" />
	</div>
</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	console.log(new Date());
	var MyPointForEventChatroom = "/EventChatRoom/${param.eventID}/${accountInfoVO.accountID}";
	var hostForEventChatroom = window.location.host;
	var pathForEventChatroom = window.location.pathname;
	console.log(hostForEventChatroom);
	console.log(pathForEventChatroom);
	var webCtxForEventChatroom = pathForEventChatroom.substring(0, pathForEventChatroom.indexOf('/', 1));
	console.log(webCtxForEventChatroom);
	var endPointURL = "ws://" + hostForEventChatroom + webCtxForEventChatroom + MyPointForEventChatroom;
	// ws://localhost:8081/justeat-maven/Event/accountID
	var webSocketForEventChatroom;
	var eventID = "${param.eventID}";
	var accountID = "${accountInfoVO.accountID}";
	
	function connectForEventChatroom(){
		webSocketForEventChatroom = new WebSocket(endPointURL);
		
		webSocketForEventChatroom.onopen = function(event){
			console.log("WebSocket連線成功");
			$("#messagesArea").val("");
			$("#sendMessage").prop("disabled","");
			$("#connect").attr("disabled",true);
			$("#disconnect").prop("disabled","");
			
			var jsonObjForEventChatroom = {
				"type" : "history",
				"eventID" : eventID,
				"senderID" : accountID,
				"senderName" : "",
				"message" : "",
				"sendTime":""
			};
			webSocketForEventChatroom.send(JSON.stringify(jsonObjForEventChatroom));
		}
		
		webSocketForEventChatroom.onmessage = function(event){
			var jsonObjForEventChatroom = JSON.parse(event.data);
			var messagesAreaForEventChatroom = $("#messagesArea");
			console.log(event.data);
			if("history" === jsonObjForEventChatroom["type"]){
				for(var i =0;jsonObjForEventChatroom.length;i++){
					var message = jsonObjForEventChatroom['senderName']+":"+jsonObjForEventChatroom['message'] +"\r\n"+jsonObjForEventChatroom['sendTime']+"\r\n";
					messagesAreaForEventChatroom.val(messagesAreaForEventChatroom.val()+message);
				}
				messagesAreaForEventChatroom.scrollTop(messagesAreaForEventChatroom.attr("scrollHeight"));
			}else if("chat"===jsonObjForEventChatroom["type"]){
				var message = jsonObjForEventChatroom['senderName']+":"+jsonObjForEventChatroom['message'] +"\r\n" + jsonObjForEventChatroom['sendTime']+"\r\n";
				messagesAreaForEventChatroom.val(messagesAreaForEventChatroom.val()+message);
				messagesAreaForEventChatroom.scrollTop(messagesAreaForEventChatroom.attr("scrollHeight"));	
			}
		}
		
		webSocketForEventChatroom.onclose = function(event){
			console.log("WebSocket連線關閉");
		}
	}
	
	function sendMessageForEventChatroom(){
		var userNameForEventChatroom = $("#userName").text();
		var messageForEventChatroom = $("#message").val();
		
		var jsonObjForEventChatroom = {
			"type" : "chat",
			"eventID" : eventID,
			"senderID" : accountID,
			"senderName" : userNameForEventChatroom,
			"message" : messageForEventChatroom,
			"sendTime": new Date().toLocaleString()
		};
		webSocketForEventChatroom.send(JSON.stringify(jsonObjForEventChatroom));
		$("#message").val("");
		$("#message").focus();
	}
		
	function disconnectForEventChatroom(){
		webSocketForEventChatroom.close();
		$("#sendMessage").prop("disabled","disabled");
		$("#connect").attr("disabled",false);
		$("#disconnect").prop("disabled","disabled");
	}
	
	$("#sendMessage").on("click",function(){
		sendMessageForEventChatroom();
	});
	
	$("#message").on("keydown",function(event){
		if(event.keyCode==13){
			sendMessageForEventChatroom();
		}
	});
	
	$("#connect").on("click",function(){
		connectForEventChatroom();
	});
	
	$("#disconnect").on("click",function(){
		disconnectForEventChatroom();
	});
	
	$(function(){
		connectForEventChatroom();
	});
	
	$(window).on("unload",function(){
		disconnectForEventChatroom();
	});
	</script>
</html>