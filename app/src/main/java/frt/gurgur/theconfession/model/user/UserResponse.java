package frt.gurgur.theconfession.model.user;

import com.google.gson.annotations.SerializedName;

public class UserResponse{

	@SerializedName("user")
	private User user;

	@SerializedName("status")
	private int status;

	public User getUser(){
		return user;
	}

	public int getStatus(){
		return status;
	}
}