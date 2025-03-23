package com.prm392.assignment.productsale.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SocialUserModel {

    @SerializedName("id")
    private String id;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("email")
    private String email;

    @SerializedName("profile_img")
    private String image;

    @SerializedName("thirdParty_id")
    private String thirdPartId;

    public SocialUserModel(){
        id = "id";
        fullName = "fullName";
        email = "";
        image = "";
        thirdPartId = "";
    }

    public String getImageLink() {
        if(image.contains("http:")) image = image.replace("http:","https:");
        return image;
    }

    public String getEncodedImage() {
        if(image.contains("http")) return "";
        return image;
    }

    public void setEncodedImage(String image) {
        this.image = "data:image/webp;base64,"+image;
    }

    public void setImageUrl(String imageUrl) {
        this.image = imageUrl;
    }

}
