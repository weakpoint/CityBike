<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML>
<html>
<head>
<link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
<title>Nowy rower</title>
</head>
<body>
	<header>
		<%@include file="header.jsp"%>
	</header>
	<section class="center" id="content">
	<section class="center_form">
	<h2>Dane Roweru</h2>
	<form:form method="POST" commandName="newBike" action="/admin/addNewBike">
		<table>
			<tr>
				<td>Nazwa producenta:</td>
				<td><form:input path="technicalDetails.name" /></td>
				<td><form:errors path="technicalDetails.name" cssClass="error" /></td>
			</tr>

			<tr>
				<td>Kod wypożyczalni:</td>
				<td><form:select path="rentalOfficeCode">
						<form:options items="${rentalOfficeCodeList}"></form:options>
					</form:select>
				<td><a href="/addNewRentalOffice">Nowa wypożyczalnia</a></td>
			</tr>

			<tr>
				<td colspan="3"><input type="submit" value="OK" /></td>

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
