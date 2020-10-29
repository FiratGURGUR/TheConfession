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
import frt.gurgur.theconfession.data.remote.APIResponseModel;
import frt.gurgur.theconfession.ui.ViewModelFactory;
import frt.gurgur.theconfession.ui.base.BaseFragment;
import frt.gurgur.theconfession.ui.main.MainFragment;
import frt.gurgur.theconfession.ui.user.RequestUser;
import frt.gurgur.theconfession.util.Constants;

public class RegisterFragment extends BaseFragment implements View.OnClickListener {
    ViewDataBinding binding;
    public static final String FRAGMENT_TAG = "RegisterFragment";
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

    }

    public void initView(){
        registerButton.setOnClickListener(this);
    }

    public void observeRegister(){
        vm.getUser().observe(this, new Observer<APIResponseModel>() {
            @Override
            public void onChanged(APIResponseModel apiResponseModel) {
                //shared preferences kaydetme yapma
                mainActivity.pushFragment(new MainFragment(),MainFragment.FRAGMENT_TAG);
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

    public void doRegister(){
        String username= edtUserName.getText().toString();
        String fullname= edtFullName.getText().toString();
        String email= edtEmail.getText().toString();
        String password= edtPassword.getText().toString();
        RequestUser user = new RequestUser(username,fullname,Constants.generateUserPhoto(fullname),password,email, Constants.DEFAULT_COVERPHOTO_URL);
        vm.registerUser(user);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerButton:
                doRegister();
                break;
        }
    }
}