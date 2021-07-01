<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>


<FORM METHOD="post" ACTION="addcuisine.do" name="form1">
<table>
	<tr>
		<td>料理分類名稱:</td>
		<td><input type="TEXT" name="categoryName" size="45" 
			  /></td>
	</tr>
	

</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>



</body>
</html>