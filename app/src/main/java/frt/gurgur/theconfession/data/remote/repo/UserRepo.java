package frt.gurgur.theconfession.data.remote.repo;

import javax.inject.Inject;

import frt.gurgur.theconfession.data.remote.APIResponseModel;
import frt.gurgur.theconfession.data.remote.APIService;
import frt.gurgur.theconfession.model.main.PostResponse;
import frt.gurgur.theconfession.model.user.UserResponse;
import frt.gurgur.theconfession.ui.user.RequestUser;
import io.reactivex.Single;

public class UserRepo {

    private final APIService api;

    @Inject
    public UserRepo(APIService api) {
        this.api = api;
    }

    public Single<UserResponse> loginUser(RequestUser user){
        return api.login(user);
    }

    public Single<APIResponseModel> registerUser(RequestUser user){
        return api.register(user);
    }

    public Single<UserResponse> getSingleUSer(RequestUser user){
        return api.getSingleUser(user);
    }

    public Single<PostResponse> getFollowerList(int user_id){return api.followersList(user_id);}

    public Single<PostResponse> getFollowingList(int user_id){return api.followingsList(user_id);}

}
