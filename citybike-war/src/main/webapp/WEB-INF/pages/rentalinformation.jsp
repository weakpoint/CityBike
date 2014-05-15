<%@ page language="java" contentType="text/html; charset=ISO-8859-2"
	pageEncoding="ISO-8859-2"%>
<!DOCTYPE HTML>
<html>
<head>
<script
	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDY0kkJiTPVd2U7aTOAwhc9ySH6oHxOIYM&sensor=false">
	
</script>
<script>
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
		to sie powinno schowac jak nie ma zadnych wypozyczonych aktualnie
		<section>
			<header>
				<h1>Statystyki aktualnego wypo¿yczenia</h1>
			</header>
			<span>Czas:</span><span>${actualRentTime}</span></br> <span>Koszt:</span><span>${actualRentCost}</span>
		</section>

		<section>
			<header>
				<h1>Ogólne statystyki wypozyczenia</h1>
			</header>
			<span>Czas:</span><span>${overallRentalTime}</span></br> <span>Koszt:</span><span>${overallRentalCost}</span>
		</section>
	</section>

	<section>
		<!-- mapa -->
		<div id="googleMap" style="width: 500px; height: 380px;"></div>
	</section>
</body>
</html>
