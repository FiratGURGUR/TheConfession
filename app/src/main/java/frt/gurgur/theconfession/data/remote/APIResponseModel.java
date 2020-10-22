package frt.gurgur.theconfession.data.remote;

import com.google.gson.annotations.SerializedName;

public class APIResponseModel {

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public String getMessage(){
		return message;
	}

	public int getStatus(){
		return status;
	}
}