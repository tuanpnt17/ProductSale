package com.prm392.assignment.productsale.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpcItemDbResponseModel {

    @SerializedName("items")
    private ArrayList<Item> items;

    public static class Item {
        @SerializedName("ean")
        private String code;

        @SerializedName("title")
        private String productName;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }
    }

}
