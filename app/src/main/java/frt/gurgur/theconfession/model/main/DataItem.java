package frt.gurgur.theconfession.model.main;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("isWithImage")
	private int isWithImage;

	@SerializedName("fav_count")
	private int favCount;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("photo")
	private String photo;

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

	public int getFavCount(){
		return favCount;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public int getUserId(){
		return userId;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getPhoto(){
		return photo;
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
}