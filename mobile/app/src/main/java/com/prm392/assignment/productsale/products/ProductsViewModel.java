package com.prm392.assignment.productsale.products;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.prm392.assignment.productsale.data.models.Product;
import com.prm392.assignment.productsale.data.repositories.ProductRepository;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ProductsViewModel extends ViewModel {
    private MutableLiveData<List<Product>> products  = new MutableLiveData<>();
    private ProductRepository repository;

    @Inject
    public ProductsViewModel(ProductRepository productRepository) {
        repository = productRepository;
    }

    public LiveData<List<Product>> getProducts() {
        return products;
    }

    public void loadProducts() {
        repository.getAllProducts(new ProductRepository.ProductsDataCallback() {
            @Override
            public void onSuccess(List<Product> productList) {
                products.postValue(productList);
            }

            @Override
            public void onFailure(Throwable throwable) {
                // Xử lý lỗi (ví dụ: post giá trị rỗng hoặc thông báo lỗi)
            }
        });
    }
}
