package frt.gurgur.theconfession.data.remote;

import frt.gurgur.theconfession.model.main.PostResponse;
import frt.gurgur.theconfession.model.user.UserResponse;
import frt.gurgur.theconfession.ui.user.RequestUser;
import frt.gurgur.theconfession.util.Constants;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {


    @POST(Constants.LOGIN)
    Single<UserResponse> login(@Body RequestUser requestUser);


    @POST(Constants.REGISTER)
    Single<APIResponseModel> register(@Body RequestUser requestUser);


    @GET(Constants.POST_LIST)
    Single<PostResponse> postList(@Query("page") int page , @Query("user_id") int user_id);

}
