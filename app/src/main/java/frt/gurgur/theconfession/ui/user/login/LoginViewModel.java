package frt.gurgur.theconfession.ui.user.login;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import javax.inject.Inject;

import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.data.remote.repo.UserRepo;
import frt.gurgur.theconfession.model.ValidationModel;
import frt.gurgur.theconfession.model.user.UserResponse;
import frt.gurgur.theconfession.ui.base.BaseViewModel;
import frt.gurgur.theconfession.model.user.RequestUser;
import frt.gurgur.theconfession.util.ErrorUtils;
import frt.gurgur.theconfession.util.Helper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel {

    private final UserRepo userRepo;
    private CompositeDisposable disposable;
    private MutableLiveData<UserResponse> user = new MutableLiveData<>();
    private MutableLiveData<ValidationModel> loginValidation = new MutableLiveData<>();

    @Inject
    Context context;

    @Inject
    public LoginViewModel(UserRepo userRepo) {
        this.userRepo = userRepo;
        disposable = new CompositeDisposable();
    }

    public LiveData<UserResponse> getUser() {
        return user;
    }
    public LiveData<ValidationModel> getLoginValidation() {
        return loginValidation;
    }

    public void setLoginValidation(String email,String password){
        ValidationModel model= new ValidationModel();
        if (email.isEmpty() || password.isEmpty()){
            model.setValid(false);
            model.setErrorMessage(context.getString(R.string.login_validation_error));
        }else {
            if (Helper.isValidEmail(email)){
                model.setValid(true);
            }else {
                model.setValid(false);
                model.setErrorMessage(context.getString(R.string.enter_valid_email));
            }
        }
        loginValidation.setValue(model);
    }

    public void loadData(RequestUser requestUser) {

        disposable.add(userRepo.loginUser(requestUser)
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
