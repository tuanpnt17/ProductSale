package com.prm392.assignment.productsale.viewmodel.fragment.main.home;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.prm392.assignment.productsale.data.Repository;
import com.prm392.assignment.productsale.data.repository.CartRepository;
import com.prm392.assignment.productsale.model.BaseResponseModel;
import com.prm392.assignment.productsale.model.ProductsResponseModel;
import com.prm392.assignment.productsale.model.cart.CartModel;
import com.prm392.assignment.productsale.model.cart.CartTotalResponse;
import com.prm392.assignment.productsale.util.UserAccountManager;
import retrofit2.Response;

public class OnSaleViewModel extends ViewModel {
    private Repository repository;
    private CartRepository cartRepository;
    private LiveData<Response<CartModel>> cartLiveData;
    private LiveData<Response<ProductsResponseModel>> products;
    private MutableLiveData<Double> totalPrice;
    private String token;

    public OnSaleViewModel(@NonNull Application application) {
        super();
        cartRepository = new CartRepository();
        repository = new Repository();
        token = UserAccountManager.getToken(application,UserAccountManager.TOKEN_TYPE_BEARER);
        totalPrice = new MutableLiveData<>();
    }

    public LiveData<Double> getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice.setValue(totalPrice);
    }
    public LiveData<Response<CartTotalResponse>> getCartTotal(int userId) {
        return cartRepository.getCartTotal(token, userId);
    }

    public void updateTotalPrice(double totalPrice) {
        this.totalPrice.setValue(totalPrice);
    }

    public static final ViewModelInitializer<OnSaleViewModel> initializer = new ViewModelInitializer<>(
            OnSaleViewModel.class,
            creationExtras -> {
                Application app = (Application) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                return new OnSaleViewModel(app);
            }
    );
    // Lấy giỏ hàng của người dùng
    public LiveData<Response<CartModel>> getCart(int userId) {
        cartLiveData = cartRepository.getCart(token, userId);
        return cartLiveData;
    }

    public LiveData<Response<BaseResponseModel>> removeCartItem(int userId, int productId) {
        return cartRepository.removeItemFromCart(token, userId, productId);
    }

    public LiveData<Response<BaseResponseModel>> updateCartItem(int userId, int productId, int quantity) {
        return cartRepository.updateCartItemQuantity(token,userId,productId,quantity);
    }

    public LiveData<Response<BaseResponseModel>> clearCart(int userId) {
        return cartRepository.clearCart(token, userId);
    }

//    public LiveData<Response<ProductsResponseModel>> getOnSaleProducts(){
//        products = repository.getOnSaleProducts(token);
//        return products;
//    }
//
//    public void removeObserverOfProducts(LifecycleOwner lifecycleOwner){
//        products.removeObservers(lifecycleOwner);
//    }
//
//    public LiveData<Response<BaseResponseModel>> addFavourite(long productId){
//        return repository.addFavourite(token,productId);
//    }
//
//    public LiveData<Response<BaseResponseModel>> removeFavourite(long productId){
//        return repository.removeFavourite(token,productId);
//    }
}
