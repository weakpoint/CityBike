package edu.citybike.charts.filters;

import java.util.Date;

public class DateFilterModel extends FilterModel<Date> {

	public DateFilterModel(Operator operator, Date value) {
		super(operator, value);
	}

	@Override
	public boolean checkCondition(Date valueToBeChcecked) {

		switch(this.operator){
		case EQ:
			return valueToBeChcecked.compareTo(value) == 0;
		case NEQ:
			return valueToBeChcecked.compareTo(value) != 0;
		case GT:
			return valueToBeChcecked.compareTo(value) > 0;
		case GEQ:
			return valueToBeChcecked.compareTo(value) >= 0;
		case LT:
			return valueToBeChcecked.compareTo(value) < 0;
		case LEQ:
			return valueToBeChcecked.compareTo(value) <= 0;
		default:
			return false;		
		}
		
	}

	@Override
	public boolean isValid(Object obj) {
		return obj instanceof Date;
	}



}
