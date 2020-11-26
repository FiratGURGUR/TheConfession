package frt.gurgur.theconfession.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.trendyol.medusalib.navigator.MultipleStackNavigator;

import dagger.android.support.DaggerFragment;
import frt.gurgur.theconfession.MainActivity;
import frt.gurgur.theconfession.ui.post.comments.CommentFragment;

public abstract class BaseFragment extends DaggerFragment {

    public MultipleStackNavigator multipleStackNavigator;
    protected void observerErrorStatus(){}
    protected void observeLoadStatus(){}
    protected MainActivity mActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) this.getActivity();
    }

    protected void onError(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        initStackNavigator(context);
    }

    private void initStackNavigator(Context context){
        if (context instanceof MainActivity){
            multipleStackNavigator = ((MainActivity) context).multipleStackNavigator;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initStackNavigator(getContext());
    }
    public MainActivity getMainActivity() {
        return mActivity;
    }
}
