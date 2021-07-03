<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
<title>Admin register page</title>
    <!-- Custom fonts for this template-->
    <link href="<%=request.getContextPath()%>/vendors/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">
    <!-- Custom styles for this template-->
    <link href="<%=request.getContextPath()%>/Dashboard/css/sb-admin-2.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/vendors/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
<style>


        #chatPage {
            width: 100%;
            background: #eee;
            overflow: hidden;
        }

        .friendList {
            padding: 10px;
            background-color: #fff;
        }
       
        .friendList .friendName {
            font-size: 24px;
            cursor: pointer;
            background-color: #fff;
        }
        .friendList .friendName .friendImg {
            width: 40px;
            height: 40px;
            overflow: hidden;
            background-color: #fff;
        }
        .friendName .friendImg img {
            height: 100%;
            min-height: 100%;
        }
        .friendList .friendName .name {
            font-size: 24px;
        }

        .statusOutput .friendName {
            background-color: #4e73df;
        }
        .statusOutput .friendName .friendImg {
            width: 50px;
            height: 50px;
            overflow: hidden;
            background-color: #fff;
        }
        /* .statusOutput .friendName .friendImg img {
            height: 100%;
            min-height: 100%;
        } */
        .statusOutput .friendName .name {
            font-size: 28px;
            color:#fff;
        }

        ul#area li {
            display: inline-block;
            clear: both;
            margin-bottom: 2px;
            font-family: Helvetica, Arial, sans-serif;
            position: relative;
        }

        .friend {
            float: left;
            margin-right: 28px;
        }

        .me {
            float: right;
            color: #fff;
            margin-left: 28px;
            text-align: right;
        }

        .friend span.text,
        .me span.text {
            display: inline-block;
            padding: 12px 15px;
            border-radius: 20px;
            background-color: #0084ff;
        }

        .friend span.text {
            background-color: #eee;
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
        
        .-none {
            display: none !important;
        }
        
        .message-area {
            resize: none;
            overflow: auto;
            background-color: #ffffff;
            height: 400px;
            padding: 7px;
        }

        #message {
            padding: 10px;
        }

</style>
</head>
<body id="page-top">
<!-- <body onload="connect();" onunload="disconnect();" id="page-top"> -->

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
                    <h1 class="h3 mb-2 text-gray-800">客服聊天室</h1>
<!--                     <p class="mb-4"> -->
						
<!-- 					</p> -->

                    <!-- DataTales Example -->
<!--                     <div class="card shadow mb-4"> -->
<!--                         <div class="card-header py-3"> -->
<!--                             <h6 class="m-0 font-weight-bold text-primary">管理員列表</h6> -->
<!--                         </div> -->
<!--                         <div class="card-body"> -->
                  
                  
                  
<section id="chatPage" class="row rounded-lg border shadow">
	<div class="friendList col-lg-4 border-right">
	</div>
	<div class="chatBody col-lg-8 p-0">
		<div id="statusOutput" class="statusOutput p-0">
			<div id="1" class="friendName row m-0 p-2">
				<div class="friendImg rounded-circle border">
					<img class="border" src="<%=request.getContextPath()%>/Account/Pic/Pic/" alt="">
				</div>
				<div class="name ml-2"></div>
			</div>
		</div>
		<div id="messagesArea" class="panel message-area p-2">
<!-- 			<ul id="area" class="clearfix p-0"> -->
<!-- 			</ul> -->
		</div>
		<div class="panel input-area">
			<input id="message" class="form-control form-control-lg text-field" type="text" placeholder="Message">
<!--                         onkeydown="if (event.keyCode == 13) sendMessage();"> -->
		</div>
	</div>
</section>
	
	
	
	
<!-- 						</div> -->
<!--                     </div> -->

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
    
<script src="<%=request.getContextPath()%>/common/js/dateFormat.js"></script>
<script>


