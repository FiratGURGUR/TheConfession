package frt.gurgur.theconfession.data.remote.repo;

import javax.inject.Inject;
import frt.gurgur.theconfession.data.remote.APIService;
import frt.gurgur.theconfession.model.user.UserResponse;
import frt.gurgur.theconfession.ui.user.RequestUser;
import io.reactivex.Single;

public class UserRepo {

    private final APIService api;

    @Inject
    public UserRepo(APIService api) {
        this.api = api;
    }

    public Single<UserResponse> fetchUser(RequestUser user){
        return api.getUser(user);
    }

    public Single<UserResponse> loginUser(RequestUser user){
        return api.login(user);
    }

}
