package frt.gurgur.theconfession.ui.post;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import frt.gurgur.theconfession.data.remote.APIResponseModel;
import frt.gurgur.theconfession.data.remote.repo.PostRepo;
import frt.gurgur.theconfession.data.remote.repo.UserRepo;
import frt.gurgur.theconfession.model.post.PostRequestModel;
import frt.gurgur.theconfession.ui.base.BaseViewModel;
import frt.gurgur.theconfession.ui.user.RequestUser;
import frt.gurgur.theconfession.util.ErrorUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class PostViewModel  extends BaseViewModel {

    private static final String TAG = "PostViewModel";
    private final PostRepo postRepo;
    private CompositeDisposable disposable;
    private MutableLiveData<APIResponseModel> response = new MutableLiveData<>();

    @Inject
    public PostViewModel(PostRepo postRepo) {
        this.postRepo = postRepo;
        disposable = new CompositeDisposable();
    }

    public LiveData<APIResponseModel> getResponse() {
        return response;
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
                        onError.setValue(ErrorUtils.showError(t).getMessage());
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
