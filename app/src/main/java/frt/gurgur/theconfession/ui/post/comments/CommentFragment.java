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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.databinding.FragmentCommentBinding;
import frt.gurgur.theconfession.model.comment.CommentsItem;
import frt.gurgur.theconfession.model.main.DataItem;
import frt.gurgur.theconfession.ui.ViewModelFactory;
import frt.gurgur.theconfession.ui.adapters.CommentListAdapter;
import frt.gurgur.theconfession.ui.adapters.PostListAdapter;
import frt.gurgur.theconfession.ui.base.BaseFragment;
import frt.gurgur.theconfession.ui.main.MainViewModel;
import frt.gurgur.theconfession.ui.post.PostViewModel;
import frt.gurgur.theconfession.util.PreferencesHelper;
import frt.gurgur.theconfession.util.SimpleDividerItemDecoration;
import frt.gurgur.theconfession.util.Utils;


public class CommentFragment extends BaseFragment {
    FragmentCommentBinding binding;
    @Inject
    ViewModelFactory vmFactory;
    MainViewModel vm;

    @BindView(R.id.rcycleCommentList)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    GridLayoutManager gridLayoutManager;

    int postId = 0;
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
        vm = ViewModelProviders.of(this, vmFactory).get(MainViewModel.class);
        postId = getArguments().getInt("post_id");
        vm.loadCommentList(page,postId);
        observeCommentList();
        observeLoadStatus();
        observerErrorStatus();
        setRecyclerView();

    }


    @Override
    protected void observerErrorStatus() {
        vm.getErrorStatus().observe(this,
                error -> {
                    if (error != null) {
                        onError(getContext(), error);
                        showProgressBar(false);
                        Log.e("fff", "Error");
                        isLastPage = true;
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
                        Log.e("fff","addfooter");
                    }else {
                        isLastPage = true;
                        Log.e("fff","isLastPage True");
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
                    Log.e("fff" , "visibleItemCount : " + visibleItemCount + "*******" + "totalItemCount : " + totalItemCount+ "*******" + "firstVisibleItemPosition : " + firstVisibleItemPosition);
                }
            }
        }
    };

}