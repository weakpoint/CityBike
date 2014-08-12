package edu.citybike.charts.filters;

public abstract class FilterModel<T> {
	protected Operator operator;
	protected T value;

	public FilterModel(Operator operator, T value) {
		this.operator = operator;
		this.value = value;
	}

	public Operator getOperator() {
		return operator;
	}

	public T getValue() {
		return value;
	}
	
	public abstract boolean checkCondition(T valueToBeChcecked);
	public abstract boolean isValid(Object obj);
}
