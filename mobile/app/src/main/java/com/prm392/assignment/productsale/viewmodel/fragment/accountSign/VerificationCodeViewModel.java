package com.prm392.assignment.productsale.viewmodel.fragment.accountSign;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.prm392.assignment.productsale.data.Repository;
import com.prm392.assignment.productsale.model.BaseResponseModel;
import com.prm392.assignment.productsale.model.EmailVerificationModel;
import retrofit2.Response;

public class VerificationCodeViewModel extends ViewModel {
    private Repository repository;

    public VerificationCodeViewModel() {
        super();
        repository = new Repository();
    }

    public LiveData<Response<BaseResponseModel>> verifyToken(String token){
        return repository.verifyToken(token);
    }

    public LiveData<Response<BaseResponseModel>> resendEmailVerification(String email){
        EmailVerificationModel emailVerificationModel = new EmailVerificationModel();
        emailVerificationModel.setEmail(email);
        return repository.sendEmailVerification(emailVerificationModel);
    }
}
