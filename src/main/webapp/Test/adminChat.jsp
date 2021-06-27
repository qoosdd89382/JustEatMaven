<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="com.admininfo.model.*" %>

<jsp:useBean id="adminChatSvc" class="com.adminchatroom.model.AdminChatroomService" />
<jsp:useBean id="adminSvc" class="com.admininfo.model.AdminInfoService" />
<jsp:useBean id="accounrSvc" class="com.accountinfo.model.AccountInfoService" />

<%

// 	AdminInfoVO loginAdmin = adminSvc.getOneAdmin((int) session.getAttribute("loginAdminID"));

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
        integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
<style>

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            background-color: #eee;
        }

        #chatbox {
            position: fixed;
            bottom: 0;
            right: 20px;
            width: 400px;
            /* border: 2px solid #0078ae; */
            /* padding: 20px; */
            /* background-color: #eee; */
            border-radius: 10px;
            overflow: hidden;
        }

        /* .panel {
            border: 2px solid #0078ae;
            border-radius: 5px;
        } */

        .message-area {
            resize: none;
            overflow: auto;
            background-color: #ffffff;
            height: 400px;
            padding: 7px;
        }

        /* .input-area {
            background: #0078ae;
            box-shadow: inset 0 0 10px #00568c;
        } */

        /* #message {
            width: 90%;
            padding: 10px;
        }

        .input-area input {
            margin: 0.5em 0em 0.5em 0.5em;
        } */

        /* .text-field {
            border: 1px solid grey;
            padding: 0.2em;
            box-shadow: 0 0 5px #000000;
        } */

        /* h1 {
            font-size: 1.5em;
            padding: 5px;
            margin: 5px;
        } */

        .statusOutput {
            background: #0078ae;
            text-align: center;
            color: #ffffff;
            line-height: 2;
            margin: 0 !important;
            /* padding: 0.2em; */
            /* box-shadow: 0 0 5px #000000; */
            /* width: 30%; */
            /* margin-top: 10%; */
            /* margin-left: 60%; */
            font-size: 22px;

        }


        ul {
            list-style: none;
            margin: 0;
            padding: 0;
        }

        ul li {
            display: inline-block;
            clear: both;
            /* padding: 15px;
            border-radius: 30px; */
            margin-bottom: 2px;
            font-family: Helvetica, Arial, sans-serif;
            position: relative;
        }

        .friend {
            /* background: #eee; */
            float: left;
            margin-right: 28px;
        }

        .me {
            float: right;
            /* background: #0084ff; */
            color: #fff;
            margin-left: 28px;
            text-align: right;
        }

        .friend .fuck,
        .me .fuck {
            /* padding: 1px; */
        }

        .friend span.text,
        .me span.text {
            display: inline-block;
            padding: 12px 15px;
            border-radius: 20px;
            background: #0084ff;
        }

        .friend span.text {
            background: #eee;
        }

        .friend span.time,
        .me span.time {
            float: right;
            margin-right: 10px;
            display: block;
            font-size: 10px;
            text-align: right;
            color: gray;
            margin-top: 2px;
            margin-bottom: 3px;
            /* display:none; */
        }

        .friend span.time {
            float: left;
            margin-right: 0px;
            margin-left: 10px;
        }

        .friend+.me,
        .me+.friend {
            margin-top: 5px;
        }

        .none {
            display: none !important;
        }
</style>
</head>
<!-- <body onload="connect();" onunload="disconnect();"> -->
<body>

    <div id="chatbox">
        <h3 id="statusOutput" class="statusOutput">admin</h3>
        <div id="messagesArea" class="panel message-area none">
        </div>
        
        
        <div class="panel input-area none">
			<input id="message" class="form-control form-control-lg text-field" type="text" placeholder="Message" />
