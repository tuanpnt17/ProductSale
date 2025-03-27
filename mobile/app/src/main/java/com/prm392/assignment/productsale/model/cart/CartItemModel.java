package com.prm392.assignment.productsale.model.cart;

import com.google.gson.annotations.SerializedName;
import com.prm392.assignment.productsale.model.products.ProductSaleModel;

public class CartItemModel {
    @SerializedName(value = "cartItemId")
    private int cartItemId;

    @SerializedName(value = "cartId")
    private int cartId;

    @SerializedName(value = "productId")
    private int productId;

    @SerializedName(value = "quantity")
    private int quantity;

    @SerializedName(value = "price")
    private float price;

    @SerializedName(value = "product")
    private ProductSaleModel product;

    // Getter cho cartItemId
    public int getCartItemId() {
        return cartItemId;
    }

    // Getter cho cartId
    public int getCartId() {
        return cartId;
    }

    // Getter cho productId
    public int getProductId() {
        return productId;
    }

    // Getter cho quantity
    public int getQuantity() {
        return quantity;
    }

    // Getter cho price
    public float getPrice() {
        return price;
    }

    // Getter cho product (đối tượng ProductSaleModel)
    public ProductSaleModel getProduct() {
        return product;
    }

    // Getter cho tên sản phẩm từ đối tượng ProductSaleModel
    public String getProductName() {
        return product != null ? product.getProductName() : null;
    }

    // Getter cho hình ảnh sản phẩm từ đối tượng ProductSaleModel
    public String getProductImage() {
        return product != null ? product.getProductImage() : null;
    }

}


