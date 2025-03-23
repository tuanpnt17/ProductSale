package com.prm392.assignment.productsale.viewmodel.fragment.main.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.prm392.assignment.productsale.data.Repository;

public class HomeViewModel extends ViewModel {
    private Repository repository;

    public HomeViewModel() {
        super();

        repository = new Repository();
    }

}
