package com.prm392.assignment.productsale.viewmodel.fragment.main.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.prm392.assignment.productsale.data.Repository;
import com.prm392.assignment.productsale.model.BaseResponseModel;
import com.prm392.assignment.productsale.model.ProductsResponseModel;
import com.prm392.assignment.productsale.util.UserAccountManager;
import retrofit2.Response;

public class OnSaleViewModel extends ViewModel {
    private Repository repository;
    private LiveData<Response<ProductsResponseModel>> products;
    private String token;

    public OnSaleViewModel(@NonNull Application application) {
        super();

        repository = new Repository();
        token = UserAccountManager.getToken(application,UserAccountManager.TOKEN_TYPE_BEARER);
    }

    public LiveData<Response<ProductsResponseModel>> getOnSaleProducts(){
        products = repository.getOnSaleProducts(token);
        return products;
    }

    public void removeObserverOfProducts(LifecycleOwner lifecycleOwner){
        products.removeObservers(lifecycleOwner);
    }

    public LiveData<Response<BaseResponseModel>> addFavourite(long productId){
        return repository.addFavourite(token,productId);
    }

    public LiveData<Response<BaseResponseModel>> removeFavourite(long productId){
        return repository.removeFavourite(token,productId);
    }
}
