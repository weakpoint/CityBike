<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
<!DOCTYPE html>
<html>
<head>
<link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>System do zarządzania rowerem miejskim</title>

</head>
<body>
	<header>
		<%@include file="header.jsp"%>
	</header>
	<section class="center">
		Projekt ten jest praktyczną częścią pracy magisterskiej<br /> <strong>System
			do zarządzania miejską wypożyczalnią rowerową wykorzystującą
			środowisko chmurowe Google App Engine</strong>
			<br /> Autorem jest Emil Płuciennikowski 
			<br />Numer indeksu 186200<br />
		    <br />Lato 2014
	</section>


	<a href="/startup">START UP!</a>
	<footer>
		<%@include file="footer.jsp"%>
	</footer>
</body>
</html>