<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.List, edu.citybike.model.Fee, edu.citybike.model.view.FeeManagerView" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Zarządzanie opłatami</title>
<script>
var newElementIndex = -1;
var addNewRow = function(){
	var table = document.getElementById("feeTable");
	  {
	  var row = table.insertRow(Math.abs(newElementIndex));
	  row.insertCell(0).innerHTML="<td><input type='checkbox' name='selected' value='"+(newElementIndex--)+"'/></td>";
	  row.insertCell(1).innerHTML="<input type'text' name='time'/>";
	  row.insertCell(2).innerHTML="<input type'text' name='fee'/>";
	  }
	}

</script>
</head>
<body>
<h2>Zarządzanie opłatami</h2>

<form:form method="post" action="/feeManager" modelAttribute="feeList">
<table id="feeTable">
<tr>
<th></th>
<th>Czas [min]</th>
<th>Koszt [zł]</th>
</tr>

<c:forEach items="${feeList}" var="fee" varStatus="i">
        <tr>
            <td align="center">${i.count}</td>
            <td><input type="checkbox" name="selected" value="${i.count-1}" ${fee.checked?"checked":""}/></td>
            <td><input name="time" value="${fee.fee.time}"/></td>
            <td><input name="fee" value="${fee.fee.fee}"/></td>
            
        </tr>
    </c:forEach>

<tr>
<td><button type="submit" name="submitbtn" value="add">Dodaj opłatę</button></td>
<td><button type="submit" name="submitbtn" value="save">Zapisz</button></td>
<td><button type="submit" name="submitbtn" value="delete">Usuń</button></td>
</tr>

</table>
</form:form>

</body>
</html>