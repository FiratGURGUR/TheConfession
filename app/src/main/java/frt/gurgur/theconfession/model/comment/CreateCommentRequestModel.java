package frt.gurgur.theconfession.model.comment;

import com.google.gson.annotations.SerializedName;

public class CreateCommentRequestModel {

    @SerializedName("comment")
    private String comment;
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("post_id")
    private int post_id;

    public CreateCommentRequestModel(String comment, int user_id, int post_id) {
        this.comment = comment;
        this.user_id = user_id;
        this.post_id = post_id;
    }

    public String getComment() {
        return comment;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getPost_id() {
        return post_id;
    }
}
