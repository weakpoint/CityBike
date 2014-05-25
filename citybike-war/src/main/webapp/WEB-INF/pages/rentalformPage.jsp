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

		<form:form method="POST" commandName="newRental">
			<form:errors path="*" cssClass="errorblock" element="div" />
			<table>
				<tr>
					<td>Kod uzytkownika:</td>
					<td><form:input path="userCode" /></td>
					<td><button type="button">Pokaz profil</button></td> popup z userem
					<td><form:errors path="userCode" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Kod roweru:</td>
					<td><form:input path="bikeCode" /></td>
					<td><button type="button">Pokaz dane</button></td>
					<td><form:errors path="bikeCode" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Kod wypozyczalni</td>
					<td><form:select path="rentalOfficeCode">
							<form:options items="${rentalOfficeCodeList}"></form:options>
						</form:select>
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