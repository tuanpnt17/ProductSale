package com.prm392.assignment.productsale.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GoogleSocialAuthModel {

    @SerializedName("fullname")
    private String fullName;

    @SerializedName("email")
    private String email;

    @SerializedName("thirdParty_id")
    private String clientId;

    @SerializedName("profile_img")
    private String image;


}
