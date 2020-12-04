package frt.gurgur.theconfession.ui.user.profile.followpage;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.databinding.FragmentFollowBinding;
import frt.gurgur.theconfession.ui.adapters.TabAdapter;
import frt.gurgur.theconfession.ui.base.BaseFragment;

public class FollowFragment extends BaseFragment {

    FragmentFollowBinding binding;
    public static final String FRAGMENT_TAG = "ProfileFragment";
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    public TabAdapter adapter;
    public String whichClick="";
    public int userId;

    public FollowFragment() {
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
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_follow, container, false);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         initView();

    }

    public void initView(){
        whichClick =  getArguments().getString("whichClick");
        userId =  getArguments().getInt("idForCounts");

        Bundle arguments = new Bundle();
        arguments.putInt("idForCounts", userId);
        FollowerListFragment followerListFragment = new FollowerListFragment();
        FollowingListFragment followingListFragment = new FollowingListFragment();
        followerListFragment.setArguments(arguments);
        followingListFragment.setArguments(arguments);
        adapter = new TabAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(followerListFragment, "Takipçiler");
        adapter.addFragment(followingListFragment, "Takip");


        viewPager.setAdapter(adapter);


        if (whichClick.equals("follower")){
            viewPager.setCurrentItem(0);
        }else {
            viewPager.setCurrentItem(1);
        }
        tabLayout.setupWithViewPager(viewPager);

    }

}