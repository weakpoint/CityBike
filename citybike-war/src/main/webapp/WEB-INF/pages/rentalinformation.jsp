<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<script
	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDY0kkJiTPVd2U7aTOAwhc9ySH6oHxOIYM&sensor=false">	

	function initialize() {
		var mapProp = {
			center : new google.maps.LatLng(52.508742, 21.120850),
			zoom : 5,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
		var map = new google.maps.Map(document.getElementById("googleMap"),
				mapProp);
	}

	google.maps.event.addDomListener(window, 'load', initialize);
</script>

<title>Dane wypozyczen</title>
</head>
<body>
	<section>
		<!-- dane -->
		<section ${hideActualSection}>
			<header>
				<h1>Statystyki aktualnego wypożyczenia</h1>
			</header>
			<span>Czas:</span><span>${actualRentTime}</span><br /> <span>Koszt:</span><span>${actualRentCost}</span>
		</section>

		<section>
			<header>
				<h1>Ogólne statystyki wypożyczenia</h1>
			</header>
			<span>Czas:</span><span>${overallRentalTime}</span><br /> <span>Koszt:</span><span>${overallRentalCost}</span>
		</section>
	</section>

	<section>
		<!-- mapa -->
		<div id="googleMap" style="width: 500px; height: 380px;"></div>
	</section>
</body>
</html>
