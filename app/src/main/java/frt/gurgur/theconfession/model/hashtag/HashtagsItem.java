package frt.gurgur.theconfession.model.hashtag;

import com.google.gson.annotations.SerializedName;

public class HashtagsItem{

	@SerializedName("hashtag_count")
	private int hashtagCount;

	@SerializedName("hashtag")
	private String hashtag;

	public int getHashtagCount(){
		return hashtagCount;
	}

	public String getHashtag(){
		return hashtag;
	}
}