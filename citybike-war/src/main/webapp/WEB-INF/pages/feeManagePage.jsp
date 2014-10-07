<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page
	import="java.util.List, edu.citybike.model.Fee, edu.citybike.model.view.FeeManagerView"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Zarządzanie opłatami</title>
<link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
</head>
<body>
	<header>
		<%@include file="header.jsp"%>
	</header>
	<section class="center" id="content">
	<section class="center_form">
	<h2>Zarządzanie opłatami</h2>

	<form:form method="post" action="/admin/feeManager" modelAttribute="feeList">
		<table id="feeTable">
			<tr>
				<th></th>
				<th></th>
				<th>Czas [min]</th>
				<th>Koszt [zł]</th>
			</tr>

			<c:forEach items="${feeList}" var="fee" varStatus="i">
				<tr>
					<td align="center">${i.count}</td>
					<td><input type="checkbox" name="selected"
						value="${i.count-1}" ${fee.checked?"checked":""} /></td>
					<c:choose>
						<c:when test="${fee.newRow}">
							<td><input name="time" value="${fee.fee.time}" pattern="^[0-9]*$" /></td>
						</c:when>
						<c:otherwise>
							<td><input name="time" value="${fee.fee.time}" readonly /></td> <!-- immutable field -->
						</c:otherwise>
					</c:choose>

					<td><input name="fee" value="${fee.fee.fee}" pattern="^[0-9]+\.[0-9]+|[0-9]+$"/></td>

				</tr>
			</c:forEach>
		</table>
		<table>
			<tr>
				<td><button type="submit" name="submitbtn" value="add">Dodaj
						opłatę</button></td>
				<td><button type="submit" name="submitbtn" value="save">Zapisz</button></td>
				<td><button type="submit" name="submitbtn" value="delete">Usuń</button></td>
			</tr>

		</table>
	</form:form>
	</section>
	</section>
	<footer>
		<%@include file="footer.jsp"%>
	</footer>
</body>
</html>