package frt.gurgur.theconfession.data.remote.repo;

import javax.inject.Inject;

import frt.gurgur.theconfession.data.remote.APIService;
import frt.gurgur.theconfession.model.comment.CommentResponse;
import frt.gurgur.theconfession.model.main.PostResponse;
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

    public Single<CommentResponse> getCommentList(int page, int post_id){
        return api.commentList(page,post_id);
    }

}
