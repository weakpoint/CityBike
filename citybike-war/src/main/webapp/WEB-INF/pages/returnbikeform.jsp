<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE HTML>
<html>
<head>
<script src="http://code.jquery.com/jquery-2.1.0.min.js"></script>
<meta charset="utf-8">
<title>Formularz zwrotu</title>
</head>
<body>
	<header>
		<%@include file="header.jsp"%>
	</header>
	<!-- user code -->
	<section>
		<header>
			<h1>Zwrot roweru</h1>
		</header>
		<form id="userCodeForm" onsubmit="return userCodeValidator();">
			<span>Kod uzytkownika:</span> <input id="usercode-input" />
			<!-- <button type="button" onclick="window.open('/');">Wyszukaj</button> -->
			<button type="submit">Zatwierdź</button>
		</form>
	</section>

	<!-- active rent -->
	<section id="activeRentSection" hidden>
		<header>
			<h2>Aktywne wypozyczenia</h2>
		</header>

		<form id="activeRentForm" onsubmit="return activeRentFormValidator();">
			<table id="resp">
			</table>
			<button type="submit" id="activeRentBtn">Zwroc i rozlicz</button>
		</form>
	</section>

	<!-- summary -->
	<section id="summarySection" hidden>
		<header>
			<h2>Podsumowanie wypozyczenia</h2>
		</header>
		<span>Nalezna oplata:</span><span id="payment"></span><br /> <br />
		<form:form method="POST" action="/returnApporval">
			<input type="submit" value="Zatwierdz" />
			<button type="button" onclick="location.reload(true);">Odrzuć</button>
		</form:form>
	</section>

	<script>
		var userCodeValidator = function() {
			var userCode = $('#usercode-input').val();
			return (userCode !== "") && !isNaN(userCode);
		};

		var activeRentFormValidator = function() {
			var checkboxes = document.getElementsByName('hiredBike');
			var selected = "";
			for (var i = 0; i < checkboxes.length; i++) {
				if (checkboxes[i].checked) {
					selected += i + ",";
				}
			}

			if (selected == "") {
				alert("Wybierz które wypożyczenie ma być rozliczone!");
				return false;
			}
			return true;
		};

		$(document)
				.ready(
						function() {

							$('#userCodeForm')
									.submit(
											function(event) {

												var code = $('#usercode-input')
														.val();
												//if user code will be changed
												document
														.getElementById("activeRentSection").hidden = true;
												document
														.getElementById("summarySection").hidden = true;

												if (userCodeValidator()) {
													var json = {
														"userCode" : code
													};

													$
															.ajax({
																url : '/getRent',
																data : JSON
																		.stringify(json),
																type : "POST",

																beforeSend : function(
																		xhr) {
																	xhr
																			.setRequestHeader(
																					"Accept",
																					"application/json");
																	xhr
																			.setRequestHeader(
																					"Content-Type",
																					"application/json");
																},
																success : function(
																		rent) {
																	var respContent = "<tr><th></th><th>"
																			+ "Aktywny</th><th>"
																			+ "Kod roweru</th><th>"
																			+ "Data i godzina wypozyczenia</th><th>"
																			+ "Aktualny czas wypozyczenia</th></tr>";

																	for (var i = 0; i < rent.length; i++) {
																		respContent += "<tr><td><input type='checkbox' name='hiredBike'></td><td>";
																		respContent += rent[i].active
																				+ "</td><td>";
																		respContent += rent[i].bikeCode
																				+ "</td><td>";
																		respContent += rent[i].startDate
																				+ "</td><td>";
																		respContent += rent[i].rentDuration
																				+ "</td></tr>";
																	}

																	document
																			.getElementById("activeRentSection").hidden = false;

																	if (rent.length == 0) {
																		document
																				.getElementById("activeRentBtn").hidden = true;
																		respContent = "<span><b>Brak aktywnych wypożyczeń</b><span>";
																	} else {
																		document
																				.getElementById("activeRentBtn").hidden = false;
																	}
																	$("#resp")
																			.html(
																					respContent);
																}
															});

													event.preventDefault();
												} else {
													alert("Podaj prawidłowy kod użytkownika!");
												}
											});

							//active rent form	
							$('#activeRentForm')
									.submit(
											function(event) {
												var checkboxes = document
														.getElementsByName('hiredBike');
												var selected = "";
												for (var i = 0; i < checkboxes.length; i++) {
													if (checkboxes[i].checked) {
														selected += i + ",";
													}
												}

												if (selected !== "") {
													var json = {
														"selected" : selected
													};

													$
															.ajax({
																url : '/summarize.do',
																data : JSON
																		.stringify(json),
																type : "POST",

																beforeSend : function(
																		xhr) {
																	xhr
																			.setRequestHeader(
																					"Accept",
																					"application/json");
																	xhr
																			.setRequestHeader(
																					"Content-Type",
																					"application/json");
																},
																success : function(
																		payment) {
																	$(
																			"#payment")
																			.html(
																					payment);
																	document
																			.getElementById("summarySection").hidden = false;
																	document
																			.getElementById("activeRentBtn").disable = true;
																}
															});

													event.preventDefault();
												}
											});

						});
	</script>
	<footer>
		<%@include file="footer.jsp"%>
	</footer>
</body>
</html>