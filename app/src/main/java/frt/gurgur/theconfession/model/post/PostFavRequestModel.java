package frt.gurgur.theconfession.model.post;

import com.google.gson.annotations.SerializedName;

public class PostFavRequestModel {

    @SerializedName("post_id")
    private int post_id;
    @SerializedName("user_id")
    private int user_id;

    public PostFavRequestModel(int post_id, int user_id) {
        this.post_id = post_id;
        this.user_id = user_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public int getUser_id() {
        return user_id;
    }
}
