package com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.Activity.HomeActivity;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.dialog.DialogFragmentAboutUs;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.dialog.DialogFragmentFeedback;


/**
 * *************************************************************************
 *
 * @ClassdName:HelpFragment
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class use for display about-us content
 * <p/>
 * *************************************************************************
 */


public class HelpFragment extends BaseFragment {


    private RelativeLayout rlEmailUs;
    private RelativeLayout rlAboutUs;
   // private RelativeLayout rlSendFeedback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_help, container, false);
        initToolbar();
        setHasOptionsMenu(true);
        initComponents(rootView);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    /**
     * init Components
     */

    @Override
    public void initComponents(View rootView) {

        rlEmailUs = (RelativeLayout) rootView.findViewById(R.id.fragment_help_rlEmailUs);
        rlAboutUs = (RelativeLayout) rootView.findViewById(R.id.fragment_help_rlAboutUs);
       // rlSendFeedback = (RelativeLayout) rootView.findViewById(R.id.fragment_help_rlFeedback);


       // rlSendFeedback.setOnClickListener(this);
        rlEmailUs.setOnClickListener(this);
        rlAboutUs.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (v == rlAboutUs) {
            DialogFragmentAboutUs dialogFragmentAboutUs = new DialogFragmentAboutUs();
            dialogFragmentAboutUs.show(getFragmentManager(), getString(R.string.lbl_aboutus));
        } else if (v == rlEmailUs) {

        } /*else if (v == rlSendFeedback) {

            DialogFragmentFeedback dialogFragmentFeedback = new DialogFragmentFeedback();
            dialogFragmentFeedback.show(getFragmentManager(), getString(R.string.lbl_feedback));
        }*/
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.findItem(R.id.menu_left);
        item.setVisible(false);
    }


    /**
     * SetUp Toolbar & title
     */


    public void initToolbar() {

        ((HomeActivity) getActivity()).setUpToolbar(getString(R.string.help), false, true, false,false);


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
