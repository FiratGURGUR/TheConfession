package frt.gurgur.theconfession.ui.user.login;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.model.ValidationModel;
import frt.gurgur.theconfession.util.Common;
import frt.gurgur.theconfession.util.PreferencesHelper;
import frt.gurgur.theconfession.model.user.UserResponse;
import frt.gurgur.theconfession.ui.ViewModelFactory;
import frt.gurgur.theconfession.ui.base.BaseFragment;
import frt.gurgur.theconfession.ui.main.MainFragment;
import frt.gurgur.theconfession.model.user.RequestUser;
import frt.gurgur.theconfession.ui.user.register.RegisterFragment;


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
    @BindView(R.id.goRegister)
    TextView goRegister;

    @Inject
    PreferencesHelper preferencesHelper;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNavigation(false);
        showToolbar(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        showNavigation(true);
        showToolbar(true);
    }


    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
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
        observeLoginValidation();
    }

    public void initView(){
        goRegister.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    public void observeLogin() {
        vm.getUser().observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse user) {
                if (user != null) {
                  //shared a kaydet
                  preferencesHelper.setUser("user",user);
                  multipleStackNavigator.start(new MainFragment());
                }
            }
        });
    }

    @Override
    protected void observerErrorStatus() {
        vm.getErrorStatus().observe(this,
                error -> {
                    if (error != null) {
                        onError(getContext(), error.getMessage());
                        showProgressBar(false);
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
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }


    public void doLogin() {
        String mEmail = email.getText().toString();
        String mPassword = password.getText().toString();
        vm.setLoginValidation(mEmail,mPassword);

    }

    public void observeLoginValidation(){
        vm.getLoginValidation().observe(this, new Observer<ValidationModel>() {
            @Override
            public void onChanged(ValidationModel validationModel) {
                if (validationModel.isValid){
                    String mEmail = email.getText().toString();
                    String mPassword = password.getText().toString();
                    RequestUser user = new RequestUser(mEmail, mPassword);
                    vm.loadData(user);
                }else{
                    Common.customAlert(getActivity(),"", validationModel.errorMessage,"Tamam");
                }
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                doLogin();
                break;
            case R.id.goRegister:
                multipleStackNavigator.start(new RegisterFragment());
                break;
        }
    }
}