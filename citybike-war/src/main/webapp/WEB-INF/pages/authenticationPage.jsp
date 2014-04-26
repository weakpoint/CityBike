<%@ page language="java" contentType="text/html; charset=ISO-8859-2"
    pageEncoding="ISO-8859-2"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-2">
<title>Zaloguj się</title>
</head>
<body>
logowanie zwykle<br/>
logowanie przez google<br/>
logowanie przez facebooka<br/>

nie mozna zmieniac adresu mailowego - weryfikacja z tokenu oauth2<br/>

<form:form method="POST" commandName="currentUser"
		action="/login.do">
		<input type="submit" value="OK" />
		</form:form>
</body>
</html>