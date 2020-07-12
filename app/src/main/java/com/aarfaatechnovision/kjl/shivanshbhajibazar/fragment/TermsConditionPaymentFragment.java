package com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.Activity.HomeActivity;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.Activity.PaytmPaymentTransaction;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.Activity.checksum;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.GrocerApplication;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.dialog.DialogFragmentPrivacy;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.dialog.DialogFragmentTerms;
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
import com.androidquery.AQuery;
import com.gocashfree.cashfreesdk.CFPaymentService;

import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlAssetsImageGetter;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_APP_ID;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_ID;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_NOTE;

public class TermsConditionPaymentFragment extends AppCompatActivity implements AsyncTaskCompleteListener{
    private HtmlTextView textTermsAndConditionDisplay;
    String email, userIdFromDb;
    Boolean resultReceivedFromSignup;
    ImageView cameraId;

    final int UPI_PAYMENT = 11;

    String paymentAmount = "1699";


    String tagPost = "registration_terms";

    String mainTransactionId = "";

    private ParseContent parseContent;
    private static final String IMAGE_DIRECTORY = "/shivansh_upload_gallery";
    private final int GALLERY = 1;
    private AQuery aQuery;
    RadioGroup radioGroup;

    String userName, userContact;





    String instaMojoUniqueId = "";




    private static final String TAG = "MainActivity";

    String DATATOKEN = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_termsconditionpayment);

        parseContent = new ParseContent(this);
        aQuery = new AQuery(this);






        if (ContextCompat.checkSelfPermission(TermsConditionPaymentFragment.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(TermsConditionPaymentFragment.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        }


        textTermsAndConditionDisplay =  findViewById(R.id.textTermsAndConditionDisplay);
        cameraId = findViewById(R.id.cameraId);

        Bundle extras = getIntent().getExtras();

        email = extras.getString("user_email_id");
        userName = extras.getString("user_fullname");
        userContact = extras.getString("user_phone_number");

        userIdFromDb = extras.getString(getString(R.string.preferances_userIdFromDB));
        resultReceivedFromSignup = extras.getBoolean(getString(R.string.preferances_islogin));

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        cameraId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(galleryIntent, GALLERY);
            }
        });


        RequestQueue requestQueueGetToken = Volley.newRequestQueue(this);

        String urlToken = "https://api.cashfree.com/api/v2/cftoken/order";

        JSONObject jsonObjectgetToke = new JSONObject();
        try {

            jsonObjectgetToke.put("orderId", "ShivanshOrder"+userIdFromDb);
            jsonObjectgetToke.put("orderAmount", "1699");
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

        requestQueueGetToken.add(jsonObjectRequestGetToken);



        radioGroup =  findViewById(R.id.paymentidd);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch(checkedId) {
                    case R.id.wallet_upi:
                        // switch to fragment 1
                        String amount = "1699";
                        String note = "Registration Deposite Fees";
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

                        String orderId = "ShivanshOrder"+userIdFromDb;
                        System.out.println(orderId);
                        String orderAmount = "1699";
                        String orderNote = "Registration Shivansh";
                        String customerName = userName;
                        String customerPhone = userContact;
                        String customerEmail = email;

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
                        cfPaymentService.doPayment(TermsConditionPaymentFragment.this, params, token, stage);

                        break;
                }
            }
        });









        String urlForGetTermsandConditionCode = "https://aarfaatechnovision.com/shivansh/api/getTermsAndConditionRegardingSecurityDeposite.php";

        RequestQueue requestQueue = Volley.newRequestQueue(TermsConditionPaymentFragment.this);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("tags", tagPost);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlForGetTermsandConditionCode, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    String demo = response.getString("msg");

                    textTermsAndConditionDisplay.setHtml(demo, new HtmlHttpImageGetter(textTermsAndConditionDisplay));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getActivity(), "Network error, please try again", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);


    }







    void payUsingUpi(String amount, String upiId, String name, String note) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("tr", "294295")
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();




        //Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        //upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        // Intent chooser = Intent.createChooser(upiPayIntent, "Pay with ");

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.setPackage("com.phonepe.app");

        if(isPackageInstalled("com.phonepe.app", this.getPackageManager()))
        {
            startActivityForResult(intent, UPI_PAYMENT);
        }
        else{
            Toast.makeText(TermsConditionPaymentFragment.this, "Phonepe App is Not Installed, Please instal first", Toast.LENGTH_SHORT).show();
        }


    }



    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {

        boolean found = true;

        try {
            packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            found = false;
        }

        return found;
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

                            GrocerApplication.getmInstance().savePreferenceDataString(getString(R.string.preferances_userIdFromDB), userIdFromDb);


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
                        Toast.makeText(TermsConditionPaymentFragment.this, "Failed!", Toast.LENGTH_SHORT).show();
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
                        String urlForRegistration = "https://shivanshbhajibazzar.com/api/update_payment_deposite.php";

                        RequestQueue requestQueue = Volley.newRequestQueue(this);

                        JSONObject jsonObject = new JSONObject();

                        mainTransactionId = bundle.getString("referenceId");
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
                                    if (response.getString("status").equalsIgnoreCase("success")) {

                                        GrocerApplication.getmInstance().savePreferenceDataBoolean(getString(R.string.preferances_islogin), resultReceivedFromSignup);
                                        GrocerApplication.getmInstance().savePreferenceDataString(getString(R.string.preferances_userName), email);
                                        GrocerApplication.getmInstance().savePreferenceDataString(getString(R.string.preferances_userIdFromDB), userIdFromDb);

                                        Intent intent = new Intent(TermsConditionPaymentFragment.this, HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);

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
        if (isConnectionAvailable(TermsConditionPaymentFragment.this)) {
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
                Toast.makeText(TermsConditionPaymentFragment.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: "+mainTransactionId);


                String urlForRegistration = "https://shivanshbhajibazzar.com/api/update_payment_deposite.php";

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

                                GrocerApplication.getmInstance().savePreferenceDataBoolean(getString(R.string.preferances_islogin), resultReceivedFromSignup);
                                GrocerApplication.getmInstance().savePreferenceDataString(getString(R.string.preferances_userName), email);
                                GrocerApplication.getmInstance().savePreferenceDataString(getString(R.string.preferances_userIdFromDB), userIdFromDb);

                                Intent intent = new Intent(TermsConditionPaymentFragment.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

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
                Toast.makeText(TermsConditionPaymentFragment.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(TermsConditionPaymentFragment.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        }
    }





    private void uploadImageToServer(final String path) throws IOException, JSONException {

        if (!AndyUtils.isNetworkAvailable(TermsConditionPaymentFragment.this)) {
            Toast.makeText(TermsConditionPaymentFragment.this, "Internet is required!", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("url", "https://shivanshbhajibazzar.com/api/upload_bill.php");
        map.put("filename", path);
        System.out.println(path);

        map.put("userid", userIdFromDb);
        new MultiPartRequester(TermsConditionPaymentFragment.this, map, GALLERY, this);
        AndyUtils.showSimpleProgressDialog(this);
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






}
