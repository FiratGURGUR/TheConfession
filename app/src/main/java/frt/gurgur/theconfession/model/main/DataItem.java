package frt.gurgur.theconfession.model.main;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("isWithImage")
	private int isWithImage;

	@SerializedName("selfLikes")
	private String selfLikes;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("selfPost")
	private String selfPost;

	@SerializedName("photo")
	private String photo;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("likeCount")
	private int likeCount;

	@SerializedName("id")
	private int id;

	@SerializedName("fullname")
	private String fullname;

	@SerializedName("content_image")
	private String contentImage;

	@SerializedName("content")
	private String content;

	@SerializedName("username")
	private String username;

	public int getIsWithImage(){
		return isWithImage;
	}

	public String getSelfLikes(){
		return selfLikes;
	}

	public int getUserId(){
		return userId;
	}

	public String getSelfPost(){
		return selfPost;
	}

	public String getPhoto(){
		return photo;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getLikeCount(){
		return likeCount;
	}

	public int getId(){
		return id;
	}

	public String getFullname(){
		return fullname;
	}

	public String getContentImage(){
		return contentImage;
	}

	public String getContent(){
		return content;
	}

	public String getUsername(){
		return username;
	}

	public void setSelfLikes(String selfLikes) {
		this.selfLikes = selfLikes;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
}