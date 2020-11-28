package frt.gurgur.theconfession.model.post.giphy;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("gif_name")
	private String gifName;

	@SerializedName("gif_category")
	private String gifCategory;

	@SerializedName("id")
	private int id;

	@SerializedName("gif_url")
	private String gifUrl;

	public String getGifName(){
		return gifName;
	}

	public String getGifCategory(){
		return gifCategory;
	}

	public int getId(){
		return id;
	}

	public String getGifUrl(){
		return gifUrl;
	}
}