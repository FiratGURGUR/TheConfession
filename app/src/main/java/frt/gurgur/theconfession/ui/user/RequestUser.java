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

	public RequestUser(String username,String fullname,String photo,String password,String email,String coverphoto) {
		this.username = username;
		this.fullname = fullname;
		this.photo = photo;
		this.password = password;
		this.email = email;
		this.coverphoto = coverphoto;
	}


	@SerializedName("username")
	private String username;
	@SerializedName("fullname")
	private String fullname;
	@SerializedName("photo")
	private String photo;
	@SerializedName("coverphoto")
	private String coverphoto;
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

	public String getUsername() {
		return username;
	}

	public String getFullname() {
		return fullname;
	}

	public String getPhoto() {
		return photo;
	}

	public String getCoverphoto() {
		return coverphoto;
	}
}