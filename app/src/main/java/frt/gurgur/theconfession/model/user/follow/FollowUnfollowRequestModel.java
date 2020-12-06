package frt.gurgur.theconfession.model.user.follow;

import com.google.gson.annotations.SerializedName;

public class FollowUnfollowRequestModel {

    @SerializedName("follower_id")
    private int follower_id;
    @SerializedName("followed_id")
    private int followed_id;

    public FollowUnfollowRequestModel(int follower_id, int followed_id) {
        this.follower_id = follower_id;
        this.followed_id = followed_id;
    }

    public int getFollower_id() {
        return follower_id;
    }

    public int getFollowed_id() {
        return followed_id;
    }
}
