package frt.gurgur.theconfession.ui.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel extends ViewModel {

    protected final MutableLiveData<Boolean> loadingStatus = new  MutableLiveData<>();
    protected final MutableLiveData<String> onError = new  MutableLiveData<>();

    public MutableLiveData<Boolean> getLoadingStatus() {
        return loadingStatus;
    }

    public MutableLiveData<String> getErrorStatus() {
        return onError;
    }
}

