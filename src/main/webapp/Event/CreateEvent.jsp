<%@page import="com.accountinfo.model.AccountInfoVO"%>
<%@page import="com.cuisinecategory.model.CuisineCategoryService"%>
<%@page import="com.cuisinecategory.model.CuisineCategoryVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@	page import="com.eventinfo.model.EventInfoVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	EventInfoVO eventInfoVO = (EventInfoVO) request.getAttribute("eventInfoVO");
	CuisineCategoryService cuisineCatSvc = new CuisineCategoryService();
	pageContext.setAttribute("cuisineCatSvc",cuisineCatSvc);
	String cuisineCatID = request.getParameter("cuisineCatID");
	List<CuisineCategoryVO> cuisineCategoryVOs = (List<CuisineCategoryVO>) request.getAttribute("cuisineCategoryVOs");

	if(eventInfoVO!=null){
		if(eventInfoVO.getEventStartTime()!=null){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String formatStartDateTime = df.format(eventInfoVO.getEventStartTime());
			pageContext.setAttribute("formatStartDateTime", formatStartDateTime);
		}
		if(eventInfoVO.getEventEndTime()!=null){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String formatEndDateTime = df.format(eventInfoVO.getEventEndTime());
			pageContext.setAttribute("formatEndDateTime", formatEndDateTime);
		}	
		if(eventInfoVO.getEventRegistartionStartTime()!=null){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String formatRegStartDateTime = df.format(eventInfoVO.getEventRegistartionStartTime());
			pageContext.setAttribute("formatRegStartDateTime", formatRegStartDateTime);
		}
		if(eventInfoVO.getEventRegistartionEndTime()!=null){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String formatRegEndDateTime = df.format(eventInfoVO.getEventRegistartionEndTime());
			pageContext.setAttribute("formatRegEndDateTime", formatRegEndDateTime);
		}
	}
	request.getAttribute("errorMsgs");
	
	String dishAndIngJson = request.getParameter("dishAndIngJson");
	JSONObject jsonObject =null;
	JSONArray  jsonArray = null;
	if(dishAndIngJson ==null || dishAndIngJson.length()==2 || dishAndIngJson.isEmpty()){
		
	}else{
		jsonArray = new JSONArray(dishAndIngJson);
		jsonObject = jsonArray.getJSONObject(0);
	}
	String replaceDishAndIngJson = null;
	if(dishAndIngJson!=null){
		replaceDishAndIngJson = dishAndIngJson.replaceAll("\"","&quot;");
	}
	
	AccountInfoVO accountInfoVO = (AccountInfoVO)session.getAttribute("accountInfoVOLogin");
	pageContext.setAttribute("accountInfoVO", accountInfoVO);
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>建立活動</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/jquery-ui/css/jquery-ui.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/datetimepicker/css/jquery.datetimepicker.css">
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
			<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/index.jsp">首頁</a></li>
			<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/Event/EventList.jsp">活動列表</a></li>
			<li class="breadcrumb-item active" aria-current="page">建立活動</li>
		</ol>
	</nav>
	
	    <div class="title">
	        <h2>建立活動</h2>
	    </div>
	   	<form method="post" action="<%= request.getContextPath()%>/Event/EventInfo.do" enctype="multipart/form-data">
	   	<div class="temp_data">
	   		<input type="hidden" name="dishAndIngJson" value="<%=replaceDishAndIngJson==null?"":replaceDishAndIngJson%>">
	   		<input type="hidden" name="accountID" value="${accountInfoVO.accountID}">
	   	</div>
	    <div class="event_content col-12 col-lg-12 row">
	        <div class="info col-6 col-lg-6">
	        <span class="error">${errorMsgs.get("dishAndIngredientIsNull")} </span>
	        <span class="error">${errorMsgs.get("eventPicError")} </span>
	        <span class="error">${errorMsgs.get("accountIDIsNull")} </span>
	            <div class="title_separate form-group">
	                請選擇揪團類型
	                <label>
	                    <input type="radio" name="choose_type" value="1" <%= (eventInfoVO==null)?"":(eventInfoVO.getGroupType()==null)?"":(eventInfoVO.getGroupType()==1)?"checked":"" %>>一人一菜
