package frt.gurgur.theconfession.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import frt.gurgur.theconfession.data.remote.repo.MainRepo;
import frt.gurgur.theconfession.model.comment.CommentResponse;
import frt.gurgur.theconfession.model.comment.CommentsItem;
import frt.gurgur.theconfession.model.main.DataItem;
import frt.gurgur.theconfession.model.main.PostResponse;
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
    private MutableLiveData<List<CommentsItem>> commentList = new MutableLiveData<>();

    @Inject
    public MainViewModel(MainRepo mainRepo) {
        this.mainRepo = mainRepo;
        disposable = new CompositeDisposable();
    }

    public LiveData<List<DataItem>> getPostList() {
        return postList;
    }
    public LiveData<List<CommentsItem>> getCommentList() {
        return commentList;
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

    public void loadCommentList(int page,int post_id) {
        disposable.add(mainRepo.getCommentList(page,post_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> loadingStatus.setValue(true))
                .doAfterTerminate(() -> loadingStatus.setValue(false))
                .subscribeWith(new DisposableSingleObserver<CommentResponse>() {
                    @Override
                    public void onSuccess(CommentResponse commentResponse) {
                        commentList.setValue(commentResponse.getComments());
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
