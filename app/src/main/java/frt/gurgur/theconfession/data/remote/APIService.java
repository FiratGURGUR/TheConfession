package frt.gurgur.theconfession.data.remote;


import frt.gurgur.theconfession.model.APIResponseModel;
import frt.gurgur.theconfession.model.comment.CommentResponse;
import frt.gurgur.theconfession.model.main.PostResponse;
import frt.gurgur.theconfession.model.post.PostFavRequestModel;
import frt.gurgur.theconfession.model.post.PostRequestModel;
import frt.gurgur.theconfession.model.user.UserResponse;
import frt.gurgur.theconfession.model.user.follow.FollowListResponse;
import frt.gurgur.theconfession.ui.user.RequestUser;
import frt.gurgur.theconfession.util.Constants;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIService {


    @POST(Constants.LOGIN)
    Single<UserResponse> login(@Body RequestUser requestUser);


    @POST(Constants.REGISTER)
    Single<APIResponseModel> register(@Body RequestUser requestUser);


    @GET(Constants.POST_LIST)
    Single<PostResponse> postList(@Query("page") int page , @Query("user_id") int user_id);


    @POST(Constants.GET_USER)
    Single<UserResponse> getSingleUser(@Body RequestUser requestUser);


    @POST(Constants.POST_CREATE)
    Single<APIResponseModel> createPost(@Body PostRequestModel postRequestModel);

    @Multipart
    @POST(Constants.POST_CREATE_WITH_IMAGE)
    Single<APIResponseModel> createPostWithImage(
            @Part MultipartBody.Part content_image,
            @Part("user_id") RequestBody user_id,
            @Part("content") RequestBody content);

    @GET(Constants.GET_FOLLOWERS_LIST)
    Single<FollowListResponse> followersList(@Query("user_id") int user_id);

    @GET(Constants.GET_FOLLOWING_LIST)
    Single<FollowListResponse> followingsList(@Query("user_id") int user_id);

    @POST(Constants.POST_FAV)
    Single<APIResponseModel> favPost(@Body PostFavRequestModel postFavRequestModel);

    @GET(Constants.COMMENT_LIST)
    Single<CommentResponse> commentList(@Query("page") int page , @Query("post_id") int post_id);

}
