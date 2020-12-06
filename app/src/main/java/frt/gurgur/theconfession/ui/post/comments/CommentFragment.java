package frt.gurgur.theconfession.ui.post.comments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.databinding.FragmentCommentBinding;
import frt.gurgur.theconfession.model.APIResponseModel;
import frt.gurgur.theconfession.model.comment.CommentsItem;
import frt.gurgur.theconfession.model.comment.CreateCommentRequestModel;
import frt.gurgur.theconfession.model.main.DataItem;
import frt.gurgur.theconfession.ui.ViewModelFactory;
import frt.gurgur.theconfession.ui.adapters.CommentListAdapter;
import frt.gurgur.theconfession.ui.adapters.PostListAdapter;
import frt.gurgur.theconfession.ui.base.BaseFragment;
import frt.gurgur.theconfession.ui.main.MainViewModel;
import frt.gurgur.theconfession.ui.post.PostViewModel;
import frt.gurgur.theconfession.util.Constants;
import frt.gurgur.theconfession.util.Helper;
import frt.gurgur.theconfession.util.PreferencesHelper;
import frt.gurgur.theconfession.util.SimpleDividerItemDecoration;
import frt.gurgur.theconfession.util.Utils;


public class CommentFragment extends BaseFragment {
    FragmentCommentBinding binding;
    @Inject
    ViewModelFactory vmFactory;
    MainViewModel vm;
    @Inject
    PreferencesHelper preferencesHelper;
    @BindView(R.id.etComment)
    EditText etComment;
    @BindView(R.id.tvShare)
    TextView tvShare;
    @BindView(R.id.rcycleCommentList)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tvNoCommentWarning)
    TextView tvNoCommentWarning;


    GridLayoutManager gridLayoutManager;

    int postId = 0;
    int userId = 0;
    public int page = 1;
    public static final int PAGE_SIZE = 10;
    public boolean isLastPage = false;

    private List<CommentsItem> commentList = new ArrayList<>();

    public CommentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
        showBackButton(true);

        vm = ViewModelProviders.of(this, vmFactory).get(MainViewModel.class);
        postId = getArguments().getInt("post_id");
        userId = preferencesHelper.getUserId();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (multipleStackNavigator.canGoBack()){
            showBackButton(true);
        }else {
            showBackButton(false);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment, container, false);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

        vm.loadCommentList(page,postId);
        observeCommentList();
        observeLoadStatus();
        observerErrorStatus();
        setRecyclerView();
        observeAddComment();

    }

    public void initView(){

        Helper.keyboardVisibility(getContext(), getView(), new Constants.KeyboardVisibility() {
            @Override
            public void onKeyboardOpen() {
                showNavigation(false);
            }

            @Override
            public void onKeyboardClose() {
                showNavigation(true);
            }
        });

        etComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length()==0){
                    tvShare.setAlpha(0.45f);
                    tvShare.setEnabled(false);
                }else {
                    tvShare.setAlpha(1);
                    tvShare.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateCommentRequestModel requestModel = new CreateCommentRequestModel(etComment.getText().toString().trim(),userId,postId);
                vm.AddComment(requestModel);
            }
        });
    }

    public void observeAddComment(){
        vm.getAddCommentResponse().observe(this, new Observer<APIResponseModel>() {
            @Override
            public void onChanged(APIResponseModel apiResponseModel) {
                if (apiResponseModel.getStatus()==1){
                    //başarılı

                    CommentsItem newComment = new CommentsItem();
                    newComment.setComment(etComment.getText().toString().trim());
                    newComment.setFullname(preferencesHelper.getUserFullname());
                    newComment.setPhoto(preferencesHelper.getPhoto());
                    commentList.add(newComment);
                    etComment.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


    @Override
    protected void observerErrorStatus() {
        vm.getErrorStatus().observe(this,
                error -> {
                    if (error != null) {

                        showProgressBar(false);
                        isLastPage = true;
                        if (error.getStatus()==404){
                            recyclerView.setVisibility(View.GONE);
                            tvNoCommentWarning.setVisibility(View.VISIBLE);
                        }else {
                            recyclerView.setVisibility(View.VISIBLE);
                            tvNoCommentWarning.setVisibility(View.GONE);
                        }

                    }
                });
    }



    @Override
    protected void observeLoadStatus() {
        vm.getLoadingStatus().observe(
                this,
                isLoading -> showProgressBar(isLoading)
        );
    }
    private void showProgressBar(boolean isVisible) {
        if (isVisible)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }
    public void observeCommentList(){
        vm.getCommentList().observe(this, new Observer<List<CommentsItem>>() {
            @Override
            public void onChanged(List<CommentsItem> dataItems) {
                if (dataItems != null) {
                    commentList.addAll(dataItems);
                    recyclerView.getAdapter().notifyDataSetChanged();

                    if (dataItems.size() >= PAGE_SIZE){
                        //addfooter
                    }else {
                        isLastPage = true;
                    }

                }
            }
        });
    }


    CommentListAdapter adapter;
    private void setRecyclerView() {
        gridLayoutManager = new GridLayoutManager(getContext(),1);
        adapter = new CommentListAdapter(commentList);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);

    }
    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = gridLayoutManager.getChildCount();
            int totalItemCount = gridLayoutManager.getItemCount();
            int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();

            if (!vm.getLoadingStatus().getValue() && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    page=page+1;
                    vm.loadCommentList(page,postId);
                }
            }
        }
    };

}