$(window).on("unload", function(e) {
	disconnect();
});
$(function(){

	
    $(document).on("click", "span.text", function () {
        $(this).parent().next().toggleClass("-none");
    });
    
	var MyPoint = "/AdminChat/admin";
	var host = window.location.host;
	var path = window.location.pathname;
	var webCtx = path.substring(0, path.indexOf('/', 1));
	var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;

	var statusOutput = $(".statusOutput").find(".name")[0];
	var messagesArea = document.getElementById("messagesArea");
	var self = 'admin';
	var webSocket;

	function connect() {
		// create a websocket
		webSocket = new WebSocket(endPointURL);

		webSocket.onopen = function(event) {
			console.log("Connect Success!");
		};

		webSocket.onmessage = function(event) {
			var jsonObj = JSON.parse(event.data);
			if ("open" === jsonObj.type) {
				refreshFriendList(jsonObj);
			} else if ("history" === jsonObj.type) {
				messagesArea.innerHTML = '';
				var ul = document.createElement('ul');
				ul.id = "area";
				messagesArea.appendChild(ul);
				$("ul#area").addClass("clearfix p-0");
				// 這行的jsonObj.message是從redis撈出跟好友的歷史訊息，再parse成JSON格式處理
				var messages = JSON.parse(jsonObj.message);
				
				for (var i = 0; i < messages.length; i++) {
					var historyData = JSON.parse(messages[i]);
					var showMsg = historyData.message;
					var showTime = historyData.time;
					var li = document.createElement('li');
					
					// 根據發送者是自己還是對方來給予不同的class名, 以達到訊息左右區分
					historyData.sender === self ? li.className += 'me' : li.className += 'friend';
					li.innerHTML = '<div><span class="text">' + showMsg + '</span></div><span class="time -none">' + showTime + '</span>';
					
					ul.appendChild(li);
				}
				
				messagesArea.scrollTop = messagesArea.scrollHeight;
				
			} else if ("chat" === jsonObj.type) {
				if ( (statusOutput.textContent == jsonObj.sender) || jsonObj.sender == self) {
					var li = document.createElement('li');
					jsonObj.sender === self ? li.className += 'me' : li.className += 'friend';
// 					li.innerHTML = jsonObj.message;
					li.innerHTML = '<div><span class="text">' + jsonObj.message + '</span></div><span class="time -none">' + jsonObj.time + '</span>';
					document.getElementById("area").appendChild(li);
					messagesArea.scrollTop = messagesArea.scrollHeight;
				}
				
			} else if ("close" === jsonObj.type) {
				refreshFriendList(jsonObj);
				
			}
			
		};

		webSocket.onclose = function(event) {
			console.log("Disconnected!");
		};
	}
	
	connect();
	
	function sendMessage() {
		var inputMessage = document.getElementById("message");
		var friend = statusOutput.textContent;
		var message = inputMessage.value.trim();

		console.log(message)
		if (message === "") {
			alert("Input a message");
			inputMessage.focus();
		} else if (friend === "") {
			alert("Choose a friend");
		} else {
			var jsonObj = {
				"type" : "chat",
				"sender" : self,
				"receiver" : friend,
				"time" : (new Date()).format("yyyy-MM-dd hh:mm") + "",
				"message" : message +""
			};
			webSocket.send(JSON.stringify(jsonObj));
			inputMessage.value = "";
			inputMessage.focus();
		}
	}
	
	// 有好友上線或離線就更新列表
	function refreshFriendList(jsonObj) {
		var friends = jsonObj.userIDs;
		var row = $(".friendList")[0];
		row.innerHTML = '';
		for (var i = 0; i < friends.length; i++) {
			if (friends[i] === self) { continue; }
				row.innerHTML =
// 					'<div id=' + i + ' class="column" name="friendName" value=' + friends[i] + ' ><h2>' + friends[i] + '</h2></div>' + row.innerHTML;
				'<div id=' + i + ' class="friendName row px-1 m-0 mb-2 border-bottom pb-2" name="friendName" value="' + friends[i] + '">' + 
					'<div class="friendImg rounded-circle border">' + 
					'<img class="border" src="/justeat-maven/Account/Pic/Pic/' + friends[i] + '" alt="">' + 
                '</div>' + 
                '<div class="name ml-2">' + friends[i] + '</div>' +
	            '</div>'
	            + row.innerHTML
	            ;
				
	            var srcURL = $(".statusOutput").find("img").attr("src");
	            $(".statusOutput").find("img").attr("src", srcURL + friends[i]);
	            $(".statusOutput").find(".name").text(friends[i]);
	            
					var jsonObj = {
						"type" : "history",
						"sender" : self,
						"receiver" : friends[i],
						"time" : "", 
						"message" : ""
					};
					webSocket.send(JSON.stringify(jsonObj));
					statusOutput.innerHTML = friends[i];
			
		}
		
		addListener();
	}
	// 註冊列表點擊事件並抓取好友名字以取得歷史訊息
	function addListener() {
		var container = $(".friendList")[0];
		container.addEventListener("click", function(e) {
			var friend = e.srcElement.textContent;
			updateFriendName(friend);
			var jsonObj = {
					"type" : "history",
					"sender" : self,
					"receiver" : friend,
					"time" : "", 
					"message" : ""
				};
			webSocket.send(JSON.stringify(jsonObj));
		});
	}
	
	function disconnect() {
		webSocket.close();
	}
	
	function updateFriendName(name) {
		statusOutput.innerHTML = name;
	}

	
	$("#message").keydown(function(e){
		if (event.keyCode == 13) {
			sendMessage();
		}
	});
	
});

</script>
</body>
</html>