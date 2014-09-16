<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Statystyki</title>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript" charset="utf-8">
	google.load('visualization', '1.0', {
		'packages' : [ 'corechart' ]
	});


	function drawChart(columnData) {

		var response = JSON.parse(columnData);

		if (response['p'].error === undefined) {
			var data = new google.visualization.DataTable(columnData);
			var title = JSON.parse(columnData)['p'].title;

			var options = {
				'title' : title,
				'width' : 400,
				'height' : 400
			};

		/*	var chart = new google.visualization.PieChart(document
					.getElementById('chart_div'));
		*/	
			var chart = new google.visualization.ColumnChart(document
					.getElementById('chart_div'));
			
			chart.draw(data, options);
		} else {
			document.getElementById('chart_div').innerHTML = "<span style='color: #ff0000;'>"
					+ response['p'].error + "</span>";
		}
	}

	function loadChartData() {
		var chartDataReq = new XMLHttpRequest();
		chartDataReq.onreadystatechange = function() {
			if (chartDataReq.readyState == 4 && chartDataReq.status == 200) {
				drawChart(chartDataReq.responseText);
			}
		}

		var operationVal = document.getElementById("operation").value;
		var timeIntervalVal = document.getElementById("timeInterval").value;
		var startIntervalVal = document.getElementById("startInterval").value;
		var endIntervalVal = document.getElementById("endInterval").value;

		console.log(startIntervalVal + " " + endIntervalVal);
		chartDataReq.open("POST", "/statistics.do", true);
		chartDataReq.setRequestHeader("Content-Type",
				"application/json;charset=UTF-8");
		chartDataReq.send(JSON.stringify({
			operation : operationVal,
			timeInterval : timeIntervalVal,
			startInterval : startIntervalVal,
			endInterval : endIntervalVal
		}));
	}
</script>
<link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
</head>
<body>
	<header>
		<%@include file="header.jsp"%>
	</header>
	<section class="center" id="content">
		<br /> 
		<table style="float:left">
			<tr>
				<td>Operacja:</td>
				<td><form:select path="operation" id="operation">
						<form:options items="${operation}"></form:options>
					</form:select></td>
			</tr>
			<tr>
				<td>Wielkość przedziału czasu:</td>
				<td><form:select path="timeInterval" id="timeInterval">
						<form:options items="${timeInterval}"></form:options>
					</form:select></td>
			</tr>
			<tr>
				<td><span>Początek przedziału: (YYYY-MM-DD)</span></td>
				<td><input type="date" id="startInterval"
					pattern="^(19|20)\d\d-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$"></td>
			</tr>
			<tr>
				<td><span>Koniec przedziału: (YYYY-MM-DD)</span></td>
				<td><input type="date" id="endInterval"
					pattern="^(19|20)\d\d-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$"></td>
			</tr>
			<tr>
				<td></td>
				<td><button id="generateBtn" onclick="loadChartData();">Wyświetl
						wykres</button></td>
			</tr>

		</table>

		<div id="chart_div" class="map"></div>
	</section>
	<footer>
		<%@include file="footer.jsp"%>
	</footer>
</body>
</html>