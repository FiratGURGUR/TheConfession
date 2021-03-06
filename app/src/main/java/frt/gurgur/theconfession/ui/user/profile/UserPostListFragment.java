package frt.gurgur.theconfession.ui.user.profile;

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
import android.widget.LinearLayout;
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
import frt.gurgur.theconfession.databinding.FragmentUserPostListBinding;
import frt.gurgur.theconfession.model.main.DataItem;
import frt.gurgur.theconfession.model.post.PostFavRequestModel;
import frt.gurgur.theconfession.ui.ViewModelFactory;
import frt.gurgur.theconfession.ui.listeners.CommentClickListener;
import frt.gurgur.theconfession.ui.listeners.FavClickListener;
import frt.gurgur.theconfession.ui.listeners.HashtagClickListener;
import frt.gurgur.theconfession.ui.listeners.OnItemClickListener;
import frt.gurgur.theconfession.ui.adapters.PostListAdapter;
import frt.gurgur.theconfession.ui.listeners.ProfileClickListener;
import frt.gurgur.theconfession.ui.base.BaseFragment;
import frt.gurgur.theconfession.ui.main.MainViewModel;
import frt.gurgur.theconfession.ui.post.PostViewModel;
import frt.gurgur.theconfession.ui.post.comments.CommentFragment;
import frt.gurgur.theconfession.util.SimpleDividerItemDecoration;


public class UserPostListFragment extends BaseFragment{

    FragmentUserPostListBinding binding;
    @Inject
    ViewModelFactory vmFactory;
    MainViewModel vm;
    PostViewModel post_vm;

    @BindView(R.id.rcycleSharedPostList)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.emptyLayout)
    LinearLayout emptyLayout;


    GridLayoutManager gridLayoutManager;

    int userId;
    public int page = 1;
    public static final int PAGE_SIZE = 10;
    public boolean isLastPage = false;
    private List<DataItem> postList = new ArrayList<>();

    public UserPostListFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_post_list, container, false);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = ViewModelProviders.of(this, vmFactory).get(MainViewModel.class);
        post_vm = ViewModelProviders.of(this, vmFactory).get(PostViewModel.class);

        userId = getArguments().getInt("userId");
        vm.loadSharedPostList(page,userId);
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
                        showProgressBar(false);
                        if (error.getStatus()==404){
                            setWarning(true);
                        }else {
                            setWarning(false);
                        }
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

    public void setWarning(boolean warning){
        if (warning){
            emptyLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        }
    }

    private void showProgressBar(boolean isVisible) {
        if (isVisible)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }
    public void observePostList(){
        vm.getSharedPostList().observe(this, new Observer<List<DataItem>>() {
            @Override
            public void onChanged(List<DataItem> dataItems) {
                if (dataItems != null) {
                    setWarning(false);
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
    private void setRecyclerView() {
        gridLayoutManager = new GridLayoutManager(getContext(),1);
        adapter = new PostListAdapter(getContext(),postList,imageClick,favClickListener,commentClickListener,profileClickListener,hashtagClickListener);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);

    }

    HashtagClickListener hashtagClickListener = new HashtagClickListener() {
        @Override
        public void clickHashtag(String hashtag) {
            Toast.makeText(mActivity, hashtag + " tıklandı.", Toast.LENGTH_SHORT).show();
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
                    vm.loadSharedPostList(page,userId);
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

            if (multipleStackNavigator.getCurrentFragment() instanceof ProfileFragment){

            }else {
                Bundle arguments = new Bundle();
                arguments.putInt("userId", user_Id);
                ProfileFragment profileFragment = new ProfileFragment();
                profileFragment.setArguments(arguments);
                multipleStackNavigator.start(profileFragment);
            }


        }
    };

}