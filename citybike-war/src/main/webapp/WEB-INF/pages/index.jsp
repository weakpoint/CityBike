<%@ page language="java" contentType="text/html; charset=ISO-8859-2"
    pageEncoding="ISO-8859-2"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-2">
<title>System do zarządzania rowerem miejskim</title>
</head>
<body>
jakis header i stopka wspolna dla wszystkich stron <br />
jakies tlo

logowanie


<a href="/addNewBike">Dodaj rower</a>
<a href="/addNewEmployee">Dodaj pracownika</a>

<a href="/rentalInformation">O wypozyczeniu</a>
<a href="/rentBike">Wypozycz rower</a>
<a href="/returnBike">Oddawaj rowera!</a>
<br/>
<a href="/login.do">Logowanie</a>


<%
  java.util.Enumeration<String> sessEnum = request.getSession()
    .getAttributeNames();
  while (sessEnum.hasMoreElements()) {
    String s = sessEnum.nextElement();
    out.print(s);
    edu.citybike.model.User user = (edu.citybike.model.User) request.getSession().getAttribute(s);
    out.println("==" + user.getName());
    out.println("rentalNetworkCode => "+ user.getRentalNetworkCode());
%><br />
<%
  }
%>
</body>
</html>