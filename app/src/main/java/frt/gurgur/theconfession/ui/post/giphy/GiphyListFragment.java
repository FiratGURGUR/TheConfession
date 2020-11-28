package frt.gurgur.theconfession.ui.post.giphy;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.stfalcon.imageviewer.StfalconImageViewer;
import com.stfalcon.imageviewer.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.databinding.FragmentGiphyListBinding;
import frt.gurgur.theconfession.model.post.PostFavRequestModel;
import frt.gurgur.theconfession.model.post.giphy.DataItem;
import frt.gurgur.theconfession.ui.ViewModelFactory;
import frt.gurgur.theconfession.ui.adapters.CommentClickListener;
import frt.gurgur.theconfession.ui.adapters.FavClickListener;
import frt.gurgur.theconfession.ui.adapters.GiphyListAdapter;
import frt.gurgur.theconfession.ui.adapters.OnItemClickListener;
import frt.gurgur.theconfession.ui.adapters.PostListAdapter;
import frt.gurgur.theconfession.ui.adapters.ProfileClickListener;
import frt.gurgur.theconfession.ui.base.BaseFragment;
import frt.gurgur.theconfession.ui.main.MainViewModel;
import frt.gurgur.theconfession.ui.post.PostViewModel;
import frt.gurgur.theconfession.ui.post.comments.CommentFragment;
import frt.gurgur.theconfession.ui.user.profile.ProfileFragment;
import frt.gurgur.theconfession.util.SimpleDividerItemDecoration;


public class GiphyListFragment extends BaseFragment {

    FragmentGiphyListBinding binding;
    @Inject
    ViewModelFactory vmFactory;
    MainViewModel vm;

    @BindView(R.id.rcycleGiphyList)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    GridLayoutManager gridLayoutManager;

    public int page = 1;
    public static final int PAGE_SIZE = 10;
    public boolean isLastPage = false;
    private List<DataItem> giphyList = new ArrayList<>();


    public GiphyListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showBackButton(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        showBackButton(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_giphy_list, container, false);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = ViewModelProviders.of(this, vmFactory).get(MainViewModel.class);


        vm.loadGiphyList(page);
        observePostList();
        observeLoadStatus();
        observerErrorStatus();
        setRecyclerView();
    }





    @Override
    protected void observerErrorStatus() {
        vm.getErrorStatus().observe(this,
                error -> {
                    if (error != null) {
                        onError(getContext(), error.getMessage());
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
    public void observePostList(){
        vm.getGiphyList().observe(this, new Observer<List<frt.gurgur.theconfession.model.post.giphy.DataItem>>() {
            @Override
            public void onChanged(List<frt.gurgur.theconfession.model.post.giphy.DataItem> dataItems) {
                if (dataItems != null) {
                    giphyList.addAll(dataItems);
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
    GiphyListAdapter adapter;
    private void setRecyclerView() {
        gridLayoutManager = new GridLayoutManager(getContext(),2);
        adapter = new GiphyListAdapter(giphyList);
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
                    vm.loadGiphyList(page);
                    Log.e("fff" , "visibleItemCount : " + visibleItemCount + "*******" + "totalItemCount : " + totalItemCount+ "*******" + "firstVisibleItemPosition : " + firstVisibleItemPosition);
                }
            }
        }
    };












}