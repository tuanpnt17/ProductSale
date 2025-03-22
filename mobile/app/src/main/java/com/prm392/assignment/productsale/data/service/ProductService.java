package com.prm392.assignment.productsale.data.service;

import io.reactivex.rxjava3.core.Observable;

import com.prm392.assignment.productsale.model.products.ProductModel;
import com.prm392.assignment.productsale.model.products.ProductPageResponseModel;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ProductService {
    @GET("products/{id}")
    @Headers({"client: mobile"})
    Observable<Response<ProductPageResponseModel>> getProductById(@Path("id") long productId);



}
