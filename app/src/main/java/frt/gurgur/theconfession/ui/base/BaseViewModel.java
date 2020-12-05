package frt.gurgur.theconfession.ui.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import frt.gurgur.theconfession.model.APIError;

public abstract class BaseViewModel extends ViewModel {

    protected final MutableLiveData<Boolean> loadingStatus = new  MutableLiveData<>();
    protected final MutableLiveData<APIError> onError = new  MutableLiveData<>();




    public MutableLiveData<Boolean> getLoadingStatus() {
        return loadingStatus;
    }

    public MutableLiveData<APIError> getErrorStatus() {
        return onError;
    }

}

