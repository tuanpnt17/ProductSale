package com.prm392.assignment.productsale.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.prm392.assignment.productsale.data.remote.RetrofitClient;
import com.prm392.assignment.productsale.data.service.ProductService;
import com.prm392.assignment.productsale.model.BaseResponseModel;
import com.prm392.assignment.productsale.model.products.ProductModel;
import com.prm392.assignment.productsale.model.products.ProductPageResponseModel;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductsRepository {

    private Retrofit mainClient;

    // Headers
    private static final String AUTHORIZATION = "Authorization";

    public ProductsRepository() {
        mainClient = RetrofitClient.getMainInstance();
    }

    public LiveData<Response<ProductPageResponseModel>> getProduct(String token, long productId) {
        return LiveDataReactiveStreams.fromPublisher(
                mainClient.create(ProductService.class).getProductById(productId)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(exception -> {
                            MediaType mediaType = MediaType.parse("application/json");
                            ResponseBody responseBody = ResponseBody.create(mediaType, "");

                            if (exception instanceof HttpException) {
                                return Response.error(((HttpException) exception).code(), responseBody);
                            }

                            return Response.error(
                                    BaseResponseModel.FAILED_REQUEST_FAILURE, responseBody);
                        })
                        .toFlowable(BackpressureStrategy.LATEST)
        );

    }


}
