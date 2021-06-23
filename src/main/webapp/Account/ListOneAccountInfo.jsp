<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.accountinfo.model.*"%>
<!DOCTYPE html>

<%
AccountInfoVO accountInfoVO = (AccountInfoVO) request.getAttribute("accountInfoVO"); 
//EmpServlet.java(Concroller), 存入req的VO物件
%>

<html>

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap 的 CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/vendors/slick/slick.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/vendors/slick/slick-theme.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/css/footer.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/index.css">

<title>ListOneAccountInfo.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-top: 50px;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 600px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>
</head>

<body bgcolor='white'>

	<header>
		<%@ include file="/common/header.jsp"%>
	</header>

	<table id="table-1">
		<tr><td>
			 <h3>會員資料</h3>
			 <h4><a href="AccountPage.jsp">回首頁</a></h4>
		</td></tr>
	</table>

	<table>
		<tr>
			<th>會員流水號</th>
		
			<th>會員信箱</th>
			<th>會員暱稱</th>
			<th>會員密碼</th>
			<th>會員狀態</th>
			<th>會員層級</th>
			
			<th>會員姓名</th>
			<th>會員性別</th>
			<th>會員生日</th>
			<th>會員電話</th>
			<th>會員照片</th>
			
			<th>會員身分證正面</th>
			<th>會員身分證背面</th>
			<th>會員自我介紹</th>
			<th>會員註冊時間</th>			
		</tr>
		<tr>
			<td><%=accountInfoVO.getAccountID()%></td>
			
			<td><%=accountInfoVO.getAccountMail()%></td>
			<td><%=accountInfoVO.getAccountNickname()%></td>
			<td><%=accountInfoVO.getAccountPassword()%></td>
			<td><%=accountInfoVO.getAccountState()%></td>
			<td><%=accountInfoVO.getAccountLevel()%></td>
			
			<td><%=accountInfoVO.getAccountName()%></td>
			<td><%=accountInfoVO.getAccountGender()%></td>
			<td><%=accountInfoVO.getAccountBirth()%></td>
			<td><%=accountInfoVO.getAccountPhone()%></td>
			<td><%=accountInfoVO.getAccountPic()%><img src="images/accountTest.jpg" width="100" height="100" border="0"></td>
			
			<td><%=accountInfoVO.getAccountIDcardFront()%><img src="images/accountIDFTest.jpg" width="100" height="100" border="0"></td>
			<td><%=accountInfoVO.getAccountIDcardBack()%><img src="images/accountIDBTest.jpg" width="100" height="100" border="0"></td>
			<td><%=accountInfoVO.getAccountText()%></td>
			<td><%=accountInfoVO.getAccountRegisterTime()%></td>
		</tr>
	</table>
	
	<h3><a id="AccountLogin" href='AccountPage.jsp'>回到會員中心</a></h3>

	<footer>
		<%@ include file="/common/footer.jsp"%>
	</footer>
	
	<%-- body 結束標籤之前，載入Bootstrap 的 JS 及其相依性安裝(jQuery、Popper) --%>
	<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.6.0.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
	<%-- <script src="https://cdnjs.cloudflare.com/ajax/libs/skrollr/0.6.30/skrollr.min.js"></script> --%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/vendors/slick/slick.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/footer.js"></script>
	<script src="<%=request.getContextPath()%>/js/index.js"></script>
	
</body>
</html>