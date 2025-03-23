package com.prm392.assignment.productsale.data.remote;

import com.prm392.assignment.productsale.model.BarcodeMonsterResponseModel;
import com.prm392.assignment.productsale.model.BaseResponseModel;
import com.prm392.assignment.productsale.model.ChangePasswordModel;
import com.prm392.assignment.productsale.model.CreateProductRequestModel;
import com.prm392.assignment.productsale.model.CreateStoreRequestModel;
import com.prm392.assignment.productsale.model.CreateStoreResponseModel;
import com.prm392.assignment.productsale.model.ProductPageResponseModel;
import com.prm392.assignment.productsale.model.ProductRateModel;
import com.prm392.assignment.productsale.model.ProductsResponseModel;
import com.prm392.assignment.productsale.model.SignInModel;
import com.prm392.assignment.productsale.model.SignUpModel;
import com.prm392.assignment.productsale.model.StorePageModel;
import com.prm392.assignment.productsale.model.UpcItemDbResponseModel;
import com.prm392.assignment.productsale.model.UserModel;
import com.prm392.assignment.productsale.model.UserResponseModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {

    //Account Sign in/up
    @Headers({"client: mobile"})
    @POST("api/Auth/login")
    Observable<Response<UserResponseModel>> signIn(@Body SignInModel signInModel);

    @Headers({"client: mobile"})
    @POST("api/Auth/register")
    Observable<Response<UserResponseModel>> signUp(@Body SignUpModel signUpModel);

    //User Data Calls
    @Headers({"client: mobile"})
    @GET("users")
    Observable<Response<UserResponseModel>> getUser(@Header("Authorization") String token);

    @Headers({"client: mobile"})
    @PATCH("users")
    Observable<Response<UserResponseModel>> updateUser(@Header("Authorization") String token, @Body UserModel userModel);

    @Headers({"client: mobile"})
    @PATCH("users/updatePassword")
    Observable<Response<BaseResponseModel>> changePassword(@Header("Authorization") String token, @Body ChangePasswordModel changePasswordModel);

    //Products Calls
    @Headers({"client: mobile"})
    @GET("products")
    Observable<Response<ProductsResponseModel>> searchProducts(@Header("Authorization") String token,
                                                               @Header("language") String language,
                                                               @Header("latitude") double userLat,
                                                               @Header("longitude") double userLong,
                                                               @Query("searchText") String keyword,
                                                               @Query("storeType") String storeType,
                                                               @Query("sort") String sort,
                                                               @Query("price_min") long minPrice,
                                                               @Query("price_max") long maxPrice,
                                                               @Query("category") String category,
                                                               @Query("brand") String brand,
                                                               @Query("cursor") long cursorLastItemId,
                                                               @Query("limit") int limit);

    @GET("prod/trial/lookup")
    Observable<Response<UpcItemDbResponseModel>> barcodeLookupUpcItemDb(@Query("upc") String barcode);

    @GET("amrk000/{barcode}")
    Observable<Response<BarcodeMonsterResponseModel>> barcodeLookupBarcodeMonster(@Path("barcode") String barcode);

    @Headers({"client: mobile"})
    @GET("products/{id}")
    Observable<Response<ProductPageResponseModel>> getProduct(@Header("Authorization") String token, @Path("id") long productId);

    @Headers({"client: mobile"})
    @POST("products/favourites/{id}")
    Observable<Response<BaseResponseModel>> addFavourite(@Header("Authorization") String token, @Path("id") long productId);

    @Headers({"client: mobile"})
    @DELETE("products/favourites/{id}")
    Observable<Response<BaseResponseModel>> removeFavourite(@Header("Authorization") String token, @Path("id") long productId);

    @Headers({"client: mobile"})
    @PATCH("products/{id}/rating")
    Observable<Response<BaseResponseModel>> rateProduct(@Header("Authorization") String token, @Path("id") long productId, @Body ProductRateModel productRateModel);

    @Headers({"client: mobile"})
    @GET("products/recommended")
    Observable<Response<ProductsResponseModel>> getRecommendedProducts(@Header("Authorization") String token);

    @Headers({"client: mobile"})
    @GET("products/viewed")
    Observable<Response<ProductsResponseModel>> getProductsViewsHistory(@Header("Authorization") String token);

    @Headers({"client: mobile"})
    @GET("products/favourites")
    Observable<Response<ProductsResponseModel>> getFavoriteProducts(@Header("Authorization") String token);

    @Headers({"client: mobile"})
    @GET("products/sales")
    Observable<Response<ProductsResponseModel>> getOnSaleProducts(@Header("Authorization") String token);

    //Store Calls
    @Headers({"client: mobile"})
    @GET("stores/{id}")
    Observable<Response<StorePageModel>> getStore(@Header("Authorization") String token, @Path("id") long storeId, @Query("page") int page);

    @Headers({"client: mobile"})
    @POST("stores")
    Observable<Response<CreateStoreResponseModel>> createStore(@Header("Authorization") String token, @Body CreateStoreRequestModel createStoreRequestModel);

    @Headers({"client: mobile"})
    @PATCH("stores/{id}")
    Observable<Response<BaseResponseModel>> updateStore(@Header("Authorization") String token, @Path("id") long storeId, @Body CreateStoreRequestModel createStoreRequestModel);

    // Dashboard calls
    @Headers({"client: mobile"})
    @POST("stores/{id}/products/")
    Observable<Response<BaseResponseModel>> createProduct(@Header("Authorization") String token, @Path("id") long storeId, @Body CreateProductRequestModel createProductRequestModel);

    @Headers({"client: mobile"})
    @PATCH("stores/{id}/products/{pid}/")
    Observable<Response<BaseResponseModel>> updateProduct(@Header("Authorization") String token, @Path("id") long storeId, @Path("pid") long productId, @Body CreateProductRequestModel createProductRequestModel);

    @Headers({"client: mobile"})
    @DELETE("stores/{id}/products/{pid}/")
    Observable<Response<BaseResponseModel>> deleteProduct(@Header("Authorization") String token, @Path("id") long storeId, @Path("pid") long productId);
}
