<%@page import="java.sql.Timestamp"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.evaluatedmember.model.*"%>
<%@ page import="com.eventmember.model.*"%>
<%@ page import="com.accountinfo.model.*"%>

<jsp:useBean id="accountSvc" scope="page" class="com.accountinfo.model.AccountInfoService" />
<jsp:useBean id="evaluatedmemberSvc" scope="page" class="com.evaluatedmember.model.EvaluatedMemberService" />
<jsp:useBean id="eventMemberSvc" scope="page" class="com.eventmember.model.EventMemberService" />
<%	
	String eventID = request.getParameter("eventID");
	List<EvaluatedMemberVO> list = evaluatedmemberSvc.getAllByEventID(new Integer(eventID));
	if (list.size() == 0) {
		List<EventMemberVO> membetList = eventMemberSvc.getAllByEventID(new Integer(eventID));
		for (EventMemberVO memberVO : membetList) {
			for (int i = 0; i < membetList.size(); i++) {
				if (memberVO.getAccountID() != membetList.get(i).getAccountID()) {
					evaluatedmemberSvc.addEvaluatedMember(
						memberVO.getAccountID(), membetList.get(i).getAccountID(), 
						new Integer(eventID), 0);
				}
			}
		}
		list = evaluatedmemberSvc.getAllByEventID(new Integer(eventID));
	}
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
<style>

        /* ===== 重要性的星號 ===== */
        div.star_block > span.star {
            cursor: pointer;
            display: inline-block;
            margin-right: 3px;
        }

        div.star_block > span.star.-on, div.star_block > span.nonChangeStar.-on{
            color: yellow;
        }
</style>
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
				<c:if test="${not empty accountInfoVOLogin && accountInfoVOLogin.accountID != evaluatedmemberVO.accepterAccountID}">
					
					<c:if test="${evaluatedmemberVO.giveScore > 0}">
					<div class="star_block">
						<span class='nonChangeStar ${evaluatedmemberVO.giveScore >= 1 ? "-on" : ""}' data-star="1"><i class="fas fa-star"></i></span>
						<span class='nonChangeStar ${evaluatedmemberVO.giveScore >= 2 ? "-on" : ""}' data-star="2"><i class="fas fa-star"></i></span>
						<span class='nonChangeStar ${evaluatedmemberVO.giveScore >= 3 ? "-on" : ""}' data-star="3"><i class="fas fa-star"></i></span>
						<span class='nonChangeStar ${evaluatedmemberVO.giveScore >= 4 ? "-on" : ""}' data-star="4"><i class="fas fa-star"></i></span>
						<span class='nonChangeStar ${evaluatedmemberVO.giveScore >= 5 ? "-on" : ""}' data-star="5"><i class="fas fa-star"></i></span>	
					</div>
					</c:if>
					<c:if test="${evaluatedmemberVO.giveScore == 0}">
					<div class="star_block">
						<span class='star' data-star="1"><i class="fas fa-star"></i></span>
						<span class='star' data-star="2"><i class="fas fa-star"></i></span>
						<span class='star' data-star="3"><i class="fas fa-star"></i></span>
						<span class='star' data-star="4"><i class="fas fa-star"></i></span>
						<span class='star' data-star="5"><i class="fas fa-star"></i></span>	
					</div>
					<form action="<%= request.getContextPath() %>/Event/evaluatedMember.do"method="post">
						<input type="hidden" name="eventID" value="${param.eventID}">
						<input type="hidden" name="giverID" value="${accountInfoVOLogin.accountID}">
						<input type="hidden" name="accepterID" value="${evaluatedmemberVO.accepterAccountID}">
						<input type="hidden" name="score" value="0">
						<input type="hidden" name="action" value="giveScore">
						<button type="submit">送出評分</button>
					</form>
					</c:if>
				</c:if>
				</td>
			</tr> 
		</c:forEach>
	</table>
<!-- 	  <div class="btn_margin" align="center"  > -->
<!-- 	              <input type ="button" onclick="history.back()" value="確定送出"></input> -->
<!-- 	  </div> -->
			
			
	<footer>
		<%@ include file="/common/footer.jsp"%>
	</footer>
	<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.6.0.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/footer.js"></script>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
	<script>
	
			    $(function(){
			        $("div.star_block").on("click", "span.star", function () {
			            var star_count = $(this).attr("data-star");
			            $(this).addClass("-on");
			            $(this).prevAll().addClass("-on");
			            $(this).nextAll().removeClass("-on");
			            $(this).closest("li").attr("data-star", star_count);
			            $(this).parent().next().find('input[name="score"]').val(star_count);
			        });
			    });
	
		$("[name=position]").on("change",function(){
			if($(this).is(":checked")){
				$(".positionSubmit").submit();
			}
		});
	</script>
</body>
</html>