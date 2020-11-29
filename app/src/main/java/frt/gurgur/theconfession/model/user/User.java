package frt.gurgur.theconfession.model.user;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("password")
	private String password;

	@SerializedName("following_count")
	private int followingCount;

	@SerializedName("post_count")
	private int postCount;

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

	@SerializedName("about")
	private String about;

	@SerializedName("isTakip")
	private String isTakip;

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

	public int getPostCount() {
		return postCount;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFollowingCount(int followingCount) {
		this.followingCount = followingCount;
	}

	public void setPostCount(int postCount) {
		this.postCount = postCount;
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

	public void setCoverphoto(String coverphoto) {
		this.coverphoto = coverphoto;
	}

	public void setFollowerCount(int followerCount) {
		this.followerCount = followerCount;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getIsTakip() {
		return isTakip;
	}

	public void setIsTakip(String isTakip) {
		this.isTakip = isTakip;
	}
}