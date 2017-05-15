package com.upreckless.support.newsapp.ui.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.upreckless.support.newsapp.R;
import com.upreckless.support.newsapp.ui.launch.MainActivity;

/**
 * Created by @mistreckless on 15.05.2017. !
 */

public class About extends Fragment {

    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_about,container,false);
        toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar.setTitle(R.string.dev_about);
        ((MainActivity) getActivity()).setToolbarToDrawer(toolbar,false);
    }
}
