package frt.gurgur.theconfession.ui.main;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import frt.gurgur.theconfession.model.post.PostFavRequestModel;
import frt.gurgur.theconfession.model.stories.StoriessItem;
import frt.gurgur.theconfession.ui.adapters.StoryListAdapter;
import frt.gurgur.theconfession.ui.listeners.CommentClickListener;
import frt.gurgur.theconfession.ui.listeners.FavClickListener;
import frt.gurgur.theconfession.ui.listeners.OnItemClickListener;
import frt.gurgur.theconfession.ui.listeners.ProfileClickListener;
import frt.gurgur.theconfession.ui.listeners.StoryClickListener;
import frt.gurgur.theconfession.ui.post.PostFragment;
import frt.gurgur.theconfession.ui.post.PostViewModel;
import frt.gurgur.theconfession.ui.post.comments.CommentFragment;
import frt.gurgur.theconfession.ui.user.profile.ProfileFragment;
import frt.gurgur.theconfession.util.PreferencesHelper;
import frt.gurgur.theconfession.model.main.DataItem;
import frt.gurgur.theconfession.ui.ViewModelFactory;
import frt.gurgur.theconfession.ui.adapters.PostListAdapter;
import frt.gurgur.theconfession.ui.base.BaseFragment;
import frt.gurgur.theconfession.util.SimpleDividerItemDecoration;

public class MainFragment extends BaseFragment implements View.OnClickListener {

    ViewDataBinding binding;
    @Inject
    ViewModelFactory vmFactory;
    MainViewModel vm;
    PostViewModel post_vm;

    @BindView(R.id.rcyclePostList)
   public RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.fabPost)
    FloatingActionButton fabPost;


    @BindView(R.id.rcyclerStoryList)
    RecyclerView rcyclerStoryList;

    @Inject
    PreferencesHelper preferencesHelper;

    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;

    int userId;
    public int page = 1;
    public static final int PAGE_SIZE = 10;
    public boolean isLastPage = false;



    private List<DataItem> postList = new ArrayList<>();
    private List<StoriessItem> storylist = new ArrayList<>();



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
        showToolbar(true);
        showNavigation(true);

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

        initView();
        vm = ViewModelProviders.of(this, vmFactory).get(MainViewModel.class);
        post_vm = ViewModelProviders.of(this, vmFactory).get(PostViewModel.class);
        userId = preferencesHelper.getUserId();
        vm.loadPostList(page,userId);
        observePostList();
        observeLoadStatus();
        observerErrorStatus();
        setRecyclerView();


        setStoryAdapter();
        observeStoryList();
        vm.loadStoryList();


    }


    public void initView(){
        fabPost.setOnClickListener(this);
    }


    public void observeStoryList(){
        vm.getStoryList().observe(this, new Observer<List<StoriessItem>>() {
            @Override
            public void onChanged(List<StoriessItem> storiessItems) {
                storylist.addAll(storiessItems);
                rcyclerStoryList.getAdapter().notifyDataSetChanged();
                Toast.makeText(mActivity, storiessItems.get(0).getStoryUrl(), Toast.LENGTH_SHORT).show();
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
                    }else {
                        isLastPage = true;
                    }

                }
            }
        });
    }
    PostListAdapter adapter;
    StoryListAdapter storyListAdapter;
    private void setRecyclerView() {
        gridLayoutManager = new GridLayoutManager(getContext(),1);
        adapter = new PostListAdapter(postList,imageClick,favClickListener,commentClickListener,profileClickListener);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);
    }

    private void setStoryAdapter(){
        linearLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        storyListAdapter = new StoryListAdapter(storylist,storyClickListener);
        rcyclerStoryList.setLayoutManager(linearLayoutManager);
        rcyclerStoryList.setAdapter(storyListAdapter);
        recyclerView.setHasFixedSize(true);

    }


    StoryClickListener storyClickListener = new StoryClickListener() {
        @Override
        public void onStoryClick(int position) {

            StoryFragment fragment = new StoryFragment();
            Bundle arguments = new Bundle();
            arguments.putInt("position",position);
            fragment.setArguments(arguments);
            multipleStackNavigator.start(fragment);
        }
    };



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
        public void favClick(DataItem item,int pos) {
            PostFavRequestModel favModel = new PostFavRequestModel(item.getId(),userId);
            post_vm.favPost(favModel);

            if (item.getSelfLikes().equals("false") ){
                item.setLikeCount(item.getLikeCount()+1);
                item.setSelfLikes("true");
            }else{
                item.setLikeCount(item.getLikeCount()-1);
                item.setSelfLikes("false");
            }

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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabPost:
                multipleStackNavigator.start(new PostFragment());
                break;
        }
    }


    public void loadStories(){
        //bundle ile pos gonder ona göre ac
        multipleStackNavigator.start(new StoryFragment());
    }




}