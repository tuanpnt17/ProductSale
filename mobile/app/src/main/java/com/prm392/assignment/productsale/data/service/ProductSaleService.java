package com.prm392.assignment.productsale.data.service;

import com.prm392.assignment.productsale.model.BaseResponseModel;
import com.prm392.assignment.productsale.model.cart.AddProductCartModel;
import com.prm392.assignment.productsale.model.cart.CartModel;
import com.prm392.assignment.productsale.model.products.ProductSalePageResponseModel;
import com.prm392.assignment.productsale.model.products.ProductsSaleResponseModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductSaleService {

    @Headers({"client: mobile"})
    @GET("product/demo")
    Observable<Response<ProductsSaleResponseModel>> getDemoProducts(@Header("Authorization") String token);

    @Headers({"client: mobile"})
    @GET("product/{productId}")
    Observable<Response<ProductSalePageResponseModel>> getProductSale(@Header("Authorization") String token, @Path("productId") long productId);

    @Headers({"client: mobile"})
    @POST("cart")
    Observable<Response<BaseResponseModel>> addToCart(@Header("Authorization") String token, @Body AddProductCartModel addProductCartModel);
    @Headers({"client: mobile"})
    @GET("cart/{userId}")
    Observable<Response<CartModel>> getCart(@Header("Authorization") String token, @Path("userId") int userId);
}
