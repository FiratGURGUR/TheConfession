package frt.gurgur.theconfession.ui.main;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
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
import android.widget.Toast;


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
import frt.gurgur.theconfession.model.APIResponseModel;
import frt.gurgur.theconfession.model.post.PostFavRequestModel;
import frt.gurgur.theconfession.ui.adapters.FavClickListener;
import frt.gurgur.theconfession.ui.adapters.OnItemClickListener;
import frt.gurgur.theconfession.ui.post.PostViewModel;
import frt.gurgur.theconfession.util.PreferencesHelper;
import frt.gurgur.theconfession.model.main.DataItem;
import frt.gurgur.theconfession.ui.ViewModelFactory;
import frt.gurgur.theconfession.ui.adapters.PostListAdapter;
import frt.gurgur.theconfession.ui.base.BaseFragment;
import frt.gurgur.theconfession.util.SimpleDividerItemDecoration;
import frt.gurgur.theconfession.util.Utils;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;
import static frt.gurgur.theconfession.util.Constants.FAV_ADDED;
import static frt.gurgur.theconfession.util.Constants.FAV_DELETED;
import static frt.gurgur.theconfession.util.Constants.FAV_ERROR;

public class MainFragment extends BaseFragment {
    ViewDataBinding binding;
    @Inject
    ViewModelFactory vmFactory;
    MainViewModel vm;
    PostViewModel post_vm;

    @BindView(R.id.rcyclePostList)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Inject
    PreferencesHelper preferencesHelper;

    GridLayoutManager gridLayoutManager;

    int userId;
    public int page = 1;
    public static final int PAGE_SIZE = 10;
    public boolean isLastPage = false;



    private List<DataItem> postList = new ArrayList<>();



    public MainFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = ViewModelProviders.of(this, vmFactory).get(MainViewModel.class);
        post_vm = ViewModelProviders.of(this, vmFactory).get(PostViewModel.class);

        userId = preferencesHelper.getUserId();

        vm.loadPostList(page,userId);
        observePostList();
        observeLoadStatus();
        observerErrorStatus();
        setRecyclerView();

        Log.e("boyut" , Utils.dpToPx(20)+ "");

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
    public void observePostList(){
        vm.getPostList().observe(this, new Observer<List<DataItem>>() {
            @Override
            public void onChanged(List<DataItem> dataItems) {
                if (dataItems != null) {
                    postList.addAll(dataItems);
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
    PostListAdapter adapter;
    private void setRecyclerView() {
        gridLayoutManager = new GridLayoutManager(getContext(),1);
        adapter = new PostListAdapter(postList,imageClick,favClickListener);
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
                   vm.loadPostList(page,userId);
                   Log.e("fff" , "visibleItemCount : " + visibleItemCount + "*******" + "totalItemCount : " + totalItemCount+ "*******" + "firstVisibleItemPosition : " + firstVisibleItemPosition);
                }
            }
        }
    };

    OnItemClickListener imageClick = new OnItemClickListener() {
        @Override
        public void onItemClick(DataItem item) {
            new StfalconImageViewer.Builder<String>(getContext(), new ArrayList<>(Arrays.asList(item.getContentImage())), new ImageLoader<String>() {
                @Override
                public void loadImage(ImageView imageView, String image) {
                    Glide.with(getContext())
                            .load(image)
                            .into(imageView);
                }
            }).show();
        }
    };

    FavClickListener favClickListener = new FavClickListener() {
        @Override
        public void favClick(DataItem item) {
            PostFavRequestModel favModel = new PostFavRequestModel(item.getId(),userId);
            post_vm.favPost(favModel);

            if (item.getSelfLikes().equals("false") ){
                item.setLikeCount(item.getLikeCount()+1);
                item.setSelfLikes("true");
            }else{
                item.setLikeCount(item.getLikeCount()-1);
                item.setSelfLikes("false");
            }
            recyclerView.getAdapter().notifyDataSetChanged();


        }
    };



}