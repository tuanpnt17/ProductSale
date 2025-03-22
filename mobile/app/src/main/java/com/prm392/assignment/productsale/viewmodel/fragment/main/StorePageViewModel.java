package com.prm392.assignment.productsale.viewmodel.fragment.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.prm392.assignment.productsale.data.Repository;
import com.prm392.assignment.productsale.model.BaseResponseModel;
import com.prm392.assignment.productsale.model.StorePageModel;
import com.prm392.assignment.productsale.util.UserAccountManager;

import org.jetbrains.annotations.NotNull;

import retrofit2.Response;

public class StorePageViewModel extends ViewModel {
    private Repository repository;
    private LiveData<Response<StorePageModel>> storeData;

    private long storeId;
    private int page = 1;
    private String token;
    private StorePageModel storePageModel;

    public StorePageViewModel(@NotNull Application application) {
        super();

        repository = new Repository();

        token = UserAccountManager.getToken(application,UserAccountManager.TOKEN_TYPE_BEARER);
    }

    public LiveData<Response<StorePageModel>> getStore(){
        storeData = repository.getStore(token,storeId,1);
        return storeData;
    }

    public LiveData<Response<StorePageModel>> getNextPage(){
        page++;
        storeData = repository.getStore(token,storeId,page);
        return storeData;
    }

    public void removeObserverStoreData(LifecycleOwner lifecycleOwner){
        storeData.removeObservers(lifecycleOwner);
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getStoreId() {
        return storeId;
    }

    public StorePageModel getStorePageModel() {
        return storePageModel;
    }

    public void setStorePageModel(StorePageModel storePageModel) {
        this.storePageModel = storePageModel;
    }

    public LiveData<Response<BaseResponseModel>> addFavourite(long productId){
        return repository.addFavourite(token,productId);
    }

    public LiveData<Response<BaseResponseModel>> removeFavourite(long productId){
        return repository.removeFavourite(token,productId);
    }
}
