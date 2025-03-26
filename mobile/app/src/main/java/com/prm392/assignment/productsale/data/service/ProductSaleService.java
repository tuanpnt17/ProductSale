package com.prm392.assignment.productsale.data.service;

import com.prm392.assignment.productsale.model.products.ProductsSaleResponseModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface ProductSaleService {

//    @Headers({"client: mobile"})
//    @GET("product/demo")
//    Observable<Response<ProductsSaleResponseModel>> getDemoProducts(@Header("Authorization") String token);

    @Headers({"client: mobile"})
    @GET("product")
    Observable<Response<ProductsSaleResponseModel>> getProducts(@Header("Authorization") String token);


}
