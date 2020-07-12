package com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.Activity.HomeActivity;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.Activity.checksum;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.GrocerApplication;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter.ProfileAddressListAdapter;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.customecomponent.CustomTextView;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.data.FinalPayment;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.AddressList.AddressListModelNew;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.AndyUtils;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.AsyncTaskCompleteListener;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.MultiPartRequester;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.ParseContent;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gocashfree.cashfreesdk.CFPaymentService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static android.app.Activity.RESULT_OK;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_APP_ID;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_ID;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_NOTE;

/**
 * *************************************************************************
 *
 * @ClassdName:PaymentFragment
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class use for select payment method
 * <p/>
 * *************************************************************************
 */

public class PaymentFragment extends AppCompatActivity implements AsyncTaskCompleteListener {


    //declration
    private RelativeLayout rlProceesToPay;
    RadioGroup radioGroup;
    final int UPI_PAYMENT = 11;

    String userIdFromDb;
    String DATATOKEN;

    long number;

    private static final String TAG = "MainActivity";

    private static final String IMAGE_DIRECTORY = "/shivansh_upload_gallery";
    private final int GALLERY = 1;
    String mainTransactionId = "";
    String paymentAmount = FinalPayment.getFinalPayment();
    String email = "";
    private ParseContent parseContent;
    String userFullName, userEmail, userContactNumber;

