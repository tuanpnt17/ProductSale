package com.prm392.assignment.productsale.model.products;

import com.google.gson.annotations.SerializedName;
import com.prm392.assignment.productsale.model.BaseResponseModel;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductsSaleResponseModel extends BaseResponseModel {

    @SerializedName("results")
    private int resultsCount;

    @SerializedName("products")
    private ArrayList<ProductSaleModel> products;

}