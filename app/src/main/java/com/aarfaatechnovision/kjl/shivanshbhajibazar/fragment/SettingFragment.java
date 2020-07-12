package com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.Activity.HomeActivity;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.dialog.DialogFragmentPrivacy;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.dialog.DialogFragmentTerms;

public class SettingFragment extends BaseFragment {


    private RelativeLayout rlPrivacyPolicy;
    private RelativeLayout rlTerms;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        initToolbar();
        setHasOptionsMenu(true);
        initComponents(rootView);

        return rootView;
    }
    @Override
    public void initComponents(View rootView) {

        rlPrivacyPolicy = (RelativeLayout) rootView.findViewById(R.id.fragment_setting_rlPrivacy);
        rlTerms = (RelativeLayout) rootView.findViewById(R.id.fragment_setting_rlTermOfUse);
        rlPrivacyPolicy.setOnClickListener(this);
        rlTerms.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (v == rlTerms) {
            DialogFragmentTerms dialogFragmentTerms = new DialogFragmentTerms();
            dialogFragmentTerms.show(getFragmentManager(), getString(R.string.lbl_TermsOfUse));
        } else if (v == rlPrivacyPolicy) {
            DialogFragmentPrivacy dialogFragmentPrivacy = new DialogFragmentPrivacy();
            dialogFragmentPrivacy.show(getFragmentManager(), getString(R.string.lbl_PrivacyPolicy));
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.findItem(R.id.menu_left);
        item.setVisible(false);
    }
    public void initToolbar() {

        ((HomeActivity) getActivity()).setUpToolbar(getString(R.string.settings), false, true, false,false);


    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            try {
                initToolbar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
