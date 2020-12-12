package frt.gurgur.theconfession.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import frt.gurgur.theconfession.data.remote.repo.MainRepo;
import frt.gurgur.theconfession.model.APIResponseModel;
import frt.gurgur.theconfession.model.comment.CommentResponse;
import frt.gurgur.theconfession.model.comment.CommentsItem;
import frt.gurgur.theconfession.model.comment.CreateCommentRequestModel;
import frt.gurgur.theconfession.model.main.DataItem;
import frt.gurgur.theconfession.model.main.PostResponse;
import frt.gurgur.theconfession.model.post.giphy.GiphyModel;
import frt.gurgur.theconfession.model.stories.StoriessItem;
import frt.gurgur.theconfession.model.stories.StoryListResponse;
import frt.gurgur.theconfession.ui.base.BaseViewModel;
import frt.gurgur.theconfession.util.ErrorUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends BaseViewModel {

    private static final String TAG = "MainViewModel";
    private final MainRepo mainRepo;
    private CompositeDisposable disposable;
    private MutableLiveData<List<DataItem>> postList = new MutableLiveData<>();
    private MutableLiveData<List<frt.gurgur.theconfession.model.post.giphy.DataItem>> giphyList = new MutableLiveData<>();
    private MutableLiveData<List<StoriessItem>> storyList = new MutableLiveData<>();
    private MutableLiveData<List<DataItem>> sharedPostList = new MutableLiveData<>();
    private MutableLiveData<List<DataItem>> favoritedPostList = new MutableLiveData<>();
    private MutableLiveData<List<CommentsItem>> commentList = new MutableLiveData<>();
    private MutableLiveData<APIResponseModel> responseAddComment = new MutableLiveData<>();

    @Inject
    public MainViewModel(MainRepo mainRepo) {
        this.mainRepo = mainRepo;
        disposable = new CompositeDisposable();
    }

    public LiveData<List<DataItem>> getPostList() {
        return postList;
    }
    public LiveData<List<frt.gurgur.theconfession.model.post.giphy.DataItem>> getGiphyList() {
        return giphyList;
    }
    public LiveData<List<StoriessItem>> getStoryList() {
        return storyList;
    }
    public LiveData<List<DataItem>> getSharedPostList() {
        return sharedPostList;
    }
    public LiveData<List<DataItem>> getFavoritedPostList() {
        return favoritedPostList;
    }
    public LiveData<List<CommentsItem>> getCommentList() {
        return commentList;
    }

    public LiveData<APIResponseModel> getAddCommentResponse() {
        return responseAddComment;
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
                        onError.setValue(ErrorUtils.showError(e));
                    }
                }));

    }

    public void loadGiphyList(int page) {

        disposable.add(mainRepo.getGiphyList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> loadingStatus.setValue(true))
                .doAfterTerminate(() -> loadingStatus.setValue(false))
                .subscribeWith(new DisposableSingleObserver<GiphyModel>() {

                    @Override
                    public void onSuccess(GiphyModel giphyModel) {
                        giphyList.setValue(giphyModel.getData());

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        onError.setValue(ErrorUtils.showError(e));
                    }
                }));

    }

    public void loadStoryList() {

        disposable.add(mainRepo.getStoryList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> loadingStatus.setValue(true))
                .doAfterTerminate(() -> loadingStatus.setValue(false))
                .subscribeWith(new DisposableSingleObserver<StoryListResponse>() {

                    @Override
                    public void onSuccess(StoryListResponse storyListResponse) {
                        storyList.setValue(storyListResponse.getStoriess());

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        onError.setValue(ErrorUtils.showError(e));
                    }
                }));

    }

    public void loadSharedPostList(int page,int user_id) {

        disposable.add(mainRepo.getSharedPostList(page,user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> loadingStatus.setValue(true))
                .doAfterTerminate(() -> loadingStatus.setValue(false))
                .subscribeWith(new DisposableSingleObserver<PostResponse>() {

                    @Override
                    public void onSuccess(PostResponse resultsResponse) {
                        sharedPostList.setValue(resultsResponse.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        onError.setValue(ErrorUtils.showError(e));
                    }
                }));

    }

    public void loadFavoritedPostList(int page,int user_id) {

        disposable.add(mainRepo.getFavoritedPostList(page,user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> loadingStatus.setValue(true))
                .doAfterTerminate(() -> loadingStatus.setValue(false))
                .subscribeWith(new DisposableSingleObserver<PostResponse>() {

                    @Override
                    public void onSuccess(PostResponse resultsResponse) {
                        favoritedPostList.setValue(resultsResponse.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        onError.setValue(ErrorUtils.showError(e));
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
                        onError.setValue(ErrorUtils.showError(e));
                    }
                }));

    }

    public void AddComment(CreateCommentRequestModel createCommentRequestModel){
        disposable.add(mainRepo.createComment(createCommentRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> loadingStatus.setValue(true))
                .doAfterTerminate(() -> loadingStatus.setValue(false))
                .subscribeWith(new DisposableSingleObserver<APIResponseModel>() {
                    @Override
                    public void onSuccess(@NonNull APIResponseModel apiResponseModel) {
                        responseAddComment.setValue(apiResponseModel);
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
