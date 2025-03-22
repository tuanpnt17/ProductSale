package com.prm392.assignment.productsale.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Setter
public class StoreModel {
    @Getter
    @SerializedName("id")
    private int id;
    @Getter
    @SerializedName("name")
    private String name;
    @Getter
    @SerializedName("type")
    private String type;
    @SerializedName("logo")
    private String logo;
    @Getter
    @SerializedName("phone")
    private String phone;
    @Getter
    @SerializedName("address")
    private String address;
    @Getter
    @SerializedName("niche_market")
    private String storeCategory;
    @Getter
    @SerializedName("description")
    private String description;
    @Getter
    @SerializedName("latitude")
    private Double latitude;
    @Getter
    @SerializedName("longitude")
    private Double longitude;
    @Getter
    @SerializedName("whatsapp")
    private String whatsapp;
    @Getter
    @SerializedName("facebook")
    private String facebook;
    @Getter
    @SerializedName("instagram")
    private String instagram;
    @Getter
    @SerializedName("website")
    private String website;

    public String getLogo() {
        return logo.replace("http:","https:");
    }
}
