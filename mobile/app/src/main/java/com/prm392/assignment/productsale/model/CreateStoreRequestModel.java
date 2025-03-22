package com.prm392.assignment.productsale.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateStoreRequestModel {
    @SerializedName("logo")
    private String logoBase64;
    @SerializedName("name")
    private String name;
    @SerializedName("niche_market")
    private String category;
    @SerializedName("address")
    private String address;
    @SerializedName("latitude")
    private Double latitude;
    @SerializedName("longitude")
    private Double longitude;
    @SerializedName("description")
    private String description;
    @SerializedName("phone")
    private String phone;
    @SerializedName("whatsapp")
    private String whatsappPhoneNumber;
    @SerializedName("website")
    private String websiteLink;
    @SerializedName("facebook")
    private String facebookLink;
    @SerializedName("instagram")
    private String instagramLink;

}
