package frt.gurgur.theconfession.ui.user.profile;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import frt.gurgur.theconfession.R;


public class ProfileBottomSheetFragment extends BottomSheetDialogFragment   implements View.OnClickListener{
    @BindView(R.id.bsm_settings)
    TextView bsm_settings;
    @BindView(R.id.bsm_explore_other)
    TextView bsm_explore_other;
    @BindView(R.id.bsm_statistics)
    TextView bsm_statistics;
    @BindView(R.id.bsm_about_app)
    TextView bsm_about_app;
    @BindView(R.id.bsm_exit_app)
    TextView bsm_exit_app;

    public static final String TAG = "ActionBottomDialog";

    private ItemClickListener mListener;

    public static ProfileBottomSheetFragment newInstance() {
        return new ProfileBottomSheetFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_bottom_sheet, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bsm_settings.setOnClickListener(this);
        bsm_explore_other.setOnClickListener(this);
        bsm_statistics.setOnClickListener(this);
        bsm_about_app.setOnClickListener(this);
        bsm_exit_app.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickListener) {
            mListener = (ItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override public void onClick(View view) {
        switch (view.getId()){
            case R.id.bsm_settings:
                mListener.onItemClick(0);
                break;
            case R.id.bsm_explore_other:
                mListener.onItemClick(1);
                break;
            case R.id.bsm_statistics:
                mListener.onItemClick(2);
                break;
            case R.id.bsm_about_app:
                mListener.onItemClick(3);
                break;
            case R.id.bsm_exit_app:
                mListener.onItemClick(4);
                break;
        }
        dismiss();
    }


    public interface ItemClickListener {
        void onItemClick(int position);
    }
}