package com.prm392.assignment.productsale.viewmodel.fragment.main;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashSet;

import com.prm392.assignment.productsale.data.Repository;
import com.prm392.assignment.productsale.model.BaseResponseModel;
import com.prm392.assignment.productsale.model.ProductsResponseModel;
import com.prm392.assignment.productsale.model.SortAndFilterModel;
import com.prm392.assignment.productsale.util.UserAccountManager;

import org.jetbrains.annotations.NotNull;

import retrofit2.Response;

public class SearchResultsViewModel extends ViewModel {
    private Repository repository;
    private LiveData<Response<ProductsResponseModel>> initialLoadedProducts;
    private LiveData<Response<ProductsResponseModel>> onlinePaginatedProducts;
    private LiveData<Response<ProductsResponseModel>> localPaginatedProducts;

    private String token;
    private String language;
    private String keyword;
    private LatLng userLocation;
    private SortAndFilterModel sortAndFilterModel;
    private HashSet<String> categories, brands;
    private long cursorLastOnlineItem, cursorLastLocalItem;
    private int productsCountPerPage = 10; //Min 10

    public SearchResultsViewModel(@NotNull Application application) {
        super();

        repository = new Repository();

        token = UserAccountManager.getToken(application,UserAccountManager.TOKEN_TYPE_BEARER);
        language = "en";
        userLocation = new LatLng(0,0);
        sortAndFilterModel = new SortAndFilterModel();
        categories = new HashSet<>();
        brands = new HashSet<>();
    }

    public static final ViewModelInitializer<SearchResultsViewModel> initializer = new ViewModelInitializer<>(
            SearchResultsViewModel.class,
            creationExtras -> {
                Application app = (Application) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                return new SearchResultsViewModel(app);
            }
    );

    public LiveData<Response<ProductsResponseModel>> loadResults(){
        initialLoadedProducts = repository.searchProducts(token,
                language,
                userLocation.latitude,
                userLocation.longitude,
                keyword,
                "all",
                sortAndFilterModel.getSortBy(),
                sortAndFilterModel.getMinPrice(),
                sortAndFilterModel.getMaxPrice(),
                sortAndFilterModel.getCategory(),
                sortAndFilterModel.getBrand(),
                0,
                productsCountPerPage);

        return initialLoadedProducts;
    }

    public void removeObserverInitialLoadedProducts(LifecycleOwner lifecycleOwner){
        initialLoadedProducts.removeObservers(lifecycleOwner);
    }

    public LiveData<Response<ProductsResponseModel>> loadMoreOnlineResults(){
        onlinePaginatedProducts = repository.searchProducts(token,
                language,
                userLocation.latitude,
                userLocation.longitude,
                keyword,
                "online",
                sortAndFilterModel.getSortBy(),
                sortAndFilterModel.getMinPrice(),
                sortAndFilterModel.getMaxPrice(),
                sortAndFilterModel.getCategory(),
                sortAndFilterModel.getBrand(),
                cursorLastOnlineItem,
                productsCountPerPage);

        return onlinePaginatedProducts;
    }

    public void removeObserverOnlineLoadedProducts(LifecycleOwner lifecycleOwner){
        onlinePaginatedProducts.removeObservers(lifecycleOwner);
    }

    public LiveData<Response<ProductsResponseModel>> loadMoreLocalResults(){
        localPaginatedProducts = repository.searchProducts(token,
                language,
                userLocation.latitude,
                userLocation.longitude,
                keyword,
                "offline",
                sortAndFilterModel.getSortBy(),
                sortAndFilterModel.getMinPrice(),
                sortAndFilterModel.getMaxPrice(),
                sortAndFilterModel.getCategory(),
                sortAndFilterModel.getBrand(),
                cursorLastLocalItem,
                productsCountPerPage);

        return localPaginatedProducts;
    }

    public void removeObserverLocalLoadedProducts(LifecycleOwner lifecycleOwner){
        localPaginatedProducts.removeObservers(lifecycleOwner);
    }

    public LiveData<Response<BaseResponseModel>> addFavourite(long productId){
        return repository.addFavourite(token,productId);
    }

    public LiveData<Response<BaseResponseModel>> removeFavourite(long productId){
        return repository.removeFavourite(token,productId);
    }

    public int getProductsCountPerPage() {
        return productsCountPerPage;
    }

    public void setProductsCountPerPage(int productsCountPerPage) {
        this.productsCountPerPage = productsCountPerPage;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public LatLng getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(LatLng userLocation) {
        this.userLocation = userLocation;
    }

    public SortAndFilterModel getSortAndFilterModel() {
        return sortAndFilterModel;
    }

    public void setSortAndFilterModel(SortAndFilterModel sortAndFilterModel) {
        this.sortAndFilterModel = sortAndFilterModel;
    }

    public long getCursorLastOnlineItem() {
        return cursorLastOnlineItem;
    }

    public void setCursorLastOnlineItem(long cursorLastOnlineItem) {
        this.cursorLastOnlineItem = cursorLastOnlineItem;
    }

    public long getCursorLastLocalItem() {
        return cursorLastLocalItem;
    }

    public void setCursorLastLocalItem(long cursorLastLocalItem) {
        this.cursorLastLocalItem = cursorLastLocalItem;
    }

    public HashSet<String> getCategories() {
        return categories;
    }

    public void clearCategories() {
        categories.clear();
    }

    public void addCategory(String category) {
        categories.add(category);
    }

    public HashSet<String> getBrands() {
        return brands;
    }

    public void clearBrands() {
        brands.clear();
    }

    public void addBrand(String brand) {
        brands.add(brand);
    }
}
