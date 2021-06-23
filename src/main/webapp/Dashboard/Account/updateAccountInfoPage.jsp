<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
AccountInfoVO accountInfoVO = (AccountInfoVO) request.getAttribute("accountInfoVO"); 
%>


<jsp:useBean id="accountInfoSvc" scope="page" class="com.accountinfo.model.AccountInfoService" />
<%--    <% com.emp.model.EmpService dao =new com.emp.model.EmpService(); --%>
<!-- //    		pageContext.setAttribute("dao",dao); -->

<%@ page import="com.accountinfo.model.*"%>
<!DOCTYPE html>

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

<title>DashboardPage-updateAccountInfoPage</title>
</head>
<style>
div#Dashboard_AccountInfo_Area{
	margin-top:150px;
}
table {
	width: 600px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
}
table, th, td {
	border: 1px solid #CCCCFF;
 }
th{
	padding:auto 30px;
	text-align: center;
}
td{
   text-align: center;
}
</style>
</head>
<body>
	<header>
		<%@ include file="/common/header.jsp"%>
	</header>
	<div class="container">
	
		<div id="Dashboard_AccountInfo_Area" class="row">
		
			<div class="col">
				<form id="update" method="post" action="dashboard.do">
					<span>會員信箱:</span><br> 
					<%-- <input type="text" name="accountMail" value="<%=(accountMail == null) ? "" : accountMail%>"> --%>
					<input type="text" name="accountMail" value="${accountInfoVO.accountMail}">
					<span style="color:red">${errorMsgs.get("accountMailError")}</span><br> 
					
					<span>會員暱稱:</span><br> 
					<input type="text" name="accountNickname" value="${accountInfoVO.accountNickname}">
					<span style="color:red">${errorMsgs.get("accountNicknameError")}</span><br> 
					
					<span>會員密碼:</span><br> 
					<input type="text" name="accountPassword" value="${accountInfoVO.accountPassword}"><br>
					<span style="color:red">${errorMsgs.get("accountPasswordError")}</span><br> 
					
					<span>會員狀態:</span>
					<input type="text" name="accountState" value="${accountInfoVO.accountState}"><br>
					<span style="color:red">${errorMsgs.get("accountStateError")}</span><br>
					
					<span>會員層級:</span>
					<input type="text" name="accountLevel" value="${accountInfoVO.accountLevel}"><br>
					<span style="color:red">${errorMsgs.get("accountLevelError")}</span><br>
					
					<span>會員姓名:</span><br>
					<input type="text" name="accountName" value="${accountInfoVO.accountName}"><br>
					
					<span>會員性別 :</span> 
					<input type="radio" name="accountGender" value="1" ${(accountInfoVO.accountGender)== 1?"checked":""}>男
					<input type="radio" name="accountGender" value="2" ${(accountInfoVO.accountGender)== 2?"checked":""}>女 <br>
					<span style="color:red">${errorMsgs.get("accountGenderError")}</span><br>
					
					<span>會員生日</span><br>
					<input type="date" name="accountBirth" value="${accountInfoVO.accountBirth}"><br>
					<span style="color:red">${errorMsgs.get("accountBirthError")}</span><br>
					
					<span>會員電話:</span><br>
					<input type="text" name="accountPhone" value="${accountInfoVO.accountPhone}"><br>
					<span style="color:red">${errorMsgs.get("accountPhoneError")}</span><br>
					
					<span>會員照片:(還沒做預覽圖)</span>
					<input type="file" name="accountPic" value="<%=(accountInfoVO.getAccountPic()==null? "":"有圖片")%>"><br>
					<img src="<%=request.getContextPath()%>/Account/Pic/Pic/${accountInfoVO.accountID}" width="300px" height="150px"><br>
					 
					<span>會員身分證正面:</span>
					<input type="file" name="accountIDcardFront" value="<%=(accountInfoVO.getAccountIDcardFront()==null? "":"有圖片")%>"><br>
					<img src="<%=request.getContextPath()%>/Account/Pic/Front/${accountInfoVO.accountID}" width="300px" height="150px"><br>
					
					<span>會員身分證背面:</span>
					<input type="file" name="accountIDcardBack" value="<%=(accountInfoVO.getAccountIDcardBack()==null? "":"有圖片")%>"><br>
					<img src="<%=request.getContextPath()%>/Account/Pic/Back/${accountInfoVO.accountID}" width="300px" height="150px"><br>
					
					<span>會員自我介紹:</span><br>
					<textarea id="textarea" name="accountText" rows="5" cols="50" onkeyup="autogrow(this)">${accountInfoVO.accountText}</textarea><br>
					<span style="color:red">${errorMsgs.get("accountTextError")}</span><br>
					
					<span>會員註冊時間:</span>
					<span>${accountInfoVO.accountRegisterTime}</span><br>
					
					<span>會員驗證碼:</span>
					<span>${accountInfoVO.accountCode}</span><br>
					
					<input type="hidden" name="accountID"  value="${accountInfoVO.accountID}">
					<input type="hidden" name="action"	value="updateAccountInfo_From_Dashboard">
					<input id="change_submit_btn" type="submit" value="確認修改"> 
				</form>
					<a href="<%=request.getContextPath()%>/Dashboard/Account/DashboardAccountPage.jsp">取消修改</a>
			
			
