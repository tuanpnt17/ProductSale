package com.prm392.assignment.productsale.network;

import com.prm392.assignment.productsale.data.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("product")
    Call<List<Product>> getProducts();
}
