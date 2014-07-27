package edu.citybike.model.view;

public class ExternalLoginUser {

	private String id;
	private String email;
	private String first_name;
	private String last_name;
	
	public ExternalLoginUser(String id, String email, String first_name, String last_name) {
		this.id = id;
		this.email = email;
		this.first_name = first_name;
		this.last_name = last_name;
	}

	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getFirst_name() {
		return first_name;
	}

	public String getLast_name() {
		return last_name;
	}
	
	
	
}
