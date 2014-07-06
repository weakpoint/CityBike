package edu.citybike.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class Test {
	@Id  
	 @GeneratedValue(strategy = GenerationType.IDENTITY)  
	 private Key id;  
	   
	 private Date requestTime;

	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public Test( Date requestTime) {
		this.requestTime = requestTime;
	}  
	 
	 
}
