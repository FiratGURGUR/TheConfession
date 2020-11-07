package frt.gurgur.theconfession.ui.post;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.data.remote.APIResponseModel;
import frt.gurgur.theconfession.databinding.FragmentPostBinding;
import frt.gurgur.theconfession.model.post.PostRequestModel;
import frt.gurgur.theconfession.ui.ViewModelFactory;
import frt.gurgur.theconfession.ui.base.BaseFragment;
import frt.gurgur.theconfession.ui.main.MainFragment;
import frt.gurgur.theconfession.ui.user.register.RegisterViewModel;
import frt.gurgur.theconfession.util.PreferencesHelper;


public class PostFragment extends BaseFragment  implements View.OnClickListener{
    FragmentPostBinding binding;
    public static final String FRAGMENT_TAG = "PostFragment";

    @Inject
    ViewModelFactory vmFactory;
    PostViewModel vm;
    @Inject
    PreferencesHelper preferencesHelper;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.buttonSharePost)
    Button buttonSharePost;
    @BindView(R.id.etContent)
    EditText etContent;

    public PostFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        vm = ViewModelProviders.of(this, vmFactory).get(PostViewModel.class);

        observeCreatePost();
        this.observeLoadStatus();
        this.observerErrorStatus();
    }

    public void initView(){
        buttonSharePost.setOnClickListener(this);
        binding.setUser(preferencesHelper.getUser());
    }

    public void observeCreatePost(){
        vm.getResponse().observe(this, new Observer<APIResponseModel>() {
            @Override
            public void onChanged(APIResponseModel apiResponseModel) {
                //shared preferences kaydetme yapma
                onError(getContext(), apiResponseModel.getMessage());
            }
        });
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
        if (isVisible) {
            progressBar.setVisibility(View.VISIBLE);
            Log.e("fff", "loading T");
        } else {
            progressBar.setVisibility(View.GONE);
            Log.e("fff", "loading F");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSharePost:
                PostRequestModel model = new PostRequestModel(etContent.getText().toString().trim(),preferencesHelper.getUserId());
                vm.createPost(model);
                break;
        }
    }
}