package com.prm392.assignment.productsale.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class UserModel {

    @Setter
    @Getter
    @SerializedName("id")
    private String id;

    @Setter
    @Getter
    @SerializedName("fullname")
    private String fullName;

    @Setter
    @Getter
    @SerializedName("email")
    private String email;

    @SerializedName("profile_img")
    private String image;

    @Setter
    @Getter
    @SerializedName("last_seen")
    private String lastSeen;

    @Setter
    @Getter
    @SerializedName("signedInWith")
    private int signedInWith;

    @Setter
    @Getter
    @SerializedName("store_id")
    private int storeId;

    public static final int SIGNED_IN_WITH_EMAIL = 0;
    public static final int SIGNED_IN_WITH_GOOGLE = 1;
    public static final int SIGNED_IN_WITH_FACEBOOK = 2;

    public UserModel(){
        id = "id";
        fullName = "fullName";
        email = "email@email.com";
        image = "";
        lastSeen = "";
        signedInWith = SIGNED_IN_WITH_EMAIL;
    }

    public String getImageLink() {
        return image.replace("http:","https:");
    }

    public String getEncodedImage() {
        if(image.contains("http")) return "";
        return image;
    }

    public void setEncodedImage(String image) {
        this.image = image;
    }

    public void setImageUrl(String imageUrl) {
        this.image = imageUrl;
    }

    public boolean hasStore(){
        return storeId != 0;
    }

    public String getAccountType(){
        if (hasStore()) return "Seller Account";
        else return "User Account";
    }
}
