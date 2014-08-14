<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Zaloguj się</title>
<style>
.body {
	position: relative;
}
.error {
	color: #ff0000;
}
</style>
</head>
<body>
	<header>
		<%@include file="header.jsp"%>
	</header>
	<section>

	<form method="POST" action="/j_spring_security_check">
    <table>
        <tbody>
            <tr>
                <td>Login (E-mail):</td>
                <td><input type="email" name="j_username" value="admin@admin.pl" required/></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="password" name="j_password" value="admin" required/></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Zaloguj" /></td>
            </tr>
        </tbody>
    </table>
</form>
	</section>
	<div id="verticalLine" style="height: 500px; border-left: thick solid #ff0000;"></div>
	<section>
	<!-- external login -->
	
	</section>
	<footer>
		<%@include file="footer.jsp"%>
	</footer>
</body>
</html>