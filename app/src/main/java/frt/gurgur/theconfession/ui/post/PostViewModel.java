package frt.gurgur.theconfession.ui.post;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;



import javax.inject.Inject;

import frt.gurgur.theconfession.model.APIResponseModel;
import frt.gurgur.theconfession.data.remote.repo.PostRepo;
import frt.gurgur.theconfession.model.HashtagAddRequestModel;
import frt.gurgur.theconfession.model.post.PostFavRequestModel;
import frt.gurgur.theconfession.model.post.PostRequestModel;
import frt.gurgur.theconfession.ui.base.BaseViewModel;
import frt.gurgur.theconfession.util.ErrorUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PostViewModel  extends BaseViewModel {

    private static final String TAG = "PostViewModel";
    private final PostRepo postRepo;
    private CompositeDisposable disposable;
    private MutableLiveData<APIResponseModel> response = new MutableLiveData<>();
    private MutableLiveData<APIResponseModel> responseAddHashtag = new MutableLiveData<>();
    private MutableLiveData<APIResponseModel> responseWithImage = new MutableLiveData<>();
    private MutableLiveData<APIResponseModel> responseFavPost = new MutableLiveData<>();

    @Inject
    public PostViewModel(PostRepo postRepo) {
        this.postRepo = postRepo;
        disposable = new CompositeDisposable();
    }

    public LiveData<APIResponseModel> getResponse() {
        return response;
    }
    public LiveData<APIResponseModel> getAddHashtagResponse() {
        return responseAddHashtag;
    }
    public LiveData<APIResponseModel> getResponseWithImage() {
        return responseWithImage;
    }
    public LiveData<APIResponseModel> getResponseFavPost() {
        return responseFavPost;
    }

    public void createPost(PostRequestModel postRequestModel) {

        disposable.add(postRepo.createPost(postRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> loadingStatus.setValue(true))
                .doAfterTerminate(() -> loadingStatus.setValue(false))
                .subscribeWith(new DisposableSingleObserver<APIResponseModel>() {
                    @Override
                    public void onSuccess(APIResponseModel resultsResponse) {
                        response.setValue(resultsResponse);
                    }

                    @Override
                    public void onError(Throwable t) {
                        onError.setValue(ErrorUtils.showError(t));
                    }
                }));
    }
    public void addHashtag(HashtagAddRequestModel hashtagAddRequestModel) {
        disposable.add(postRepo.addHashTag(hashtagAddRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> loadingStatus.setValue(true))
                .doAfterTerminate(() -> loadingStatus.setValue(false))
                .subscribeWith(new DisposableSingleObserver<APIResponseModel>() {
                    @Override
                    public void onSuccess(APIResponseModel resultsResponse) {
                        responseAddHashtag.setValue(resultsResponse);
                    }

                    @Override
                    public void onError(Throwable t) {
                        onError.setValue(ErrorUtils.showError(t));
                    }
                }));
    }

    public void favPost(PostFavRequestModel postFavRequestModel) {

        disposable.add(postRepo.favPost(postFavRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> loadingStatus.setValue(true))
                .doAfterTerminate(() -> loadingStatus.setValue(false))
                .subscribeWith(new DisposableSingleObserver<APIResponseModel>() {
                    @Override
                    public void onSuccess(APIResponseModel resultsResponse) {
                        responseFavPost.setValue(resultsResponse);
                    }

                    @Override
                    public void onError(Throwable t) {
                        onError.setValue(ErrorUtils.showError(t));
                    }
                }));
    }

    public void createPostWithImage(MultipartBody.Part content_image, RequestBody user_id, RequestBody content) {

        disposable.add(postRepo.createPostWithImage(content_image,user_id,content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> loadingStatus.setValue(true))
                .doAfterTerminate(() -> loadingStatus.setValue(false))
                .subscribeWith(new DisposableSingleObserver<APIResponseModel>() {
                    @Override
                    public void onSuccess(APIResponseModel resultsResponse) {
                        responseWithImage.setValue(resultsResponse);
                    }

                    @Override
                    public void onError(Throwable t) {
                        onError.setValue(ErrorUtils.showError(t));
                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }


}
