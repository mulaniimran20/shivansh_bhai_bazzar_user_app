package com.aarfaatechnovision.kjl.shivanshbhajibazar.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.GrocerApplication;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter.AddressAdapter;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment.CartListFragment;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment.CheckOutFragmnet;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment.MyAccountFragment;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.AddressList.AddressListModelNew;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.addressBook.Address;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.util.Utils;
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


/**
 * *************************************************************************
 *
 * @ClassdName:DialogFragmentProfile
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class is use to display Profile dialog
 * <p/>
 * *************************************************************************
 */

public class DialogFragmentProfile extends DialogFragment implements View.OnClickListener
{
    private TextView tvSave;
    private TextView tvCancel;
    private TextView tvChange;
    private ImageView ivProfile;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etPhone;
    private EditText etEmail;
    private EditText etFlatNo;
    private EditText etWingName;
    private EditText etGender;
    private Spinner spinnerSocietyName;
    String URL="https://shivanshbhajibazzar.com/api/get_society_name.php";
    private AddressAdapter addressAdapter;
    private ArrayList<Address> addressListModelArrayList;
    private boolean erroCall = true;


    String selectedSocietyId = "";

    String societyName = "";
    String societyAddress = "";
    int positionOfA = 0;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimationTultip;
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.CENTER);

        dialog.setContentView(R.layout.dialog_profile_update);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes((WindowManager.LayoutParams) params);
        initializeComponent(dialog);
        return dialog;
    }


    protected void initializeComponent(Dialog v) {
        tvCancel = (TextView) v.findViewById(R.id.dailog_edit_profile_tvCancel);
        tvSave = (TextView) v.findViewById(R.id.dailog_edit_profile_tvSave);
        etFirstName = (EditText) v.findViewById(R.id.dailog_edit_profile_etFirstName);
        etLastName = (EditText) v.findViewById(R.id.dailog_edit_profile_etLastName);
        etPhone = (EditText) v.findViewById(R.id.dailog_edit_profile_etPhone);
        etEmail = (EditText) v.findViewById(R.id.dailog_edit_profile_etEmail);

        etFlatNo = v.findViewById(R.id.dailog_edit_profile_flatNumber);
        etWingName = v.findViewById(R.id.dailog_edit_profile_wingName);
        spinnerSocietyName=v.findViewById(R.id.dailog_add_societyname_etsocietyname);



        addressListModelArrayList = new ArrayList<>();

        RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST,
                URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {

                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i=0; i< jsonArray.length(); i++)
                    {
                        Log.e("data", String.valueOf(response));

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int society_id = jsonObject.getInt("society_id");
                        String society_name = jsonObject.getString("society_name");
                        String society_address=jsonObject.getString("society_address");
                        int society_pincode = jsonObject.getInt("society_pincode");

                        Address address = new Address();

                        if (i == 0)
                        {
                            Address address1 = new Address();

                            address1.setSociety_id(0);
                            address1.setSociety_name("Select Society Name");
                            address1.setSociety_address(" ");
                            address1.setSociety_pincode(000000);

                            addressListModelArrayList.add(address1);

                        }

                        address.setSociety_id(society_id);
                        address.setSociety_name(society_name);
                        address.setSociety_address(society_address);
                        address.setSociety_pincode(society_pincode);


                        addressListModelArrayList.add(address);
                        if (i == (jsonArray.length() -1 )) {
                            addressAdapter = new AddressAdapter(getActivity(), addressListModelArrayList);
                            spinnerSocietyName.setAdapter(addressAdapter);
                            addressAdapter.notifyDataSetChanged();
                        }
                    }

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

        requestQueue1.add(jsonObjectRequest1);



        spinnerSocietyName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if (addressListModelArrayList.get(position).getSociety_id() == 0) {
                    erroCall = false;
                }
                else{
                    erroCall = true;
                    selectedSocietyId = addressListModelArrayList.get(position).getSociety_id()+"";
                    societyName = addressListModelArrayList.get(position).getSociety_name();
                    societyAddress = addressListModelArrayList.get(position).getSociety_address();
                }

                
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });





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

                    String flatNo = response.getString("society_flat_address");
                    String wingName = response.getString("wing_names");

                    int societId = Integer.parseInt(response.getString("society_id_spinner"));

                    for (int i=0; i<addressListModelArrayList.size(); i++)
                    {
                        int scid = addressListModelArrayList.get(i).getSociety_id();

                        if (scid == societId)
                        {
                             positionOfA = i;
                            System.out.println(positionOfA);
                        }
                        else{

                        }
                    }

                    spinnerSocietyName.setSelection(positionOfA);

                    etFirstName.setText(user_first_name);
                    etLastName.setText(user_last_name);
                    etEmail.setText(user_email_id);
                    etPhone.setText(user_contact_number);
                    etFlatNo.setText(flatNo);
                    etWingName.setText(wingName);

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


        tvSave.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    @Override
    public void onClick(View v) {

        if (v == tvCancel) {
            dismiss();
        }
        else if (v == tvSave)
        {
            if (etFirstName.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), R.string.enter_first_name, Toast.LENGTH_SHORT).show();
            }
            else if (etLastName.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), R.string.enter_last_name, Toast.LENGTH_SHORT).show();
            }
            else if (etPhone.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), R.string.enter_your_phone, Toast.LENGTH_SHORT).show();
            }
            else if (etEmail.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), R.string.enter_your_email, Toast.LENGTH_SHORT).show();
            }
            else if(erroCall != true)
            {
                Toast.makeText(getActivity(), "Please Select Society Name", Toast.LENGTH_SHORT).show();
            }
            else {

                final String userFirstName = etFirstName.getText().toString();
                final String userLastName = etLastName.getText().toString();
                final String userEmailId = etEmail.getText().toString();
                final String userPhoneNo = etPhone.getText().toString();
                final String flatNo = etFlatNo.getText().toString();
                final String wingName = etWingName.getText().toString();
                String userid = GrocerApplication.getmInstance().getSharedPreferences().getString(getString(R.string.preferances_userIdFromDB), "");

                JSONObject jsonObject = new JSONObject();
                try {

                    jsonObject.put("userFirstName", userFirstName);
                    jsonObject.put("userLastName", userLastName);
                    jsonObject.put("userEmailId", userEmailId);
                    jsonObject.put("userPhoneNo", userPhoneNo);
                    jsonObject.put("userid", userid);
                    jsonObject.put("flatNo", flatNo);
                    jsonObject.put("wingName", wingName);
                    jsonObject.put("selectedSocietyId", selectedSocietyId);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                String url = "https://shivanshbhajibazzar.com/api/update_user.php";

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                        url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                            System.out.println(response.toString());

                        try {
                            if (response.getString("status").equalsIgnoreCase("success"))
                            {

                                MyAccountFragment.tvName.setText(userFirstName+" "+userLastName);
                                MyAccountFragment.tvEmail.setText(userEmailId);
                                MyAccountFragment.tvPhone.setText(userPhoneNo);

                                String addressShow = flatNo+", "+wingName+"\n"+societyName+"\n"+"\n"+societyAddress;

                                MyAccountFragment.setAddress(addressShow);

                                dismiss();

                            }
                            else{
                                Toast.makeText(getActivity(), response.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
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


                //dismiss();
            }
        }

    }






}
