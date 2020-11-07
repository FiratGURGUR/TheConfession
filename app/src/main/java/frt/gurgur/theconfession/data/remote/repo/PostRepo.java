package frt.gurgur.theconfession.data.remote.repo;

import javax.inject.Inject;

import frt.gurgur.theconfession.data.remote.APIResponseModel;
import frt.gurgur.theconfession.data.remote.APIService;
import frt.gurgur.theconfession.model.main.PostResponse;
import frt.gurgur.theconfession.model.post.PostRequestModel;
import io.reactivex.Single;

public class PostRepo {

    private final APIService api;

    @Inject
    public PostRepo(APIService api) {
        this.api = api;
    }

    public Single<APIResponseModel> createPost(PostRequestModel postRequestModel){
        return api.createPost(postRequestModel);
    }
}