<%-- 	                    <input type="radio" name="choose_type" value="2" <%= (eventInfoVO==null)?"":(eventInfoVO.getGroupType()==null)?"":(eventInfoVO.getGroupType()==2)?"checked":"" %>>我當主廚 --%>
	                </label>
					<span class="error">${errorMsgs.get("GroupTypeIsNull")}</span>
	            </div>
	            <div class="form-group">
		活動名稱:
	                <input type="text" name="event_name" value="<%=(eventInfoVO==null)?"":(eventInfoVO.getEventName()==null)?"":(eventInfoVO.getEventName()) %>" placeholder="請輸入活動名稱" class="form-control">
	                活動人數:
	                <input type="number" name="event_member" value="<%=(eventInfoVO==null)?"":(eventInfoVO.getEventCurrentCount()==null)?"":(eventInfoVO.getEventCurrentCount()) %>" placeholder="請輸入活動人數" class="form-control">
	            	<div>
	            	<span class="error">${errorMsgs.get("EventNameIsNull")}</span><pre></pre><span class="error">${errorMsgs.get("EventMemberIsNull")}${errorMsgs.get("EventMemberMustBeGreaterThanZero")}</span>
	            	</div>
	            </div>
	            <div class="form-group">
	                活動報名開始時間:
	                <input type="text" name="event_reg_start" id="eventRegStart" value="<%=(eventInfoVO==null)?"":(eventInfoVO.getEventRegistartionStartTime()==null)?"":(pageContext.getAttribute("formatRegStartDateTime"))%>" class="form-control">
	            	<span class="error">${errorMsgs.get("EventRegStartTimeIsNull")} ${errorMsgs.get("EventRegStartTimeNotConform")}</span>
	            </div>
	            <div class="form-group">
	                活動報名結束時間:
	                <input type="text" name="event_reg_end" id="eventRegEnd" value="<%=(eventInfoVO==null)?"":(eventInfoVO.getEventRegistartionEndTime()==null)?"":(pageContext.getAttribute("formatRegEndDateTime"))%>" class="form-control">
	            	<span class="error">${errorMsgs.get("EventRegEndTimeIsNull")} ${errorMsgs.get("EventRegEndTimeNotConform")} ${errorMsgs.get("RegEndMustAfterRegStart")}</span>
	            </div>
	            <div class="form-group">
	                活動開始時間:
	                <input type="text" name="event_start" id="eventStart" value="<%=(eventInfoVO==null)?"":(eventInfoVO.getEventStartTime()==null)?"":(pageContext.getAttribute("formatStartDateTime")) %>" class="form-control">
	                <span class="error">${errorMsgs.get("EventStartTimeIsNull")} ${errorMsgs.get("EventStartTimeNotConform")} ${errorMsgs.get("StartMustAfterRegStart")}</span>
	            </div>
	            <div class="form-group">
	                活動結束時間:
	                <input type="text" name="event_end" id="eventEnd" value="<%=(eventInfoVO==null)?"":(eventInfoVO.getEventEndTime()==null)?"":(pageContext.getAttribute("formatEndDateTime"))%>" class="form-control">
	            	<span class="error">${errorMsgs.get("EventEndTimeIsNull")} ${errorMsgs.get("EventEndTimeNotConform")} ${errorMsgs.get("EndMustAfterStart")}</span>
	            </div>
	            <div class="form-group">
	            地址:
	                <select name="city" class="form-control">
	                <!--北台灣-->
	                <option value="基隆市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("基隆市")?"selected":"" %>>基隆市</option>
	                <option value="新北市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("新北市")?"selected":"" %>>新北市</option>
	                <option value="台北市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("台北市")?"selected":"" %>>台北市</option>
	                <option value="桃園市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("桃園市")?"selected":"" %>>桃園市</option>
	                <option value="宜蘭縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("宜蘭市")?"selected":"" %>>宜蘭縣</option>
	                <option value="新竹縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("新竹縣")?"selected":"" %>>新竹縣</option>
	                <option value="新竹市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("新竹市")?"selected":"" %>>新竹市</option>
	                <!--中台灣-->
	                <option value="苗栗縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("苗栗市")?"selected":"" %>>苗栗縣</option>
	                <option value="台中市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("台中市")?"selected":"" %>>台中市</option>
	                <option value="彰化縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("彰化市")?"selected":"" %>>彰化縣</option>
	                <option value="南投縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("南投市")?"selected":"" %>>南投縣</option>
	                <option value="雲林縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("雲林市")?"selected":"" %>>雲林縣</option>
	                <!--南台灣-->
	                <option value="嘉義縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("嘉義縣")?"selected":"" %>>嘉義縣</option>
	                <option value="嘉義市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("嘉義市")?"selected":"" %>>嘉義市</option>
	                <option value="台南市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("台南市")?"selected":"" %>>台南市</option>
	                <option value="高雄市" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("高雄市")?"selected":"" %>>高雄市</option>
	                <option value="屏東縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("屏東市")?"selected":"" %>>屏東縣</option>
	                <!--東台灣-->
	                <option value="花蓮縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("花蓮縣")?"selected":"" %>>花蓮縣</option>
	                <option value="台東縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("台東縣")?"selected":"" %>>台東縣</option>
	                <!--離島區域-->
	                <option value="澎湖縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("澎湖縣")?"selected":"" %>>澎湖縣</option>
	                <option value="金門縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("金門縣")?"selected":"" %>>金門縣</option>
	                <option value="連江縣" <%= (eventInfoVO==null)?"":eventInfoVO.getGroupCity().equals("連江縣")?"selected":"" %>>連江縣</option>
	            </select>
	            <input type="text" name="address" placeholder="請輸入地址" class="inputAddress form-control" value="<%=(eventInfoVO==null)?"":(eventInfoVO.getGroupAddress()==null)?"":(eventInfoVO.getGroupAddress()) %>">
	            <span class="error">${errorMsgs.get("GroupAddressIsNull")}</span>
	            </div>
	            <div class="form-group">
	                類型:		<input type="text" id="cuisineCatInput" class="form-control">
	                <span class="error">${errorMsgs.get("cuisineCatError")}</span>
	            </div>
	            <div class="cuisineCatAutoOutput">
			                <ul>
				                <c:if test="${not empty cuisineCategoryVOs}">
				                	<c:forEach var="cuisineCatVO" items="${cuisineCategoryVOs}">
				                		<li data-id="${cuisineCatVO.cuisineCategoryID}">
				                			<span>${cuisineCatSvc.getOneCategory(cuisineCatVO.cuisineCategoryID).cuisineCategoryName}</span>
				                			<i class="fas fa-times"></i>
				                		</li>
				                	</c:forEach>
				                </c:if>
			                </ul>
			          		<input class="cuisineCatAutoInput" type="hidden" name="cuisineCatID" value="<%= (cuisineCatID==null)?"":cuisineCatID %>">
			    </div>
			    <%
			    	if(dishAndIngJson==null || dishAndIngJson.length()==2 || dishAndIngJson.isEmpty()){
			    		
			    	}else {
			    		if(jsonObject!=null){
			    %>
				<div>
			    	我的菜色:
			    	<span class="border"><%=jsonObject.getString("dishName") %></span>
			    </div>
			    <%
			    		}
			    	}
			    %>
	            <div class="form-group">
	            	上傳活動圖片:<input type="file" name="eventPic" id="uploadEventImg" class="form-control-file">
	            </div>
	            <div id="preview_img">
	            </div>
	            <div>
	            	<%
	            		if(dishAndIngJson==null || dishAndIngJson.length()==2 || dishAndIngJson.isEmpty()){ 
	            	%>
	               			<input type="submit" name="action" value="新增菜色" class="insertDish btn btn-secondary">
	                <%
	            		}else{
	                %>	
	                		<input type="submit" name="action" value="重新新增菜色" class="insertDish btn btn-secondary">
	                <%
	            		}
	                %>
