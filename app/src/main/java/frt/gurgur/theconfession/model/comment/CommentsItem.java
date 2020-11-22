package frt.gurgur.theconfession.model.comment;

import com.google.gson.annotations.SerializedName;

public class CommentsItem{

	@SerializedName("post_id")
	private int postId;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("photo")
	private String photo;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("comment")
	private String comment;

	@SerializedName("id")
	private int id;

	@SerializedName("fullname")
	private String fullname;

	@SerializedName("username")
	private String username;

	public int getPostId(){
		return postId;
	}

	public int getUserId(){
		return userId;
	}

	public String getPhoto(){
		return photo;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getComment(){
		return comment;
	}

	public int getId(){
		return id;
	}

	public String getFullname(){
		return fullname;
	}

	public String getUsername(){
		return username;
	}
}