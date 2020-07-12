package com.aarfaatechnovision.kjl.shivanshbhajibazar.model.product;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;

import java.util.ArrayList;

public class ProductListModel implements Parcelable
{

    int CategoryId;
    int productId,productQuantity;
    String productImageUrl;
    String productDiscountPercent;
    String productDescription;
    String extraImages;
    JSONArray jsonArrayOfImagesOfProduct;


    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public JSONArray getJsonArrayOfImagesOfProduct() {
        return jsonArrayOfImagesOfProduct;
    }

    public void setJsonArrayOfImagesOfProduct(JSONArray jsonArrayOfImagesOfProduct) {
        this.jsonArrayOfImagesOfProduct = jsonArrayOfImagesOfProduct;
    }

    public String getExtraImages() {
        return extraImages;
    }

    public void setExtraImages(String extraImages) {
        this.extraImages = extraImages;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getProductDiscountPercent() {
        return productDiscountPercent;
    }

    public void setProductDiscountPercent(String productDiscountPercent) {
        this.productDiscountPercent = productDiscountPercent;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getCaegoryImageUrl() {
        return caegoryImageUrl;
    }

    public void setCaegoryImageUrl(String caegoryImageUrl) {
        this.caegoryImageUrl = caegoryImageUrl;
    }

    String caegoryImageUrl;

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    private String CategoryName;

    private String productName;
    private String productPrice;
    private int productImage;
    private String kG;
    private int totalKg;
    private boolean isLike=false;


    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public static Creator<ProductListModel> getCREATOR() {
        return CREATOR;
    }



    public ProductListModel(Parcel in) {
        productName = in.readString();
        productPrice = in.readString();
        productImage = in.readInt();
        kG = in.readString();
        totalKg = in.readInt();
    }

    public static final Creator<ProductListModel> CREATOR = new Creator<ProductListModel>() {
        @Override
        public ProductListModel createFromParcel(Parcel in) {
            return new ProductListModel(in);
        }

        @Override
        public ProductListModel[] newArray(int size) {
            return new ProductListModel[size];
        }
    };

    public ProductListModel() {

    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public int getTotalKg() {
        return totalKg;
    }

    public void setTotalKg(int totalKg) {
        this.totalKg = totalKg;
    }



    public String getkG() {
        return kG;
    }

    public void setkG(String kG) {
        this.kG = kG;
    }


    public int getProductImage() {
        return productImage;
    }


    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productName);
        dest.writeString(productPrice);
        dest.writeInt(productImage);
        dest.writeString(kG);
        dest.writeInt(totalKg);
    }
}