<!-- 				<table> -->
<!-- 					<tr> -->
<!-- 						<th>會員編號</th> -->
<!-- 						<th>會員信箱</th> -->
<!-- 						<th>會員暱稱</th> -->
<!-- 						<th>會員密碼</th> -->
<!-- 						<th>會員狀態</th> -->
						
<!-- 						<th>會員層級</th> -->
<!-- 						<th>會員名稱</th> -->
<!-- 						<th>會員性別</th> -->
<!-- 						<th>會員生日</th> -->
<!-- 						<th>會員電話</th> -->
						
<!-- 						<th>會員照片</th> -->
<!-- 						<th>會員身分證正面</th> -->
<!-- 						<th>會員身分證背面</th> -->
<!-- 						<th>會員自我介紹</th> -->
<!-- 						<th>會員註冊時間</th> -->
<!-- 						<th>會員驗證碼</th> -->
<!-- 					</tr> -->
<!-- 					<tr> -->
<%-- 						<td><%=accountInfoVO.getAccountID()%></td> --%>
<%-- 						<td><%=accountInfoVO.getAccountMail()%></td> --%>
<%-- 						<td><%=accountInfoVO.getAccountNickname()%></td> --%>
<%-- 						<td><%=accountInfoVO.getAccountPassword()%></td> --%>
<%-- 						<td><%=accountInfoVO.getAccountState()%></td> --%>
						
<%-- 						<td><%=accountInfoVO.getAccountLevel()%></td> --%>
<%-- 						<td><%=accountInfoVO.getAccountName()%></td> --%>
<%-- 						<td><%=accountInfoVO.getAccountGender()==1?"男":"女"%></td> --%>
<%-- 						<td><%=accountInfoVO.getAccountBirth()%></td> --%>
<%-- 						<td><%=accountInfoVO.getAccountPhone()%></td> --%>
						
<%-- 						<td><img src="<%=request.getContextPath()%>/Account/Pic/Pic/${accountInfoVO.accountID}" width="300px" height="150px"></td> --%>
<%-- 						<td><img src="<%=request.getContextPath()%>/Account/Pic/Front/${accountInfoVO.accountID}" width="300px" height="150px"></td> --%>
<%-- 						<td><img src="<%=request.getContextPath()%>/Account/Pic/Back/${accountInfoVO.accountID}" width="300px" height="150px"></td> --%>
<%-- 						<td><%=accountInfoVO.getAccountText()%></td> --%>
<%-- 						<td><%=accountInfoVO.getAccountRegisterTime()%></td> --%>
<%-- 						<td><%=accountInfoVO.getAccountCode()%></td> --%>
<!-- 						<td> -->
<!-- 						  <form method="post" action="dashboard.do" style="margin-bottom: 0px;"> -->
<%-- 						     <input type="hidden" name="accountID"  value="${accountInfoVO.accountID}"> --%>
<!-- 						     <input type="hidden" name="action"	value="updateAccountInfo_From_Dashboard"></FORM> -->
<!-- 						     <input type="submit" value="確認修改"> -->
<!-- 						</td> -->
<!-- 						<td> -->
<%-- 							<a href="<%=request.getContextPath()%>/Dashboard/Account/DashboardAccountPage.jsp">取消修改</a> --%>
<!-- 						</td> -->
<!-- 					</tr> -->
<!-- 				</table>	 -->
						
			</div>
		</div>
		
		<div class="row">
			<br>
			<a href="<%=request.getContextPath()%>/Dashboard/Account/DashboardAccountPage.jsp">返回會員管理</a>
		</div>
	</div>


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