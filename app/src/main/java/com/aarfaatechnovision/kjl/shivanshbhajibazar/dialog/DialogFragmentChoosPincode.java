package com.aarfaatechnovision.kjl.shivanshbhajibazar.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter.AddressAdapter;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter.ProductListAdapter;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.addressBook.Address;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.product.ProductListModel;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DialogFragmentChoosPincode extends DialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
   // private RelativeLayout rlCurrentLocation;
    private Spinner spinnerSocietyName;
    private EditText edtAddress;
    private TextView tvSave;
    private TextView tvCancel;
    private ImageView ivClose;


    //Spinner spinner;
    String URL="https://shivanshbhajibazzar.com/api/get_society_name.php";
    ArrayList<String> SocietyName;
    private AddressAdapter addressAdapter;
    private ArrayList<Address> addressListModelArrayList;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimationTultip;
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.CENTER);

        dialog.setContentView(R.layout.dialog_add_location);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes((WindowManager.LayoutParams) params);
        initializeComponent(dialog);
        return dialog;
    }

    protected void initializeComponent(Dialog v) {
       // rlCurrentLocation = (RelativeLayout) v.findViewById(R.id.dailog_add_pincode_rlCurrentLocation);
        tvCancel = (TextView) v.findViewById(R.id.dailog_add_pincode_tvCancel);
        tvSave = (TextView) v.findViewById(R.id.dailog_add_pincode_tvSave);
        spinnerSocietyName=v.findViewById(R.id.dailog_add_societyname_etsocietyname);
        ivClose = (ImageView) v.findViewById(R.id.dailog_add_pincode_ivClose);

        tvSave.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        ivClose.setOnClickListener(this);

        SocietyName=new ArrayList<>();
       // spinnerSocietyName=(Spinner)findViewById(R.id.country_Name);
        //loadSpinnerData(URL);
        getListData();

    }


    private void getListData() {

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
        } else if (v == ivClose) {
            dismiss();
        } else if (v == tvSave) {
            if (edtAddress.toString().isEmpty()) {
                Toast.makeText(getActivity(), R.string.enter_address, Toast.LENGTH_SHORT).show();
            } else {
                dismiss();
            }
        }

    }



    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        Toast.makeText(getActivity(), addressListModelArrayList.get(position).getSociety_id(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }




}
