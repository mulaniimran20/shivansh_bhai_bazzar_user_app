package com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.aarfaatechnovision.kjl.shivanshbhajibazar.Activity.HomeActivity;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.GrocerApplication;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter.AddressListAdapter;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter.OrderSummaryListAdapter;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter.ProfileAddressListAdapter;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter.ProfileAddressListAdapterNew;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.customecomponent.CustomTextView;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.data.DBUtils;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.data.ProductsHelper;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.data.TempListData;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.data.TotalBill;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.AddressList.AddressListModelNew;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.OrderList.OrderListModelNew;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.cart.CartlistModel;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.util.Utils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CheckOutFragmnet extends BaseFragment implements View.OnClickListener
{


    //Declaration
    private RelativeLayout rlConfirmOreder;
    private RecyclerView rvAddressList;
    private RecyclerView rvOrderList;
    private LinearLayoutManager mLayoutManager;
    private ProfileAddressListAdapterNew addressListAdapterNew;
    private OrderSummaryListAdapter orderListAdapter;
    private List<AddressListModelNew> addressListModelNewList;
    private List<CartlistModel> orderListModelNewList;
    private OrderListFragment paymentFragment;
    private CustomTextView fragment_order_list_tvTotalKg;

    ImageButton imageButtonApplyCoupenCode;
    EditText editTextCoupenCode;

    private float percent = 0;
    private String coupenApplied = "";

    String orderId = "";


    public CheckOutFragmnet() {
        // Required empty public constructor
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_check_out_fragmnet, container, false);
        initToolbar();
        initComponents(v);
        setAddress();
        setOrderList();


        imageButtonApplyCoupenCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coupenCode = editTextCoupenCode.getText().toString();

                if (coupenCode == "")
                {

                }
                else{

                    String url = "https://shivanshbhajibazzar.com/api/applyCoupenCode.php";

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("coupencode", coupenCode);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                            url, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {


                            try {
                                if (response.getString("status").equalsIgnoreCase("success"))
                                {
                                     percent = Float.parseFloat(response.getString("discount"));
                                    coupenApplied = editTextCoupenCode.getText().toString();

                                    float pm = ((getTotalPrice() * (100 - percent)) / 100 );
                                    fragment_order_list_tvTotalKg.setText("Rs. "+pm+" /-");
                                    Toast.makeText(getActivity(), ""+pm, Toast.LENGTH_SHORT).show();
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



                }

            }
        });


        return v;
    }


    /**
     * init Components
     */

    @Override
    public void initComponents(View rootView) {

        mLayoutManager = new LinearLayoutManager(getActivity());
        rlConfirmOreder=rootView.findViewById(R.id.fragment_checkout_btnConfirmOrder);
        fragment_order_list_tvTotalKg = rootView.findViewById(R.id.fragment_order_list_tvTotalKg);
        imageButtonApplyCoupenCode = rootView.findViewById(R.id.applyCoupenCode);
        editTextCoupenCode = rootView.findViewById(R.id.edtCoupenCode);
        rvAddressList = rootView.findViewById(R.id.fragment_checkout_rvAddressList);
        rvAddressList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvOrderList = rootView.findViewById(R.id.fragment_checkout_rvOrderList);
        rvOrderList.setLayoutManager(mLayoutManager);

        rlConfirmOreder.setOnClickListener(this);

        String getorderidurl = "https://shivanshbhajibazzar.com/api/orderidgenerate.php";

        RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, getorderidurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    orderId = response.getString("orderid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue1.add(jsonObjectRequest1);




    }

    /**
     * SetUp Toolbar & title
     */

    public void initToolbar() {
        HomeActivity activity = (HomeActivity) getActivity();

                activity.setUpToolbar(getString(R.string.check_out_now), false, true, false,false);

    }


    private int getTotalPrice() {
        int total = 0;

        for (int i = 0; i < orderListModelNewList.size(); i++) {
            int totalQty = orderListModelNewList.get(i).getProductQuantity();


            String[] splitm;

            if(orderListModelNewList.get(i).getProductPrice().contains("/")) {
                splitm = orderListModelNewList.get(i).getProductPrice().split("/");
            }
            else{
                splitm = new String[]{orderListModelNewList.get(i).getProductPrice()};
            }

            String[] split1;

            int price;
            if(splitm[0].contains("."))
            {
                split1 = splitm[0].split(". ");
                System.out.println(split1[1]);
                price = Integer.parseInt(split1[1]);
            }
            else {
                split1 = splitm[0].split("Rs");
                price = Integer.parseInt(split1[1]);
            }



            int totalPrice = totalQty * price;
            total = total + totalPrice;
        }

        TotalBill totalBill = new TotalBill();
        totalBill.setTotalBill(total+"");

        return total;
    }


    /**
     * set delivery Address
     */

    private void setAddress() {


        addressListModelNewList = new ArrayList<>();


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




                    AddressListModelNew addressListModelNew = new AddressListModelNew();



                    addressListModelNewList = new ArrayList<>();

                    addressListModelNew.setAddress(userCurrentAddress);

                    addressListModelNewList.add(addressListModelNew);

                    addressListAdapterNew = new ProfileAddressListAdapterNew(addressListModelNewList, getActivity(), CheckOutFragmnet.this);
                    rvAddressList.setAdapter(addressListAdapterNew);
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

    /**
     * get Order list data and setUp adapter
     */


    private void setOrderList()
    {

        orderListModelNewList = new ArrayList<>();

        ProductsHelper productsHelper = new ProductsHelper(getActivity());

        orderListModelNewList = productsHelper.getAllNotes();


        orderListAdapter = new OrderSummaryListAdapter(orderListModelNewList, getActivity(), CheckOutFragmnet.this);
        rvOrderList.setAdapter(orderListAdapter);



        fragment_order_list_tvTotalKg.setText("Rs. "+getTotalPrice()+" /-");

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if(v==rlConfirmOreder)
        {






                String urlOrder = "https://shivanshbhajibazzar.com/api/order_place.php";

            String userid = GrocerApplication.getmInstance().getSharedPreferences().getString(getString(R.string.preferances_userIdFromDB), "");


            String prr[] = fragment_order_list_tvTotalKg.getText().toString().split("/");
            String pricetotal[] = prr[0].split("Rs. ");

            String actualSendPrice = pricetotal[1];


            for (int i=0; i < orderListModelNewList.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                try {

                    jsonObject.put("userid", userid);
                    jsonObject.put("fullcost", actualSendPrice);
                    jsonObject.put("order_product_price", orderListModelNewList.get(i).getProductPrice());
                    jsonObject.put("order_product_quantity", orderListModelNewList.get(i).getProductQuantity());
                    jsonObject.put("order_discount_coupen", coupenApplied);
                    jsonObject.put("coupen_discount_percent", percent);
                    jsonObject.put("productid", orderListModelNewList.get(i).getProductId());
                    jsonObject.put("orderId", orderId);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                final int finalid = i;

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                        urlOrder, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            if (response.getString("status").equalsIgnoreCase("success"))
                            {

                                DBUtils.getInstance(getActivity()).deleteProduct(orderListModelNewList.get(finalid).getProductDbID());

                            }
                            else{
                                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
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


                if (i == (orderListModelNewList.size() - 1))
                {

                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                    getActivity().finish();

                }

                }

        }
    }



    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if(!hidden)
        {
            initToolbar();
        }
    }
}
