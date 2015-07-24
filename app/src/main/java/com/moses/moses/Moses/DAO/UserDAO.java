package com.moses.moses.Moses.DAO;

public class UserDAO {
	private static UserDAO userInstance = null;
	private String first_name;
	private String full_name;
	private String facebook_id;
	private String timezone;
	private String locale;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private String email;

	public static UserDAO getUserInstance() {
		return userInstance;
	}

	public static void setUserInstance(UserDAO userInstance) {
		UserDAO.userInstance = userInstance;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getFacebook_id() {
		return facebook_id;
	}

	public void setFacebook_id(String facebook_id) {
		this.facebook_id = facebook_id;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public static UserDAO getInstance() {
		if (userInstance == null) {
			userInstance = new UserDAO();
		}
		return userInstance;
	}
}

