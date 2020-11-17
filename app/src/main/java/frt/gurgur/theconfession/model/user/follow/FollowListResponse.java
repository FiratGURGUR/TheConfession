package frt.gurgur.theconfession.model.user.follow;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FollowListResponse{

	@SerializedName("followers")
	private List<FollowsItem> followers;

	@SerializedName("followings")
	private List<FollowsItem> followings;

	@SerializedName("status")
	private int status;

	public List<FollowsItem> getFollowings() {
		return followings;
	}

	public List<FollowsItem> getFollowers(){
		return followers;
	}

	public int getStatus(){
		return status;
	}
}