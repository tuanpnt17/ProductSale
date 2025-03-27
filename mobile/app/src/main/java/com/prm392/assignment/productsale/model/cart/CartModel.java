package com.prm392.assignment.productsale.model.cart;

import com.google.gson.annotations.SerializedName;
import com.prm392.assignment.productsale.model.products.ProductSaleModel;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartModel {
    @SerializedName(value = "cartId")
    private int cartId;

    @SerializedName(value = "userId")
    private int userId;

    @SerializedName(value = "totalPrice")
    private float totalPrice;

    @SerializedName("cartItems")
    private ArrayList<CartItemModel> cartItems;

    public ArrayList<CartItemModel> getCartItems() {
        return cartItems;
    }

    // Optionally, you can provide setters or other methods if needed
    public void setCartItems(ArrayList<CartItemModel> cartItems) {
        this.cartItems = cartItems;
    }
}
