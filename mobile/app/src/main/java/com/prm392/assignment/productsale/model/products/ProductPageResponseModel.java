package com.prm392.assignment.productsale.model.products;

import com.google.gson.annotations.SerializedName;
import com.prm392.assignment.productsale.model.BaseResponseModel;

public class ProductPageResponseModel extends BaseResponseModel {
    @SerializedName("product")
    private ProductPageModel product;

    public ProductPageModel getProduct() {
        return product;
    }

    public void setProduct(ProductPageModel product) {
        this.product = product;
    }
}
