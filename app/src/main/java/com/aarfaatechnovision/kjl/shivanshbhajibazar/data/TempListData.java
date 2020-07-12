package com.aarfaatechnovision.kjl.shivanshbhajibazar.data;

import android.content.Context;
import android.widget.Toast;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.AddressList.AddressListModelNew;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.DialogFiiter.DialogOfferListModel;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.DialogFiiter.DiloagFitterDiscountModel;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.DialogFiiter.DiloagFitterItemModel;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.OrderList.OrderListModelNew;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.cart.CartlistModel;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.order.OrderListModel;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.product.ProductListModel;
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

public class TempListData {
    Context context;

    public List<ProductListModel> getHomeProductList(final Context context) {

        final List<ProductListModel> productListModelArrayList = new ArrayList<>();

        String urlForVolleyRequest = "https://aarfaatechnovision.com/shivansh/api/category_list.php";

        RequestQueue requestQueue = Volley.newRequestQueue(context);

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
                        productListModel.setCategoryName(categoryName);
                        productListModelArrayList.add(productListModel);



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error);

                Toast.makeText(context, "Internet Error", Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(jsonObjectRequest);



        return productListModelArrayList;

    }

    public List<ProductListModel> getProductList() {

        List<ProductListModel> productListModelArrayList = new ArrayList<>();
        ProductListModel productListModel = new ProductListModel();

        productListModel.setProductName("Apple");
        productListModel.setProductPrice("$10 ");
        productListModel.setProductImage(R.drawable.p_apple);
        productListModel.setkG("1Kg");
        productListModel.setTotalKg(0);
        productListModelArrayList.add(productListModel);


        productListModel = new ProductListModel();
        productListModel.setProductName("Tomato");
        productListModel.setProductPrice("$15 ");
        productListModel.setkG("1Kg");
        productListModel.setTotalKg(0);
        productListModel.setProductImage(R.drawable.p_tomato);
        productListModelArrayList.add(productListModel);


        productListModel = new ProductListModel();
        productListModel.setProductName("Lemon");
        productListModel.setProductPrice("$25 ");
        productListModel.setkG("1Kg");
        productListModel.setTotalKg(0);
        productListModel.setProductImage(R.drawable.p_lemon);
        productListModelArrayList.add(productListModel);

        productListModel = new ProductListModel();
        productListModel.setProductName("Pineapple");
        productListModel.setProductPrice("$8 ");
        productListModel.setkG("1Kg");
        productListModel.setTotalKg(0);
        productListModel.setProductImage(R.drawable.pineple);
        productListModelArrayList.add(productListModel);


        productListModel.setProductName("Kiwi Fruit");
        productListModel.setProductPrice("$10 ");
        productListModel.setkG("1Kg");
        productListModel.setTotalKg(0);
        productListModel.setProductImage(R.drawable.p_kiwi);
        productListModelArrayList.add(productListModel);


        productListModel = new ProductListModel();
        productListModel.setProductName("Guava");
        productListModel.setProductPrice("$15 ");
        productListModel.setkG("1Kg");
        productListModel.setTotalKg(0);
        productListModel.setProductImage(R.drawable.gw);
        productListModelArrayList.add(productListModel);


        productListModel = new ProductListModel();
        productListModel.setProductName("Graps");
        productListModel.setProductPrice("$25 ");
        productListModel.setkG("1Kg");
        productListModel.setTotalKg(0);
        productListModel.setProductImage(R.drawable.graps);
        productListModelArrayList.add(productListModel);

        productListModel = new ProductListModel();
        productListModel.setProductName("Pineapple");
        productListModel.setProductPrice("$8 ");
        productListModel.setkG("1Kg");
        productListModel.setTotalKg(0);
        productListModel.setProductImage(R.drawable.pineple);
        productListModelArrayList.add(productListModel);

        productListModel.setProductName("Apple");
        productListModel.setProductPrice("$10 ");
        productListModel.setkG("1Kg");
        productListModel.setTotalKg(0);
        productListModel.setProductImage(R.drawable.p_apple);
        productListModelArrayList.add(productListModel);


        productListModel = new ProductListModel();
        productListModel.setProductName("Guava");
        productListModel.setProductPrice("$15 ");
        productListModel.setkG("1Kg");
        productListModel.setTotalKg(0);
        productListModel.setProductImage(R.drawable.gw);
        productListModelArrayList.add(productListModel);


        productListModel = new ProductListModel();
        productListModel.setProductName("Graps");
        productListModel.setProductPrice("$25 ");
        productListModel.setkG("1Kg");
        productListModel.setTotalKg(0);
        productListModel.setProductImage(R.drawable.graps);
        productListModelArrayList.add(productListModel);

        productListModel = new ProductListModel();
        productListModel.setProductName("Pineapple");
        productListModel.setProductPrice("$8 ");
        productListModel.setkG("1Kg");
        productListModel.setTotalKg(0);
        productListModel.setProductImage(R.drawable.pineple);
        productListModelArrayList.add(productListModel);


        return productListModelArrayList;

    }

