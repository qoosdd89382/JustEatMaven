<%@page import="com.dish.model.DishService"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.BufferedInputStream"%>
<%@page import="com.ingredient.model.IngredientVO"%>
<%@page import="java.util.List"%>
<%@page import="com.ingredient.model.IngredientService"%>
<%@	page import="com.eventinfo.model.EventInfoVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	EventInfoVO eventInfoVO = (EventInfoVO) request.getAttribute("eventInfoVO");
	IngredientService ingSvc = new IngredientService();
	DishService dishSvc = new DishService();
	pageContext.setAttribute("ingSvc", ingSvc);
	pageContext.setAttribute("dishSvc", dishSvc);
	List<IngredientVO> list= ingSvc.getAll();
	request.setAttribute("AllIngredientVO", list);
// 	Part eventPic = request.getPart("eventPic");
	
	List<IngredientVO> ingredientVOs = (List<IngredientVO>) request.getAttribute("ingredientVOs"); 
	String ingredientID = request.getParameter("ingredientID");
	
	Integer[][] ingID = null;
	String[] dishName = null;
	String dishAndIngJson = request.getParameter("dishAndIngJson");

	String replaceDishAndIngJson = null;
	if(!dishAndIngJson.isEmpty()){
		replaceDishAndIngJson = dishAndIngJson.replaceAll("\"","&quot;");
		
// 		System.out.println(dishAndIngJson);
// 		JSONArray jsonArray = new JSONArray(dishAndIngJson);
// 		ingID = new Integer[jsonArray.length()][];
// 		dishName = new String[jsonArray.length()];
// 		for (int i = 0; i < jsonArray.length(); i++) {
// 			JSONObject jsonObj = jsonArray.getJSONObject(i);
// 			Integer[] tempIngID = new Integer[jsonObj.getJSONArray("IngID").length()];
// 			String[] tempIngIDStr = new String[jsonObj.getJSONArray("IngID").length()];
// 			for (int j = 0; j < jsonObj.getJSONArray("IngID").length(); j++) {
// 				tempIngID[j] = Integer.parseInt(jsonObj.getJSONArray("IngID").getString(j));
// 			}
// 			dishName[i] = jsonObj.getString("dishName");
// 			ingID[i] = tempIngID;
// 		}
	}
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>新增菜色</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/jquery-ui/css/jquery-ui.css">
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
			<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/Event/CreateEvent.jsp">建立活動</a></li>
			<li class="breadcrumb-item active" aria-current="page">新增菜色</li>
		</ol>
	</nav>
	<form method="post" action="<%=request.getContextPath()%>/Event/EventInfo.do" enctype="multipart/form-data">
		<div class="temp_data">
			<input type="hidden" name="eventID" value="${eventInfoVO.eventID}">
			<input type="hidden" name="choose_type" value="${eventInfoVO.groupType}">
			<input type="hidden" name="event_name" value="${eventInfoVO.eventName}">
			<input type="hidden" name="event_member" value="${eventInfoVO.eventCurrentCount}">
			<input type="hidden" name="event_start" value="${eventInfoVO.eventStartTime}">
			<input type="hidden" name="event_end" value="${eventInfoVO.eventEndTime}">
			<input type="hidden" name="event_reg_start" value="${eventInfoVO.eventRegistartionStartTime}">
			<input type="hidden" name="event_reg_end" value="${eventInfoVO.eventRegistartionEndTime}">
			<input type="hidden" name="city" value="${eventInfoVO.groupCity}">
			<input type="hidden" name="address" value="${eventInfoVO.groupAddress}">
			<input type="hidden" name="event_description" value="${eventInfoVO.eventDescription}">
			<input type="hidden" name="cuisineCatID" value="${cuisineCatID}">
