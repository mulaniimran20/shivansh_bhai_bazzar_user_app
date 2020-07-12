package com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.Activity.HomeActivity;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.GrocerApplication;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter.CartListAdapter;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter.CartListAdapterOrderHistory;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter.HomeProductListAdapter;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter.OrderListAdapter;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.data.DBUtils;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.data.ProductsHelper;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.data.TotalBill;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.ClickedOrderId;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.cart.CartlistModel;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.product.ProductListModel;
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
import java.util.List;

public class OrderDetailsFragment extends BaseFragment {

    //Declaration
    private RecyclerView rvProductList;
    private TextView tvTotalPrice;
    private RelativeLayout rlCheckOut;
    private RelativeLayout rlEmpty;
    // private LinearLayout llSelectPin;
    private ImageView ivClose;
    private ImageView ivHome;
    private TextView tvCartCount;


    private LinearLayoutManager mLayoutManager;
    private CartListAdapterOrderHistory productListAdapter;
    private List<CartlistModel> productListModelArrayList;
    private MenuItem item;

    String orderid = "";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_cart_list, container, false);
        initToolbar();
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


        rvProductList = (RecyclerView) rootView.findViewById(R.id.fragment_cartlist_rvProductList);
        rlCheckOut = (RelativeLayout) rootView.findViewById(R.id.fragment_cartlist_rlCheckOut);
        rlEmpty = (RelativeLayout) rootView.findViewById(R.id.fragment_cartlist_rlEmpty);
        tvTotalPrice = (TextView) rootView.findViewById(R.id.fragment_cartlist_tvTotalKg);
        tvCartCount = (TextView) rootView.findViewById(R.id.fragment_cartlist_tvCartCount);
        ivClose = (ImageView) rootView.findViewById(R.id.fragment_cartlist_ivClose);
        ivHome = (ImageView) rootView.findViewById(R.id.fragment_cartlist_ivHome);
        //   llSelectPin = (LinearLayout) rootView.findViewById(R.id.fragment_cartlist_llSelectPin);

        tvCartCount.setTextSize(12);

        rlCheckOut.setVisibility(View.GONE);



        mLayoutManager = new LinearLayoutManager(getActivity());
        rvProductList.setLayoutManager(mLayoutManager);
        rlCheckOut.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        ivHome.setOnClickListener(this);
        //llSelectPin.setOnClickListener(this);

        getListData();


    }

    private float getTotalPrice(String dicsount_percent) {
        int total = 0;

        Float discountpercent = Float.parseFloat(dicsount_percent);

        for (int i = 0; i < productListModelArrayList.size(); i++) {
            int totalQty = productListModelArrayList.get(i).getProductQuantity();

            System.out.println(totalQty);

            String[] splitm = productListModelArrayList.get(i).getProductPrice().split("/");
            System.out.println(productListModelArrayList.get(i).getProductPrice());
            String[] split1 = splitm[0].split(". ");

            int price = Integer.parseInt(split1[1]);



            int totalPrice = totalQty * price;
            total = total + totalPrice;
        }

        TotalBill totalBill = new TotalBill();
        totalBill.setTotalBill(total+"");




        return ((total * (100 - discountpercent)) / 100);
    }

    /**
     * get Cart list data and setUp adapter
     */

    private void getListData() {

        productListModelArrayList = new ArrayList<>();

        String urlDemo = "https://shivanshbhajibazzar.com/api/order_details_list.php";

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        String userid = GrocerApplication.getmInstance().getSharedPreferences().getString(getString(R.string.preferances_userIdFromDB), "");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid", userid);
            jsonObject.put("orderid", ClickedOrderId.getOrderId());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlDemo, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {

                    JSONArray jsonArray = response.getJSONArray("data");




                    for (int i=0; i< jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String order_product_price = jsonObject.getString("order_product_price");
                        String order_product_quantity = jsonObject.getString("order_product_quantity");
                        String order_discount_coupen = jsonObject.getString("order_discount_coupen");
                        String discount_percent = jsonObject.getString("discount_percent");
                        String order_dates = jsonObject.getString("order_dates");
                        String order_number = jsonObject.getString("order_number");
                        String product_name = jsonObject.getString("product_name");
                        String product_image_url = jsonObject.getString("product_image_url");
                        String payment_status = jsonObject.getString("payment_status");
                        String order_delivery_status = jsonObject.getString("order_delivery_status");



                        CartlistModel cartlistModel = new CartlistModel();

                        cartlistModel.setProductName(product_name);
                        cartlistModel.setProductPrice(order_product_price);
                        cartlistModel.setProductQuantity(Integer.parseInt(order_product_quantity));
                        cartlistModel.setProductImages(product_image_url);
                        cartlistModel.setDiscount_percent(discount_percent);
                        cartlistModel.setOrder_discount_coupen(order_discount_coupen);
                        cartlistModel.setOrder_dates(order_dates);


                        String order_delivery_status_new = "";

                        if (order_delivery_status.equalsIgnoreCase("1"))
                        {
                            order_delivery_status_new = "Delivered";
                        }
                        else{
                            order_delivery_status_new = "Pending";
                        }



                        cartlistModel.setOrder_delivery_status(order_delivery_status_new);
                        cartlistModel.setOrder_number(order_number);


                        String payment_status_new = "";

                        if (payment_status.equalsIgnoreCase("1"))
                        {
                            payment_status_new = "Paid";
                        }
                        else{
                            payment_status_new = "Pending";
                        }

                        tvCartCount.setText(" Order Id : "+order_number+" \n \n Order Date : "+order_dates + "\n \n Delivery Status : "+order_delivery_status_new+" \n \n Payment Status : "+payment_status_new);


                        cartlistModel.setPayment_status(payment_status_new);

                        productListModelArrayList.add(cartlistModel);
                        productListAdapter = new CartListAdapterOrderHistory(getActivity(), productListModelArrayList, OrderDetailsFragment.this);
                        rvProductList.setAdapter(productListAdapter);
                        productListAdapter.notifyDataSetChanged();

                        if (i == (jsonArray.length()-1)) {
                            tvTotalPrice.setText("Rs." + getTotalPrice(discount_percent));
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        item = menu.findItem(R.id.menu_left);
        item.setVisible(false);
    }


    @Override
    public void onClick(View v) {

        if (v == rlCheckOut) {
            CheckOutFragmnet cartFragment = new CheckOutFragmnet();
            Utils.addNextFragment(getActivity(), cartFragment, OrderDetailsFragment.this, true);
        }
        else if (v == ivClose) {

            getFragmentManager().popBackStack();
        } else if (v == ivHome) {
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }


    /**
     * SetUp Toolbar & title
     */


    public void initToolbar() {
        ((HomeActivity) getActivity()).setUpToolbar
                (getString(R.string.nav_menu_home), false, false, false, false);


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


    /**
     * Add to cart & refresh adapter
     */




    public void addToCart(boolean addTocart, int position) {
        CartlistModel cartlistModel = productListModelArrayList.get(position);

        if (addTocart) {
            int totalKg = productListModelArrayList.get(position).getProductQuantity();
            totalKg = totalKg + 1;
            productListModelArrayList.get(position).setProductQuantity(totalKg);

            ProductsHelper p = new ProductsHelper(getActivity());
            p.updateNote(productListModelArrayList.get(position));


        } else {
            int totalKg = productListModelArrayList.get(position).getProductQuantity();

            if (totalKg > 1) {
                totalKg = totalKg - 1;
                productListModelArrayList.get(position).setProductQuantity(totalKg);

                ProductsHelper p = new ProductsHelper(getActivity());
                p.updateNote(productListModelArrayList.get(position));

            }
        }



        productListAdapter.notifyDataSetChanged();

    }
    //    public void updateItem(int position) {
//        DBUtils.getInstance(getActivity()).updateProduct(
//                tvCartCount.getImeActionId()
//
//                     );
//}
    public void deleteItem(int position, int dbid) {


        DBUtils.getInstance(getActivity()).deleteProduct(dbid);
        productListModelArrayList.remove(position);
        productListAdapter.notifyDataSetChanged();


    }






}
