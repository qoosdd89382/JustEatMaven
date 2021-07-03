<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="footer_block">

    <div class="web_map col-sm-6">
        <h1><a href="<%=request.getContextPath()%>">Just Eat 揪食</a></h1>
        <p>104 台北市中山區南京東路三段219號4樓<br>
        Just Eat Project<br>
        Tibame TFA101 Team 3</p>
    </div>
    <div class="web_map col-sm-3">
        <h3>會員</h3>
        <ul>
            <li><a href="<%=request.getContextPath()%>/Account/accountInfo.do?action=gotoAccountLoginPage">${sessionScope.accountInfoVOLogin == null ? "登入" : "會員中心" }</a></li>
            <li><a href="<%=request.getContextPath()%>/Account/AccountRegister/AccountRegisterPage.jsp">${sessionScope.accountInfoVOLogin == null ? "註冊" : "" }</a></li>
        </ul>
    </div>
    <div class="web_map col-sm-3">
        <h3>服務</h3>
        <ul>
            <li><a href="<%=request.getContextPath()%>">首頁</a></li>
            <li><a href="<%=request.getContextPath()%>/Recipe/listAllRecipe.jsp">食譜</a></li>
            <li><a href="<%=request.getContextPath()%>/Event/EventList.jsp">揪飯</a></li>
        </ul>
    </div>
</div>
<div class="copyright">
    <span class="copyright_text">
        <span><a href="<%=request.getContextPath()%>">Just Eat 揪食 <i class="fas fa-utensils fa-lg"></i></a></span>
        <span><a href="<%= request.getContextPath() %>/Dashboard" target="_blank">後臺管理</a></span>
    </span>
</div>