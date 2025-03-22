package com.prm392.assignment.productsale.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StorePageModel extends BaseResponseModel {
    @SerializedName("store")
    private StoreModel store;
    @SerializedName("productsLength")
    private int productsCount;
    @SerializedName("products")
    private ArrayList<ProductModel> products;

}
