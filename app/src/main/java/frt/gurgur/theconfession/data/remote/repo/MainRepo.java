package frt.gurgur.theconfession.data.remote.repo;

import javax.inject.Inject;

import frt.gurgur.theconfession.data.remote.APIService;
import frt.gurgur.theconfession.model.APIResponseModel;
import frt.gurgur.theconfession.model.comment.CommentResponse;
import frt.gurgur.theconfession.model.comment.CreateCommentRequestModel;
import frt.gurgur.theconfession.model.main.PostResponse;
import frt.gurgur.theconfession.model.post.PostRequestModel;
import frt.gurgur.theconfession.model.post.giphy.GiphyModel;
import frt.gurgur.theconfession.model.stories.StoryListResponse;
import io.reactivex.Single;

public class MainRepo {

    private final APIService api;

    @Inject
    public MainRepo(APIService api) {
        this.api = api;
    }

    public Single<PostResponse> getPostList(int page, int user_id){
        return api.postList(page,user_id);
    }

    public Single<PostResponse> getExploreList(String hashtag, int user_id){
        return api.getExploreList(hashtag,user_id);
    }

    public Single<GiphyModel> getGiphyList(int page){
        return api.giphyList(page);
    }

    public Single<StoryListResponse> getStoryList(){
        return api.getStoryList();
    }

    public Single<StoryListResponse> getWatchList(){
        return api.getWatchList();
    }

    public Single<PostResponse> getSharedPostList(int page, int user_id){
        return api.sharedPostList(page,user_id);
    }

    public Single<PostResponse> getFavoritedPostList(int page, int user_id){
        return api.favoritedPostList(page,user_id);
    }

    public Single<CommentResponse> getCommentList(int page, int post_id){
        return api.commentList(page,post_id);
    }

    public Single<APIResponseModel> createComment(CreateCommentRequestModel createCommentRequestModel){
        return api.createComment(createCommentRequestModel);
    }

}