<!--                     onkeydown="if (event.keyCode == 13) sendMessage();" /> -->
            <!-- <input type="submit" id="sendMessage" class="button" value="Send" onclick="sendMessage();" /> -->
            <!-- <input type="button" id="connect" class="button" value="Connect" onclick="connect();" />
            <input type="button" id="disconnect" class="button" value="Disconnect" onclick="disconnect();" /> -->
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <!-- 載入 Font Awesome -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>
<script src="<%=request.getContextPath()%>/common/js/dateFormat.js"></script>
<script>
window.onload = function() {
	var MyPoint = "/AdminChat/${accountID}";
	var host = window.location.host;
	var path = window.location.pathname;
	var webCtx = path.substring(0, path.indexOf('/', 1));
	var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;

	var statusOutput = document.getElementById("statusOutput");
	var messagesArea = document.getElementById("messagesArea");
	var self = '${accountID}';
	var webSocket;
	
    $(document).on("click", "span.text", function () {
        $(this).parent().next().toggleClass("none");
    });
    $("#statusOutput").on("click", function () {
        $(this).next().toggleClass("none");
        $(this).next().next().toggleClass("none");
        if (!$(this).next().hasClass("none")) {
            connect();
        } else {
            disconnect();
        }
    });

	function connect() {
		// create a websocket
		webSocket = new WebSocket(endPointURL);

		webSocket.onopen = function(event) {
			console.log("Connect Success!");
// 			document.getElementById('sendMessage').disabled = false;
// 			document.getElementById('connect').disabled = true;
// 			document.getElementById('disconnect').disabled = false;
			
			var jsonObj = {
					"type" : "history",
					"sender" : self,
					"receiver" : "admin",
					"time" : "", 
					"message" : ""
				};
			webSocket.send(JSON.stringify(jsonObj));
		};

		webSocket.onmessage = function(event) {
			var jsonObj = JSON.parse(event.data);
			if ("open" === jsonObj.type) {
// 				refreshFriendList(jsonObj);
			} else if ("history" === jsonObj.type) {
				messagesArea.innerHTML = '';
				var ul = document.createElement('ul');
				ul.id = "area";
				messagesArea.appendChild(ul);
				// 這行的jsonObj.message是從redis撈出跟好友的歷史訊息，再parse成JSON格式處理
				var messages = JSON.parse(jsonObj.message);
				for (var i = 0; i < messages.length; i++) {
					var historyData = JSON.parse(messages[i]);
					var showMsg = historyData.message;
					var showTime = historyData.time;
					
					var li = document.createElement('li');
					// 根據發送者是自己還是對方來給予不同的class名, 以達到訊息左右區分
					historyData.sender === self ? li.className += 'me' : li.className += 'friend';
					li.innerHTML = '<div><span class="text">' + showMsg + '</span></div><span class="time none">' + showTime + '</span>';
					ul.appendChild(li);
				}
				messagesArea.scrollTop = messagesArea.scrollHeight;
			} else if ("chat" === jsonObj.type) {
				if ( (statusOutput.textContent == jsonObj.sender) || jsonObj.sender == self) {
					var li = document.createElement('li');
					jsonObj.sender === self ? li.className += 'me' : li.className += 'friend';
					li.innerHTML = '<div><span class="text">' + jsonObj.message + '</span></div><span class="time none">' + jsonObj.time + '</span>';
					console.log(li);
					document.getElementById("area").appendChild(li);
					messagesArea.scrollTop = messagesArea.scrollHeight;
				}
			} else if ("close" === jsonObj.type) {
// 				refreshFriendList(jsonObj);
			}
			
		};

		webSocket.onclose = function(event) {
			console.log("Disconnected!");
		};
	}
	
	function sendMessage() {
		var inputMessage = document.getElementById("message");
		var friend = statusOutput.textContent;
		var message = inputMessage.value.trim();

		if (message === "") {
			alert("Input a message");
			inputMessage.focus();
		} else if (friend === "") {
			alert("Choose a friend");
		} else {
			var jsonObj = {
				"type" : "chat",
				"sender" : self,
				"receiver" : "admin",
				"time" : (new Date()).format("yyyy-MM-dd hh:mm") + "",
				"message" : message
			};
			webSocket.send(JSON.stringify(jsonObj));
			inputMessage.value = "";
			inputMessage.focus();
		}
	}
	
// 	// 有好友上線或離線就更新列表
// 	function refreshFriendList(jsonObj) {
// 		var friends = jsonObj.userIDs;
// 		var row = document.getElementById("row");
// 		row.innerHTML = '';
// 		for (var i = 0; i < friends.length; i++) {
// 			if (friends[i] === self) { continue; }
// 			row.innerHTML +='<div id=' + i + ' class="column" name="friendName" value=' + friends[i] + ' ><h2>' + friends[i] + '</h2></div>';
// 		}
// 		addListener();
// 	}
	// 註冊列表點擊事件並抓取好友名字以取得歷史訊息
// 	function addListener() {
// 		var container = document.getElementById("row");
// 		container.addEventListener("click", function(e) {
// 			var friend = e.srcElement.textContent;
// 			updateFriendName(friend);
// 			var jsonObj = {
// 					"type" : "history",
// 					"sender" : self,
// 					"receiver" : "admin",
// 					"time" : "", 
// 					"message" : ""
// 				};
// 			webSocket.send(JSON.stringify(jsonObj));
// 		});
// 	}
	
	function disconnect() {
		webSocket.close();
		document.getElementById('sendMessage').disabled = true;
		document.getElementById('connect').disabled = false;
		document.getElementById('disconnect').disabled = true;
	}
	
	$("#message").keydown(function(e){
		if (event.keyCode == 13) {
			sendMessage();
		}
	});
	
	
// 	function updateFriendName(name) {
// 		statusOutput.innerHTML = name;
// 	}
}
</script>
</body>
</html>