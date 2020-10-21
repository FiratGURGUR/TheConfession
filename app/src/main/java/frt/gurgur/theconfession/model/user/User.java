package frt.gurgur.theconfession.model.user;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("password")
	private String password;

	@SerializedName("following_count")
	private int followingCount;

	@SerializedName("photo")
	private String photo;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("fullname")
	private String fullname;

	@SerializedName("coverphoto")
	private String coverphoto;

	@SerializedName("follower_count")
	private int followerCount;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

	public String getPassword(){
		return password;
	}

	public int getFollowingCount(){
		return followingCount;
	}

	public String getPhoto(){
		return photo;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}

	public String getFullname(){
		return fullname;
	}

	public String getCoverphoto(){
		return coverphoto;
	}

	public int getFollowerCount(){
		return followerCount;
	}

	public String getEmail(){
		return email;
	}

	public String getUsername(){
		return username;
	}
}