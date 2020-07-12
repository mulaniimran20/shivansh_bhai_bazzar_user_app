package com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.Activity.HomeActivity;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.GrocerApplication;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter.OrderListAdapter;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.data.TempListData;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.ClickedOrderId;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.order.OrderListModel;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * *************************************************************************
 *
 * @ClassdName:OrderListFragment
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class use for display order list with field order Date, Delivery date and Status
 * <p/>
 * *************************************************************************
 */


public class OrderListFragment extends BaseFragment implements OrderListAdapter.OnItemClickListener{

    //Declaration
    private RecyclerView rvProductList;
    private GridLayoutManager mLayoutManager;
    private OrderListAdapter orderListAdapter;
    private List<OrderListModel> orderListModelList;
    private MenuItem item;

    Float pendingBill = 0.0f;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_order_list, container, false);

        initComponents(rootView);
        setHasOptionsMenu(true);
        initToolbar();
        return rootView;
    }

    /**
     * init Components
     */

    @Override
    public void initComponents(View rootView) {
        rvProductList = (RecyclerView) rootView.findViewById(R.id.fragment_orderlist_rvOrder);
        mLayoutManager = new GridLayoutManager(getActivity(), 1);
        rvProductList.setLayoutManager(mLayoutManager);


        getListData();

    }

    /**
     * SetUp Toolbar & title
     */

    public void initToolbar() {

        ((HomeActivity) getActivity()).setUpToolbar(getString(R.string.order_history), false, true, false, false);

    }

    /**
     * get Product list data and setUp adapter
     */
    private void getListData() {

        orderListModelList = new ArrayList<>();


        String urlOrderHistory = "https://shivanshbhajibazzar.com/api/getOrderHistory.php";
        String userid = GrocerApplication.getmInstance().getSharedPreferences().getString(getString(R.string.preferances_userIdFromDB), "");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }



        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                urlOrderHistory, jsonObject, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    System.out.println(response.toString());

                    for (int i =0; i< jsonArray.length(); i++)
                    {
                        OrderListModel OrderListModelNew = new OrderListModel();

                        OrderListModelNew.setOrderId(jsonArray.getJSONObject(i).getString("order_number"));
                        OrderListModelNew.setPrice(jsonArray.getJSONObject(i).getString("complete_order_cost"));
                        OrderListModelNew.setOrderDate(jsonArray.getJSONObject(i).getString("order_dates"));

                        if (jsonArray.getJSONObject(i).getString("payment_status").equalsIgnoreCase("1"))
                        {
                            OrderListModelNew.setStatus("delivery");
                        }
                        else{
                            OrderListModelNew.setStatus("pending");

                            pendingBill = pendingBill + Float.parseFloat(jsonArray.getJSONObject(i).getString("complete_order_cost"));
                        }





                        orderListModelList.add(OrderListModelNew);



                        orderListAdapter = new OrderListAdapter(getActivity(), orderListModelList, getActivity(), OrderListFragment.this);
                        rvProductList.setAdapter(orderListAdapter);

                        if(i == (jsonArray.length()-1))
                        {
                            OrderListModel OrderListModelNew1 = new OrderListModel();

                            OrderListModelNew1.setOrderId("Total Pending Bill");
                            OrderListModelNew1.setPrice(pendingBill+"");
                            OrderListModelNew1.setStatus("pending");

                            final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = new Date();
                            OrderListModelNew1.setOrderDate(sdf.format(date)+"");
                            orderListModelList.add(OrderListModelNew1);
                            orderListAdapter = new OrderListAdapter(getActivity(), orderListModelList, getActivity(), OrderListFragment.this);
                            rvProductList.setAdapter(orderListAdapter);

                        }

                        orderListAdapter.notifyDataSetChanged();
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

    /**
     * Menu item click listener & open fragment
     */

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        item = menu.findItem(R.id.menu_left);
        item.setVisible(false);

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            try {
                initToolbar();
            } catch (Exception e) {

            }


        }
    }


    @Override
    public void onItemClick(View view, OrderListModel viewModel) {

        System.out.println("called new");

        OrderDetailsFragment fragmentProductDetails = new OrderDetailsFragment();
        Bundle args = new Bundle();
        args.putString("orderid", viewModel.getOrderId() );

        ClickedOrderId clickedOrderId = new ClickedOrderId();
        clickedOrderId.setOrderId(viewModel.getOrderId());


        fragmentProductDetails.setArguments(args);
        Utils.addNextFragment(getActivity(), fragmentProductDetails, OrderListFragment.this, false);

    }


}
