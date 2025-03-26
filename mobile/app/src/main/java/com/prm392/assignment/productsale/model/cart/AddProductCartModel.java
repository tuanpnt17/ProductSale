package com.prm392.assignment.productsale.model.cart;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProductCartModel {
    @SerializedName("productId")
    private long productId;
    @SerializedName("quantity")
    private int quantity;
}
