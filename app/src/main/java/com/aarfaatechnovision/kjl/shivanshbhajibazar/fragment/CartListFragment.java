package com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment;


import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.Activity.HomeActivity;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter.CartListAdapter;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.data.DBUtils;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.data.ProductsHelper;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.data.TempListData;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.data.TotalBill;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.dialog.DialogFragmentChoosPincode;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.cart.CartlistModel;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.product.ProductListModel;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.util.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class CartListFragment extends BaseFragment {

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
    private CartListAdapter productListAdapter;
    private List<CartlistModel> productListModelArrayList;
    private MenuItem item;



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




        mLayoutManager = new LinearLayoutManager(getActivity());
        rvProductList.setLayoutManager(mLayoutManager);
        rlCheckOut.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        ivHome.setOnClickListener(this);
        //llSelectPin.setOnClickListener(this);

        getListData();
        tvTotalPrice.setText("Rs." + getTotalPrice());
    }

    private int getTotalPrice() {
        int total = 0;

        for (int i = 0; i < productListModelArrayList.size(); i++) {
            int totalQty = productListModelArrayList.get(i).getProductQuantity();

            String[] splitm;

            if(productListModelArrayList.get(i).getProductPrice().contains("/")) {
                splitm = productListModelArrayList.get(i).getProductPrice().split("/");
            }
            else{
                splitm = new String[]{productListModelArrayList.get(i).getProductPrice()};
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
     * get Cart list data and setUp adapter
     */

    private void getListData() {

        productListModelArrayList = new ArrayList<>();
        ProductsHelper productsHelper = new ProductsHelper(getActivity());

        productListModelArrayList.addAll(productsHelper.getAllNotes());
        productListAdapter =new CartListAdapter(getActivity(), productListModelArrayList, this);

        tvCartCount.setText("My Cart ("+productListModelArrayList.size()+")");

        rvProductList.setAdapter(productListAdapter);


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
            Utils.addNextFragment(getActivity(), cartFragment, CartListFragment.this, true);
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
        tvTotalPrice.setText("Rs. "+ getTotalPrice());

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

        tvCartCount.setText("My Cart ("+productListModelArrayList.size()+")");
    }


}
