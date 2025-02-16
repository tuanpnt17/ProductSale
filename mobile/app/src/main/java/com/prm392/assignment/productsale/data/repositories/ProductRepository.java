package com.prm392.assignment.productsale.data.repositories;

import androidx.annotation.NonNull;

import com.prm392.assignment.productsale.network.ApiService;
import com.prm392.assignment.productsale.data.models.Product;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductRepository {
    private final ApiService apiService;

    @Inject
    public ProductRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public void getAllProducts(final ProductsDataCallback callback) {
        apiService.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Response error"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public interface ProductsDataCallback {
        void onSuccess(List<Product> products);
        void onFailure(Throwable throwable);
    }
}
