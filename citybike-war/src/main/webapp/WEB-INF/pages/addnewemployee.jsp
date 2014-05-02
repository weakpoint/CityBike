<%@ page language="java" contentType="text/html; charset=ISO-8859-2"
	pageEncoding="ISO-8859-2"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-2">
<title>Dodawanie nowego pracownika</title>
</head>
<body>
	<h2>Dane pracownika</h2>
	<form:form method="POST" commandName="newEmployee"
		action="/addNewEmployee">
		<form:errors path="*" cssClass="errorblock" element="div" />
		<table>
			<tr>
				<td>Imie :</td>
				<td><form:input path="name" /></td>
				<td><form:errors path="name" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Nazwisko :</td>
				<td><form:input path="lastName" /></td>
				<td><form:errors path="lastName" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Numer telefonu :</td>
				<td><form:input path="phoneNumber" /></td>
				<td><form:errors path="phoneNumber" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Adres e-mail :</td>
				<td><form:input path="emailAddress" /></td>
				<td><form:errors path="emailAddress" cssClass="error" /></td>
			</tr>
			<tr>
				<td><h2>Adres</h2></td>
			</tr>
			<tr>
				<td>Ulica :</td>
				<td><form:input path="address.street" /></td>
				<td><form:errors path="address.street" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Numer domu :</td>
				<td><form:input path="address.houseNumber" /></td>
				<td><form:errors path="address.houseNumber" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td>Numer mieszkania :</td>
				<td><form:input path="address.flatNumber" /></td>
				<td><form:errors path="address.flatNumber" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td>Kod pocztowy :</td>
				<td><form:input path="address.postalCode" /></td>
				<td><form:errors path="address.postalCode" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td>Miasto :</td>
				<td><form:input path="address.city" /></td>
				<td><form:errors path="address.city" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Wyb�r stanowiska</td> trzeba dodac wpisywanie rentalnetworkcode i dodawanie admina dla superadmina - dadawanie pracownikow dla admina
				<td><form:select path="role">
						<form:options items="${employeeRoleList}"></form:options>
					</form:select></td>
			</tr>
			<tr>
				<td colspan="3"><input type="submit" value="OK" /></td>

			</tr>

		</table>

	</form:form>

</body>
</html>