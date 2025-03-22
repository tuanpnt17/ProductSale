package com.prm392.assignment.productsale.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FacebookSocialAuthModel {

    @SerializedName("fullname")
    private String fullName;

    @Expose
    private transient String email; //Ignore in serialization

    @SerializedName("thirdParty_id")
    private String clientId;

    @SerializedName("profile_img")
    private String image;


}
