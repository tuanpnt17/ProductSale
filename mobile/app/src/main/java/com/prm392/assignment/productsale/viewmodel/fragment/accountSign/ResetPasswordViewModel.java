package com.prm392.assignment.productsale.viewmodel.fragment.accountSign;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.prm392.assignment.productsale.data.Repository;
import com.prm392.assignment.productsale.model.BaseResponseModel;
import com.prm392.assignment.productsale.model.ResetPasswordModel;
import retrofit2.Response;

public class ResetPasswordViewModel extends ViewModel {
    private Repository repository;

    public ResetPasswordViewModel() {
        super();
        repository = new Repository();
    }

    public LiveData<Response<BaseResponseModel>> resetPassword(String pin,String newPassword, String newPasswordConfirm){
        ResetPasswordModel resetPasswordModel = new ResetPasswordModel();
        resetPasswordModel.setPassword(newPassword);
        resetPasswordModel.setPasswordConfirm(newPasswordConfirm);

        return repository.resetPassword(pin,resetPasswordModel);
    }
}
