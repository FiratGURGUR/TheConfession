package frt.gurgur.theconfession.ui.user.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
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
import frt.gurgur.theconfession.MainActivity;
import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.model.user.UserResponse;
import frt.gurgur.theconfession.ui.ViewModelFactory;
import frt.gurgur.theconfession.ui.base.BaseFragment;
import frt.gurgur.theconfession.ui.confession.ConfessionListFragment;
import frt.gurgur.theconfession.ui.user.RequestUser;
import retrofit2.adapter.rxjava2.HttpException;


public class LoginFragment extends BaseFragment implements View.OnClickListener {

    ViewDataBinding binding;
    public static final String FRAGMENT_TAG = "LoginFragment";
    @Inject
    ViewModelFactory vmFactory;
    LoginViewModel vm;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.edtEmail)
    EditText email;
    @BindView(R.id.edtPassword)
    EditText password;
    @BindView(R.id.loginButton)
    Button loginButton;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        vm = ViewModelProviders.of(this, vmFactory).get(LoginViewModel.class);

        observeLogin();
        this.observeLoadStatus();
        this.observerErrorStatus();

    }

    public void initView(){
        loginButton.setOnClickListener(this);
    }

    public void observeLogin() {
        vm.getUser().observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse notes) {
                if (notes != null) {
                  mainActivity.pushFragment(new ConfessionListFragment(), ConfessionListFragment.FRAGMENT_TAG);
                }
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


    public void doLogin() {
        String mEmail = email.getText().toString();
        String mPassword = password.getText().toString();
        RequestUser user = new RequestUser(mEmail, mPassword);
        vm.loadData(user);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                doLogin();
                break;
        }
    }
}