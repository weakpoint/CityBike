<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Zaloguj się</title>
</head>
<body>
	logowanie zwykle
	<br /> logowanie przez google
	<br /> logowanie przez facebooka
	<br /> nie mozna zmieniac adresu mailowego - weryfikacja z tokenu
	oauth2
	<br />
	<header>
		<%@include file="header.jsp"%>
	</header>
	<form:form method="POST" commandName="credentials" action="/login.do">
		<tr>
			<td>Adres e-mail :</td>
			<td><form:input path="emailAddress" /></td>
			<td><form:errors path="emailAddress" cssClass="error" /></td>
		</tr>
		<tr>
			<td>Hasło :</td>
			<td><form:input path="password" type="password"/></td>
			<td><form:errors path="password" cssClass="error" /></td>
		</tr>
		<input type="submit" value="OK" />
	</form:form>
	<footer>
		<%@include file="footer.jsp"%>
	</footer>
</body>
</html>