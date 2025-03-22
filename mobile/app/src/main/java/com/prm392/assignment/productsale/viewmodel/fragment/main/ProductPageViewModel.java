package com.prm392.assignment.productsale.viewmodel.fragment.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.prm392.assignment.productsale.data.Repository;
import com.prm392.assignment.productsale.model.BaseResponseModel;
import com.prm392.assignment.productsale.model.ProductPageModel;
import com.prm392.assignment.productsale.model.ProductPageResponseModel;
import com.prm392.assignment.productsale.model.ProductRateModel;
import com.prm392.assignment.productsale.util.UserAccountManager;

import org.jetbrains.annotations.NotNull;

import retrofit2.Response;

public class ProductPageViewModel extends ViewModel {
    private Repository repository;

    private long productId;
    private String token;
    private ProductPageModel productPageModel;

    public ProductPageViewModel(@NotNull Application application) {
        super();

        repository = new Repository();

        token = UserAccountManager.getToken(application,UserAccountManager.TOKEN_TYPE_BEARER);
    }

    public LiveData<Response<ProductPageResponseModel>> getProduct(){
        return repository.getProduct(token,productId);
    }

    public LiveData<Response<BaseResponseModel>> addFavourite(){
        return repository.addFavourite(token,productId);
    }

    public LiveData<Response<BaseResponseModel>> removeFavourite(){
        return repository.removeFavourite(token,productId);
    }

    public LiveData<Response<BaseResponseModel>> rateProduct(int rating){
        ProductRateModel productRateModel = new ProductRateModel();
        productRateModel.setRating(rating);
        return repository.rateProduct(token,productId,productRateModel);
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public ProductPageModel getProductPageModel() {
        return productPageModel;
    }

    public void setProductPageModel(ProductPageModel productPageModel) {
        this.productPageModel = productPageModel;
    }
}
