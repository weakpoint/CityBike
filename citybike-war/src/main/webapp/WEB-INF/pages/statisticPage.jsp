<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Statystyki</title>
 <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
   
      // Load the Visualization API and the piechart package.
      google.load('visualization', '1.0', {'packages':['corechart']});
     
      // Set a callback to run when the Google Visualization API is loaded.
     // google.setOnLoadCallback(drawChart);


      // Callback that creates and populates a data table, 
      // instantiates the pie chart, passes in the data and
      // draws it.
      function drawChart(columnData) {

      // Create the data table.
      console.log(columnData);
      var data = new google.visualization.DataTable(columnData);
		var title = "TTTTTTTTTTTTTTTTTTTTTTTTTTTTTT"
      // Set chart options
      var options = {'title': title,
                     'width':400,
                     'height':300};

      // Instantiate and draw our chart, passing in some options.
      var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
      chart.draw(data, options);
    }

      function loadChartData(){
var chartDataReq = new XMLHttpRequest();
chartDataReq.onreadystatechange=function()
{
if (chartDataReq.readyState==4 && chartDataReq.status==200)
  {
	drawChart(chartDataReq.responseText);
  }
}

var operationVal = document.getElementById("operation").value;
var timeIntervalVal = document.getElementById("timeInterval").value;
var startIntervalVal = document.getElementById("startInterval").value;
var endIntervalVal = document.getElementById("endInterval").value; 

console.log(startIntervalVal + " "+endIntervalVal);
chartDataReq.open("POST","/statistics.do",true);
chartDataReq.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
chartDataReq.send(JSON.stringify({operation:operationVal, timeInterval:timeIntervalVal, startInterval:startIntervalVal, endInterval:endIntervalVal}));
}
	</script>
</head>
<body>
	Operacja:
	<form:select path="operation" id="operation">
		<form:options items="${operation}"></form:options>
	</form:select>
	<br /> Wielkość przedziału czasu:
	<form:select path="timeInterval" id="timeInterval">
		<form:options items="${timeInterval}"></form:options>
	</form:select>
	<br /> Początek przedziału:
	<input type="date" id="startInterval">
	<br /> Koniec przedziału:
	<input type="date" id="endInterval">
	<br />
	<button id="generateBtn" onclick="loadChartData()">Wyświetl wykres</button>
	<div id="chart_div"></div>
</body>
</html>