package frt.gurgur.theconfession.ui.user.profile;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.stfalcon.imageviewer.StfalconImageViewer;
import com.stfalcon.imageviewer.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.databinding.FragmentProfileCopyBinding;
import frt.gurgur.theconfession.model.APIResponseModel;
import frt.gurgur.theconfession.model.user.UserResponse;
import frt.gurgur.theconfession.model.user.follow.FollowUnfollowRequestModel;
import frt.gurgur.theconfession.ui.ViewModelFactory;
import frt.gurgur.theconfession.ui.adapters.ViewPagerAdapter;
import frt.gurgur.theconfession.ui.base.BaseFragment;
import frt.gurgur.theconfession.model.user.RequestUser;
import frt.gurgur.theconfession.ui.user.profile.followpage.FollowFragment;
import frt.gurgur.theconfession.util.CustomProgressDialogue;
import frt.gurgur.theconfession.util.PreferencesHelper;
import frt.gurgur.theconfession.util.Utils;


public class ProfileFragment extends BaseFragment implements View.OnClickListener{
    FragmentProfileCopyBinding binding;
    ProfileViewModel vm;
    public ViewPagerAdapter adapter;
    public int userId;
    public int my_userId;

    @Inject
    ViewModelFactory vmFactory;
    @Inject
    PreferencesHelper preferencesHelper;

    @BindView(R.id.layoutFollowerCount)
    LinearLayout layoutFollowerCount;
    @BindView(R.id.layoutFollowingCount)
    LinearLayout layoutFollowingCount;
    @BindView(R.id.layoutSharedCount)
    LinearLayout layoutSharedCount;
    @BindView(R.id.tablayoutProfile)
    TabLayout tabLayout;
    @BindView(R.id.viewpagerProfile)
    ViewPager viewPager;
    @BindView(R.id.profileAppBar)
    AppBarLayout profileAppBar;
    @BindView(R.id.editProfileButton)
    Button editProfileButton;
    @BindView(R.id.followButton)
    Button followButton;
    @BindView(R.id.cover_image)
    ImageView coverImage;
    @BindView(R.id.profile_image)
    ImageView profileImage;


    public ProfileFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_copy, container, false);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);

        Log.e("log","onCreateView");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = ViewModelProviders.of(this, vmFactory).get(ProfileViewModel.class);

        initView();
        observeSingleUser();
        observeLoadStatus();
        observerErrorStatus();
        observeFollowUnfollow();
        Log.e("log","onViewCreated");
    }

    public void initView(){
        userId = getArguments().getInt("userId");
        my_userId = preferencesHelper.getUserId();
        coverImage.setOnClickListener(this);
        profileImage.setOnClickListener(this);
        editProfileButton.setOnClickListener(this);
        followButton.setOnClickListener(this);

        if (userId == preferencesHelper.getUserId()){
            //benim profilimse
            editProfileButton.setVisibility(View.VISIBLE);
            followButton.setVisibility(View.GONE);
            showBackButton(false);
        }else {
            editProfileButton.setVisibility(View.GONE);
            followButton.setVisibility(View.VISIBLE);
            showBackButton(true);
        }

        if (Utils.getConnectionType(getContext()) != Utils.NO_CONNECTION){
            RequestUser user = new RequestUser(my_userId,userId);
            vm.getSingleUser(user);
        }

        layoutFollowerCount.setOnClickListener(this);
        layoutFollowingCount.setOnClickListener(this);
        layoutSharedCount.setOnClickListener(this);
        adapter = new ViewPagerAdapter(getChildFragmentManager());


        UserPostListFragment userPostListFragment = new UserPostListFragment();
        UserFavoritedPostListFragment userFavoritedPostListFragment = new UserFavoritedPostListFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("userId", userId);
        userPostListFragment.setArguments(arguments);
        userFavoritedPostListFragment.setArguments(arguments);

        adapter.addFrag(userPostListFragment, getString(R.string.profile_posts));
        adapter.addFrag(userFavoritedPostListFragment, getString(R.string.profile_favs));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }



    public void observeSingleUser(){
      vm.getUser().observe(this, new Observer<UserResponse>() {
          @Override
          public void onChanged(UserResponse userResponse) {
              binding.setSingleUser(userResponse.getUser());
          }
      });
    }



    @Override
    protected void observerErrorStatus() {
        vm.getErrorStatus().observe(this,
                error -> {
                    if (error != null) {
                        onError(getContext(), error.getMessage());
                    }
                });
    }

    @Override
    protected void observeLoadStatus() {
        vm.getLoadingStatus().observe(
                this,
                new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {

                    }});
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layoutFollowerCount:
                openFollowFragment("follower");
                break;
            case R.id.layoutFollowingCount:
                openFollowFragment("following");
                break;
            case R.id.layoutSharedCount:
                viewPager.setCurrentItem(0);
                profileAppBar.setExpanded(false);
                break;
            case R.id.cover_image:
                showFullScreenPhoto(vm.getUser().getValue().getUser().getCoverphoto());
                break;
            case R.id.profile_image:
                showFullScreenPhoto(vm.getUser().getValue().getUser().getPhoto());
                break;
            case R.id.editProfileButton:

                break;
            case R.id.followButton:
                followUnfollowClick();
                break;
        }
    }

    public void showFullScreenPhoto(String url){
        new StfalconImageViewer.Builder<String>(getContext(), new ArrayList<>(Arrays.asList(url)), new ImageLoader<String>() {
            @Override
            public void loadImage(ImageView imageView, String image) {
                Glide.with(getContext())
                        .load(image)
                        .into(imageView);
            }
        }).show();
    }


    public void openFollowFragment(String click){
        Bundle arguments = new Bundle();
        arguments.putString("whichClick", click);
        arguments.putInt("idForCounts", userId);
        FollowFragment followFragment = new FollowFragment();
        followFragment.setArguments(arguments);
        multipleStackNavigator.start(followFragment);
    }

    public void observeFollowUnfollow(){
        vm.getFollowUnfollowResponse().observe(this, new Observer<APIResponseModel>() {
            @Override
            public void onChanged(APIResponseModel apiResponseModel) {
                if (apiResponseModel.getStatus()==40){
                    vm.getUser().getValue().getUser().setFollowerCount(vm.getUser().getValue().getUser().getFollowerCount()+1);
                    vm.getUser().getValue().getUser().setIsTakip("true");
                    binding.setSingleUser(vm.getUser().getValue().getUser());
                }else if (apiResponseModel.getStatus()==30){
                    vm.getUser().getValue().getUser().setFollowerCount(vm.getUser().getValue().getUser().getFollowerCount()-1);
                    vm.getUser().getValue().getUser().setIsTakip("false");
                    binding.setSingleUser(vm.getUser().getValue().getUser());
                }
            }
        });
    }


    public void followUnfollowClick(){
        FollowUnfollowRequestModel model = new FollowUnfollowRequestModel(my_userId,userId);
        vm.followUnfollowUser(model);
    }

}