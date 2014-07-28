package edu.citybike.model.view;

public class FacebookExternalLoginUser extends ExternalLoginUser{

	private String gender;
	private String link;
	private String locale;
	private String name;
	private int timezone;
	private String updated_time;
	private boolean verified;
	
	public FacebookExternalLoginUser(){
		super("", "", "", "");
	}
	
	public FacebookExternalLoginUser(String id, String email, String first_name, String last_name, String gender, String link,
			String locale, String name, int timezone, String updated_time, boolean verified) {
		super(id, email, first_name, last_name);
		this.gender = gender;
		this.link = link;
		this.locale = locale;
		this.name = name;
		this.timezone = timezone;
		this.updated_time = updated_time;
		this.verified = verified;
	}

	public String getGender() {
		return gender;
	}

	public String getLink() {
		return link;
	}

	public String getLocale() {
		return locale;
	}

	public String getName() {
		return name;
	}

	public int getTimezone() {
		return timezone;
	}

	public String getUpdated_time() {
		return updated_time;
	}

	public boolean isVerified() {
		return verified;
	}
	
	
	
}
