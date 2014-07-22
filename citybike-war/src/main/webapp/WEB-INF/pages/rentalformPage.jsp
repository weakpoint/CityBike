<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>Wypozyczenie</title>
</head>
<body>
	<header>
		<%@include file="header.jsp"%>
	</header>
	<section>
		<header>
			<h1>Nowe wypożyczenie</h1>
		</header>

		<form:form method="POST" commandName="newRental" action="/rentBike">
			<form:errors path="*" cssClass="errorblock" element="div" />
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
					<td>Kod uzytkownika:</td>
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
					<td>Kod wypozyczalni:</td>
					<td><form:select path="rentalOfficeKey">
							<form:options items="${rentalOfficeMap}"></form:options>
						</form:select>
						</td>
					<td><form:errors path="rentalOfficeKey" cssClass="error" /></td>
				</tr>
				<tr>
					<td colspan="3"><input type="submit" value="Wypozycz" /></td>

				</tr>
			</table>
		</form:form>

	</section>
	<footer>
		<%@include file="footer.jsp"%>
	</footer>
</body>
</html>