<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>System do zarządzania rowerem miejskim</title>

</head>
<body>
	<header>
		<%@include file="header.jsp"%>
	</header>
<span>${log }</span>
			<c:if test="${currentUser.authorities[0] == 'USER'}">
				<a href="/rentBike">Wypozycz rower</a><br />
			</c:if>
	
	<a href="/mail">Test maila</a>
	<a href="/facebookLogin.do">Test logowania</a>
	<a href="/startup">START UP!</a>
	<footer>
		<%@include file="footer.jsp"%>
	</footer>
</body>
</html>