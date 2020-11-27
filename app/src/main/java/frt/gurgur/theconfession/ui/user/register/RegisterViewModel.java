package frt.gurgur.theconfession.ui.user.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import frt.gurgur.theconfession.model.APIResponseModel;
import frt.gurgur.theconfession.data.remote.repo.UserRepo;
import frt.gurgur.theconfession.ui.base.BaseViewModel;
import frt.gurgur.theconfession.ui.user.RequestUser;
import frt.gurgur.theconfession.util.ErrorUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class RegisterViewModel extends BaseViewModel {

    private static final String TAG = "RegisterViewModel";
    private final UserRepo userRepo;
    private CompositeDisposable disposable;
    private MutableLiveData<APIResponseModel> user = new MutableLiveData<>();

    @Inject
    public RegisterViewModel(UserRepo userRepo) {
        this.userRepo = userRepo;
        disposable = new CompositeDisposable();
    }

    public LiveData<APIResponseModel> getUser() {
        return user;
    }


    public void registerUser(RequestUser requestUser) {

        disposable.add(userRepo.registerUser(requestUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> loadingStatus.setValue(true))
                .doAfterTerminate(() -> loadingStatus.setValue(false))
                .subscribeWith(new DisposableSingleObserver<APIResponseModel>() {
                    @Override
                    public void onSuccess(APIResponseModel resultsResponse) {
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
