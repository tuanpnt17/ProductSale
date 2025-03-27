package com.prm392.assignment.productsale.viewmodel.fragment.main;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.prm392.assignment.productsale.data.repository.CartRepository;
import com.prm392.assignment.productsale.data.repository.ProductsSaleRepository;
import com.prm392.assignment.productsale.model.UserModel;
import com.prm392.assignment.productsale.model.cart.CartModel;
import com.prm392.assignment.productsale.util.UserAccountManager;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.Setter;
import retrofit2.Response;

public class CheckoutPageViewModel extends ViewModel {
    private final ProductsSaleRepository productsSaleRepository;
    private final CartRepository cartRepository;
    private LiveData<Response<CartModel>> cartLiveData;

    private long productId;
    private final String token;

    @Getter
    @Setter
    private CartModel cartModel;

    @Getter
    private final UserModel userModel;

    @Getter
    @Setter
    private String paymentMethod;

    private final Application app;

    public CheckoutPageViewModel(@NotNull Application application) {
        super();
        app = application;
        cartRepository = new CartRepository();
        productsSaleRepository = new ProductsSaleRepository();

        token = UserAccountManager.getToken(application, UserAccountManager.TOKEN_TYPE_BEARER);
        userModel = UserAccountManager.getUser(application);
    }

    public static final ViewModelInitializer<CheckoutPageViewModel> initializer = new ViewModelInitializer<>(
            CheckoutPageViewModel.class,
            creationExtras -> {
                Application app = creationExtras.get(APPLICATION_KEY);
                assert app != null;
                return new CheckoutPageViewModel(app);
            }
    );

    public LiveData<Response<CartModel>> getCart(int userId) {
        cartLiveData = cartRepository.getCart(token, userId);
        return cartLiveData;
    }

    public void buyNow() {
        if (cartModel == null) {
        }

    }
}
