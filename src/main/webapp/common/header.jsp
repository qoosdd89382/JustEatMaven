<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="top -scroll">
    <h1><a href="#">Just Eat 揪食<i style="margin-left:10px; color: white; width: 20px;" class="fas fa-utensils"></i></a>
    </h1>
    <nav class="navigator -tog">
        <ul>
            <li><a href="#" id="homepage_btn">首頁</a></li>
            <li><a href="<%=request.getContextPath()%>/Recipe/home.jsp" id="recipe_btn">食譜</a></li>
            <li><a href="#" id="party_btn">揪飯</a></li>
            <li><a href="#" id="shop_btn">商城</a></li>
            <!-- <li><a href="#" id="logbox_pop" class="select">Sign up / Log in</a></li> -->
            
<%-- 			<li><a href="<%=request.getContextPath()%>/Account/AccountLoginPage.jsp" id="account_btn" class="select">會員中心</a></li> --%>
            <li>
            	<form method="post" action="<%=request.getContextPath()%>/Account/accountInfo.do">
            		<input type="hidden" name="action" value="gotoAccountLoginPage">
					<input type="submit" value="會員中心">		
            	</form>
            </li>
            
            <li><img class="profile_pic" src="<%=request.getContextPath()%>/img/test_profile.jpg" alt=""></li>
        </ul>
    </nav>
    <button type="button" class="menu-btn">
        <i class="fas fa-bars"></i>
    </button>
</div>
