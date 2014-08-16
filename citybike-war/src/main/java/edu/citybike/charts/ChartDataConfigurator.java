package edu.citybike.charts;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import edu.citybike.charts.datafactories.RentDataFactory;
import edu.citybike.charts.datafactories.UserDataFactory;
import edu.citybike.charts.dividers.DateDivider;
import edu.citybike.charts.operations.SumOperation;
import edu.citybike.database.DatabaseFacade;
import edu.citybike.model.Rent;
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
		db1.setOperation(new SumOperation(""));
		db1.setDivider(new DateDivider<Rent>("startDate"));
		
		UserDataFactory userDataFactory = new UserDataFactory();
		userDataFactory.setFacade(facade);
		
		RegistrationCounterDataBuilder db2 = new RegistrationCounterDataBuilder();
		db2.setDataFactory(userDataFactory);
		db2.setOperation(new SumOperation(""));
		db2.setDivider(new DateDivider<User>("registrationDate"));
		

		userResult.put(random.nextInt(1000), new ChartData("Liczba wypożyczeń", db1));
		adminResult.put(random.nextInt(1000), new ChartData("Liczba wypożyczeń", db1));
		
		adminResult.put(random.nextInt(1000), new ChartData("Liczba rejestracji", db2));
	}

	public Map<Integer, ChartData> getChartData(String userRole) {
		return (userRole.equals(User.ADMINISTRATOR)) ? adminResult : userResult;

	}
}
