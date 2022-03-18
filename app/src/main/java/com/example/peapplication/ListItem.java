package com.example.peapplication;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

public class ListItem {
    @SerializedName("Title")
    private String title;
    @SerializedName("HasBuyNow")
    private boolean hasBuyNow;
    @SerializedName("BuyNowPrice")
    private double buyNowPrice;
    @SerializedName("PictureHref")
    private String pictureHref;
    @SerializedName("Region")
    private String region;
    @SerializedName("PriceDisplay")
    private String priceDisplay;
    @SerializedName("IsBuyNowOnly")
    private boolean isBuyNowOnly;
    @SerializedName("IsClassified")
    private boolean isClassified;
    private Bitmap bitmap;

    public ListItem() {}

    public String getPictureHref() {
        return pictureHref;
    }

    public String getTitle() {
        return title;
    }

    public String getRegion() {
        return region;
    }

    public String getPriceDisplay() {
        return priceDisplay;
    }

    public boolean isHasBuyNow() {
        return hasBuyNow;
    }

    public double getBuyNowPrice() {
        return buyNowPrice;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
