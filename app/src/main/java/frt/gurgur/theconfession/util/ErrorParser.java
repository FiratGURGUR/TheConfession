package frt.gurgur.theconfession.util;

import com.google.gson.annotations.SerializedName;

public class ErrorParser{

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