package edu.citybike.charts;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import edu.citybike.charts.datafactories.RentDataFactory;
import edu.citybike.charts.dividers.RentalStartDateDivider;
import edu.citybike.charts.operations.SumOperation;
import edu.citybike.database.DatabaseFacade;

public class ChartDataConfigurator {
	private DatabaseFacade facade;

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}

	public Map<Integer, ChartData> generateChartData(){
		Random random = new Random();
		Map<Integer, ChartData> result = new HashMap<Integer, ChartData>();
		
		RentDataFactory rentDataFactory = new RentDataFactory();
		rentDataFactory.setFacade(facade);
		
		RentDataBuilder db1 = new RentDataBuilder();
		db1.setDataFactory(rentDataFactory);
		db1.setOperation(new SumOperation());
		db1.setDivider(new RentalStartDateDivider());

		result.put(random.nextInt(100), new ChartData("Liczba wypożyczeń",db1));
		return result;
	}
}
