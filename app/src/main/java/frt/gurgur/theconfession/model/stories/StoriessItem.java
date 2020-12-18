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

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public void setStoryUrl(String storyUrl) {
		this.storyUrl = storyUrl;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}