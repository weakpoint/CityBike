<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		var map;
		var mapProp;
		var latlng;

		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(function(position) {
				lat = position.coords.latitude;
				lon = position.coords.longitude;

				latlng = new google.maps.LatLng(lat, lon);
				mapProp.center = latlng;
				map = new google.maps.Map(document.getElementById("googleMap"),
						mapProp);
			});

		}
		latlng = new google.maps.LatLng(lon, lat);
		mapProp = {
			center : latlng,
			zoom : 12,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
		map = new google.maps.Map(document.getElementById("googleMap"), mapProp);

	}

	google.maps.event.addDomListener(window, 'load', initialize);
</script>

<title>Dane wypożyczeń</title>
</head>
<body>
	<header>
		<%@include file="header.jsp"%>
	</header>
	<section>
		<!-- dane -->
		<section>
			<header>
				<h1>Statystyki aktualnego wypożyczenia</h1>
			</header>
			<c:if test="${actualRentTime > 0}">
				<span>Czas:</span>
				<span>${actualRentTime}</span>
				<br />
				<span>Koszt:</span>
				<span>${actualRentCost}</span>
			</c:if>

			<c:if test="${actualRentTime == 0}">
				<span>Brak aktualnych wypożyczeń</span>
				</c:if>
		</section>

		<section>
			<header>
				<h1>Ogólne statystyki wypożyczenia</h1>
			</header>
			<span>Czas:</span> <span>${overallRentalTime}</span> <br /> <span>Koszt:</span><span>${overallRentalCost}</span>
		</section>
	</section>

	<section>
		<h2>Znajdź najbliższą wypożyczalnie</h2>
		<!-- mapa -->
		<div id="googleMap" style="width: 500px; height: 380px;"></div>
	</section>
	<footer>
		<%@include file="footer.jsp"%>
	</footer>
</body>
</html>
