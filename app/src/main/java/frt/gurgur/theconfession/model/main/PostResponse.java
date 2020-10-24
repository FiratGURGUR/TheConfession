package frt.gurgur.theconfession.model.main;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PostResponse{

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