<%@page import="com.product.model.ProductVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%
ProductVO productVO = (ProductVO) request.getAttribute("ProductVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>���u��Ʒs�W - addEmp.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>



<h3>��Ʒs�W:</h3>


<FORM METHOD="post" ACTION="product.do" name="form1">
<table>


	<tr>
		<td>�ӫ~�W��</td>
		<td><input type="TEXT" name="producttext" size="45" 
			 value="<%= (productVO==null)? "ex:�f�X" : productVO.getProductText()%>" /></td>
	</tr>
	<tr>
		<td>�W��</td>
		<td><input type="TEXT" name="productunit" size="45"
			 value="<%= (productVO==null)? "ex:�@�]" : productVO.getProductUnit()%>" /></td>
	</tr>
	<tr>
		<td>���q</td>
		<td><input type="TEXT" name="productspecification" size="45"
			 value="<%= (productVO==null)? "ex:200���J" : productVO.getProductSpecification()%>" /></td>
	</tr>
	<tr>
		<td>���a</td>
		<td><input type="TEXT" name="sal" size="45"
			 value="<%= (productVO==null)? "ex:�V�n" : productVO.getProductOrigin()%>" /></td>
	</tr>
	<tr>
		<td>�O�s�覡</td>
		<td><input type="TEXT" name="comm" size="45"
			 value="<%= (productVO==null)? "�N��" : productVO.getProductStorageMethod()%>" /></td>
	</tr>



</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="�e�X�s�W"></FORM>
</body>



<!-- =========================================�H�U�� datetimepicker �������]�w========================================== -->

<% 

/* java.sql.Date hiredate = null;
  try {
	    hiredate = empVO.getHiredate();
   } catch (Exception e) {
	    hiredate = new java.sql.Date(System.currentTimeMillis());
   }
  
 */
%>




<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>


</html>