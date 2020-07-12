package com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.Activity.HomeActivity;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.GrocerApplication;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter.AddressAdapter;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.dialog.DialogFragmentChoosPincode;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.dialog.DialogFragmentForgotPsw;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.addressBook.Address;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.product.ProductListModel;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.util.Utils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginSignUpFragment extends BaseFragment  {

    private LinearLayout llContainer;
    private EditText etLoginEmail,etLoginPassword,etSignUpEmail,etSignUpPassword,etSignUpPhone,
            etSignUpFirstName,etSignUpLastName, etFlatNo, etWingName;

    private TextView tvForgotPassword,tvTabLogin,tvTabSignUp,tvLogin,tvSignUp;
    private CardView cvLogin,cvSignUp;
    private ImageView ivBack;

    private Spinner spinnerSocietyName;
    String URL="https://shivanshbhajibazzar.com/api/get_society_name.php";
    ArrayList<String> SocietyName;
    private AddressAdapter addressAdapter;
    private ArrayList<Address> addressListModelArrayList;

    private boolean erroCall = true;

    String selectedSocietyId = "";

    String societyName = "";
    String societyAddress = "";
    int positionOfA = 0;



    private String email,password,firstName,lastName,phone, flatNo, wingName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_login_signup, container, false);
         initComponents(rootView);
        return rootView;

    }

    /**
     * init Components
     */


    @Override
    public void initComponents(View rootView) {

        llContainer = (LinearLayout) rootView.findViewById(R.id.fragment_login_llContainer);

        etLoginEmail = (EditText) rootView.findViewById(R.id.fragment_login_etEmail);
        etLoginPassword = (EditText) rootView.findViewById(R.id.fragment_login_etPassword);

        etSignUpPhone = (EditText) rootView.findViewById(R.id.fragment_signup_etPhone);
        etSignUpFirstName = (EditText) rootView.findViewById(R.id.fragment_signup_etFirstName);
        etSignUpLastName = (EditText) rootView.findViewById(R.id.fragment_signup_etLastName);
        etSignUpEmail = (EditText) rootView.findViewById(R.id.fragment_signup_etEmail);
        etSignUpPassword = (EditText) rootView.findViewById(R.id.fragment_signup_etPassword);
        etFlatNo = rootView.findViewById(R.id.fragment_signup_etFlatNumber);
        etWingName = rootView.findViewById(R.id.fragment_signup_etWingName);

        spinnerSocietyName=rootView.findViewById(R.id.dailog_add_societyname_etsocietyname);
        SocietyName=new ArrayList<>();


        String urlForVolleyRequest="https://shivanshbhajibazzar.com/api/get_society_name.php";
        JSONObject jsonObject = new JSONObject();

        addressListModelArrayList = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                urlForVolleyRequest, jsonObject, new Response.Listener<JSONObject>() {
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

        requestQueue.add(jsonObjectRequest);



        tvForgotPassword = (TextView) rootView.findViewById(R.id.fragment_login_tvForgotPassword);
        tvTabLogin = (TextView) rootView.findViewById(R.id.fragment_login_tab_tvLogin);
        tvTabSignUp = (TextView) rootView.findViewById(R.id.fragment_login_tab_tvSignUp);
        tvLogin = (TextView) rootView.findViewById(R.id.fragment_login_tvLogin);
        tvSignUp = (TextView) rootView.findViewById(R.id.fragment_signup_tvSignup);
        cvSignUp = (CardView) rootView.findViewById(R.id.fragment_signup_cvSignup);
        cvLogin = (CardView) rootView.findViewById(R.id.fragment_login_signup_cvLogin);
        ivBack = (ImageView) rootView.findViewById(R.id.fragment_signup_ivBack);

        tvTabLogin.setOnClickListener(this);
        tvTabSignUp.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        cvLogin.setOnClickListener(this);
        cvSignUp.setOnClickListener(this);
        ivBack.setOnClickListener(this);


        String url = "https://shivanshbhajibazzar.com/api/user_registration.php";

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


    }


    private void doLogin() {

        email = etLoginEmail.getText().toString().trim();
        password = etLoginPassword.getText().toString().trim();


        if (email.isEmpty()) {
            Utils.snackbar(llContainer, getString(R.string.val_enter_email), true, getActivity());
        } else if (!Utils.isValidEmail(email)) {
            Utils.snackbar(llContainer, getString(R.string.val_enter_valid_email), true, getActivity());
        } else if (password.isEmpty()) {
            Utils.snackbar(llContainer, getString(R.string.val_enter_password), true, getActivity());
        } else if (password.length() < 6) {
            Utils.snackbar(llContainer, getString(R.string.val_password_greter_six), true, getActivity());
        } else {

            if (Utils.isOnline(getActivity(), true)) {


                String urlForLogin = "https://shivanshbhajibazzar.com/api/login.php";

                RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity());

                JSONObject jsonObjectLogin = new JSONObject();

                try {
                    jsonObjectLogin.put("user_email_id", email);
                    jsonObjectLogin.put("user_password", password);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, urlForLogin, jsonObjectLogin, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (response.getString("status").equalsIgnoreCase("success"))
                            {
                                Boolean resultReceivedFromLogin = true;
                                String userIdFromDb = response.getString("userloginid");

                                GrocerApplication.getmInstance().savePreferenceDataBoolean(getString(R.string.preferances_islogin), resultReceivedFromLogin);
                                GrocerApplication.getmInstance().savePreferenceDataString(getString(R.string.preferances_userName), email);
                                GrocerApplication.getmInstance().savePreferenceDataString(getString(R.string.preferances_userIdFromDB), userIdFromDb);


                                Intent i= new Intent(getActivity(), HomeActivity.class);
                                startActivity(i);
                                getActivity().finish();
                            }
                            else{


                                Toast.makeText(getActivity(), response.getString("msg"), Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Network Error ...", Toast.LENGTH_SHORT).show();
                    }
                });

                requestQueue1.add(jsonObjectRequest1);





            } else {
                Utils.snackbar(llContainer, "" + getString(R.string.check_connection), true, getActivity());

            }
        }




        /**
         * Check Validating field email,password or others & signup process
         */
    }
    private void doSignUp() {


        email = etSignUpEmail.getText().toString().trim();
        password = etSignUpPassword.getText().toString().trim();
        firstName = etSignUpFirstName.getText().toString().trim();
        lastName = etSignUpLastName.getText().toString().trim();
        phone = etSignUpPhone.getText().toString().trim();

        flatNo = etFlatNo.getText().toString().trim();
        wingName = etWingName.getText().toString().trim();

        if (firstName.isEmpty()) {
            Utils.snackbar(llContainer, getString(R.string.val_enter_fname), true, getActivity());
        } else if (lastName.isEmpty()) {
            Utils.snackbar(llContainer, getString(R.string.val_enter_lname), true, getActivity());
        } else if (phone.isEmpty()) {
            Utils.snackbar(llContainer, getString(R.string.val_enter_phone), true, getActivity());
        } else if (email.isEmpty()) {
            Utils.snackbar(llContainer, getString(R.string.val_enter_email), true, getActivity());
        } else if (!Utils.isValidEmail(email)) {
            Utils.snackbar(llContainer, getString(R.string.val_enter_valid_email), true, getActivity());
        } else if (password.isEmpty()) {
            Utils.snackbar(llContainer, getString(R.string.val_enter_password), true, getActivity());
        } else if (password.length() < 6) {
            Utils.snackbar(llContainer, getString(R.string.val_password_greter_six), true, getActivity());
        }else if (flatNo.length() < 1) {
            Utils.snackbar(llContainer, "Please enter valid flat number", true, getActivity());
        }
        else if (Integer.parseInt(selectedSocietyId) < 1) {
            Utils.snackbar(llContainer, "Please select valid Society", true, getActivity());
        }
        else {

            if (Utils.isOnline(getActivity(), true)) {

                String urlForRegistration = "https://shivanshbhajibazzar.com/api/user_registration.php";

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("user_email_id", email);
                    jsonObject.put("user_password", password);
                    jsonObject.put("user_contact_number", phone);
                    jsonObject.put("user_first_name", firstName);
                    jsonObject.put("user_last_name", lastName);
                    jsonObject.put("flatNo", flatNo);
                    jsonObject.put("wingName", wingName);
                    jsonObject.put("selectedSocietyId", selectedSocietyId);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlForRegistration, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            if (response.getString("status").equalsIgnoreCase("success"))
                            {

                                if (response.getString("paymentstatus").equalsIgnoreCase("0")) {
                                    String userIdFromDb = response.getString("userid");
                                    Intent intent = new Intent(getActivity(), TermsConditionPaymentFragment.class);
                                    intent.putExtra(getString(R.string.preferances_islogin), true);

                                    intent.putExtra("user_email_id", email);
                                    intent.putExtra("user_fullname", firstName+" "+lastName);
                                    intent.putExtra("user_phone_number", phone);

                                    intent.putExtra(getString(R.string.preferances_userIdFromDB), userIdFromDb);
                                    startActivity(intent);
                                }
                                else{

                                    Boolean resultReceivedFromLogin = true;
                                    String userIdFromDb = response.getString("userid");

                                    GrocerApplication.getmInstance().savePreferenceDataBoolean(getString(R.string.preferances_islogin), resultReceivedFromLogin);
                                    GrocerApplication.getmInstance().savePreferenceDataString(getString(R.string.preferances_userName), email);
                                    GrocerApplication.getmInstance().savePreferenceDataString(getString(R.string.preferances_userIdFromDB), userIdFromDb);


                                    Intent i= new Intent(getActivity(), HomeActivity.class);
                                    startActivity(i);
                                    getActivity().finish();

                                }



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
                        Toast.makeText(getActivity(), "Network Error, Please try again...", Toast.LENGTH_SHORT).show();
                    }
                });

                requestQueue.add(jsonObjectRequest);




            } else {
                Utils.snackbar(llContainer, "" + getString(R.string.check_connection), true, getActivity());

            }

        }

        /**
         * OnClick listener :  Login & Register & Forgot password & Facebook & google click listener
         */
    }


    @Override
    public void onClick(View v) {

        Utils.hideKeyboard(getActivity());

        if (v == tvLogin) {
            doLogin();

        } else if (v == tvSignUp) {
            doSignUp();
        } else if (v == tvTabLogin) {
            cvSignUp.setVisibility(View.GONE);
            cvLogin.setVisibility(View.VISIBLE);
            tvTabLogin.setTextColor(getResources().getColor(R.color.color_black));
            tvTabSignUp.setTextColor(getResources().getColor(R.color.signup_tab_color));
        } else if (v == tvTabSignUp) {
            tvTabLogin.setTextColor(getResources().getColor(R.color.signup_tab_color));
            tvTabSignUp.setTextColor(getResources().getColor(R.color.color_black));
            cvSignUp.setVisibility(View.VISIBLE);
            cvLogin.setVisibility(View.GONE);
        } else if (v == ivBack) {
            getFragmentManager().popBackStack();
        } else if (v == tvForgotPassword) {
            DialogFragmentForgotPsw dialogFragmentForgotPsw = new DialogFragmentForgotPsw();
            dialogFragmentForgotPsw.show(getFragmentManager(), getString(R.string.forgot_psw_lable));
        }

    }






}