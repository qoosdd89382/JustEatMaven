<%@page import="java.sql.Timestamp"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.evaluatedmember.model.*"%>
<%@ page import="com.accountinfo.model.*"%>

<jsp:useBean id="accountSvc" scope="page" class="com.accountinfo.model.AccountInfoService" />
<jsp:useBean id="evaluatedmemberSvc" scope="page" class="com.evaluatedmember.model.EvaluatedMemberService" />
<jsp:useBean id="eventMemberSvc" scope="page" class="com.eventmember.model.EventMemberService" />
<%
	List<EvaluatedMemberVO> list = evaluatedmemberSvc.getAllByEventID(300002);
	pageContext.setAttribute("list", list);
// 	int accountAvgScore = eventMemberSvc.getAvgScoreByAccountID(100001);
// 	pageContext.setAttribute("accountAvgScore", accountAvgScore);

%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>成員評分</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/Event/css/abc.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/footer.css">
</head>
<body>

	<header>
		<%@ include file="/common/header.jsp"%>
	</header>
	<h2>成員評分</h2>
	<nav aria-label="breadcrumb" style="-bs-breadcrumb-divider: '&gt;';">
		<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href=" # ">首頁</a></li>
			<li class="breadcrumb-item"><a href=" # ">我的活動</a></li>
			<li class="breadcrumb-item"><a href=" # ">參加/結束的活動</a></li>
			<li class="breadcrumb-item"><a href=" # ">評鑑頁面</a></li>
			
		</ol>
	</nav>
	
<%-- 錯誤表列 --%>

	
	<table> 
	<tr> 
		<th>帳號</th> 
		<th>性別</th>
		<th>平均星數</th>
		<th>給予星數</th>
		<th>社交</th>
		
	</tr>
		<c:forEach var="evaluatedmemberVO" items="${list}" >
			<tr> 
				<td>${accountSvc.selectOneAccountInfo(evaluatedmemberVO.accepterAccountID).accountNickname}</td>
				<td>
					<c:if test="${accountSvc.selectOneAccountInfo(evaluatedmemberVO.accepterAccountID).accountGender == 1}">男</c:if>
					<c:if test="${accountSvc.selectOneAccountInfo(evaluatedmemberVO.accepterAccountID).accountGender == 2}">女</c:if>
				</td> 
				<td>${eventMemberSvc.getAvgScoreByAccountID(evaluatedmemberVO.accepterAccountID)}</td> 
				<td>${evaluatedmemberVO.giveScore}</td> 
				<td>			
					<form action="<%= request.getContextPath() %>/Event/evaluatedmember.do"method="post">
					<input type="hidden" name="eventID" value="${eventID}">
					<input type="hidden" name="giverID" value="${account}">
					<input type="hidden" name="accepterID" value="${accountVO.accountID}">
					<input type="hidden" name="score" value="1">
					<input type="hidden" name="action" value="giveScore">
					<button type="submit">送出評分</button>
		
					</form>
				</td>
			</tr> 
		</c:forEach>
	</table>
	  <div class="btn_margin" align="center"  >
	  
	              <input type ="button" onclick="history.back()" value="確定送出"></input>

	  </div>
			
			
	<footer>
		<%@ include file="/common/footer.jsp"%>
	</footer>
	<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.6.0.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/js/footer.js"></script>
	<script>
	  <script>
      window.onload=function(){  //載入視窗時就呼叫該方法
          var imgs=document.getElementsByTagName("img");
          for(var i=0;i<imgs.length;i++){
              imgs[i].setAttribute("score",i+1);//設定分數
              imgs[i].onclick=function(){   //點選時呼叫方法


                  var srcEl=event.srcElement;//通過event來獲取原元素


                  var score=srcEl.getAttribute("score");//獲取分數
                  for(var j=0;j<score;j++){
                      imgs[j].src="img/Star.png";
                  }
                  for(var j=score;j<imgs.length;j++){
                      imgs[j].src="img/StarCopy.png";
                  }
                  document.getElementById("lab").innerHTML=srcEl.getAttribute("score");//通過srcEl呼叫getAttribute方法獲取分數
              }
          }
      }
  </script>
	
	<body>
		    <img src="img/StarCopy.png">
		    <img src="img/StarCopy.png">
		    <img src="img/StarCopy.png">
		    <img src="img/StarCopy.png">
		    <img src="img/StarCopy.png">
		    <label id="lab">0</label>分
	</body>
	
	

	<script>

		$("[name=position]").on("change",function(){
			if($(this).is(":checked")){
				$(".positionSubmit").submit();
			}
		});
	</script>
</body>
</html>