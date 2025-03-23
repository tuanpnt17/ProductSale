package com.prm392.assignment.productsale.viewmodel.fragment.accountSign;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.prm392.assignment.productsale.data.Repository;
import com.prm392.assignment.productsale.model.EmailVerificationModel;
import com.prm392.assignment.productsale.model.BaseResponseModel;
import retrofit2.Response;

public class ForgotPasswordViewModel extends ViewModel {
    private Repository repository;

    public ForgotPasswordViewModel() {
        super();
        repository = new Repository();
    }

    public LiveData<Response<BaseResponseModel>> sendEmailVerification(String email){
        EmailVerificationModel emailVerificationModel = new EmailVerificationModel();
        emailVerificationModel.setEmail(email);
        return repository.sendEmailVerification(emailVerificationModel);
    }

}