    public List<DiloagFitterItemModel> getFilterList() {

        List<DiloagFitterItemModel> diloagFitterItemModelList = new ArrayList<>();
        DiloagFitterItemModel diloagFitterItemModel = new DiloagFitterItemModel();

        diloagFitterItemModel.setSorting("LOWEST");
        diloagFitterItemModel.setPrice("PRICE");
        diloagFitterItemModel.setFirst("FIRST");
        diloagFitterItemModelList.add(diloagFitterItemModel);

        diloagFitterItemModel = new DiloagFitterItemModel();
        diloagFitterItemModel.setSorting("HEIGHEST");
        diloagFitterItemModel.setPrice("PRICE");
        diloagFitterItemModel.setFirst("FIRST");
        diloagFitterItemModelList.add(diloagFitterItemModel);

        diloagFitterItemModel = new DiloagFitterItemModel();
        diloagFitterItemModel.setSorting("POPULER");
        diloagFitterItemModel.setPrice("PRICE");
        diloagFitterItemModel.setFirst("FIRST");
        diloagFitterItemModelList.add(diloagFitterItemModel);

        diloagFitterItemModel = new DiloagFitterItemModel();
        diloagFitterItemModel.setSorting("NEWEST");
        diloagFitterItemModel.setPrice("PRICE");
        diloagFitterItemModel.setFirst("FIRST");
        diloagFitterItemModelList.add(diloagFitterItemModel);

        diloagFitterItemModel = new DiloagFitterItemModel();
        diloagFitterItemModel.setSorting("BEST");
        diloagFitterItemModel.setPrice("PRICE");
        diloagFitterItemModel.setFirst("FIRST");
        diloagFitterItemModelList.add(diloagFitterItemModel);

        return diloagFitterItemModelList;

    }

    public List<DialogOfferListModel> getOfferList() {

        List<DialogOfferListModel> diloagFitterItemModelList = new ArrayList<>();

        DialogOfferListModel dialogOfferListModel = new DialogOfferListModel();

        dialogOfferListModel = new DialogOfferListModel();
        dialogOfferListModel.setOfferName("Buy More,\n" + "Save More");
        diloagFitterItemModelList.add(dialogOfferListModel);

        dialogOfferListModel = new DialogOfferListModel();
        dialogOfferListModel.setOfferName("Special\n" + "Price");
        diloagFitterItemModelList.add(dialogOfferListModel);

        dialogOfferListModel = new DialogOfferListModel();
        dialogOfferListModel.setOfferName("Item of\n" + "The Day");
        diloagFitterItemModelList.add(dialogOfferListModel);

        dialogOfferListModel = new DialogOfferListModel();
        dialogOfferListModel.setOfferName("BUY 1,\n" + "GET 1 FREE");
        diloagFitterItemModelList.add(dialogOfferListModel);

        return diloagFitterItemModelList;

    }

