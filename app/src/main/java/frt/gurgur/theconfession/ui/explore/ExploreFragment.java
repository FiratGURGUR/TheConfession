package frt.gurgur.theconfession.ui.explore;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.databinding.FragmentExploreBinding;
import frt.gurgur.theconfession.databinding.FragmentFavoritiesBinding;
import frt.gurgur.theconfession.model.APIResponseModel;
import frt.gurgur.theconfession.model.hashtag.HashtagsItem;
import frt.gurgur.theconfession.model.main.DataItem;
import frt.gurgur.theconfession.ui.ViewModelFactory;
import frt.gurgur.theconfession.ui.adapters.HashtagListAdapter;
import frt.gurgur.theconfession.ui.adapters.PostListAdapter;
import frt.gurgur.theconfession.ui.base.BaseFragment;
import frt.gurgur.theconfession.ui.listeners.HashtagClickListener;
import frt.gurgur.theconfession.ui.main.MainViewModel;
import frt.gurgur.theconfession.ui.post.PostViewModel;
import frt.gurgur.theconfession.util.PreferencesHelper;
import frt.gurgur.theconfession.util.SimpleDividerItemDecoration;


public class ExploreFragment extends BaseFragment {

    FragmentExploreBinding binding;
    @Inject
    ViewModelFactory vmFactory;
    PostViewModel vm;
    @BindView(R.id.rcyclerHashtagList)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.swipeRefreshLayoutExplore)
    PullRefreshLayout swipeRefreshLayout;
    @Inject
    PreferencesHelper preferencesHelper;
    GridLayoutManager gridLayoutManager;
    HashtagListAdapter adapter;
    private List<HashtagsItem> hashtagList = new ArrayList<>();

    public ExploreFragment() {
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
        showBackButton(false);
        vm = ViewModelProviders.of(this, vmFactory).get(PostViewModel.class);

        vm.loadHashtagList();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_explore, container, false);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        observeHashtagList();
        observeLoadStatus();
        observerErrorStatus();
        setRecyclerView();


        swipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearList();
                vm.loadHashtagList();
            }
        });

    }

    public void observeHashtagList(){
        vm.getHashTagList().observe(this, new Observer<List<HashtagsItem>>() {
            @Override
            public void onChanged(List<HashtagsItem> hashtagsItems) {
                swipeRefreshLayout.setRefreshing(false);
                if (hashtagsItems != null) {
                    hashtagList.addAll(hashtagsItems);
                    recyclerView.getAdapter().notifyDataSetChanged();

                }
            }
        });
    }


    public void clearList(){
        hashtagList.clear();
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void setRecyclerView() {
        gridLayoutManager = new GridLayoutManager(getContext(),1);
        adapter = new HashtagListAdapter(hashtagList,listener);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
    }


    HashtagClickListener listener = new HashtagClickListener() {
        @Override
        public void clickHashtag(String hashtag) {
            ExploreDetailFragment fragment = new ExploreDetailFragment();
            Bundle arguments = new Bundle();
            arguments.putString("hashtag", hashtag);
            fragment.setArguments(arguments);
            multipleStackNavigator.start(fragment);

        }
    };


    @Override
    protected void observerErrorStatus() {
        vm.getErrorStatus().observe(this,
                error -> {
                    if (error != null) {
                        showProgressBar(false);
                        onError(getContext(),error.getMessage());
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


}