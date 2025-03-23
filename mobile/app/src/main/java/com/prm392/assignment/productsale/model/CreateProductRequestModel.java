package com.prm392.assignment.productsale.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateProductRequestModel{
    @SerializedName("title")
    private String name;

    @SerializedName("title_ar")
    private String nameArabic;

    @SerializedName("sale")
    private int sale;

    @SerializedName("description")
    private String description;

    @SerializedName("description_ar")
    private String descriptionArabic;

    @SerializedName("price")
    private Double price;

    @SerializedName("brand")
    private String brand;

    @SerializedName("category")
    private String category;

    @SerializedName("category_ar")
    private String categoryArabic;

    @SerializedName("product_images")
    private ArrayList<String> images;


}
