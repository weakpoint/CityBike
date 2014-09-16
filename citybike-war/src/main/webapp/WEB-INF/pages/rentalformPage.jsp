<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>Wypozyczenie</title>
<link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
</head>
<body>
	<header>
		<%@include file="header.jsp"%>
	</header>
	<section class="center" id="content">
		<header>
			<h1>Nowe wypożyczenie</h1>
		</header>

		<form:form method="POST" commandName="newRental" action="/rentBike">
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
					<td><form:select path="bikeKey">
							<form:options items="${bikeMap}"></form:options>
						</form:select>
						</td>
					<td><form:errors path="bikeKey" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Kod wypożyczalni:</td>
					<td><form:select path="rentalOfficeKey">
							<form:options items="${rentalOfficeMap}"></form:options>
						</form:select>
						</td>
					<td><form:errors path="rentalOfficeKey" cssClass="error" /></td>
				</tr>
				<tr>
					<td colspan="3"><input type="submit" value="Wypożycz" /></td>

				</tr>
			</table>
		</form:form>

	</section>
	<footer>
		<%@include file="footer.jsp"%>
	</footer>
</body>
</html>