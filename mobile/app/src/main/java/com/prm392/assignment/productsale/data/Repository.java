package com.prm392.assignment.productsale.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.prm392.assignment.productsale.data.remote.RetrofitClient;
import com.prm392.assignment.productsale.data.remote.RetrofitInterface;
import com.prm392.assignment.productsale.model.BarcodeMonsterResponseModel;
import com.prm392.assignment.productsale.model.BaseResponseModel;
import com.prm392.assignment.productsale.model.ChangePasswordModel;
import com.prm392.assignment.productsale.model.CreateProductRequestModel;
import com.prm392.assignment.productsale.model.CreateStoreRequestModel;
import com.prm392.assignment.productsale.model.CreateStoreResponseModel;
import com.prm392.assignment.productsale.model.ProductPageResponseModel;
import com.prm392.assignment.productsale.model.ProductRateModel;
import com.prm392.assignment.productsale.model.ProductsResponseModel;
import com.prm392.assignment.productsale.model.StorePageModel;
import com.prm392.assignment.productsale.model.UpcItemDbResponseModel;
import com.prm392.assignment.productsale.model.UserModel;
import com.prm392.assignment.productsale.model.UserResponseModel;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Repository {
    //HEADERS
    public static final String AUTH_TOKEN_HEADER = "Authorization";
    private final Retrofit mainClient;
    private final Retrofit upcItemDbClient;
    private final Retrofit barcodeMonsterClient;

    public Repository() {
        mainClient = RetrofitClient.getMainInstance();
        upcItemDbClient = RetrofitClient.getUpcItemDbInstance();
        barcodeMonsterClient = RetrofitClient.getBarcodeMonsterInstance();
    }

    //User Data Calls
    public LiveData<Response<UserResponseModel>> getUser(String token) {
        return LiveDataReactiveStreams.fromPublisher(
                mainClient.create(RetrofitInterface.class)
                        .getUser(token)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(exception -> {
                            exception.printStackTrace();

                            if (exception.getClass() == HttpException.class)
                                return Response.error(((HttpException) exception).code(), ResponseBody.create(null, ""));

                            return Response.error(BaseResponseModel.FAILED_REQUEST_FAILURE, ResponseBody.create(null, ""));
                        })
                        .toFlowable(BackpressureStrategy.LATEST)
        );
    }

    public LiveData<Response<UserResponseModel>> updateUser(String token, UserModel userModel) {
        return LiveDataReactiveStreams.fromPublisher(
                mainClient.create(RetrofitInterface.class)
                        .updateUser(token, userModel)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(exception -> {
                            exception.printStackTrace();

                            if (exception.getClass() == HttpException.class)
                                return Response.error(((HttpException) exception).code(), ResponseBody.create(null, ""));

                            return Response.error(BaseResponseModel.FAILED_REQUEST_FAILURE, ResponseBody.create(null, ""));
                        })
                        .toFlowable(BackpressureStrategy.LATEST)
        );
    }

    public LiveData<Response<BaseResponseModel>> changePassword(String token, ChangePasswordModel changePasswordModel) {
        return LiveDataReactiveStreams.fromPublisher(
                mainClient.create(RetrofitInterface.class)
                        .changePassword(token, changePasswordModel)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(exception -> {
                            exception.printStackTrace();

                            if (exception.getClass() == HttpException.class)
                                return Response.error(((HttpException) exception).code(), ResponseBody.create(null, ""));

                            return Response.error(BaseResponseModel.FAILED_REQUEST_FAILURE, ResponseBody.create(null, ""));
                        })
                        .toFlowable(BackpressureStrategy.LATEST)
        );
    }

    public LiveData<Response<ProductsResponseModel>> searchProducts(String token, String language, double userLat, double userLong, String keyword, String storeType, String sort, long minPrice, long maxPrice, String category, String brand, long cursorLastItemId, int limit) {
        return LiveDataReactiveStreams.fromPublisher(
                mainClient.create(RetrofitInterface.class)
                        .searchProducts(token, language, userLat, userLong, keyword, storeType, sort, minPrice, maxPrice, category, brand, cursorLastItemId, limit)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(exception -> {
                            exception.printStackTrace();

                            if (exception.getClass() == HttpException.class)
                                return Response.error(((HttpException) exception).code(), ResponseBody.create(null, ""));

                            return Response.error(BaseResponseModel.FAILED_REQUEST_FAILURE, ResponseBody.create(null, ""));
                        })
                        .toFlowable(BackpressureStrategy.LATEST)
        );
    }

    public LiveData<Response<UpcItemDbResponseModel>> barcodeLookupUpcItemDb(String barcode) {
        return LiveDataReactiveStreams.fromPublisher(
                upcItemDbClient.create(RetrofitInterface.class)
                        .barcodeLookupUpcItemDb(barcode)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(exception -> {
                            exception.printStackTrace();

                            if (exception.getClass() == HttpException.class)
                                return Response.error(((HttpException) exception).code(), ResponseBody.create(null, ""));

                            return Response.error(BaseResponseModel.FAILED_REQUEST_FAILURE, ResponseBody.create(null, ""));
                        })
                        .toFlowable(BackpressureStrategy.LATEST)
        );
    }

    public LiveData<Response<BarcodeMonsterResponseModel>> barcodeLookupBarcodeMonster(String barcode) {
        return LiveDataReactiveStreams.fromPublisher(
                barcodeMonsterClient.create(RetrofitInterface.class)
                        .barcodeLookupBarcodeMonster(barcode)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(exception -> {
                            exception.printStackTrace();

                            if (exception.getClass() == HttpException.class)
                                return Response.error(((HttpException) exception).code(), ResponseBody.create(null, ""));

                            return Response.error(BaseResponseModel.FAILED_REQUEST_FAILURE, ResponseBody.create(null, ""));
                        })
                        .toFlowable(BackpressureStrategy.LATEST)
        );
    }

    public LiveData<Response<ProductPageResponseModel>> getProduct(String token, long productId) {
        return LiveDataReactiveStreams.fromPublisher(
                mainClient.create(RetrofitInterface.class)
                        .getProduct(token, productId)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(exception -> {
                            exception.printStackTrace();

                            if (exception.getClass() == HttpException.class)
                                return Response.error(((HttpException) exception).code(), ResponseBody.create(null, ""));

                            return Response.error(BaseResponseModel.FAILED_REQUEST_FAILURE, ResponseBody.create(null, ""));
                        })
                        .toFlowable(BackpressureStrategy.LATEST)
        );
    }

    public LiveData<Response<BaseResponseModel>> addFavourite(String token, long productId) {
        return LiveDataReactiveStreams.fromPublisher(
                mainClient.create(RetrofitInterface.class)
                        .addFavourite(token, productId)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(exception -> {
                            exception.printStackTrace();

                            if (exception.getClass() == HttpException.class)
                                return Response.error(((HttpException) exception).code(), ResponseBody.create(null, ""));

                            return Response.error(BaseResponseModel.FAILED_REQUEST_FAILURE, ResponseBody.create(null, ""));
                        })
                        .toFlowable(BackpressureStrategy.LATEST)
        );
    }

    public LiveData<Response<BaseResponseModel>> removeFavourite(String token, long productId) {
        return LiveDataReactiveStreams.fromPublisher(
                mainClient.create(RetrofitInterface.class)
                        .removeFavourite(token, productId)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(exception -> {
                            exception.printStackTrace();

                            if (exception.getClass() == HttpException.class)
                                return Response.error(((HttpException) exception).code(), ResponseBody.create(null, ""));

                            return Response.error(BaseResponseModel.FAILED_REQUEST_FAILURE, ResponseBody.create(null, ""));
                        })
                        .toFlowable(BackpressureStrategy.LATEST)
        );
    }

    public LiveData<Response<BaseResponseModel>> rateProduct(String token, long productId, ProductRateModel productRateModel) {
        return LiveDataReactiveStreams.fromPublisher(
                mainClient.create(RetrofitInterface.class)
                        .rateProduct(token, productId, productRateModel)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(exception -> {
                            exception.printStackTrace();

                            if (exception.getClass() == HttpException.class)
                                return Response.error(((HttpException) exception).code(), ResponseBody.create(null, ""));

                            return Response.error(BaseResponseModel.FAILED_REQUEST_FAILURE, ResponseBody.create(null, ""));
                        })
                        .toFlowable(BackpressureStrategy.LATEST)
        );
    }

    public LiveData<Response<ProductsResponseModel>> getRecommendedProducts(String token) {
        return LiveDataReactiveStreams.fromPublisher(
                mainClient.create(RetrofitInterface.class)
                        .getRecommendedProducts(token)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(exception -> {
                            exception.printStackTrace();

                            if (exception.getClass() == HttpException.class)
                                return Response.error(((HttpException) exception).code(), ResponseBody.create(null, ""));

                            return Response.error(BaseResponseModel.FAILED_REQUEST_FAILURE, ResponseBody.create(null, ""));
                        })
                        .toFlowable(BackpressureStrategy.LATEST)
        );
    }

    public LiveData<Response<ProductsResponseModel>> getProductsViewsHistory(String token) {
        return LiveDataReactiveStreams.fromPublisher(
                mainClient.create(RetrofitInterface.class)
                        .getProductsViewsHistory(token)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(exception -> {
                            exception.printStackTrace();

                            if (exception.getClass() == HttpException.class)
                                return Response.error(((HttpException) exception).code(), ResponseBody.create(null, ""));

                            return Response.error(BaseResponseModel.FAILED_REQUEST_FAILURE, ResponseBody.create(null, ""));
                        })
                        .toFlowable(BackpressureStrategy.LATEST)
        );

    }

    public LiveData<Response<ProductsResponseModel>> getFavoriteProducts(String token) {
        return LiveDataReactiveStreams.fromPublisher(
                mainClient.create(RetrofitInterface.class)
                        .getFavoriteProducts(token)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(exception -> {
                            exception.printStackTrace();

                            if (exception.getClass() == HttpException.class)
                                return Response.error(((HttpException) exception).code(), ResponseBody.create(null, ""));

                            return Response.error(BaseResponseModel.FAILED_REQUEST_FAILURE, ResponseBody.create(null, ""));
                        })
                        .toFlowable(BackpressureStrategy.LATEST)
        );

    }

    public LiveData<Response<ProductsResponseModel>> getOnSaleProducts(String token) {
        return LiveDataReactiveStreams.fromPublisher(
                mainClient.create(RetrofitInterface.class)
                        .getOnSaleProducts(token)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(exception -> {
                            exception.printStackTrace();

                            if (exception.getClass() == HttpException.class)
                                return Response.error(((HttpException) exception).code(), ResponseBody.create(null, ""));

                            return Response.error(BaseResponseModel.FAILED_REQUEST_FAILURE, ResponseBody.create(null, ""));
                        })
                        .toFlowable(BackpressureStrategy.LATEST)
        );

    }

    //Store Calls
    public LiveData<Response<StorePageModel>> getStore(String token, long storeId, int page) {
        return LiveDataReactiveStreams.fromPublisher(
                mainClient.create(RetrofitInterface.class)
                        .getStore(token, storeId, page)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(exception -> {
                            exception.printStackTrace();

                            if (exception.getClass() == HttpException.class)
                                return Response.error(((HttpException) exception).code(), ResponseBody.create(null, ""));

                            return Response.error(BaseResponseModel.FAILED_REQUEST_FAILURE, ResponseBody.create(null, ""));
                        })
                        .toFlowable(BackpressureStrategy.LATEST)
        );

    }

    public LiveData<Response<CreateStoreResponseModel>> createStore(String token, CreateStoreRequestModel createStoreRequestModel) {
        return LiveDataReactiveStreams.fromPublisher(
                mainClient.create(RetrofitInterface.class)
                        .createStore(token, createStoreRequestModel)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(exception -> {
                            exception.printStackTrace();

                            if (exception.getClass() == HttpException.class)
                                return Response.error(((HttpException) exception).code(), ResponseBody.create(null, ""));

                            return Response.error(BaseResponseModel.FAILED_REQUEST_FAILURE, ResponseBody.create(null, ""));
                        })
                        .toFlowable(BackpressureStrategy.LATEST)
        );
    }

    public LiveData<Response<BaseResponseModel>> updateStore(String token, long storeId, CreateStoreRequestModel createStoreRequestModel) {
        return LiveDataReactiveStreams.fromPublisher(
                mainClient.create(RetrofitInterface.class)
                        .updateStore(token, storeId, createStoreRequestModel)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(exception -> {
                            exception.printStackTrace();

                            if (exception.getClass() == HttpException.class)
                                return Response.error(((HttpException) exception).code(), ResponseBody.create(null, ""));

                            return Response.error(BaseResponseModel.FAILED_REQUEST_FAILURE, ResponseBody.create(null, ""));
                        })
                        .toFlowable(BackpressureStrategy.LATEST)
        );
    }

    //Dashboard Calls
    public LiveData<Response<BaseResponseModel>> createProduct(String token, long storeId, CreateProductRequestModel createProductRequestModel) {
        return LiveDataReactiveStreams.fromPublisher(
                mainClient.create(RetrofitInterface.class)
                        .createProduct(token, storeId, createProductRequestModel)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(exception -> {
                            exception.printStackTrace();

                            if (exception.getClass() == HttpException.class)
                                return Response.error(((HttpException) exception).code(), ResponseBody.create(null, ""));

                            return Response.error(BaseResponseModel.FAILED_REQUEST_FAILURE, ResponseBody.create(null, ""));
                        })
                        .toFlowable(BackpressureStrategy.LATEST)
        );
    }

    public LiveData<Response<BaseResponseModel>> updateProduct(String token, long storeId, long productId, CreateProductRequestModel createProductRequestModel) {
        return LiveDataReactiveStreams.fromPublisher(
                mainClient.create(RetrofitInterface.class)
                        .updateProduct(token, storeId, productId, createProductRequestModel)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(exception -> {
                            exception.printStackTrace();

                            if (exception.getClass() == HttpException.class)
                                return Response.error(((HttpException) exception).code(), ResponseBody.create(null, ""));

                            return Response.error(BaseResponseModel.FAILED_REQUEST_FAILURE, ResponseBody.create(null, ""));
                        })
                        .toFlowable(BackpressureStrategy.LATEST)
        );
    }

    public LiveData<Response<BaseResponseModel>> deleteProduct(String token, long storeId, long productId) {
        return LiveDataReactiveStreams.fromPublisher(
                mainClient.create(RetrofitInterface.class)
                        .deleteProduct(token, storeId, productId)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(exception -> {
                            exception.printStackTrace();

                            if (exception.getClass() == HttpException.class)
                                return Response.error(((HttpException) exception).code(), ResponseBody.create(null, ""));

                            return Response.error(BaseResponseModel.FAILED_REQUEST_FAILURE, ResponseBody.create(null, ""));
                        })
                        .toFlowable(BackpressureStrategy.LATEST)
        );
    }
}
