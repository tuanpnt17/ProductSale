package com.prm392.assignment.productsale.viewmodel.fragment.main;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.prm392.assignment.productsale.data.Repository;
import com.prm392.assignment.productsale.data.repository.CartRepository;
import com.prm392.assignment.productsale.data.repository.ProductsSaleRepository;
import com.prm392.assignment.productsale.model.BaseResponseModel;
import com.prm392.assignment.productsale.model.UserModel;
import com.prm392.assignment.productsale.model.cart.AddProductCartModel;
import com.prm392.assignment.productsale.model.cart.CartModel;
import com.prm392.assignment.productsale.model.products.ProductSaleModel;
import com.prm392.assignment.productsale.model.products.ProductSalePageResponseModel;
import com.prm392.assignment.productsale.model.products.StoreLocation;
import com.prm392.assignment.productsale.util.UserAccountManager;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.Setter;
import retrofit2.Response;

public class CheckoutPageViewModel extends ViewModel {
    private Repository repository;
    private ProductsSaleRepository productsSaleRepository;
    private CartRepository cartRepository;
    private LiveData<Response<CartModel>> cartLiveData;

    private long productId;
    private String token;

    @Getter
    @Setter
    private CartModel cartModel;

    @Getter
    private UserModel userModel;



    public CheckoutPageViewModel(@NotNull Application application) {
        super();

        repository = new Repository();
        cartRepository = new CartRepository();
        productsSaleRepository = new ProductsSaleRepository();

        token = UserAccountManager.getToken(application, UserAccountManager.TOKEN_TYPE_BEARER);
        userModel = UserAccountManager.getUser(application);
    }

    public static final ViewModelInitializer<CheckoutPageViewModel> initializer = new ViewModelInitializer<>(
            CheckoutPageViewModel.class,
            creationExtras -> {
                Application app = (Application) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                return new CheckoutPageViewModel(app);
            }
    );

    public LiveData<Response<CartModel>> getCart(int userId) {
        cartLiveData = cartRepository.getCart(token, userId);
        return cartLiveData;
    }

}
