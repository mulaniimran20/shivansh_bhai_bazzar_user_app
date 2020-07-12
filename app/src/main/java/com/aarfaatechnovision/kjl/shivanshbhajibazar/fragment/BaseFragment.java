package com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_base,
                container, false);
        return rootView;
    }


    public abstract void initComponents(View rootView);


    @Override
    public void onClick(View v) {

    }

}
