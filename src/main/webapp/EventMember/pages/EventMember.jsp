<%@page import="java.sql.Timestamp"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.eventmember.model.*"%>
<%
	EventMemberService eventMemberServiceSvc = new EventMemberService();
	EventMemberVO eventMemberVO = null;
	List<EventMemberVO> list = eventMemberServiceSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>活動列表</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/EventMember/css/style.css">
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
			<li class="breadcrumb-item"><a href=" # ">我的活動</a></li>
			<li class="breadcrumb-item"><a href=" # ">參加/結束的活動</a></li>
			<li class="breadcrumb-item"><a href=" # ">活動詳情</a></li>
			<li class="breadcrumb-item active" aria-current="page">成員列表</li>
		</ol>
	</nav>
	<div>
	<table> 
  <tr> 
   <th>帳號</th> 
   <th>性別</th>
   <th>平均星數</th>
   <th>總活動次數</th>
   <th>出席次數</th>
   <th>狀態</th>
   <th>社交</th>
  </tr> 
  <tr> 
  <td>abcd@gmail.com</th> 
   <td>男</td>
   <td>2</th> 
   <td>4</td> 
   <td>2</th> 
   <td>參與中</td> 
   <td class="item_block">
          <label></label>
          <button type="submit" class="btn_submit">加入好友</button>
   </td>

  </tr> 
  <tr> 
  <td>bcde@gmail.com</th> 
  <td>女</td> 
  <td>3</th> 
  <td>5</td> 
  <td>3</th> 
  <td>參與中</td> 
  <td class="item_block">
          <label></label>
          <button type="submit" class="btn_submit">加入好友</button>
        </td>
  </tr> 
 
     
  
</table>
	
	
	
	
	
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
		$(function() {
			$(".header").load("header.html");
			$(".footer").load("footer.html");
		});
		
		$("[name=position]").on("change",function(){
			if($(this).is(":checked")){
				$(".positionSubmit").submit();
			}
		});
		
		$("[name=date]").on("change",function(){
			$(".dateSubmit").submit();
		});
	</script>
</body>
</html>