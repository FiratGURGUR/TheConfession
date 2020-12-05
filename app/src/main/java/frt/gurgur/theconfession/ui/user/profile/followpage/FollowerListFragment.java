package frt.gurgur.theconfession.ui.user.profile.followpage;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.databinding.FragmentFollowerListBinding;
import frt.gurgur.theconfession.model.user.follow.FollowsItem;
import frt.gurgur.theconfession.ui.ViewModelFactory;
import frt.gurgur.theconfession.ui.adapters.FollowListAdapter;
import frt.gurgur.theconfession.ui.base.BaseFragment;
import frt.gurgur.theconfession.util.PreferencesHelper;
import frt.gurgur.theconfession.util.SimpleDividerItemDecoration;


public class FollowerListFragment extends BaseFragment {

    FragmentFollowerListBinding binding;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.followerRv)
    RecyclerView rvFollowerList;
    @BindView(R.id.emptyLayout)
    LinearLayout emptyLayout;
    @Inject
    ViewModelFactory vmFactory;
    FollowViewModel vm;
    @Inject
    PreferencesHelper preferencesHelper;
    GridLayoutManager gridLayoutManager;
    int userId;
    private List<FollowsItem> list = new ArrayList<>();
    public void setWarning(boolean warning){
        if (warning){
            emptyLayout.setVisibility(View.VISIBLE);
            rvFollowerList.setVisibility(View.GONE);
        }else{
            rvFollowerList.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        }
    }
    public FollowerListFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_follower_list, container, false);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = ViewModelProviders.of(this, vmFactory).get(FollowViewModel.class);
        userId = getArguments().getInt("idForCounts");

        vm.getFollowerList(userId);
        observeFollowerList();
        observeLoadStatus();
        observerErrorStatus();
        setRecyclerView();

    }


    public void observeFollowerList(){
        vm.getResponse().observe(this, new Observer<List<FollowsItem>>() {
            @Override
            public void onChanged(List<FollowsItem> followsItems) {
                if (followsItems != null) {
                    setWarning(false);
                    list.addAll(followsItems);
                    rvFollowerList.getAdapter().notifyDataSetChanged();
                }
            }
        });
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
    private void setRecyclerView() {
        gridLayoutManager = new GridLayoutManager(getContext(),1);
        FollowListAdapter adapter = new FollowListAdapter(list);
        rvFollowerList.setLayoutManager(gridLayoutManager);
        rvFollowerList.setHasFixedSize(true);
        rvFollowerList.setAdapter(adapter);
        rvFollowerList.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

    }
}