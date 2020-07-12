package com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.Activity.HomeActivity;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter.ProductListAdapter;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.data.DBUtils;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.data.ProductsHelper;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.data.TempListData;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.dialog.DialogFragmnetFiltter;
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

public class ProductListFragment extends BaseFragment implements ProductListAdapter.OnItemClickListener {

    //Declaration
    private RecyclerView rvProductList;
    private ImageView ivFilter;
    private GridLayoutManager mLayoutManager;
    private ProductListAdapter productListAdapter;
    private List<ProductListModel> productListModelArrayList;
    private MenuItem item;
    private FragmentActivity myContext;
    private RelativeLayout rlFilter;
    int categoryIdSelected;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_product_list, container, false);

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
        rvProductList = (RecyclerView) rootView.findViewById(R.id.fragment_productlist_rvProductList);
        ivFilter = (ImageView) rootView.findViewById(R.id.fragment_productlist_ivFilter);
        rlFilter = (RelativeLayout) rootView.findViewById(R.id.fragment_productlist_rlFilter);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvProductList.setLayoutManager(mLayoutManager);
        ivFilter.setOnClickListener(this);

        productListModelArrayList = new ArrayList<>();

        productListAdapter = new ProductListAdapter(getActivity(), productListModelArrayList, ProductListFragment.this);
        rvProductList.setAdapter(productListAdapter);
        productListAdapter.setOnItemClickListener(this);

        String value = getArguments().getString("categoryIdSelected");

        categoryIdSelected = Integer.parseInt(value);

        getListData();

    }

    /**
     * SetUp Toolbar & title
     */

    public void initToolbar() {

        if(getTargetFragment()!=null)
        {

            rlFilter.setVisibility(View.GONE);
            ((HomeActivity) getActivity()).setUpToolbar(getString(R.string.my_wishlist), false, true, false,true);


        }
        else
        {
            rlFilter.setVisibility(View.VISIBLE);
            ((HomeActivity) getActivity()).setUpToolbar(getString(R.string.nav_menu_home), false, true, true,true);
        }

        ((HomeActivity) getActivity()).getIvCart().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CartListFragment mFragment = new CartListFragment();
                Utils.addNextFragment(getActivity(), mFragment, ProductListFragment.this, true);
            }
        });

    }

    /**
     * get Product list data and setUp adapter
     */

    //3 feb changes
    private void getListData() {

        int catid = categoryIdSelected;


        String urlForVolleyRequest = "https://shivanshbhajibazzar.com/api/product_list.php";

        JSONObject jsonObject = new JSONObject();

        try {
            //jsonObject.put("catid", catid);
            jsonObject.put("categoryid",catid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
                        String productName = jsonObject.getString("product_name");
                        System.out.println(jsonObject.getString("product_image_url").replace("\\", ""));
                        String productImageUrl = jsonObject.getString("product_image_url").replace("\\", "");
                        int productId = jsonObject.getInt("product_id");
                        String product_rate_per_kg=jsonObject.getString("product_rate_per_kg");
                        String product_discount_percentage=jsonObject.getString("product_discount_percentage");
                        String product_description=jsonObject.getString("product_description");

                        JSONArray jsonArrayImagesForProducts=jsonObject.getJSONArray("productmultipleimageurlfinal");

                        ProductListModel productListModel = new ProductListModel();

                        productListModel.setProductImageUrl(productImageUrl);
                        productListModel.setJsonArrayOfImagesOfProduct(jsonArrayImagesForProducts);
                        productListModel.setProductId(productId);
                        productListModel.setProductName(productName);
                        productListModel.setProductPrice(product_rate_per_kg);
                        productListModel.setProductDescription(product_description);
                        productListModel.setProductDiscountPercent(product_discount_percentage);
                        //productListModel.setExtraImages(productmultipleimageurlfinal);

                        productListModelArrayList.add(productListModel);
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
     * Menu item click listener & open fragment
     */

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        item = menu.findItem(R.id.menu_left);
        item.setVisible(true);
        item.setIcon(R.drawable.cart);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                Utils.hideKeyboard(getActivity());
                CartListFragment fragmentProductDetails = new CartListFragment();
                fragmentProductDetails.setTargetFragment(ProductListFragment.this, 222);
                Utils.addNextFragment(getActivity(), fragmentProductDetails, ProductListFragment.this, false);

                return true;
            }
        });
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

        ProductDetailsFragment fragmentProductDetails = new ProductDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(getString(R.string.bdl_model), viewModel);
        fragmentProductDetails.setArguments(bundle);
        Utils.addNextFragment(getActivity(), fragmentProductDetails, ProductListFragment.this, false);
    }

    /**
     * Add to cart & refresh adapter
     */


    public void addToCart(boolean addTocart, int position) {
        if (addTocart) {
            int totalKg = productListModelArrayList.get(position).getTotalKg();
            totalKg = totalKg + 1;
            productListModelArrayList.get(position).setTotalKg(totalKg);

            ProductsHelper productsHelper = new ProductsHelper(getActivity());

            productsHelper.addProduct(
                    productListModelArrayList.get(position).getProductName(),
                    productListModelArrayList.get(position).getProductPrice(),
                    totalKg,
                    productListModelArrayList.get(position).getProductImageUrl(),
                    productListModelArrayList.get(position).getProductId()

            );

        } else {
            int totalKg = productListModelArrayList.get(position).getTotalKg();

            if (totalKg < 1) {
                productListModelArrayList.get(position).setTotalKg(0);

                DBUtils.getInstance(getActivity()).deleteProduct(productListModelArrayList.get(position).getProductId());
                productListModelArrayList.remove(position);
                productListAdapter.notifyDataSetChanged();

            } else {
                totalKg = totalKg - 1;
                productListModelArrayList.get(position).setTotalKg(totalKg);

                ProductsHelper productsHelper = new ProductsHelper(getActivity());

                productsHelper.addProduct(
                        productListModelArrayList.get(position).getProductName(),
                        productListModelArrayList.get(position).getProductPrice(),
                        totalKg,
                        productListModelArrayList.get(position).getProductImageUrl(),
                        productListModelArrayList.get(position).getProductId()

                );

            }


        }

        productListAdapter.notifyDataSetChanged();

    }

    @Override
    public void onAttach(Context context) {
        myContext = (FragmentActivity) context;
        super.onAttach(context);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (v == ivFilter) {
            DialogFragmnetFiltter addPhotoBottomDialogFragment = DialogFragmnetFiltter.newInstance();
            addPhotoBottomDialogFragment.show(myContext.getSupportFragmentManager(), getString(R.string.lbl_filter));
        }

    }

    public void likeUnLike(int position) {
        if (productListModelArrayList.get(position).isLike()) {
            productListModelArrayList.get(position).setLike(false);
        } else {
            productListModelArrayList.get(position).setLike(true);
        }

        productListAdapter.notifyDataSetChanged();
    }
}