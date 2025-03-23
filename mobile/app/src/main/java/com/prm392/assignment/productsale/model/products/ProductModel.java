package com.prm392.assignment.productsale.model.products;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {
    @SerializedName(value = "product_id",alternate = {"id"})
    private long id;

    @SerializedName(value = "product_title",alternate = {"title"})
    private String name;

    @SerializedName(value = "product_title_ar",alternate = {"title_ar"})
    private String nameArabic;

    @SerializedName(value = "product_price",alternate = {"price"})
    private double originalPrice;

    @SerializedName(value = "product_rating",alternate = {"rating"})
    private double rate;

    @SerializedName("sale")
    private int salePercent;

    @SerializedName(value = "product_brand",alternate = {"brand"})
    private String brand;

    @SerializedName(value = "product_category",alternate = {"category"})
    private String category;

    @SerializedName(value = "product_category_ar",alternate = {"category_ar"})
    private String categoryArabic;

    @SerializedName(value = "image_url",alternate = {"image"})
    private String image;

    @SerializedName("is_favourite")
    private int favorite;

    @SerializedName("store_id")
    private long storeId;

    @SerializedName("store_name")
    private String storeName;

    @SerializedName("store_type")
    private String storeType;

    @SerializedName("store_logo")
    private String storeLogo;

    @SerializedName("store_latitude")
    private Double storeLatitude;

    @SerializedName("store_longitude")
    private Double storeLongitude;

    @SerializedName(value = "viewed_at",alternate = {"favourite_date"})
    private String date;

    @SerializedName("description")
    private String description;

    @SerializedName("description_ar")
    private String descriptionArabic;

    public static final String ONLINE_STORE = "online";

    public static final String LOCAL_STORE = "local";
}
