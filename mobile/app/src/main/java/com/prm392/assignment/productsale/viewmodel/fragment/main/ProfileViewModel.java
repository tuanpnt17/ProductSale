package com.prm392.assignment.productsale.viewmodel.fragment.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.prm392.assignment.productsale.data.Repository;
import com.prm392.assignment.productsale.model.UserModel;
import com.prm392.assignment.productsale.model.UserResponseModel;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {
    private Repository repository;

    public ProfileViewModel() {
        super();

        repository = new Repository();
    }

    public LiveData<Response<UserResponseModel>> updateUser(String token, UserModel userModel){
        return repository.updateUser(token, userModel);
    }
}
