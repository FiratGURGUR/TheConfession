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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
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
import frt.gurgur.theconfession.databinding.FragmentExploreBinding;
import frt.gurgur.theconfession.databinding.FragmentExploreDetailBinding;
import frt.gurgur.theconfession.model.hashtag.HashtagsItem;
import frt.gurgur.theconfession.model.main.DataItem;
import frt.gurgur.theconfession.model.post.PostFavRequestModel;
import frt.gurgur.theconfession.ui.ViewModelFactory;
import frt.gurgur.theconfession.ui.adapters.HashtagListAdapter;
import frt.gurgur.theconfession.ui.adapters.PostListAdapter;
import frt.gurgur.theconfession.ui.base.BaseFragment;
import frt.gurgur.theconfession.ui.listeners.CommentClickListener;
import frt.gurgur.theconfession.ui.listeners.FavClickListener;
import frt.gurgur.theconfession.ui.listeners.HashtagClickListener;
import frt.gurgur.theconfession.ui.listeners.OnItemClickListener;
import frt.gurgur.theconfession.ui.listeners.ProfileClickListener;
import frt.gurgur.theconfession.ui.main.MainViewModel;
import frt.gurgur.theconfession.ui.post.PostViewModel;
import frt.gurgur.theconfession.ui.post.comments.CommentFragment;
import frt.gurgur.theconfession.ui.user.profile.ProfileFragment;
import frt.gurgur.theconfession.util.PreferencesHelper;
import frt.gurgur.theconfession.util.SimpleDividerItemDecoration;


public class ExploreDetailFragment extends BaseFragment {

    FragmentExploreDetailBinding binding;
    @Inject
    ViewModelFactory vmFactory;
    MainViewModel vm;
    PostViewModel post_vm;
    @BindView(R.id.rcyclerExploreDetail)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.swipeRefreshLayoutExploreDetail)
    PullRefreshLayout swipeRefreshLayout;
    @Inject
    PreferencesHelper preferencesHelper;
    GridLayoutManager gridLayoutManager;
    int userId;
    String hashtag;
    PostListAdapter adapter;

    private List<DataItem> postlist = new ArrayList<>();


    public ExploreDetailFragment() {
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
        vm = ViewModelProviders.of(this, vmFactory).get(MainViewModel.class);
        post_vm = ViewModelProviders.of(this, vmFactory).get(PostViewModel.class);

        userId = preferencesHelper.getUserId();
        hashtag = getArguments().getString("hashtag");
        Log.e("fff" , userId + "--- " + hashtag);
        vm.loadExploreList(hashtag,userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_explore_detail, container, false);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        observePostList();
        observeLoadStatus();
        observerErrorStatus();
        setRecyclerView();

        swipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearList();
                vm.loadExploreList(hashtag,userId);
            }
        });

    }

    public void clearList(){
        postlist.clear();
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void setRecyclerView() {
        gridLayoutManager = new GridLayoutManager(getContext(),1);
        adapter = new PostListAdapter(getContext(),postlist,imageClick,favClickListener,commentClickListener,profileClickListener,hashtagClickListener);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
    }


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


    HashtagClickListener hashtagClickListener = new HashtagClickListener() {
        @Override
        public void clickHashtag(String hashtag) {
            Toast.makeText(mActivity, hashtag + " tıklandı.", Toast.LENGTH_SHORT).show();
        }
    };

    FavClickListener favClickListener = new FavClickListener() {
        @Override
        public void favClick(DataItem item,int pos) {
            PostFavRequestModel favModel = new PostFavRequestModel(item.getId(),userId);
            post_vm.favPost(favModel);

            postlist.remove(pos);
            adapter.notifyDataSetChanged();
        }
    };


    CommentClickListener commentClickListener = new CommentClickListener() {
        @Override
        public void openCommentClick(int post_id) {
            Bundle arguments = new Bundle();
            arguments.putInt("post_id", post_id);
            CommentFragment commentFragment = new CommentFragment();
            commentFragment.setArguments(arguments);
            multipleStackNavigator.start(commentFragment);
        }
    };

    ProfileClickListener profileClickListener = new ProfileClickListener() {
        @Override
        public void showProfile(int user_Id) {

            Bundle arguments = new Bundle();
            arguments.putInt("userId", user_Id);
            ProfileFragment profileFragment = new ProfileFragment();
            profileFragment.setArguments(arguments);
            multipleStackNavigator.start(profileFragment);

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
    public void observePostList(){
        vm.getExploreList().observe(this, new Observer<List<DataItem>>() {
            @Override
            public void onChanged(List<DataItem> dataItems) {
                swipeRefreshLayout.setRefreshing(false);
                if (dataItems != null) {
                    postlist.addAll(dataItems);
                    recyclerView.getAdapter().notifyDataSetChanged();

                }
            }
        });
    }

}