<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="java.util.*"%>
<%@ page import="com.recipe.model.*"%>
<%@ page import="com.cuisinecategory.model.*"%>
<%@page import="com.recipecuisinecategory.model.RecipeCuisineCategoryVO"%>


<jsp:useBean id="accountSrv" scope="page" class="com.accountinfo.model.AccountInfoService" />
<jsp:useBean id="categorySvc" scope="page" class="com.cuisinecategory.model.CuisineCategoryService" />
<jsp:useBean id="reicpeCatSvc" scope="page" class="com.recipecuisinecategory.model.RecipeCuisineCategoryService" />
<jsp:useBean id="ingredientSvc" scope="page" class="com.ingredient.model.IngredientService" />
<jsp:useBean id="unitSvc" scope="page" class="com.unit.model.UnitService" />

<%
	String cuisineCategoryID = request.getParameter("id");
	List<RecipeCuisineCategoryVO> recipeCatList = null;
	if (cuisineCategoryID != null) {
		recipeCatList = reicpeCatSvc.getAllByCuisineCategory(new Integer(cuisineCategoryID));
	}
	pageContext.setAttribute("recipeCatList", recipeCatList);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>