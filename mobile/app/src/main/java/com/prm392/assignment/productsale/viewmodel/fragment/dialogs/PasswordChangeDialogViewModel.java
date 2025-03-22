package com.prm392.assignment.productsale.viewmodel.fragment.dialogs;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.prm392.assignment.productsale.data.Repository;
import com.prm392.assignment.productsale.model.BaseResponseModel;
import com.prm392.assignment.productsale.model.ChangePasswordModel;
import retrofit2.Response;

public class PasswordChangeDialogViewModel extends ViewModel {
    private Repository repository;

    public PasswordChangeDialogViewModel() {
        super();

        repository = new Repository();
    }

    public LiveData<Response<BaseResponseModel>> changePassword(String token, String oldPassword, String newPassword, String newPasswordConfirm){
        ChangePasswordModel changePasswordModel = new ChangePasswordModel();
        changePasswordModel.setOldPassword(oldPassword);
        changePasswordModel.setNewPassword(newPassword);
        changePasswordModel.setNewPasswordConfirm(newPasswordConfirm);

        return repository.changePassword(token, changePasswordModel);
    }
}
