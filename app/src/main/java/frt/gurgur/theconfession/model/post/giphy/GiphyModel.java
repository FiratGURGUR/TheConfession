package frt.gurgur.theconfession.model.post.giphy;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GiphyModel{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("status")
	private int status;

	public List<DataItem> getData(){
		return data;
	}

	public int getStatus(){
		return status;
	}
}