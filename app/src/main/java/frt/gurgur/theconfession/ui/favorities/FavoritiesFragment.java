package frt.gurgur.theconfession.ui.favorities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.ui.base.BaseFragment;


public class FavoritiesFragment extends BaseFragment {


    public FavoritiesFragment() {
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
        return inflater.inflate(R.layout.fragment_favorities, container, false);
    }
}