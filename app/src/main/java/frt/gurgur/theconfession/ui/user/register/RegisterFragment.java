package frt.gurgur.theconfession.ui.user.register;

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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.model.APIResponseModel;
import frt.gurgur.theconfession.model.ValidationModel;
import frt.gurgur.theconfession.ui.ViewModelFactory;
import frt.gurgur.theconfession.ui.base.BaseFragment;
import frt.gurgur.theconfession.ui.main.MainFragment;
import frt.gurgur.theconfession.model.user.RequestUser;
import frt.gurgur.theconfession.util.Common;
import frt.gurgur.theconfession.util.Constants;
import frt.gurgur.theconfession.util.Helper;

public class RegisterFragment extends BaseFragment implements View.OnClickListener {
    ViewDataBinding binding;
    @Inject
    ViewModelFactory vmFactory;
    RegisterViewModel vm;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.edtUserName)
    EditText edtUserName;
    @BindView(R.id.edtFullName)
    EditText edtFullName;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.registerButton)
    Button registerButton;

    public RegisterFragment() {
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
        showToolbar(true);
        showBackButton(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        showToolbar(false);
        showBackButton(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        vm = ViewModelProviders.of(this, vmFactory).get(RegisterViewModel.class);
        observeRegister();
        this.observeLoadStatus();
        this.observerErrorStatus();
        observeRegisterValidation();
    }

    public void initView() {
        registerButton.setOnClickListener(this);
    }

    public void observeRegister() {
        vm.getUser().observe(this, new Observer<APIResponseModel>() {
            @Override
            public void onChanged(APIResponseModel apiResponseModel) {
                multipleStackNavigator.start(new MainFragment());
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

    public void doRegister() {
        String username = edtUserName.getText().toString();
        String fullname = edtFullName.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        vm.setRegisterValidation(username, fullname, email, password);
    }

    public void observeRegisterValidation() {
        vm.getRegisterValidation().observe(this, new Observer<ValidationModel>() {
            @Override
            public void onChanged(ValidationModel validationModel) {
                if (validationModel.isValid) {
                    String username = edtUserName.getText().toString();
                    String fullname = edtFullName.getText().toString();
                    String email = edtEmail.getText().toString();
                    String password = edtPassword.getText().toString();
                    RequestUser user = new RequestUser(username, fullname, Helper.generateUserPhoto(fullname), password, email, Constants.DEFAULT_COVERPHOTO_URL);
                    vm.registerUser(user);
                } else {
                    Common.customAlertSpanable(getContext(), "", validationModel.errorMessage, getString(R.string.action_gotit));
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerButton:
                doRegister();
                break;
        }
    }
}