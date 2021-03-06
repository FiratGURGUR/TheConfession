package frt.gurgur.theconfession.ui.user.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import frt.gurgur.theconfession.data.remote.repo.UserRepo;
import frt.gurgur.theconfession.model.APIResponseModel;
import frt.gurgur.theconfession.model.user.UserResponse;
import frt.gurgur.theconfession.model.user.follow.FollowUnfollowRequestModel;
import frt.gurgur.theconfession.ui.base.BaseViewModel;
import frt.gurgur.theconfession.model.user.RequestUser;
import frt.gurgur.theconfession.util.ErrorUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ProfileViewModel extends BaseViewModel {

    private final UserRepo userRepo;
    private CompositeDisposable disposable;
    private MutableLiveData<UserResponse> user = new MutableLiveData<>();
    private MutableLiveData<APIResponseModel> responseFollowUnfollow = new MutableLiveData<>();

    @Inject
    public ProfileViewModel(UserRepo userRepo) {
        this.userRepo = userRepo;
        disposable = new CompositeDisposable();
    }

    public LiveData<UserResponse> getUser() {
        return user;
    }
    public LiveData<APIResponseModel> getFollowUnfollowResponse() {
        return responseFollowUnfollow;
    }



    public void followUnfollowUser(FollowUnfollowRequestModel followUnfollowRequestModel){
        disposable.add(userRepo.followUnfollow(followUnfollowRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> loadingStatus.setValue(true))
                .doAfterTerminate(() -> loadingStatus.setValue(false))
                .subscribeWith(new DisposableSingleObserver<APIResponseModel>() {
                    @Override
                    public void onSuccess(@NonNull APIResponseModel apiResponseModel) {
                        responseFollowUnfollow.setValue(apiResponseModel);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        onError.setValue(ErrorUtils.showError(e));
                    }
                }));
    }

    public void getSingleUser(RequestUser requestUser) {

        disposable.add(userRepo.getSingleUSer(requestUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> loadingStatus.setValue(true))
                .doAfterTerminate(() -> loadingStatus.setValue(false))
                .subscribeWith(new DisposableSingleObserver<UserResponse>() {
                    @Override
                    public void onSuccess(UserResponse resultsResponse) {
                        user.setValue(resultsResponse);
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
