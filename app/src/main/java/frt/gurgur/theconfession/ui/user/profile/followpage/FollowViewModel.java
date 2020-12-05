package frt.gurgur.theconfession.ui.user.profile.followpage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import frt.gurgur.theconfession.data.remote.repo.UserRepo;
import frt.gurgur.theconfession.model.user.follow.FollowListResponse;
import frt.gurgur.theconfession.model.user.follow.FollowsItem;
import frt.gurgur.theconfession.ui.base.BaseViewModel;
import frt.gurgur.theconfession.util.ErrorUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class FollowViewModel extends BaseViewModel {

    private final UserRepo userRepo;
    private CompositeDisposable disposable;
    private MutableLiveData<List<FollowsItem>> response = new MutableLiveData<>();
    private MutableLiveData<List<FollowsItem>> responseFollowing = new MutableLiveData<>();

    @Inject
    public FollowViewModel(UserRepo userRepo) {
        this.userRepo = userRepo;
        disposable = new CompositeDisposable();
    }

    public LiveData<List<FollowsItem>>getResponse() {
        return response;
    }
    public LiveData<List<FollowsItem>> getFollowingResponse() {
        return responseFollowing;
    }
    public void getFollowerList(int userid){
        disposable.add(userRepo.getFollowerList(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> loadingStatus.setValue(true))
                .doAfterTerminate(() -> loadingStatus.setValue(false))
                .subscribeWith(new DisposableSingleObserver<FollowListResponse>() {
                    @Override
                    public void onSuccess(@NonNull FollowListResponse postResponse) {
                        response.setValue(postResponse.getFollowers());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        onError.setValue(ErrorUtils.showError(e));
                    }
                }));
    }

    public void getFollowingList(int userid){
        disposable.add(userRepo.getFollowingList(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> loadingStatus.setValue(true))
                .doAfterTerminate(() -> loadingStatus.setValue(false))
                .subscribeWith(new DisposableSingleObserver<FollowListResponse>() {
                    @Override
                    public void onSuccess(@NonNull FollowListResponse postResponse) {
                        responseFollowing.setValue(postResponse.getFollowings());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        onError.setValue(ErrorUtils.showError(e));
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
