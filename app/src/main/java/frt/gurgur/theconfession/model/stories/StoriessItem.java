package frt.gurgur.theconfession.model.stories;

import com.google.gson.annotations.SerializedName;

public class StoriessItem{

	@SerializedName("user_id")
	private int userId;

	@SerializedName("photo")
	private String photo;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("fullname")
	private String fullname;

	@SerializedName("story_url")
	private String storyUrl;

	@SerializedName("username")
	private String username;

	public int getUserId(){
		return userId;
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

	public String getStoryUrl(){
		return storyUrl;
	}

	public String getUsername(){
		return username;
	}
}