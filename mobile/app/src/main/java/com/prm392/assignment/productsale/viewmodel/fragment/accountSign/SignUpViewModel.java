package com.prm392.assignment.productsale.viewmodel.fragment.accountSign;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.prm392.assignment.productsale.data.Repository;
import com.prm392.assignment.productsale.model.SignUpModel;
import com.prm392.assignment.productsale.model.UserResponseModel;
import retrofit2.Response;

public class SignUpViewModel extends ViewModel {
    private Repository repository;

    public SignUpViewModel() {
        super();

        repository = new Repository();
    }

    public LiveData<Response<UserResponseModel>> signUp(String name , String email, String password, String passwordConfirm, String image){
        SignUpModel signUpModel = new SignUpModel();
        signUpModel.setFullName(name);
        signUpModel.setEmail(email);
        signUpModel.setPassword(password);
        signUpModel.setPasswordConfirm(passwordConfirm);
        signUpModel.setProfileImage(image);

        return repository.signUp(signUpModel);
    }

}
