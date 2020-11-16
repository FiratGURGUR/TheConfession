package frt.gurgur.theconfession.ui.user.profile.followpage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import frt.gurgur.theconfession.data.remote.APIResponseModel;
import frt.gurgur.theconfession.data.remote.repo.PostRepo;
import frt.gurgur.theconfession.data.remote.repo.UserRepo;
import frt.gurgur.theconfession.model.main.PostResponse;
import frt.gurgur.theconfession.ui.base.BaseViewModel;
import frt.gurgur.theconfession.util.ErrorUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class FollowViewModel extends BaseViewModel {

    private static final String TAG = "FollowViewModel";
    private final UserRepo userRepo;
    private CompositeDisposable disposable;
    private MutableLiveData<PostResponse> response = new MutableLiveData<>();

    @Inject
    public FollowViewModel(UserRepo userRepo) {
        this.userRepo = userRepo;
        disposable = new CompositeDisposable();
    }

    public LiveData<PostResponse> getResponse() {
        return response;
    }

    public void getFollowerList(int userid){
        disposable.add(userRepo.getFollowerList(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> loadingStatus.setValue(true))
                .doAfterTerminate(() -> loadingStatus.setValue(false))
                .subscribeWith(new DisposableSingleObserver<PostResponse>() {
                    @Override
                    public void onSuccess(@NonNull PostResponse postResponse) {
                        response.setValue(postResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        onError.setValue(ErrorUtils.showError(e).getMessage());
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
