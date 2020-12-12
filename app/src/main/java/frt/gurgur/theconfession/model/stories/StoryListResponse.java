package frt.gurgur.theconfession.model.stories;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class StoryListResponse{

	@SerializedName("storiess")
	private List<StoriessItem> storiess;

	@SerializedName("status")
	private int status;

	public List<StoriessItem> getStoriess(){
		return storiess;
	}

	public int getStatus(){
		return status;
	}
}