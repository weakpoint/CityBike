<%@ page language="java" contentType="text/html; charset=ISO-8859-2"
    pageEncoding="ISO-8859-2"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE HTML>
<html>
<head>
<script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDY0kkJiTPVd2U7aTOAwhc9ySH6oHxOIYM&sensor=false">
</script>
<script>
function initialize()
{
var mapProp = {
  center:new google.maps.LatLng(52.508742,21.120850),
  zoom:5,
  mapTypeId:google.maps.MapTypeId.ROADMAP
  };
var map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
}

google.maps.event.addDomListener(window, 'load', initialize);
</script>

<meta charset="ISO-8859-2">
<title>Nowa wypozyczalnia</title>
</head>
<body>
<h2>Dane wypozyczalni</h2>
<form:form method="POST" commandName="newRentalOffice">
		<form:errors path="*" cssClass="errorblock" element="div" />
				<table>

<tr>
<td><h2>Adres</h2></td>
</tr>
			<tr>
				<td>Ulica :</td>
				<td><form:input path="address.street" />
				</td>
				<td><form:errors path="address.street" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td>Numer domu :</td>
				<td><form:input path="address.houseNumber" />
				</td>
				<td><form:errors path="address.houseNumber" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td>Numer mieszkania :</td>
				<td><form:input path="address.flatNumber" />
				</td>
				<td><form:errors path="address.flatNumber" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td>Kod pocztowy :</td>
				<td><form:input path="address.postalCode" />
				</td>
				<td><form:errors path="address.postalCode" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td>Miasto :</td>
				<td><form:input path="address.city" />
				</td>
				<td><form:errors path="address.city" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td>Dlugosc geograficzna</td>
				<td><form:input path="longitude"/></td>		
			</tr>
			<tr>
				<td>Szerokosc geograficzna</td>
				<td><form:input path="latitude"/></td>		
			
			<td><input type="button" value="Pobierz z mapy"/></td>
			</tr>
			<tr>
				<td colspan="3"><input type="submit" value="OK"/></td>
				
			</tr>
			
		</table>
		
	</form:form>
	
<section> <!-- mapa -->
<div id="googleMap" style="width:500px;height:380px;"></div>
</section>

</body>
</html> 