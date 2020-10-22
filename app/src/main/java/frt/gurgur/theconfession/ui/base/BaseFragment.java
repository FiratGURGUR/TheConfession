package frt.gurgur.theconfession.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import dagger.android.support.DaggerFragment;
import frt.gurgur.theconfession.MainActivity;

public abstract class BaseFragment extends DaggerFragment {

    protected MainActivity mainActivity;
    protected void observerErrorStatus(){}
    protected void observeLoadStatus(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) this.getActivity();
    }

    protected void onError(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
