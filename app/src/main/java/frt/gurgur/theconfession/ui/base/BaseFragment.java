package frt.gurgur.theconfession.ui.base;

import android.content.Context;
import android.widget.Toast;

import dagger.android.support.DaggerFragment;

public abstract class BaseFragment extends DaggerFragment {


    protected void observerErrorStatus(){}
    protected void observeLoadStatus(){}

    protected void onError(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
