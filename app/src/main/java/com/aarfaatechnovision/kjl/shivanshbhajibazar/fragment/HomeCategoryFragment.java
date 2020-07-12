package com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.Activity.HomeActivity;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.GrocerApplication;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter.HomeProductListAdapter;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter.ShopAdapter;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.data.TempListData;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.product.ProductListModel;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.util.Utils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeCategoryFragment extends BaseFragment implements HomeProductListAdapter.OnItemClickListener {

    //Declaration
    private RecyclerView rvProductList;

    private GridLayoutManager mLayoutManager;
    private HomeProductListAdapter productListAdapter;
    private InfiniteScrollAdapter<ShopAdapter.ViewHolder> shopAdapter;
    private List<ProductListModel> productListModelArrayList;
    private List<String> pagerImgList;
    private MenuItem item;
    private DiscreteScrollView discreteScrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        initComponents(rootView);
        initToolbar();
        return rootView;
    }

    /**
     * init Components
     */

    @Override
    public void initComponents(View rootView) {

        rvProductList = (RecyclerView) rootView.findViewById(R.id.fragment_home_rvProductList);
        discreteScrollView=(DiscreteScrollView)rootView.findViewById(R.id.product_picker);
        productListModelArrayList = new ArrayList<>();

        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvProductList.setLayoutManager(mLayoutManager);

        productListAdapter = new HomeProductListAdapter(getActivity(), productListModelArrayList, HomeCategoryFragment.this);
        rvProductList.setAdapter(productListAdapter);
        productListAdapter.setOnItemClickListener(this);
        rvProductList.setNestedScrollingEnabled(false);

        getListData();
        setUpSliderImages();

    }

    /**
     * SetUp Toolbar & title
     */

    public void initToolbar() {
        ((HomeActivity) getActivity()).setUpToolbar(getString(R.string.nav_menu_home), true,true,true,true);

        ((HomeActivity) getActivity()).getIvCart().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CartListFragment mFragment = new CartListFragment();
                Utils.addNextFragment(getActivity(), mFragment, HomeCategoryFragment.this, true);
            }
        });
    }

    /**
     * get Product list data and setUp adapter
     */
    private void getListData() {


        //String urlForVolleyRequest = "https://aarfaatechnovision.com/shivansh/api/category_list.php";
        String urlForVolleyRequest="https://shivanshbhajibazzar.com/api/category_list.php";

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlForVolleyRequest, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i=0; i< jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String categoryName = jsonObject.getString("category_name");
                        String categoryImageUrl = jsonObject.getString("category_image_url");
                        int categoryId = jsonObject.getInt("category_id");

                        ProductListModel productListModel = new ProductListModel();

                        productListModel.setCaegoryImageUrl(categoryImageUrl);
                        productListModel.setCategoryId(categoryId);
                        productListModel.setCategoryName(categoryName);
                        productListModelArrayList.add(productListModel);
                        //Toast.makeText(categoryImageUrl.contains(), "", Toast.LENGTH_SHORT).show();

                        productListAdapter.notifyDataSetChanged();
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

    /**
     * SetUp slider images
     */

    private void setUpSliderImages() {
        try {
            pagerImgList = new ArrayList<>();

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

            JsonObjectRequest jsonObjectRequestSlider = new JsonObjectRequest(Request.Method.GET, "https://shivanshbhajibazzar.com/api/slider.php", null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        JSONArray jsonArray = response.getJSONArray("data");

                        for (int i = 0; i< jsonArray.length(); i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            pagerImgList.add(jsonObject.getString("slider_image"));

                            if(i == (jsonArray.length()-1))
                            {
                                discreteScrollView.setOrientation(DSVOrientation.HORIZONTAL);
                                shopAdapter = InfiniteScrollAdapter.wrap(new ShopAdapter( pagerImgList,getActivity()));
                                discreteScrollView.setAdapter(shopAdapter);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(jsonObjectRequestSlider);






        } catch (Exception e) {
            e.printStackTrace();
        }
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

    /**
     * item click listener & open fragment
     */

    @Override
    public void onItemClick(View view, ProductListModel viewModel) {

        System.out.println(viewModel.getCategoryId());
        ProductListFragment fragmentProductDetails = new ProductListFragment();
        Bundle args = new Bundle();
        args.putString("categoryIdSelected", String.valueOf(viewModel.getCategoryId()));
        fragmentProductDetails.setArguments(args);
        Utils.addNextFragment(getActivity(), fragmentProductDetails, HomeCategoryFragment.this, false);

    }



}
