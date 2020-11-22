package frt.gurgur.theconfession.model.comment;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CommentResponse{

	@SerializedName("comments")
	private List<CommentsItem> comments;

	@SerializedName("status")
	private int status;

	public List<CommentsItem> getComments(){
		return comments;
	}

	public int getStatus(){
		return status;
	}
}