<!-- 	                <input type="submit" name="action" value="邀請好友"> -->
	                <input type="submit" name="action" value="取消建立" class="btn btn-secondary">
	                <input type="submit" name="action" value="確定建立" class="confirmCreate btn btn-secondary">
	            </div>
	        </div>
		    <div class="info col-6 col-lg-6">
		        <div class="event_description">
		            <textarea name="" id="description" cols="60" rows="20" placeholder="活動說明" class="form-control"><%=(eventInfoVO==null)?"":(eventInfoVO.getEventDescription()==null)?"":(eventInfoVO.getEventDescription()) %></textarea>
		            <input type="hidden" name="event_description" value="<%=(eventInfoVO==null)?"":(eventInfoVO.getEventDescription()==null)?"":(eventInfoVO.getEventDescription()) %>"/>
		        </div>
		    </div>
	    </div>
	    </form>
	<footer>
		<%@ include file="/common/footer.jsp"%>
	</footer>
	<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.6.0.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/jquery-ui/js/jquery-ui.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/datetimepicker/js/jquery.datetimepicker.full.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/footer.js"></script>
	<script>
	<%@ include file="/common/js/scriptFooter.page"%>
		 $.datetimepicker.setLocale('zh'); // kr ko ja en
	     $('#eventStart').datetimepicker({
	        theme: '',          //theme: 'dark',
	        timepicker: true,   //timepicker: false,
	        step: 15,            //step: 60 (這是timepicker的預設間隔60分鐘)
		    format: 'Y-m-d H:i',
		    minDate:'-1970-01-01'
	     });
	     $('#eventEnd').datetimepicker({
		        theme: '',          //theme: 'dark',
		        timepicker: true,   //timepicker: false,
		        step: 15,            //step: 60 (這是timepicker的預設間隔60分鐘)
			    format: 'Y-m-d H:i',
			    minDate:'-1970-01-01'
		 });
	     $('#eventRegStart').datetimepicker({
		        theme: '',          //theme: 'dark',
		        timepicker: true,   //timepicker: false,
		        step: 15,            //step: 60 (這是timepicker的預設間隔60分鐘)
			    format: 'Y-m-d H:i',
			    minDate:'-1970-01-01'
		 });
	     $('#eventRegEnd').datetimepicker({
		        theme: '',          //theme: 'dark',
		        timepicker: true,   //timepicker: false,
		        step: 15,            //step: 60 (這是timepicker的預設間隔60分鐘)
			    format: 'Y-m-d H:i',
			    minDate:'-1970-01-01'
		 });
	     
	     $(".confirmCreate").on("click",function(){
	    	 var content = $("#description").val();
	    	 console.log(content);
	    	 $("input[name=event_description]").val(content);
	     });
	     
	     $(".insertDish").on("click",function(){
	    	 var content = $("#description").val();
	    	 console.log(content);
	    	 $("input[name=event_description]").val(content);
	     });
	     
	     //=================圖片預覽==========================
	     $("#uploadEventImg").on("change", function() {
	            console.log(this.files);
	            var reader = new FileReader();
	            reader.readAsDataURL(this.files[0]);
	            $(reader).on("load", function() {
	            	$("#preview_img").html("");
	                $("#preview_img").append("<img src="+reader.result+">");
	            });
	        });
	     //==================AutoComplete====================
	   	$(function(){
		var cuisineCatArray = new Array();
		<%
			for(CuisineCategoryVO tempVO:cuisineCatSvc.getAll()){
		%>
			var cuiCatObj = new Object();
			cuiCatObj['id'] = <%=tempVO.getCuisineCategoryID() %>;
			cuiCatObj['value'] = "<%=tempVO.getCuisineCategoryName() %>";
			cuisineCatArray.push(cuiCatObj);
		<%		
			}
		%>
		
		//================類型儲存========================
		var cuisineCatIDsEle = $('input[name="cuisineCatID"]').val().trim();
		if (cuisineCatIDsEle != "") {
			var cuisineCatIDs = cuisineCatIDsEle.split(" ");
			console.log(cuisineCatIDs);
			cuisineCatIDs.forEach(function(itm, ind, arr){
				cuisineCatArray.forEach(function(item, index, array){
		        	if (parseInt(cuisineCatIDs[ind]) == cuisineCatArray[index].id) {
		        		cuisineCatArray.splice(index, 1);
		        	}
		    	});
		    });
		}
		
		function putCuisineCatInID(id,name){
			var tempCuisineCatHTML = "<li data-id='"+id+"'><span>" +name + "</span><i class='fas fa-times'></i></li>";		
			$(".cuisineCatAutoOutput").find("ul").append(tempCuisineCatHTML);
			
			if($(".cuisineCatAutoInput").val()==""){
				$(".cuisineCatAutoInput").val(" "+id);
			}else{
				var tempCuisineCatStr = $(".cuisineCatAutoInput").val();
				tempCuisineCatStr += " "+id;
				$(".cuisineCatAutoInput").val(tempCuisineCatStr);
			}
		}

		$("#cuisineCatInput").on("keydown",function(event){
			if(event.keyCode === $.ui.keyCode.Enter){
				event.preventDefault();
			}
		}).autocomplete({
			minLength: 0,
			source: cuisineCatArray,
			select: function(event,ui){
				cuisineCatArray.forEach(function(item,index,array){
					if(ui.item.value == array[index]['value']){
						array.splice(index,1);
					}
				});
				putCuisineCatInID(ui.item.id , ui.item.value);
				$("#cuisineCatInput").val("");
				return false;
			}
		});
		
		$(".cuisineCatAutoOutput").on("click","svg",function(event){
			var selectID = $(this).parent("li").attr("data-id");
			var selectName =$(this).parent("li").find("span").html();

			var tempID = $(".cuisineCatAutoInput").val();
			var newID = tempID.replace(" "+selectID,"");
			
			$(".cuisineCatAutoInput").val(newID);
			
			var addBackCuisineCatObj = new Object();
			addBackCuisineCatObj['id'] = selectID;
			addBackCuisineCatObj['value'] = selectName;
			cuisineCatArray.push(addBackCuisineCatObj);
			
			$(this).closest("li").remove();
		});
	});
		
		
	</script>
</body>
</html>