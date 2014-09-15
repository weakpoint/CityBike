<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="edu.citybike.model.User"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
<title>Dane użytkownika</title>
</head>
<body>
	<header>
		<%@include file="header.jsp"%>
	</header>
	<h2>Dane użytkownika</h2>
<section>
	<form:form method="POST" commandName="userInfo" action="${formAction}">
		<table>
			<tr>
				<td>Imię :</td>
				<td><form:input path="name" /></td>
				<td><form:errors path="user.name" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Nazwisko :</td>
				<td><form:input path="lastName" /></td>
				<td><form:errors path="user.lastName" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Numer telefonu :</td>
				<td><form:input path="phoneNumber" /></td>
				<td><form:errors path="user.phoneNumber" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Adres e-mail (login):</td>
				<c:choose>
					<c:when test="${(formAction == '/register.do') && (userInfo.externalID == '')}">
						<td><form:input path="user.emailAddress" /></td>
					</c:when>
					<c:otherwise>
						<td><form:input path="user.emailAddress" readonly="true"/></td>
					</c:otherwise>
				</c:choose>
				<td><form:errors path="emailAddress" cssClass="error" /></td>
			</tr>
			<c:if test="${(currentUser == null) && (userInfo.externalID == '')}">
				<tr>
					<td>Hasło :</td>
					<td><form:input path="password"  type="password"/></td>
					<td><form:errors path="password" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Powtórz hasło :</td>
					<td><form:input path="repeatpassword" type="password"/></td>
					<td><form:errors path="repeatpassword" cssClass="error" /></td>
				</tr>
			</c:if>
			<tr>
				<td><h2>Dane adresowe</h2></td>
			</tr>
			<tr>
				<td>Ulica :</td>
				<td><form:input path="address.street" /></td>
				<td><form:errors path="user.address.street" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Numer domu :</td>
				<td><form:input path="address.houseNumber" /></td>
				<td><form:errors path="user.address.houseNumber" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td>Numer mieszkania :</td>
				<td><form:input path="address.flatNumber" /></td>
				<td><form:errors path="user.address.flatNumber" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td>Kod pocztowy :</td>
				<td><form:input path="address.postalCode" /></td>
				<td><form:errors path="user.address.postalCode" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td>Miasto :</td>
				<td><form:input path="address.city" /></td>
				<td><form:errors path="user.address.city" cssClass="error" /></td>
			</tr>
			<tr>
			<td><form:input path="externalID" hidden="hidden" /></td>
			</tr>

			<tr>
				<td colspan="3"><input type="submit" value="OK" /></td>

			</tr>

		</table>

	</form:form>
	</section>
	<footer>
		<%@include file="footer.jsp"%>
	</footer>
</body>
</html>
