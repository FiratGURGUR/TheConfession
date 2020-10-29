package frt.gurgur.theconfession.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.model.main.DataItem;
import frt.gurgur.theconfession.ui.ViewModelFactory;
import frt.gurgur.theconfession.ui.adapters.PostListAdapter;
import frt.gurgur.theconfession.ui.base.BaseFragment;
import frt.gurgur.theconfession.util.SimpleDividerItemDecoration;

import static android.content.Context.MODE_PRIVATE;
import static android.os.ParcelFileDescriptor.MODE_APPEND;

public class MainFragment extends BaseFragment {
    ViewDataBinding binding;
    public static final String FRAGMENT_TAG = "MainFragment";
    @Inject
    ViewModelFactory vmFactory;
    MainViewModel vm;

    @BindView(R.id.rcyclePostList)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Inject
    SharedPreferences prefs;

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

        int id = prefs.getInt("idd",-11);

        vm.loadPostList(1,id);
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
                        onError(getContext(), error);
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
                    if (postList.size() > 0)
                        postList.clear();
                    postList.addAll(dataItems);
                    recyclerView.getAdapter().notifyDataSetChanged();

                }
            }
        });
    }
    private void setRecyclerView() {
        PostListAdapter adapter = new PostListAdapter(postList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
    }
}