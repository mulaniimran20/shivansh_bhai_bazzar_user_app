package com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
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
import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter.ProductPagerAdapter;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.data.DBUtils;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.data.ProductsHelper;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.cart.CartlistModel;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.product.ProductListModel;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsFragment extends BaseFragment {

    private ViewPager viewPagerImages;
    private TextView tvQuantity;
    private TextView tvName;
    private TextView tvdescription;
    private TextView tvPrice;
    private ImageView ivPlus;
    private ImageView ivMins;
   // private ImageView ivLikeUnLike;
    private RelativeLayout rlAddTocart;
    private ProductPagerAdapter imagePagerAdapter;
    private ProductListModel productListModel;

    private List<String> pagerImgList;
    private Bundle bundle;
    private int totalKg = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View rootView = inflater.inflate(R.layout.fragment_product_detail, container, false);

        setHasOptionsMenu(true);
        initToolbar();
        initComponents(rootView);

        return rootView;
    }

    /**
     * SetUp Toolbar & title
     */

    public void initToolbar() {

        ((HomeActivity) getActivity()).setUpToolbar(getString(R.string.fruits_vegetables), false,true,false,true);

        ((HomeActivity) getActivity()).getIvCart().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CartListFragment mFragment = new CartListFragment();
                Utils.addNextFragment(getActivity(), mFragment, ProductDetailsFragment.this, true);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        bundle = getArguments();

        if (bundle != null) {
            productListModel = bundle.getParcelable(getString(R.string.bdl_model));
        }
    }
    /**
     * handle cart list click listener
     */

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.findItem(R.id.menu_left);
        item.setVisible(true);
        item.setIcon(R.drawable.cart);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Utils.hideKeyboard(getActivity());
                CartListFragment cartFragment = new CartListFragment();
                cartFragment.setTargetFragment(ProductDetailsFragment.this, 222);
                Utils.addNextFragment(getActivity(), cartFragment, ProductDetailsFragment.this, true);
                return true;
            }
        });
    }


    /**
     * init Components
     */


    @Override
    public void initComponents(View rootView) {

        viewPagerImages = rootView.findViewById(R.id.fragment_product_details_vpSlider);
        rlAddTocart = rootView.findViewById(R.id.fragment_product_details_rlAddToCart);
        tvQuantity = rootView.findViewById(R.id.fragment_product_details_tvTotalKg);
        tvName = rootView.findViewById(R.id.fragment_product_details_tvTitle);
        tvPrice = rootView.findViewById(R.id.fragment_product_details_tvPrice);
        ivPlus = rootView.findViewById(R.id.fragment_product_details_ivPlus);
        ivMins = rootView.findViewById(R.id.fragment_product_details_ivMins);
        tvdescription=rootView.findViewById(R.id.fragment_product_details_tvDescription);

        ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBUtils.getInstance(getActivity()).updateProduct(
                        productListModel.getProductId(),
                        productListModel.getProductQuantity()
                );

                addToCart(true);


            }
        });
        ivMins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart(false);
            }
        });
        setUpSliderImages();
        setUpDetails();

       rlAddTocart.setOnClickListener(new View.OnClickListener() {
         @Override
          public void onClick(View v) {
             int count = DBUtils.getInstance(getActivity()).count(productListModel.getProductId());

             ProductsHelper productsHelper = new ProductsHelper(getActivity());

                 productsHelper.addProduct(
                     tvName.getText().toString(),
                     tvPrice.getText().toString(),
                     Integer.parseInt(tvQuantity.getText().toString()),
                     productListModel.getProductImageUrl(),
                     productListModel.getProductId()

             );
             CartListFragment cartFragment = new CartListFragment();
             cartFragment.setTargetFragment(ProductDetailsFragment.this, 222);
             Utils.addNextFragment(getActivity(), cartFragment, ProductDetailsFragment.this, true);

         }
            });
    }

    public void mt(String text){

        Toast.makeText(getView().getContext(),text, Toast.LENGTH_SHORT).show();
    }
    private void setUpDetails() {

        if (productListModel != null) {

            tvName.setText(productListModel.getProductName());

            int t = productListModel.getTotalKg();

            if (t == 0)
            {
                t = 1;
            }


            tvQuantity.setText(""+t);
            tvPrice.setText(productListModel.getProductPrice());
            totalKg = productListModel.getTotalKg();
            tvdescription.setText(productListModel.getProductDescription());
            }
    }

    /**
     * SetUp product slider images
     */

    private void setUpSliderImages() {
        try {
            pagerImgList = new ArrayList<>();

            JSONArray jsonArrayOfImagesofProduct = productListModel.getJsonArrayOfImagesOfProduct();


            for (int i = 0; i < jsonArrayOfImagesofProduct.length(); i++) {

                JSONObject js = jsonArrayOfImagesofProduct.getJSONObject(i);

                String urltest1 = js.getString("url");

                pagerImgList.add(urltest1);
            }

            imagePagerAdapter = new ProductPagerAdapter(getActivity(), pagerImgList);
            viewPagerImages.setAdapter(imagePagerAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.fragment_product_details_rlAddToCart:
                CartListFragment cartFragment = new CartListFragment();
                cartFragment.setTargetFragment(ProductDetailsFragment.this, 222);
                Utils.addNextFragment(getActivity(), cartFragment, ProductDetailsFragment.this, true);
                break;
        }
    }



    /**
     * add item on cart
     */

    private void addToCart(boolean addCart) {

        if (addCart) {
            totalKg = totalKg + 1;
            tvQuantity.setText(totalKg+"");


        } else {
            if (totalKg < 1) {
                totalKg = 1;
                tvQuantity.setText(totalKg+"");
            }
            else {
                totalKg = totalKg - 1;
                tvQuantity.setText(totalKg+"");
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            initToolbar();

        }
    }
}
