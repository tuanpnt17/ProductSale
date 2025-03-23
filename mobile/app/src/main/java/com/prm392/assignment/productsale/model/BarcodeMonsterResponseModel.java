package com.prm392.assignment.productsale.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BarcodeMonsterResponseModel {
    @SerializedName("code")
    private String code;

    @SerializedName("description")
    private String productName;

}
