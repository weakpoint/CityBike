package edu.citybike.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class Credentials {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key credentialKey;
	private String username;
	private String password;


	public Credentials() {
		this.username = "";
		this.password = "";
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Key getCredentialKey() {
		return credentialKey;
	}

	public void setCredentialKey(Key credentialKey) {
		this.credentialKey = credentialKey;
	}

	@Override
	public String toString() {
		return "Credentials [emailAddress=" + username + ", password=" + password + "]";
	}
	
	

}
