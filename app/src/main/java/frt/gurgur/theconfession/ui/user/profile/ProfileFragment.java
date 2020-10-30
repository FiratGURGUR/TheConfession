package frt.gurgur.theconfession.ui.user.profile;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.ui.ViewModelFactory;
import frt.gurgur.theconfession.ui.base.BaseFragment;
import frt.gurgur.theconfession.ui.user.login.LoginViewModel;
import frt.gurgur.theconfession.util.PreferencesHelper;
import frt.gurgur.theconfession.util.Utils;


public class ProfileFragment extends BaseFragment implements View.OnClickListener {
    ViewDataBinding binding;
    public static final String FRAGMENT_TAG = "ProfileFragment";

    @Inject
    ViewModelFactory vmFactory;
    @Inject
    PreferencesHelper preferencesHelper;



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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
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
        if (Utils.getConnectionType(getContext()) == Utils.NO_CONNECTION){
            //bilgileri shared dan getir
        }else {
            //bilgileri api den al
        }
    }





    @Override
    protected void observerErrorStatus() {

    }

    @Override
    protected void observeLoadStatus() {

    }

    @Override
    public void onClick(View v) {

    }
}