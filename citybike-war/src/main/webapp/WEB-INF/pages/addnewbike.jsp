<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE HTML>
<html>
<head>
<title>Nowy rower</title>
</head>
<body>
	<header>
		<%@include file="header.jsp"%>
	</header>
	<h2>Dane Roweru</h2>
	<form:form method="POST" commandName="newBike" action="/addNewBike">
		<form:errors path="*" cssClass="errorblock" element="div" />
		<table>
			<tr>
				<td>Nazwa producenta:</td>
				<td><form:input path="technicalDetails.name" /></td>
				<td><form:errors path="technicalDetails.name" cssClass="error" /></td>
			</tr>

			<tr>
				<td>Kod wypozyczalni</td>
				<td><form:select path="rentalOfficeCode">
						<form:options items="${rentalOfficeCodeList}"></form:options>
					</form:select>
				<td><a href="/addNewRentalOffice">Nowa wypozyczalnia</a></td>
			</tr>

			<tr>
				<td colspan="3"><input type="submit" value="OK" /></td>

			</tr>

		</table>

	</form:form>
	<footer>
		<%@include file="footer.jsp"%>
	</footer>
</body>
</html>
