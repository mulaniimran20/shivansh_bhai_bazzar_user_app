package com.aarfaatechnovision.kjl.shivanshbhajibazar.model.cart;

public class CartlistModel
{
   private int productId;
    private String productName,productImages;
    private int productQuantity;
    private String productPrice;
    private String kg;
    int productDbID;

    String order_discount_coupen, discount_percent, order_dates, order_number, payment_status, order_delivery_status;


    public String getOrder_discount_coupen() {
        return order_discount_coupen;
    }

    public void setOrder_discount_coupen(String order_discount_coupen) {
        this.order_discount_coupen = order_discount_coupen;
    }

    public String getDiscount_percent() {
        return discount_percent;
    }

    public void setDiscount_percent(String discount_percent) {
        this.discount_percent = discount_percent;
    }

    public String getOrder_dates() {
        return order_dates;
    }

    public void setOrder_dates(String order_dates) {
        this.order_dates = order_dates;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getOrder_delivery_status() {
        return order_delivery_status;
    }

    public void setOrder_delivery_status(String order_delivery_status) {
        this.order_delivery_status = order_delivery_status;
    }

    public void setProductDbID(int productDbId){
        this.productDbID = productDbId;
    }

    public int getProductDbID()
    {
        return productDbID;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductImages() {
        return productImages;
    }

    public void setProductImages(String productImages) {
        this.productImages = productImages;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getKg() {
        return kg;
    }

    public void setKg(String kg) {
        this.kg = kg;
    }




}
