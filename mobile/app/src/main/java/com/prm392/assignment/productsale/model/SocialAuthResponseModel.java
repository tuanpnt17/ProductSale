package com.prm392.assignment.productsale.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SocialAuthResponseModel extends BaseResponseModel {

    @SerializedName("user")
    private SocialUserModel user;

}
