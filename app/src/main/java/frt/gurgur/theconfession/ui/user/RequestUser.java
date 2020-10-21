package frt.gurgur.theconfession.ui.user;

import com.google.gson.annotations.SerializedName;

public class RequestUser{
	public RequestUser(int id) {
		this.id = id;
	}
	public RequestUser(String email,String password) {
		this.email = email;
		this.password = password;
	}

	@SerializedName("id")
	private int id;
	@SerializedName("email")
	private String email;
	@SerializedName("password")
	private String password;

	public int getId(){
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}