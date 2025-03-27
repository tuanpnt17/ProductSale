package com.prm392.assignment.productsale.data.service;

import com.prm392.assignment.productsale.model.BaseResponseModel;
import com.prm392.assignment.productsale.model.cart.AddProductCartModel;
import com.prm392.assignment.productsale.model.cart.CartModel;
import com.prm392.assignment.productsale.model.products.ProductSalePageResponseModel;
import com.prm392.assignment.productsale.model.products.ProductsSaleResponseModel;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductSaleService {

//    @Headers({"client: mobile"})
//    @GET("product/demo")
//    Observable<Response<ProductsSaleResponseModel>> getDemoProducts(@Header("Authorization") String token);

    @Headers({"client: mobile"})
    @GET("product")
    Observable<Response<ProductsSaleResponseModel>> getProducts(@Header("Authorization") String token,
                                                                @Query("pageIndex") Integer pageIndex,
                                                                @Query("pageSize") Integer pageSize,
                                                                @Query("search") String search,
                                                                @Query("sortBy") String sortBy,
                                                                @Query("sortDescending") Boolean sortDescending,
                                                                @Query("minPrice") Double minPrice,
                                                                @Query("maxPrice") Double maxPrice,
                                                                @Query("categoryIds") List<Integer> categoryIds);
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
