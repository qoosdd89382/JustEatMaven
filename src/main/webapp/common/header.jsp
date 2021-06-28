<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="top -scroll">
    <h1><a href="<%=request.getContextPath()%>">Just Eat 揪食<i style="margin-left:10px; color: white; width: 20px;" class="fas fa-utensils"></i></a>
    </h1>
    <nav class="navigator -tog">
        <ul>
            <li><a href="<%=request.getContextPath()%>" id="homepage_btn">首頁</a></li>
            <li><a href="<%=request.getContextPath()%>/Recipe/home.jsp" id="recipe_btn">食譜</a></li>
            <li><a href="#" id="party_btn">揪飯</a></li>
            <li><a href="#" id="shop_btn">商城</a></li>
            <li><a href="<%=request.getContextPath()%>/Account/accountInfo.do?action=gotoAccountLoginPage" class="select">會員中心</a></li>
<%--             <c:if test="${sessionScope.accountInfoVOLogin}"> --%>
            	<li><img class="profile_pic" src="<%=request.getContextPath()%>/img/test_profile.jpg" alt=""></li>
<%--             </c:if> --%>
        </ul>
    </nav>
    <button type="button" class="menu-btn">
        <i class="fas fa-bars"></i>
    </button>
</div>
