package frt.gurgur.theconfession.ui.user.profile;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.databinding.FragmentProfileBinding;
import frt.gurgur.theconfession.databinding.FragmentProfileCopyBinding;
import frt.gurgur.theconfession.model.user.UserResponse;
import frt.gurgur.theconfession.ui.ViewModelFactory;
import frt.gurgur.theconfession.ui.adapters.TabAdapter;
import frt.gurgur.theconfession.ui.base.BaseFragment;
import frt.gurgur.theconfession.ui.main.MainFragment;
import frt.gurgur.theconfession.ui.user.RequestUser;
import frt.gurgur.theconfession.ui.user.login.LoginViewModel;
import frt.gurgur.theconfession.ui.user.profile.followpage.FollowFragment;
import frt.gurgur.theconfession.ui.user.profile.followpage.FollowerListFragment;
import frt.gurgur.theconfession.ui.user.profile.followpage.FollowingListFragment;
import frt.gurgur.theconfession.util.PreferencesHelper;
import frt.gurgur.theconfession.util.Utils;


public class ProfileFragment extends BaseFragment implements View.OnClickListener{
    //ViewDataBinding binding;
    FragmentProfileCopyBinding binding;


    @Inject
    ViewModelFactory vmFactory;
    @Inject
    PreferencesHelper preferencesHelper;

    ProfileViewModel vm;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.layoutFollowerCount)
    LinearLayout layoutFollowerCount;

    @BindView(R.id.layoutFollowingCount)
    LinearLayout layoutFollowingCount;

    @BindView(R.id.tablayoutProfile)
    TabLayout tabLayout;
    @BindView(R.id.viewpagerProfile)
    ViewPager viewPager;
    public TabAdapter adapter;

    public int userId;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_copy, container, false);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = ViewModelProviders.of(this, vmFactory).get(ProfileViewModel.class);
        initView();

        observeSingleUser();
    }

    public void initView(){
        userId = getArguments().getInt("userId");
        if (Utils.getConnectionType(getContext()) != Utils.NO_CONNECTION){
            //bilgileri api den al
            RequestUser user = new RequestUser(userId);
            vm.getSingleUser(user);
        }
        layoutFollowerCount.setOnClickListener(this);
        layoutFollowingCount.setOnClickListener(this);

        adapter = new TabAdapter(getActivity().getSupportFragmentManager());

        UserPostListFragment userPostListFragment = new UserPostListFragment();
        UserFavoritedPostListFragment userFavoritedPostListFragment = new UserFavoritedPostListFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("userId", userId);
        userPostListFragment.setArguments(arguments);
        userFavoritedPostListFragment.setArguments(arguments);

        adapter.addFragment(userPostListFragment, "Gönderiler");
        adapter.addFragment(userFavoritedPostListFragment, "Beğeniler");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void observeSingleUser(){
      vm.getUser().observe(this, new Observer<UserResponse>() {
          @Override
          public void onChanged(UserResponse userResponse) {
              //burada tasarıma giydirme olaca
              binding.setSingleUser(userResponse.getUser());
          }
      });
    }

    private void showProgressBar(boolean isVisible) {
        if (isVisible) {
            progressBar.setVisibility(View.VISIBLE);
            Log.e("fff", "loading T");
        } else {
            progressBar.setVisibility(View.GONE);
            Log.e("fff", "loading F");
        }
    }

    @Override
    protected void observerErrorStatus() {
        vm.getErrorStatus().observe(this,
                error -> {
                    if (error != null) {
                        onError(getContext(), error.getMessage());
                        showProgressBar(false);
                        Log.e("fff", "Error");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layoutFollowerCount:
                openFollowFragment("follower");
                break;
            case R.id.layoutFollowingCount:
                openFollowFragment("following");
                break;
        }
    }

    public void openFollowFragment(String click){
        Bundle arguments = new Bundle();
        arguments.putString("whichClick", click);
        FollowFragment followFragment = new FollowFragment();
        followFragment.setArguments(arguments);
        multipleStackNavigator.start(followFragment);
    }


}