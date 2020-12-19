package frt.gurgur.theconfession.model.hashtag;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HashtagResponseModel{

	@SerializedName("hashtags")
	private List<HashtagsItem> hashtags;

	@SerializedName("status")
	private int status;

	public List<HashtagsItem> getHashtags(){
		return hashtags;
	}

	public int getStatus(){
		return status;
	}
}