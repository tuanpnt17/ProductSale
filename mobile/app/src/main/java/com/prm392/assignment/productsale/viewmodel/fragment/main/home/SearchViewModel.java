package com.prm392.assignment.productsale.viewmodel.fragment.main.home;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.app.Application;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.prm392.assignment.productsale.data.repository.ProductsSaleRepository;
import com.prm392.assignment.productsale.model.products.ProductsSaleResponseModel;
import com.prm392.assignment.productsale.util.UserAccountManager;

import org.jetbrains.annotations.NotNull;

import retrofit2.Response;

public class SearchViewModel extends ViewModel {
    public static final ViewModelInitializer<SearchViewModel> initializer = new ViewModelInitializer<>(
            SearchViewModel.class,
            creationExtras -> {
                Application app = creationExtras.get(APPLICATION_KEY);
                assert app != null;
                return new SearchViewModel(app);
            }
    );
    private final ProductsSaleRepository productsRepository;
    private final String token;
    //    private LiveData<Response<ProductsSaleResponseModel>> demoProducts;
    private LiveData<Response<ProductsSaleResponseModel>> recommendedProducts;

    public SearchViewModel(@NotNull Application application) {
        super();

        productsRepository = new ProductsSaleRepository();

        token = UserAccountManager.getToken(application, UserAccountManager.TOKEN_TYPE_BEARER);
    }

//    public LiveData<Response<ProductsSaleResponseModel>> getDemoProducts() {
//        demoProducts = productsRepository.getDemoProducts(token);
//        return demoProducts;
//    }

    public LiveData<Response<ProductsSaleResponseModel>> getRecommendedProducts() {
        recommendedProducts = productsRepository.getProducts(token);
        return recommendedProducts;
    }

    public void removeObserverRecommendedProducts(LifecycleOwner lifecycleOwner) {
        recommendedProducts.removeObservers(lifecycleOwner);
    }

//    public LiveData<Response<BaseResponseModel>> addFavourite(long productId) {
//        return repository.addFavourite(token, productId);
//    }
//
//    public LiveData<Response<BaseResponseModel>> removeFavourite(long productId) {
//        return repository.removeFavourite(token, productId);
//    }
}
