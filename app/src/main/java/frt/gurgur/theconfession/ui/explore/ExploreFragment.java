package frt.gurgur.theconfession.ui.explore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.ui.base.BaseFragment;


public class ExploreFragment extends BaseFragment {

    public static final String FRAGMENT_TAG = "ExploreFragment";
    public ExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }
}