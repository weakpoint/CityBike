<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Dodawanie nowej sieci wypożyczalni</title>
</head>
<body>
	<h2>Dane nowej wypożyczalni</h2>
	<form:form method="POST" commandName="newRentalNetwork"
		action="/addRentalNetwork">
		<form:errors path="*" cssClass="errorblock" element="div" />
		<table>
			<tr>
				<td>Nazwa :</td>
				<td><form:input path="name" /></td>
				<td><form:errors path="name" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Kod sieci :</td>
				<td><form:input path="rentalNetworkCode" /></td>
				<td><form:errors path="rentalNetworkCode" cssClass="error" /></td>
			</tr>

			<tr>
				<td colspan="3"><input type="submit" value="OK" /></td>
			</tr>

		</table>

	</form:form>

</body>
</html>