package edu.citybike.charts;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import edu.citybike.charts.datafactories.RentDataFactory;
import edu.citybike.charts.dividers.RentalStartDateDivider;
import edu.citybike.charts.operations.SumOperation;
import edu.citybike.database.DatabaseFacade;
import edu.citybike.model.User;

public class ChartDataConfigurator {

	private Map<Integer, ChartData> userResult = new HashMap<Integer, ChartData>();
	private Map<Integer, ChartData> adminResult = new HashMap<Integer, ChartData>();

	public ChartDataConfigurator(DatabaseFacade facade) {
		Random random = new Random();
		RentDataFactory rentDataFactory = new RentDataFactory();
		rentDataFactory.setFacade(facade);

		RentDataBuilder db1 = new RentDataBuilder();
		db1.setDataFactory(rentDataFactory);
		db1.setOperation(new SumOperation());
		db1.setDivider(new RentalStartDateDivider());

		userResult.put(random.nextInt(100), new ChartData("Liczba wypożyczeń", db1));
		adminResult.put(random.nextInt(100), new ChartData("Liczba wypożyczeń", db1));
	}

	public Map<Integer, ChartData> getChartData(String userRole) {
		return (userRole.equals(User.ADMINISTRATOR)) ? adminResult : userResult;

	}
}
