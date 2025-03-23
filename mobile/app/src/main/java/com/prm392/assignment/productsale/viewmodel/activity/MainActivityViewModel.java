package com.prm392.assignment.productsale.viewmodel.activity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.prm392.assignment.productsale.data.Repository;
import com.prm392.assignment.productsale.model.UserResponseModel;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel {
    private Repository repository;

    public MainActivityViewModel() {
        super();

        repository = new Repository();

    }

    public LiveData<Response<UserResponseModel>> getUser(String token){
        return repository.getUser(token);
    }
}
