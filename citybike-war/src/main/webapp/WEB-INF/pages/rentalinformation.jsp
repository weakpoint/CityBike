<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<script
	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyCIWfnxcYC4HngsPLlYsjocie6WfUmz2zc&sensor=false">
	
</script>
<script>
	var lat = 51.774632;
	var lon = 19.456177;
	var map;
	 var markers = [];
	
	function initialize() {
		
		var mapProp;
		var latlng;
		

		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(function(position) {
				lat = position.coords.latitude;
				lon = position.coords.longitude;

				latlng = new google.maps.LatLng(lat, lon);
				mapProp.center = latlng;
				map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
			});

		}
		latlng = new google.maps.LatLng(lat, lon);
		mapProp = {
			center : latlng,
			zoom : 12,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
		
		map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
		}


	// Add a marker to the map and push to the array.
	function addMarker(location) {
	  var marker = new google.maps.Marker({
	    position: location,
	    map: map
	  });
	  markers.push(marker);
	}

	// Sets the map on all markers in the array.
	function setAllMap(map) {
	  for (var i = 0; i < markers.length; i++) {
	    markers[i].setMap(map);
	  }
	}

	// Shows any markers currently in the array.
	function showMarkers() {
	  setAllMap(map);
	}
	
	 function add() {
		 markers = [];
		<%for(edu.citybike.model.view.Coordinates coord : (java.util.ArrayList<edu.citybike.model.view.Coordinates>) request.getAttribute("coordinates")){%>
	  	addMarker(new google.maps.LatLng(<%=coord.latitude%>, <%=coord.longitude%>));
	  <%}%>
	  setAllMap(map);
	  showMarkers();
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
				<span> min</span>
				<br />
				<span>Koszt:</span>
				<span>${actualRentCost}</span>
				<span> zł</span>
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
		<input type="button" onclick="add();" value="Pokaż wypożyczalnie"/>
	</section>
	<footer>
		<%@include file="footer.jsp"%>
	</footer>
</body>
</html>
