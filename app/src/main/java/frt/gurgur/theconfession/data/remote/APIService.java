package frt.gurgur.theconfession.data.remote;

import frt.gurgur.theconfession.model.user.UserResponse;
import frt.gurgur.theconfession.ui.user.RequestUser;
import frt.gurgur.theconfession.util.Constants;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {


    @POST(Constants.GET_USER)
    Single<UserResponse> getUser(@Body RequestUser requestUser);

    @POST(Constants.LOGIN)
    Single<UserResponse> login(@Body RequestUser requestUser);


}
