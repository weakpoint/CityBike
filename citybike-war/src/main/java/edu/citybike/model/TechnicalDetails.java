package edu.citybike.model;

import javax.persistence.Embeddable;

@Embeddable
public class TechnicalDetails {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



}
