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
	var lon = 19.456177;
	var lat = 51.774632;
	var geocoder;

	function initialize() {
		geocoder = new google.maps.Geocoder();

		var latlng = new google.maps.LatLng(lat, lon);
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
			console.log(a.latLng.B);
			lat = a.latLng.k;
			lon = a.latLng.B;
		});
	}

	google.maps.event.addDomListener(window, 'load', initialize);

	function getCoordinates() {
		document.getElementById("latitudeField").value = lat; //52.0
		document.getElementById("longitudeField").value = lon;//19.0
		
		geocoder.geocode({'latLng': new google.maps.LatLng(lat, lon)}, function(results, status) {
		      if (status == google.maps.GeocoderStatus.OK) {
		    	  if(results){
		    		  document.getElementById("housenumber").value = findFieldInResults(results, "street_number"); //types[0] == street_number
		    		  document.getElementById("street").value = findFieldInResults(results, "route"); //route
		    		  document.getElementById("city").value = findFieldInResults(results, "locality"); //locality
		    		  document.getElementById("postalcode").value = findFieldInResults(results, "postal_code"); //types[0] == postal_code  
		    	  }
		      } else {
		        alert("Geocoder failed due to: " + status);
		      }
		});
	}
	

	function findFieldInResults(results, name) {

		for (var r = 0; r < results.length; r++) {
			for (var i = 0; i < results[r].address_components.length; i++) {
				if (results[r].address_components[i].types[0] === name) {
					return results[r].address_components[i].short_name;
				}
			}
		}
		return "";
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
				<td><form:input path="address.street" id="street"/></td>
				<td><form:errors path="address.street" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Numer domu :</td>
				<td><form:input path="address.houseNumber" id="housenumber"/></td>
				<td><form:errors path="address.houseNumber" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td>Numer mieszkania :</td>
				<td><form:input path="address.flatNumber" id="flatnumber"/></td>
				<td><form:errors path="address.flatNumber" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td>Kod pocztowy :</td>
				<td><form:input path="address.postalCode" id="postalcode"/></td>
				<td><form:errors path="address.postalCode" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td>Miasto :</td>
				<td><form:input path="address.city" id="city"/></td>
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
