package edu.citybike.charts.operations;

import java.util.List;

public class SumOperation implements Operation{

	@Override
	public long doOperation(List<?> data) {
		
		return data.size();
	}

}
