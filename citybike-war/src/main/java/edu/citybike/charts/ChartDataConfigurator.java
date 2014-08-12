package edu.citybike.charts;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import edu.citybike.charts.datafactories.RentDataFactory;
import edu.citybike.charts.dividers.RentalStartDateDivider;
import edu.citybike.charts.filters.RentalEndDateFilter;
import edu.citybike.charts.filters.RentalStartDateFilter;
import edu.citybike.charts.operations.SumOperation;

public class ChartDataConfigurator {

	public Map<Integer, ChartData> generateChartData(){
		Random random = new Random();
		Map<Integer, ChartData> result = new HashMap<Integer, ChartData>();
		
		DataBuilder db1 = new DataBuilder();
		db1.setDataFactory(new RentDataFactory());
		db1.addFilter(new RentalStartDateFilter());
		db1.addFilter(new RentalEndDateFilter());
		db1.setOperation(new SumOperation());
		db1.setDivider(new RentalStartDateDivider());

		result.put(random.nextInt(100), new ChartData("Liczba wypożyczeń",db1));
		return result;
	}
}
