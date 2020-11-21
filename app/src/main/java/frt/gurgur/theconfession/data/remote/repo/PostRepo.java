package frt.gurgur.theconfession.data.remote.repo;



import javax.inject.Inject;

import frt.gurgur.theconfession.model.APIResponseModel;
import frt.gurgur.theconfession.data.remote.APIService;
import frt.gurgur.theconfession.model.post.PostFavRequestModel;
import frt.gurgur.theconfession.model.post.PostRequestModel;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PostRepo {

    private final APIService api;

    @Inject
    public PostRepo(APIService api) {
        this.api = api;
    }

    public Single<APIResponseModel> createPost(PostRequestModel postRequestModel){
        return api.createPost(postRequestModel);
    }

    public Single<APIResponseModel> createPostWithImage(MultipartBody.Part content_image, RequestBody user_id, RequestBody content){
        return api.createPostWithImage(content_image,user_id,content);
    }

    public Single<APIResponseModel> favPost(PostFavRequestModel postFavRequestModel){
        return api.favPost(postFavRequestModel);
    }

}
