package com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.aarfaatechnovision.kjl.shivanshbhajibazar.data.TempListData;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.dialog.DialogDeActiveAccount;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.dialog.DialogFragmentChangePsw;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.dialog.DialogFragmentClearHistory;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.dialog.DialogFragmentProfile;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.AddressList.AddressListModelNew;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.product.ProductListModel;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyAccountFragment extends BaseFragment

{
    public static TextView tvName;
    public static TextView tvPhone;
    public static TextView tvEmail;
    private TextView tvChangePsw;
    private ImageView ivProfile;
    private ImageView tvEdit;
    private RelativeLayout rlChangePsw;
    public static ProfileAddressListAdapter addressListAdapterNew;
    public static List<AddressListModelNew> addressListModelNewList;
    public static RecyclerView rvAddress;
    private LinearLayoutManager mLayoutManager;

    public static Context mContet;
    public static MyAccountFragment mC1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_my_account, container, false);
        initToolbar();
        initComponents(rootView);

        mContet = getActivity();
        mC1 = MyAccountFragment.this;

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initComponents(View rootView) {

        tvName = (TextView) rootView.findViewById(R.id.fragment_myaccount_tvName);
        tvEmail = (TextView) rootView.findViewById(R.id.fragment_myaccount_tvEmail);
        tvPhone = (TextView) rootView.findViewById(R.id.fragment_myaccount_tvPhone);
        tvChangePsw = (TextView) rootView.findViewById(R.id.fragment_myaccount_tvChange);
        ivProfile = (ImageView) rootView.findViewById(R.id.fragment_myaccount_ivProfile);
        tvEdit = (ImageView) rootView.findViewById(R.id.fragment_myaccount_tvEdit);
        rlChangePsw = (RelativeLayout) rootView.findViewById(R.id.fragment_myaccount_rlChangePsw);
        rvAddress = (RecyclerView) rootView.findViewById(R.id.fragment_myaccount_rvAddress);

        tvEdit.setOnClickListener(this);
        rlChangePsw.setOnClickListener(this);

        rvAddress.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        setData();
        // tvName.setText();
    }


    /**
     * set shopping address list data and setUp adapter
     */

    public static void setAddress(String address)
    {
        String userCurrentAddress = address;

        AddressListModelNew addressListModelNew = new AddressListModelNew();

        addressListModelNewList = new ArrayList<>();

        addressListModelNew.setAddress(userCurrentAddress);

        addressListModelNewList.add(addressListModelNew);

        addressListAdapterNew = new ProfileAddressListAdapter(addressListModelNewList, mContet, mC1);
        rvAddress.setAdapter(addressListAdapterNew);
        addressListAdapterNew.notifyDataSetChanged();


    }

    private void setData(){
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
                    tvEmail.setText(user_email_id);
                    tvPhone.setText(user_contact_number);


                    AddressListModelNew addressListModelNew = new AddressListModelNew();



                    addressListModelNewList = new ArrayList<>();

                    addressListModelNew.setAddress(userCurrentAddress);

                    addressListModelNewList.add(addressListModelNew);

                    addressListAdapterNew = new ProfileAddressListAdapter(addressListModelNewList, getActivity(), MyAccountFragment.this);
                    rvAddress.setAdapter(addressListAdapterNew);
                    addressListAdapterNew.notifyDataSetChanged();



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

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if(v==rlChangePsw)
        {
            DialogFragmentChangePsw dialogFragmentChangePsw = new DialogFragmentChangePsw();
            dialogFragmentChangePsw.show(getFragmentManager(), getString(R.string.lbl_changepassword));
        }
        else if(v==tvEdit)
        {
            DialogFragmentProfile dialogFragmentProfile = new DialogFragmentProfile();
            dialogFragmentProfile.show(getFragmentManager(), getString(R.string.lbl_choosprofile));


        }

    }

    public void initToolbar() {
        ((HomeActivity) getActivity()).setUpToolbar(getString(R.string.nav_my_account), false,true,false,false);
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
