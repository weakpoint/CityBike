<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE HTML>
<html>
<head>
<script
	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDY0kkJiTPVd2U7aTOAwhc9ySH6oHxOIYM&sensor=false">
</script>
<script>
	var lat = 19.456177;
	var lon = 51.774632;

	function initialize() {
		var latlng = new google.maps.LatLng(lon, lat);
		var mapProp = {
			center : latlng,
			zoom : 12,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
		var map = new google.maps.Map(document.getElementById("googleMap"),
				mapProp);
		var marker = new google.maps.Marker({
			position : latlng,
			map : map,
			title : 'Wyznacz położenie',
			draggable : true
		});
		google.maps.event.addListener(marker, 'dragend', function(a) {
			console.log(a.latLng);
			lon = a.latLng.A;
			lat = a.latLng.k;
		});
	}

	google.maps.event.addDomListener(window, 'load', initialize);

	function getCoordinates() {
		document.getElementById("longitudeField").value = lon;
		document.getElementById("latitudeField").value = lat;
	}
</script>
<title>Nowa wypożyczalnia</title>
</head>
<body>
	<header>
		<%@include file="header.jsp"%>
	</header>

	<h2>Dane wypożyczalni</h2>
	<form:form method="POST" commandName="newRentalOffice">
		<form:errors path="*" cssClass="errorblock" element="div" />
		<table>

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
				<td>Szerokość geograficzna</td>
				<td><form:input path="latitude" id="latitudeField" /></td>
			</tr>
			<tr>
				<td>Długość geograficzna</td>
				<td><form:input path="longitude" id="longitudeField" /></td>

				<td><input type="button" value="Pobierz z mapy"
					onclick="getCoordinates();" /></td>
			</tr>
			<tr>
				<td colspan="3"><input type="submit" value="OK" /></td>

			</tr>

		</table>

	</form:form>

	<section style="align: center">
		<!-- mapa -->
		<div id="googleMap" style="width: 500px; height: 380px;"></div>
	</section>
	<footer>
		<%@include file="footer.jsp"%>
	</footer>
</body>
</html>
