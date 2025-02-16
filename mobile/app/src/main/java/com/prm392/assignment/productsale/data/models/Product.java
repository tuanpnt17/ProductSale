package com.prm392.assignment.productsale.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int productId;
    private String productName;
    private String briefDescription;
    private double price;
    private String imageUrl;
}