<!-- 			<input type="hidden" name="dishName" value="" class="tempDishName"> -->
<%-- 			<input type="hidden" name='dishAndIngJson' value="${replaceDishAndIngJson}">	 --%>
		</div>
		<div class="title">
	    	<h2>新增菜色</h2>
	    </div>
	    <main class="insert_content col-11 col-lg-11 row">
	        <div class="insert_content_left col-6 col-lg-6">
	            <div class="title_separate">
	                菜色名稱
	            <label>
	                <input type="text" name="dish_name" value="" placeholder="請輸入菜名" class="dish_name">
	            </label>
	            </div>
	            <div class="title_separate">
	                食材		
	               	<div class="ingredient_zone">
		            	<div class="ingredient_name ui-widget">
		                	<input type="text" placeholder="食材名稱" id="ingredientInput">
		                </div>
			            <div class="ingAutoOutput">
			                <ul>
				                <c:if test="${not empty ingredientVOs}">
				                	<c:forEach var="ingVO" items="${ingredientVOs}">
				                		<li data-id="${ingVO.ingredientID}"><span>${ingSvc.getOneIngredient(ingVO.ingredientID).ingredientName}</span><i class='fas fa-times'></i></li>
				                	</c:forEach>
				                </c:if>
			                </ul>
			             	<input class="ingAutoInput" type="hidden" name="ingredientID" value="<%= (ingredientID==null)?"":ingredientID %>">
			        	</div>
		        	</div>
	            </div>
	            <div class="btn_margin">
	                <input type="button" class="dishInsert" name="" value="菜色新增">
	                <input type="submit" name="action" value="菜色確認" class="dishConfirm">
	            </div>
	        </div>
	        <div class="insert_content_right col-6 col-lg-6">
	            <div class="dish_table">
	                <table class="insert_dish_list">
	                    <tr>
	                        <th>菜名</th>
	                        <th>食材</th>
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
	<script src="<%=request.getContextPath()%>/vendors/jquery-ui/js/jquery-ui.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/datetimepicker/js/jquery.datetimepicker.full.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/common/js/footer.js"></script>
	<script>
	<%@ include file="/common/js/scriptFooter.page"%>
	//======================AutoComplete==================================
	$(function(){
		var ingredientArray = new Array();
		<%
			for(IngredientVO tempVO:ingSvc.getAll()){
		%>
			var ingObj = new Object();
			ingObj['id'] = <%=tempVO.getIngredientID() %>;
			ingObj['value'] = "<%=tempVO.getIngredientName() %>";
			ingredientArray.push(ingObj);
		<%		
			}
		%>

		var ingredientIDsEle = $('input[name="ingredientID"]').val().trim();
		if (ingredientIDsEle != "") {
			var ingredientIDs = ingredientIDsEle.split(" ");
			console.log(ingredientIDs);
			ingredientIDs.forEach(function(itm, ind, arr){
				ingredientArray.forEach(function(item, index, array){
		        	if (parseInt(ingredientIDs[ind]) == ingredientArray[index].id) {
		        		ingredientArray.splice(index, 1);
		        	}
		    	});
		    });
		}
		
		
		<%
		if(ingredientID !=null){
			String[] orgList = ingredientID.trim().split(" ");
			for(String one:orgList){
		%>
				var tempIng = <%= one %>;
				console.log(<%=one%>);
				if(tempIng!=""){
					ingredientArray.forEach(function(item,index,array){
						if(tempIng==array[index]['id']){
							array.splice(index,1);
						}
					});
				}
		<%
			}
		}
		%>	
		
		function putIngInID(id,name){
			var tempIngHTML = "<li data-id='"+id+"'><span>" +name + "</span><i class='fas fa-times'></i></li>";		
			$(".ingAutoOutput").find("ul").append(tempIngHTML);
			
			if($(".ingAutoInput").val()==""){
				$(".ingAutoInput").val(" "+id);
			}else{
				var tempIngStr = $(".ingAutoInput").val();
				tempIngStr += " "+id;
				$(".ingAutoInput").val(tempIngStr);
			}
		}

		$("#ingredientInput").on("keydown",function(event){
			if(event.keyCode === $.ui.keyCode.Enter){
				event.preventDefault();
			}
		}).autocomplete({
			minLength: 0,
			source: ingredientArray,
			select: function(event,ui){
				ingredientArray.forEach(function(item,index,array){
					if(ui.item.value == array[index]['value']){
						array.splice(index,1);
					}
				});
				putIngInID(ui.item.id , ui.item.value);
				$("#ingredientInput").val("");
				return false;
			}
		});
		
		$(".ingAutoOutput").on("click","svg",function(event){
			var selectID = $(this).parent("li").attr("data-id");
			var selectName =$(this).parent("li").find("span").html();
			
			var tempID = $(".ingAutoInput").val();
			var newID = tempID.replace(" "+selectID,"");
			
			$(".ingAutoInput").val(newID);
			
			var addBackIngObj = new Object();
			addBackIngObj['id'] = selectID;
			addBackIngObj['value'] = selectName;
			ingredientArray.push(addBackIngObj);
			
			$(this).closest("li").remove();
		});
		//======================================================
		$(".dishInsert").on("click",function(){
			var ingArray = new Array();
			$(".ingAutoOutput").find("li").each(function(index,element){
				var ingID = $(element).attr("data-id");
				var ingName = $(element).find("span").text();
				var ingObj = new Object();
				ingObj["id"] = ingID;
				ingObj["name"] = ingName;
				ingArray.push(ingObj);	
			});
			$(".ingAutoOutput").find("ul").html("");
			
			var insertConfirmIngInfo = "";
			for(var i =0;i<ingArray.length;i++){
				insertConfirmIngInfo += "<span data-id="+ingArray[i]["id"]+">"+ingArray[i]["name"]+" </span> ";
			}
			var insertConfirmDish = "<tr><td>"+ $(".dish_name").val() +"</td><td>"+ insertConfirmIngInfo+"</td></tr>"
			$(".insert_dish_list").append(insertConfirmDish); //新增到右邊的確認菜單 
			$(".dish_name").val("");
			insertConfirmIngInfo = new String();
			ingArray = [];
			
			ingredientArray = [];
		<%
			for(IngredientVO tempVO:ingSvc.getAll()){
		%>
			var ingObj = new Object();
			ingObj['id'] = <%=tempVO.getIngredientID() %>;
			ingObj['value'] = "<%=tempVO.getIngredientName() %>";
			ingredientArray.push(ingObj);
		<%		
			}
		%>
			
			$("#ingredientInput").autocomplete({
				minLength: 0,
				source: ingredientArray,
				select: function(event,ui){
					ingredientArray.forEach(function(item,index,array){
						if(ui.item.value == array[index]['value']){
							array.splice(index,1);
						}
					});
					putIngInID(ui.item.id , ui.item.value);
					$("#ingredientInput").val("");
					return false;
				}
			});
			
			$(this).attr("disabled",true);
		});
	});
	
	//========================================
	$(".dishConfirm").on("click",function(){
		var dishAndIngArray = new Array();
		$(".insert_dish_list").find("tr").each(function(index,element){
			if(index!=0){
				var dishAndIngObj = new Object();
				var idArray = new Array();
				dishAndIngObj["dishName"]=$(element).find("td").html();
				$(element).find("span").each(function(index,element){
					idArray.push($(element).attr("data-id"));
				});
				console.log(idArray);
				dishAndIngObj["IngID"] = idArray;
				dishAndIngArray.push(dishAndIngObj);
			}
		});
		var dishAndIngJson = JSON.stringify(dishAndIngArray);
		console.log(dishAndIngJson);
		$(".temp_data").append("<input type='hidden' name='dishAndIngJson' value='"+dishAndIngJson+"'>");
	});
	
	
	
	</script>
	
</body>

</html>