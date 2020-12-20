package frt.gurgur.theconfession.data.remote;


import frt.gurgur.theconfession.model.APIResponseModel;
import frt.gurgur.theconfession.model.HashtagAddRequestModel;
import frt.gurgur.theconfession.model.comment.CommentResponse;
import frt.gurgur.theconfession.model.comment.CreateCommentRequestModel;
import frt.gurgur.theconfession.model.hashtag.HashtagResponseModel;
import frt.gurgur.theconfession.model.main.PostResponse;
import frt.gurgur.theconfession.model.post.PostFavRequestModel;
import frt.gurgur.theconfession.model.post.PostRequestModel;
import frt.gurgur.theconfession.model.post.giphy.GiphyModel;
import frt.gurgur.theconfession.model.stories.StoriessItem;
import frt.gurgur.theconfession.model.stories.StoryListResponse;
import frt.gurgur.theconfession.model.user.UserResponse;
import frt.gurgur.theconfession.model.user.follow.FollowListResponse;
import frt.gurgur.theconfession.model.user.RequestUser;
import frt.gurgur.theconfession.model.user.follow.FollowUnfollowRequestModel;
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

    @POST(Constants.ADD_HASHTAG)
    Single<APIResponseModel> addHashTag(@Body HashtagAddRequestModel hashtagAddRequestModel);

    @GET(Constants.HASHTAG_LIST)
    Single<HashtagResponseModel> getAllHashTags();

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

    @POST(Constants.COMMENT_ADD)
    Single<APIResponseModel> createComment(@Body CreateCommentRequestModel createCommentRequestModel);

    @GET(Constants.SHARED_POST_LIST)
    Single<PostResponse> sharedPostList(@Query("page") int page , @Query("user_id") int user_id);

    @GET(Constants.FAVORITED_POST_LIST)
    Single<PostResponse> favoritedPostList(@Query("page") int page , @Query("user_id") int user_id);

    @GET(Constants.GIPHY_LIST)
    Single<GiphyModel> giphyList(@Query("page") int page);

    @POST(Constants.FOLLOW_UNFOLLOW)
    Single<APIResponseModel> followUnfollow(@Body FollowUnfollowRequestModel followUnfollowRequestModel);

    @GET(Constants.STORY_LIST)
    Single<StoryListResponse> getStoryList();

    @GET(Constants.STORY_WATCH)
    Single<StoryListResponse> getWatchList();

    @GET(Constants.EXPLORE_DETAIL_LIST)
    Single<PostResponse> getExploreList(@Query("hashtag") String hashtag , @Query("user_id") int user_id);

}
