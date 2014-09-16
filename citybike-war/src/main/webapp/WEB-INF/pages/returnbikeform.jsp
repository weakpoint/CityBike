<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>Zwrot roweru</title>
<link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
</head>
<body>
	<header>
		<%@include file="header.jsp"%>
	</header>
	<section class="center" id="content">
	<section class="center_form">
		<header>
			<h1>Zwrot roweru</h1>
		</header>

		<form:form method="POST" commandName="returnBike" action="/returnApporval">
			<table>
				<tr>
					<td>Imię :</td>
					<td><form:input path="name" /></td>
					<td><form:errors path="name" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Nazwisko :</td>
					<td><form:input path="lastName" /></td>
					<td><form:errors path="lastName" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Kod użytkownika:</td>
					<td><form:input path="userKey" /></td>
					<td><form:errors path="userKey" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Kod roweru:</td>
					<td><form:input path="bikeKey"/></td>
					<td><form:errors path="bikeKey" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Kod wypożyczalni:</td>
					<td><form:input path="rentalOfficeKey"/> </td>
					<td><form:errors path="rentalOfficeKey" cssClass="error" /></td>
				</tr>
				<tr>
					<td colspan="3"><input type="submit" value="Zwróć rower" /></td>

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