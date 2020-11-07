package frt.gurgur.theconfession.model.post;

import com.google.gson.annotations.SerializedName;

public class PostRequestModel {

    @SerializedName("content")
    private String content;
    @SerializedName("user_id")
    private int user_id;

    public PostRequestModel(String content, int user_id) {
        this.content = content;
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public int getUser_id() {
        return user_id;
    }
}
