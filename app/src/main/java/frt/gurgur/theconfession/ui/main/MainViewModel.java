package frt.gurgur.theconfession.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import frt.gurgur.theconfession.data.remote.repo.MainRepo;
import frt.gurgur.theconfession.data.remote.repo.UserRepo;
import frt.gurgur.theconfession.model.main.DataItem;
import frt.gurgur.theconfession.model.main.PostResponse;
import frt.gurgur.theconfession.model.user.UserResponse;
import frt.gurgur.theconfession.ui.base.BaseViewModel;
import frt.gurgur.theconfession.util.ErrorUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends BaseViewModel {

    private static final String TAG = "MainViewModel";
    private final MainRepo mainRepo;
    private CompositeDisposable disposable;
    private MutableLiveData<List<DataItem>> postList = new MutableLiveData<>();

    @Inject
    public MainViewModel(MainRepo mainRepo) {
        this.mainRepo = mainRepo;
        disposable = new CompositeDisposable();
    }

    public LiveData<List<DataItem>> getPostList() {
        return postList;
    }

    public void loadPostList(int page,int user_id) {

        disposable.add(mainRepo.getPostList(page,user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> loadingStatus.setValue(true))
                .doAfterTerminate(() -> loadingStatus.setValue(false))
                .subscribeWith(new DisposableSingleObserver<PostResponse>() {

                    @Override
                    public void onSuccess(PostResponse resultsResponse) {
                        postList.setValue(resultsResponse.getData());

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
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
