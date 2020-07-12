package com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.Activity.HomeActivity;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.GrocerApplication;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter.ProfileAddressListAdapter;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.dialog.DialogFragmentChoosPincode;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.AddressList.AddressListModelNew;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.util.Utils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class MenuFragment extends BaseFragment {


    //Declaration
    private ImageView ivClose;
   // private ImageView ivLocation;
    private TextView tvLogin;
    private TextView tvRegister;
    private TextView tvName;
    //private TextView tvCity;
    private RelativeLayout rlProfile;
   // private RelativeLayout rlWhishList;
    private RelativeLayout rlOrderHistory;
    private RelativeLayout rlSetting;
    private RelativeLayout rlHelp;
    private RelativeLayout rlLogout;
    private CardView cvLoginRegister;
    private CardView cvProfile;
    private CircleImageView ivProfile;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        initToolbar();
        initComponents(rootView);

        return rootView;
    }


    /**
     * SetUp Toolbar & title
     */
    public void initToolbar() {
        ((HomeActivity) getActivity()).setUpToolbar(getString(R.string.nav_menu_home), false, false, false,false);
    }

    /**
     * init Components
     */


    @Override
    public void initComponents(View rootView) {

        ivClose = rootView.findViewById(R.id.fragment_menu_ivClose);
        //ivLocation = rootView.findViewById(R.id.fragment_menu_ivLocation);
        tvLogin = rootView.findViewById(R.id.fragment_menu_tvLogin);
        tvRegister = rootView.findViewById(R.id.fragment_menu_tvSignup);
        rlProfile = rootView.findViewById(R.id.fragment_menu_rlProfile);
        cvLoginRegister = rootView.findViewById(R.id.fragment_menu_cvLoginRegister);
        cvProfile = rootView.findViewById(R.id.fragment_menu_cvProfile);
        ivProfile = rootView.findViewById(R.id.fragment_menu_ivProfile);
        tvName = rootView.findViewById(R.id.fragment_menu_tvName);
       // tvCity = rootView.findViewById(R.id.fragment_menu_tvCity);
//        rlWhishList = rootView.findViewById(R.id.fragment_menu_rlWishlist);
        rlOrderHistory = rootView.findViewById(R.id.fragment_menu_rlOrderHistory);
        rlSetting = rootView.findViewById(R.id.fragment_menu_rlSetting);
        rlHelp = rootView.findViewById(R.id.fragment_menu_rlHelp);
        rlLogout = rootView.findViewById(R.id.fragment_menu_rlLogout);

        rlHelp.setOnClickListener(this);
        //rlWhishList.setOnClickListener(this);
        rlOrderHistory.setOnClickListener(this);
        rlLogout.setOnClickListener(this);
       // ivLocation.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        cvProfile.setOnClickListener(this);
        rlSetting.setOnClickListener(this);

        checkLogin();


        String urlForVolleyRequest = "https://shivanshbhajibazzar.com/api/user_details.php";

        String userid = GrocerApplication.getmInstance().getSharedPreferences().getString(getString(R.string.preferances_userIdFromDB), "");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                urlForVolleyRequest, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    System.out.println(response.toString());

                    String user_first_name = response.getString("user_first_name");
                    String user_last_name = response.getString("user_last_name");
                    String user_email_id=response.getString("user_email_id");
                    String user_contact_number=response.getString("user_contact_number");
                    String user_registration_fees=response.getString("user_registration_fees");
                    String user_registration_fees_payment_status=response.getString("user_registration_fees_payment_status");
                    String user_activation_status=response.getString("user_activation_status");

                    String userCurrentAddress = response.getString("society_flat_address")+","+response.getString("wing_names")+"\n"+response.getString("society_name")+'\n'+'\n'+response.getString("society_address")+","+response.getString("society_pincode");


                    tvName.setText(user_first_name+" "+user_last_name);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error);

                Toast.makeText(getActivity(), "Internet Error", Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(jsonObjectRequest);



    }

    /**
     * check user loin or not
     */

    private void checkLogin() {

        boolean isLogin = GrocerApplication.getmInstance().getSharedPreferences().getBoolean(getString((R.string.preferances_islogin)), false);

        if (isLogin) {
            cvLoginRegister.setVisibility(View.GONE);
            cvProfile.setVisibility(View.VISIBLE);
            rlLogout.setVisibility(View.VISIBLE);
        } else {
            cvLoginRegister.setVisibility(View.VISIBLE);
            cvProfile.setVisibility(View.GONE);
            rlLogout.setVisibility(View.GONE);
        }


    }

    /**
     * handle menu click listener & open fragment
     */


    @Override
    public void onClick(View v) {
        super.onClick(v);

        Utils.hideKeyboard(getActivity());

        if (v == ivClose) {
            getFragmentManager().popBackStack();
        }
        else if (v == tvLogin) {

            LoginSignUpFragment menuFragment = new LoginSignUpFragment();
            Utils.addNextFragment(getActivity(), menuFragment, MenuFragment.this, false);
        }
        else if (v == tvRegister) {

            LoginSignUpFragment menuFragment = new LoginSignUpFragment();
            Utils.addNextFragment(getActivity(), menuFragment, MenuFragment.this, false);
        }
        else if (v == cvProfile) {

            MyAccountFragment profileFragmentNew = new MyAccountFragment();
            Utils.addNextFragment(getActivity(), profileFragmentNew, MenuFragment.this, false);
        }
        else if(v==rlLogout)
        {
            GrocerApplication.getmInstance().savePreferenceDataBoolean(getString(R.string.preferances_islogin), false);
            //checkLogin();
            Intent i= new Intent(getActivity(), HomeActivity.class);
            startActivity(i);
            getActivity().finish();
        }
        else if(v==rlHelp)
        {
            HelpFragment helpFragment = new HelpFragment();
            Utils.addNextFragment(getActivity(), helpFragment, MenuFragment.this, false);
        }
        else if(v==rlSetting)
        {
            SettingFragment settingFragment = new SettingFragment();
            Utils.addNextFragment(getActivity(), settingFragment, MenuFragment.this, false);
        }
        else if(v==rlOrderHistory)
        {
            OrderListFragment orderListFragment = new OrderListFragment();
            Utils.addNextFragment(getActivity(), orderListFragment, MenuFragment.this, false);
        }
        /*else if (v == ivLocation) {
            DialogFragmentChoosPincode dialogFragmentChoosPincode = new DialogFragmentChoosPincode();
            dialogFragmentChoosPincode.show(getFragmentManager(), getString(R.string.choospincode));

        }
        */
//        else if(v==rlWhishList)
//        {
//            ProductListFragment fragmentProductDetails = new ProductListFragment();
//            fragmentProductDetails.setTargetFragment(this,99);
//            Utils.addNextFragment(getActivity(), fragmentProductDetails, MenuFragment.this, false);
//        }

    }



    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);


        if (!hidden) {
            checkLogin();
            initToolbar();
        }

    }


}
