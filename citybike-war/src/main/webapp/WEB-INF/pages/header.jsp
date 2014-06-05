<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section>
	<a href="/"><img src="<c:url value="/resources/images/logo.jpg" />" alt="" /></img></a>
	<nav id="first-menu">
		<ul>
			<li>Informacje</li>
			<li>Kontakt</li>
			<c:if test="${currentUser == null}">
				<li><a href="/login.do">Logowanie</a></li>
				<li><a href="/registration">Rejestracja</a></li>
			</c:if>
			<c:if test="${currentUser != null}">
				<li><a href="/userInfo">${currentUser.name} ${currentUser.lastName}</a></li>
				<li><a href="/j_spring_security_logout">Wyloguj</a></li>
			</c:if>
		</ul>
	</nav>
	<br />
<nav id="second-menu">
<ul>
<c:if test="${currentUser.role == 'USER'}">
<li><a href="/rentalInformation">Dane wypożyczeń</a></li>
</c:if>
<c:if test="${currentUser.role == 'EMPLOYEE'}">
<li><a href="/rentBike">Nowe wypożyczenie</a></li>
<li><a href="/returnBike">Zwrot roweru</a></li>
<li><a href="/statistics">Statystyki</a></li>
</c:if>
<c:if test="${currentUser.role == 'ADMINISTRATOR'}">
<li><a href="/addNewBike">Dodaj rower</a></li>
<li><a href="/addNewEmployee">Dodaj pracownika</a></li>
<li><a href="/addNewRentalOffice">Dodaj wypożyczalnie</a></li>
<li><a href="/feeManager">Zarządzanie opłatami</a></li>
<li></li>
<li></li>
<li></li>
<li><a href="/statistics">Statystyki</a></li>
</c:if>
<c:if test="${currentUser.role == 'SUPERADMIN'}">
<li><a href="/addNewEmployee">Dodaj pracownika</a></li>
<li><a href="/addRentalNetwork">Dodaj nową sieć</a></li>
</c:if>
</ul>
</nav>
</section>

	