    public List<DiloagFitterDiscountModel> getDiscountList() {

        List<DiloagFitterDiscountModel> diloagFitterDiscountModels = new ArrayList<>();

        DiloagFitterDiscountModel dialogOfferListModel = new DiloagFitterDiscountModel();

        dialogOfferListModel = new DiloagFitterDiscountModel();
        dialogOfferListModel.setDiscount("10");
        diloagFitterDiscountModels.add(dialogOfferListModel);

        dialogOfferListModel = new DiloagFitterDiscountModel();
        dialogOfferListModel.setDiscount("20");
        diloagFitterDiscountModels.add(dialogOfferListModel);

        dialogOfferListModel = new DiloagFitterDiscountModel();
        dialogOfferListModel.setDiscount("30");
        diloagFitterDiscountModels.add(dialogOfferListModel);

        dialogOfferListModel = new DiloagFitterDiscountModel();
        dialogOfferListModel.setDiscount("40");
        diloagFitterDiscountModels.add(dialogOfferListModel);

        return diloagFitterDiscountModels;

    }


    public List<OrderListModelNew> getOrderListNewList() {

        List<OrderListModelNew> orderListModelNewList = new ArrayList<>();

        OrderListModelNew orderListModelNew = new OrderListModelNew();
        orderListModelNew.setOrderName("Black Grape");
        orderListModelNew.setQuntity("Qty: 1");
        orderListModelNew.setPrice("₹ 122");
        orderListModelNewList.add(orderListModelNew);

        orderListModelNew = new OrderListModelNew();
        orderListModelNew.setOrderName("Mango");
        orderListModelNew.setQuntity("Qty: 1");
        orderListModelNew.setPrice("₹ 100");
        orderListModelNewList.add(orderListModelNew);

        orderListModelNew = new OrderListModelNew();
        orderListModelNew.setOrderName("Mango");
        orderListModelNew.setQuntity("Qty: 1");
        orderListModelNew.setPrice("₹ 100");
        orderListModelNewList.add(orderListModelNew);


        orderListModelNew = new OrderListModelNew();
        orderListModelNew.setOrderName("Mango");
        orderListModelNew.setQuntity("Qty: 1");
        orderListModelNew.setPrice("₹ 100");
        orderListModelNewList.add(orderListModelNew);

        orderListModelNew = new OrderListModelNew();
        orderListModelNew.setOrderName("Mango");
        orderListModelNew.setQuntity("Qty: 1");
        orderListModelNew.setPrice("₹ 100");
        orderListModelNewList.add(orderListModelNew);


        return orderListModelNewList;

    }


    public List<CartlistModel> getCartList() {



//        DBUtils dbUtils=new DBUtils(context);
//        dbUtils.addProduct(cartlistModel.getProductId(),cartlistModel.getProductQuantity());

        List<CartlistModel> cartlistModelNewList = new ArrayList<>();
        CartlistModel productListModel = new CartlistModel();
//
//        productListModel.setProductName("Black Grape");
//        productListModel.setProductPrice(10);
//        productListModel.setProductImages(R.drawable.p_black_graps);
//        productListModel.setKg("1Kg");
//        productListModel.setProductQuantity(1);
//        cartlistModelNewList.add(productListModel);
//
//
//        productListModel = new CartlistModel();
//        productListModel.setProductName("Tomato");
//        productListModel.setProductPrice(50);
//        productListModel.setProductImages(R.drawable.p_tomato);
//        productListModel.setKg("1Kg");
//        productListModel.setProductQuantity(1);
//        cartlistModelNewList.add(productListModel);
//
//        productListModel = new CartlistModel();
//        productListModel.setProductName("Mango");
//        productListModel.setProductPrice(30);
//        productListModel.setProductImages(R.drawable.p_mango);
//        productListModel.setKg("1Kg");
//        productListModel.setProductQuantity(1);
//        cartlistModelNewList.add(productListModel);
//
//        productListModel = new CartlistModel();
//        productListModel.setProductName("Capsicum");
//        productListModel.setProductPrice(15);
//        productListModel.setProductImages(R.drawable.p_capsecum);
//        productListModel.setKg("1Kg");
//        productListModel.setProductQuantity(1);
//        cartlistModelNewList.add(productListModel);


        cartlistModelNewList.add(productListModel);


        return cartlistModelNewList;
    }

