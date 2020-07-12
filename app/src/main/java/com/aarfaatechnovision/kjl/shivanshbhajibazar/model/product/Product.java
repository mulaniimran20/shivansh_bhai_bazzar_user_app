package com.aarfaatechnovision.kjl.shivanshbhajibazar.model.product;

public class Product {
    String pId,pQuantity;

    public Product(String pId, String pQuantity) {
        this.pId = pId;
        this.pQuantity = pQuantity;
    }

    public Product() {
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpQuantity() {
        return pQuantity;
    }

    public void setpQuantity(String pQuantity) {
        this.pQuantity = pQuantity;
    }
}