    CustomTextView fragment_order_list_tvTotalKg;









    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_payment_fragment);

        userIdFromDb = GrocerApplication.getmInstance().getSharedPreferences().getString(getString(R.string.preferances_userIdFromDB), "");

        fragment_order_list_tvTotalKg = findViewById(R.id.fragment_order_list_tvTotalKg);
        fragment_order_list_tvTotalKg.setText("Rs. "+paymentAmount);


        String urlForVolleyRequest = "https://shivanshbhajibazzar.com/api/user_details.php";

        String userid = GrocerApplication.getmInstance().getSharedPreferences().getString(getString(R.string.preferances_userIdFromDB), "");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);

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

                    userFullName = user_first_name + user_last_name;
                    userEmail = user_email_id;
                    userContactNumber = user_contact_number;



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error);

                Toast.makeText(PaymentFragment.this, "Internet Error", Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(jsonObjectRequest);



        RequestQueue requestQueueGetToken = Volley.newRequestQueue(this);

        number = System.currentTimeMillis();

        String urlToken = "https://api.cashfree.com/api/v2/cftoken/order";

        JSONObject jsonObjectgetToke = new JSONObject();
        try {



            jsonObjectgetToke.put("orderId", "ShivanshOrder"+userIdFromDb+"_"+number);
            jsonObjectgetToke.put("orderAmount", FinalPayment.getFinalPayment());
            jsonObjectgetToke.put("orderCurrency", "INR");

            System.out.println(jsonObjectgetToke);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequestGetToken = new JsonObjectRequest(Request.Method.POST, urlToken, jsonObjectgetToke, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
                try {
                    DATATOKEN = response.getString("cftoken");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("x-client-id", "423987e5ba9ef7ebdcccbb71489324");
                params.put("x-client-secret", "1638790b60f046df325bb8155786af9320fa8b65");

                return params;
            }
        };

        parseContent = new ParseContent(this);


        requestQueueGetToken.add(jsonObjectRequestGetToken);


        initComponents();



    }

    /**
     * init Components
     */

    public void initComponents() {


        rlProceesToPay = findViewById(R.id.fragment_paymentNew_rlProceestoPay);


        radioGroup =  findViewById(R.id.paymentidd);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch(checkedId) {
                    case R.id.wallet_upi:
                        // switch to fragment 1
                        String amount = FinalPayment.getFinalPayment();
                        String note = "Payment For Order";
                        String name = "Shivansh Bhaji Bazzar";
                        String upiId = "9373938887@ybl";

                        payUsingUpi(amount, upiId, name, note);

                        break;
                    case R.id.online:
                        // Fragment 2


                        /*
                         * token can be generated from your backend by calling cashfree servers. Please
                         * check the documentation for details on generating the token.
                         * READ THIS TO GENERATE TOKEN: https://bit.ly/2RGV3Pp
                         */
                        String token = DATATOKEN;


                        /*
                         * stage allows you to switch between sandboxed and production servers
                         * for CashFree Payment Gateway. The possible values are
                         *
                         * 1. TEST: Use the Test server. You can use this service while integrating
                         *      and testing the CashFree PG. No real money will be deducted from the
                         *      cards and bank accounts you use this stage. This mode is thus ideal
                         *      for use during the development. You can use the cards provided here
                         *      while in this stage: https://docs.cashfree.com/docs/resources/#test-data
                         *
                         * 2. PROD: Once you have completed the testing and integration and successfully
                         *      integrated the CashFree PG, use this value for stage variable. This will
                         *      enable live transactions
                         */
                        String stage = "PROD";

                        /*
                         * appId will be available to you at CashFree Dashboard. This is a unique
                         * identifier for your app. Please replace this appId with your appId.
                         * Also, as explained below you will need to change your appId to prod
                         * credentials before publishing your app.
                         */
                        String appId = "423987e5ba9ef7ebdcccbb71489324";


                        String orderId = "ShivanshOrder"+userIdFromDb+"_"+number;
                        System.out.println(orderId);
                        String orderAmount = FinalPayment.getFinalPayment();;
                        String orderNote = "Payment For Order";

                        String customerName = userFullName;
                        String customerPhone = userContactNumber;
                        String customerEmail = userEmail;

                        Map<String, String> params = new HashMap<>();

                        params.put(PARAM_APP_ID, appId);
                        params.put(PARAM_ORDER_ID, orderId);
                        params.put(PARAM_ORDER_AMOUNT, orderAmount);
                        params.put(PARAM_ORDER_NOTE, orderNote);
                        params.put(PARAM_CUSTOMER_NAME, customerName);
                        params.put(PARAM_CUSTOMER_PHONE, customerPhone);
                        params.put(PARAM_CUSTOMER_EMAIL,customerEmail);


                        for(Map.Entry entry : params.entrySet()) {
                            Log.d("CFSKDSample", entry.getKey() + " " + entry.getValue());
                        }

                        CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
                        cfPaymentService.setOrientation(0);

                        // Use the following method for initiating Payments
                        // First color - Toolbar background
                        // Second color - Toolbar text and back arrow color
                        cfPaymentService.doPayment(PaymentFragment.this, params, token, stage);


                        break;
                    case R.id.galary:
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(galleryIntent, GALLERY);
                        break;

                }
            }
        });

    }



    void payUsingUpi(String amount, String upiId, String name, String note) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("tr", Long.toString(number))
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();

        //Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        //upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        // Intent chooser = Intent.createChooser(upiPayIntent, "Pay with ");

    }



    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }







    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("Request code = "+requestCode);


        Log.d(TAG, "ReqCode : " + CFPaymentService.REQ_CODE);
        Log.d(TAG, "API Response : ");
        //Prints all extras. Replace with app logic.
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null)
                for (String key : bundle.keySet()) {
                    if (bundle.getString(key) != null) {
                        Log.d(TAG, key + " : " + bundle.getString(key));
                    }
                }
        }




        if((requestCode == 11)) {
            if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                if (data != null) {
                    String trxt = data.getStringExtra("response");
                    Log.e("UPI", "onActivityResult: " + trxt);
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add(trxt);
                    upiPaymentDataOperation(dataList);



                } else {
                    Log.e("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
            } else {
                //when user simply back without payment
                Log.e("UPI", "onActivityResult: " + "Return data is null");
                ArrayList<String> dataList = new ArrayList<>();
                dataList.add("nothing");
                upiPaymentDataOperation(dataList);
            }
        }
        else if((requestCode == 1)){
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    System.out.println(path);
                    uploadImageToServer(path);
                    //Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    //imageview.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(PaymentFragment.this, "Failed!", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(requestCode == 9919)
        {

            String referenceId = "";
            String txStatus = "FAILED";
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                Log.d(TAG, "referenceId" + " : " + bundle.getString("referenceId"));
                referenceId = bundle.getString("referenceId");
                txStatus = bundle.getString("txStatus");
            }

            System.out.println(txStatus);

            if (txStatus.equalsIgnoreCase("FAILED")) {

            }
            else{
                mainTransactionId = bundle.getString("referenceId");
                String urlForRegistration = "https://shivanshbhajibazzar.com/api/update_payment_deposite_order.php";

                RequestQueue requestQueue = Volley.newRequestQueue(this);

                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("userIdFromDb", userIdFromDb);
                    jsonObject.put("transactionId", mainTransactionId);
                    jsonObject.put("paymentAmount", paymentAmount);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlForRegistration, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(response.toString());

                        try {
                            if (response.getString("status").equalsIgnoreCase("success")) {

                                Toast.makeText(PaymentFragment.this, "Payment Successful", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(PaymentFragment.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });


                requestQueue.add(jsonObjectRequest);
            }



        }






    }



    private void uploadImageToServer(final String path) throws IOException, JSONException {

        if (!AndyUtils.isNetworkAvailable(PaymentFragment.this)) {
            Toast.makeText(PaymentFragment.this, "Internet is required!", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("url", "https://shivanshbhajibazzar.com/api/upload_bill_payment.php");
        map.put("filename", path);

        map.put("totalBill", FinalPayment.getFinalPayment());
        System.out.println(path);

        map.put("userid", userIdFromDb);
        new MultiPartRequester(PaymentFragment.this, map, GALLERY, this);
        AndyUtils.showSimpleProgressDialog(this);
    }


    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }



    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(PaymentFragment.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                System.out.println(equalStr[0]+" = size = "+equalStr.length);
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase()) || equalStr[0].toLowerCase().equals("txnId".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }

                    if (i == 0)
                    {
                        mainTransactionId = approvalRefNo;
                    }

                    System.out.println("tid = "+approvalRefNo);
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(PaymentFragment.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: "+mainTransactionId);


                String urlForRegistration = "https://shivanshbhajibazzar.com/api/update_payment_deposite_order.php";

                RequestQueue requestQueue = Volley.newRequestQueue(this);

                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("userIdFromDb", userIdFromDb);
                    jsonObject.put("transactionId", mainTransactionId);
                    jsonObject.put("paymentAmount", paymentAmount);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlForRegistration, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (response.getString("status").equalsIgnoreCase("success"))
                            {

                                Toast.makeText(PaymentFragment.this, "Payment Successful", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(PaymentFragment.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });


                requestQueue.add(jsonObjectRequest);




            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(PaymentFragment.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(PaymentFragment.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        }
    }




    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        AndyUtils.removeSimpleProgressDialog();
        switch (serviceCode) {

            case GALLERY:
                if (parseContent.isSuccess(response)) {
                    String url = parseContent.getURL(response);
                    System.out.println(url);
                }
        }
    }




}
