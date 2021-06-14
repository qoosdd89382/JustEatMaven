<%@	page import="com.eventinfo.model.EventInfoVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>新增菜色</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/Event/css/style.css">
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
			<li class="breadcrumb-item"><a href=" # ">活動列表</a></li>
			<li class="breadcrumb-item"><a href=" # ">活動詳情</a></li>
			<li class="breadcrumb-item active" aria-current="page">新增菜色</li>
		</ol>
	</nav>
	<form method="post" action="<%=request.getContextPath()%>/Event/EventInfo.do">
	    <main class="insert_content col-11 col-lg-11 row">
	        <div class="insert_content_left col-6 col-lg-6">
	            <div class="title">
	                <h2>新增菜色</h2>
	            </div>
	            <div class="title_separate">
	                菜色名稱
	                <label>
	                <input type="text" name="dish_name" value="" placeholder="請輸入菜名">
	            </label>
	            </div>
	            <div class="title_separate">
	                食材		
	               	<div class="ingredient_zone">
		            	<div class="ingredient_name">
		                	<input type="text" placeholder="食材名稱">
		                </div>
		                <span class="ingredient_tag">
		                        油麵
		                    <span class="ingredient_cancel_icon">
		                            ×
		                    </span>
		                </span>
		        	</div>
	            </div>
	            <div class="btn_margin">
	                <input type="submit" name="action" value="上一頁">
	                <input type="submit" name="action" value="新增">
	                <input type="submit" name="action" value="確認">
	            </div>
	        </div>
	        <div class="insert_content_right col-6 col-lg-6">
	            <div class="dish_table">
	                <table class="insert_dish_list">
	                    <tr>
	                        <th>菜名</th>
	                        <th>食材</th>
	                    </tr>
	                    <tr>
	                        <td>昱瑋炒麵</td>
	                        <td><span>油麵,</span><span>豬肉</span></td>
	                    </tr>
	                </table>
	            </div>
	        </div>
	    </main>
    </form>
    <footer>
		<%@ include file="/common/footer.jsp"%>
	</footer>
	<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.6.0.min.js"></script>
</body>

</html>