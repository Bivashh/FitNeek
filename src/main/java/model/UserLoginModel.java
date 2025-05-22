package model;

public class UserLoginModel {

private String user_ID;
	
	private String user_password;
	
	public UserLoginModel(String user_ID, String user_password) {
		super();
		this.user_ID = user_ID;
		this.user_password = user_password;
	}

	public String getUser_ID() {
		return user_ID;
	}

	public void setUser_ID(String user_ID) {
		this.user_ID = user_ID;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	
}
