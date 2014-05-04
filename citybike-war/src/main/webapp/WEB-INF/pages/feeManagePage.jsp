<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.List, edu.citybike.model.Fee" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Zarządzanie opłatami</title>
</head>
<body>
<h2>Zarządzanie opłatami</h2>

<form action="/feeManager">
<table>
<tr>
<th></th>
<th>Czas [min]</th>
<th>Koszt [zł]</th>
</tr>
<% List<Fee> feeList = (List<Fee>) request.getAttribute("feeList");
for (Fee fee : feeList){ %>
<tr>
<td><input type="checkbox" name="selected"/></td>
<td><%= fee.getTime() %></td>
<td><%= fee.getFee() %></td>
<% } %>
</tr>


<tr><td><input name="submittype" type="submit" value="OK" /></td></tr>

</table>
</form>
</body>
</html>