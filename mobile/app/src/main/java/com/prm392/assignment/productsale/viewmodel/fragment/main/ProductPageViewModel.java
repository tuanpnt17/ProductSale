package com.prm392.assignment.productsale.viewmodel.fragment.main;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.prm392.assignment.productsale.data.Repository;
import com.prm392.assignment.productsale.data.repository.ProductsSaleRepository;
import com.prm392.assignment.productsale.model.BaseResponseModel;
import com.prm392.assignment.productsale.model.ProductPageModel;
import com.prm392.assignment.productsale.model.ProductPageResponseModel;
import com.prm392.assignment.productsale.model.ProductRateModel;
import com.prm392.assignment.productsale.model.products.ProductSaleModel;
import com.prm392.assignment.productsale.model.products.ProductSalePageResponseModel;
import com.prm392.assignment.productsale.model.products.StoreLocation;
import com.prm392.assignment.productsale.util.UserAccountManager;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.Setter;
import retrofit2.Response;

public class ProductPageViewModel extends ViewModel {
    private Repository repository;
    private ProductsSaleRepository productsSaleRepository;

    private long productId;
    private String token;
    private ProductPageModel productPageModel;


    @Getter
    @Setter
    private ProductSaleModel productSaleModel;

    @Getter
    @Setter
    private StoreLocation storeLocation;

    public ProductPageViewModel(@NotNull Application application) {
        super();

        repository = new Repository();
        productsSaleRepository = new ProductsSaleRepository();

        token = UserAccountManager.getToken(application,UserAccountManager.TOKEN_TYPE_BEARER);
    }

    public static final ViewModelInitializer<ProductPageViewModel> initializer = new ViewModelInitializer<>(
            ProductPageViewModel.class,
            creationExtras -> {
                Application app = (Application) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                return new ProductPageViewModel(app);
            }
    );

    public LiveData<Response<ProductSalePageResponseModel>> getProductSale(){
        return productsSaleRepository.getDemoProduct(token,productId);
    }



    public LiveData<Response<ProductPageResponseModel>> getProduct(){
        return repository.getProduct(token,productId);
    }

    public LiveData<Response<BaseResponseModel>> addFavourite(){
        return repository.addFavourite(token,productId);
    }

    public LiveData<Response<BaseResponseModel>> removeFavourite(){
        return repository.removeFavourite(token,productId);
    }

    public LiveData<Response<BaseResponseModel>> rateProduct(int rating){
        ProductRateModel productRateModel = new ProductRateModel();
        productRateModel.setRating(rating);
        return repository.rateProduct(token,productId,productRateModel);
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public ProductPageModel getProductPageModel() {
        return productPageModel;
    }

    public void setProductPageModel(ProductPageModel productPageModel) {
        this.productPageModel = productPageModel;
    }
}