    public List<OrderListModel> getOrderList() {

        List<OrderListModel> orderListModelList = new ArrayList<>();


        OrderListModel OrderListModelNew = new OrderListModel();
        OrderListModelNew.setOrderId("#1122GG33");
        OrderListModelNew.setPrice("$10 ");
        OrderListModelNew.setOrderDate("26-12-2017");
        OrderListModelNew.setDeliveryDate("28-12-2017");
        OrderListModelNew.setStatus("pending");
        orderListModelList.add(OrderListModelNew);


        OrderListModelNew = new OrderListModel();
        OrderListModelNew.setOrderId("#3322GG33");
        OrderListModelNew.setPrice("$10 ");
        OrderListModelNew.setOrderDate("28-11-2017");
        OrderListModelNew.setDeliveryDate("30-11-2017");
        OrderListModelNew.setStatus("cancel");
        orderListModelList.add(OrderListModelNew);

        OrderListModelNew = new OrderListModel();
        OrderListModelNew.setOrderId("#4589GG32");
        OrderListModelNew.setPrice("$10 ");
        OrderListModelNew.setOrderDate("05-12-2017");
        OrderListModelNew.setDeliveryDate("07-12-2017");
        OrderListModelNew.setStatus("delivery");
        orderListModelList.add(OrderListModelNew);


        OrderListModelNew = new OrderListModel();
        OrderListModelNew.setOrderId("#1522O833");
        OrderListModelNew.setPrice("$10 ");
        OrderListModelNew.setOrderDate("23-08-2017");
        OrderListModelNew.setDeliveryDate("24-28-2017");
        OrderListModelNew.setStatus("pending");
        orderListModelList.add(OrderListModelNew);


        OrderListModelNew = new OrderListModel();
        OrderListModelNew.setOrderId("#8922HJ78");
        OrderListModelNew.setPrice("$10 ");
        OrderListModelNew.setOrderDate("08-05-2017");
        OrderListModelNew.setDeliveryDate("10-05-2017");
        OrderListModelNew.setStatus("delivery");
        orderListModelList.add(OrderListModelNew);


        OrderListModelNew = new OrderListModel();
        OrderListModelNew.setOrderId("#1256GG34");
        OrderListModelNew.setPrice("$10 ");
        OrderListModelNew.setOrderDate("08-08-2017");
        OrderListModelNew.setDeliveryDate("10-08-2017");
        OrderListModelNew.setStatus("cancel");
        orderListModelList.add(OrderListModelNew);


        return orderListModelList;


    }


    public List<AddressListModelNew> getCheckoutAddress() {

        AddressListModelNew addressListModelNew = new AddressListModelNew();
        List<AddressListModelNew> addressListModelNews = new ArrayList<>();

        addressListModelNew.setAddress("Hans Egedesvej 29\n" + "P.O. Box 1615\n" + "3900 Nuuk\n" + "Greenland");
        addressListModelNews.add(addressListModelNew);

        addressListModelNew = new AddressListModelNew();
        addressListModelNew.setAddress("Hans Egedesvej 29\n" + "P.O. Box 1615\n" + "3900 Nuuk\n" + "Greenland");
        addressListModelNews.add(addressListModelNew);


        return addressListModelNews;


    }
}
