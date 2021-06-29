<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="java.util.*"%>
<%@ page import="com.accountinfo.model.*"%>
<%@ page import="com.recipe.model.*"%>
<%@ page import="com.cuisinecategory.model.*"%>
<%@ page import="com.ingredient.model.*"%>
<%@ page import="com.unit.model.*"%>
<%@ page import="com.recipecuisinecategory.model.*"%>
<%@ page import="com.recipeingredientunit.model.*"%>
<%@ page import="com.recipestep.model.*"%>
<%@ page import="com.thumbsuprecipe.model.*"%>
<%@ page import="com.favoriterecipe.model.*"%>

<jsp:useBean id="accountSvc" scope="page" class="com.accountinfo.model.AccountInfoService" />
<jsp:useBean id="recipeSvc" scope="page" class="com.recipe.model.RecipeService" />
<jsp:useBean id="categorySvc" scope="page" class="com.cuisinecategory.model.CuisineCategoryService" />
<jsp:useBean id="ingredientSvc" scope="page" class="com.ingredient.model.IngredientService" />
<jsp:useBean id="unitSvc" scope="page" class="com.unit.model.UnitService" />

<jsp:useBean id="reicpeCatSvc" scope="page" class="com.recipecuisinecategory.model.RecipeCuisineCategoryService" />
<jsp:useBean id="recipeIngUnitSvc" scope="page" class="com.recipeingredientunit.model.RecipeIngredientUnitService" />
<jsp:useBean id="recipeStepSvc" scope="page" class="com.recipestep.model.RecipeStepService" />

<jsp:useBean id="thmupRecipeSvc" scope="page" class="com.thumbsuprecipe.model.ThumbsupRecipeService" />
<jsp:useBean id="favRecipeSvc" scope="page" class="com.favoriterecipe.model.FavoriteRecipeService" />
<!-- 基本上意思和java區塊new、放進Page scope是一樣的 -->