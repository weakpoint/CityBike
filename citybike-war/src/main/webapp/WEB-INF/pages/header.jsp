<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section>
	<a href="/"><img src="<c:url value="/resources/images/logo.jpg" />"
		alt="" /></img></a>
	<nav id="first-menu">
		<ul>
			<li>Informacje</li>
			<li>Kontakt</li>
			<c:if test="${currentUser == null}">
				<li><a href="/login.do">Logowanie</a></li>
				<li><a href="/registration">Rejestracja</a></li>
			</c:if>
			<c:if test="${currentUser != null}">
				<li><a href="/userInfo">${currentUser.username}</a></li>
				<li><a href="/j_spring_security_logout">Wyloguj</a></li>
			</c:if>
		</ul>
	</nav>
	<br />
	<nav id="second-menu">
		<ul>
			<c:if test="${currentUser.authorities[0] == 'USER'}">
				<li><a href="/rentalInformation">Dane wypożyczeń</a></li>
				<li><a href="/statistics">Statystyki</a></li>
			</c:if>
			<c:if test="${currentUser.authorities[0] == 'ADMINISTRATOR'}">
				<li><a href="/admin/addNewBike">Dodaj rower</a></li>
				<li><a href="/admin/addNewRentalOffice">Dodaj wypożyczalnie</a></li>
				<li><a href="/admin/feeManager">Zarządzanie opłatami</a></li>
				<li><a href="/statistics">Statystyki</a></li>
			</c:if>
			
		</ul>
	</nav>
		<section><span style="color:red">${errorMessage}</span></section>
	<section><span style="color:green">${infoMessage}</span></section>
</section>

