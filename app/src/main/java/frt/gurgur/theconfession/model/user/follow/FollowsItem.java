package frt.gurgur.theconfession.model.user.follow;

import com.google.gson.annotations.SerializedName;

public class FollowsItem {

	@SerializedName("user_id")
	private int userId;

	@SerializedName("photo")
	private String photo;

	@SerializedName("fullname")
	private String fullname;

	@SerializedName("username")
	private String username;

	public int getUserId(){
		return userId;
	}

	public String getPhoto(){
		return photo;
	}

	public String getFullname(){
		return fullname;
	}

	public String getUsername(){
		return username;
	}
}