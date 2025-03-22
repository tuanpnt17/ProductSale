package com.prm392.assignment.productsale.viewmodel.fragment.main;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.prm392.assignment.productsale.data.Repository;
import com.prm392.assignment.productsale.model.BaseResponseModel;
import com.prm392.assignment.productsale.model.CreateStoreRequestModel;
import com.prm392.assignment.productsale.model.CreateStoreResponseModel;
import com.prm392.assignment.productsale.util.UserAccountManager;
import com.prm392.assignment.productsale.viewmodel.fragment.main.home.SearchViewModel;

import org.jetbrains.annotations.NotNull;

import retrofit2.Response;

public class CreateStoreViewModel extends ViewModel {
    private Repository repository;
    private String token;
    private LiveData<Response<CreateStoreResponseModel>> createStoreObserver;
    private LiveData<Response<BaseResponseModel>> updateStoreObserver;

    public CreateStoreViewModel(@NotNull Application application) {
        super();

        repository = new Repository();
        token = UserAccountManager.getToken(application,UserAccountManager.TOKEN_TYPE_BEARER);
    }

    public static final ViewModelInitializer<CreateStoreViewModel> initializer = new ViewModelInitializer<>(
            CreateStoreViewModel.class,
            creationExtras -> {
                Application app = (Application) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                return new CreateStoreViewModel(app);
            }
    );
    public LiveData<Response<CreateStoreResponseModel>> createStore(String logoBase64, String name , String category, String address, Double latitude, Double longitude, String description, String phone, boolean hasWhatsapp, String websiteLink, String facebookUsername, String InstagramUsername){
        CreateStoreRequestModel createStoreRequestModel = new CreateStoreRequestModel();
        createStoreRequestModel.setLogoBase64(logoBase64);
        createStoreRequestModel.setName(name);
        createStoreRequestModel.setCategory(category);
        createStoreRequestModel.setAddress(address);
        createStoreRequestModel.setLatitude(latitude);
        createStoreRequestModel.setLongitude(longitude);
        createStoreRequestModel.setDescription(description);
        if(phone.length()!=0) createStoreRequestModel.setPhone(phone);
        if(hasWhatsapp) createStoreRequestModel.setWhatsappPhoneNumber(phone);
        if(websiteLink.length()!=0) createStoreRequestModel.setWebsiteLink(websiteLink);
        if(facebookUsername.length()!=0) createStoreRequestModel.setFacebookLink("https://www.facebook.com/"+facebookUsername+"/");
        if(InstagramUsername.length()!=0) createStoreRequestModel.setInstagramLink("https://www.instagram.com/"+InstagramUsername+"/");

        createStoreObserver = repository.createStore(token,createStoreRequestModel);
        return createStoreObserver;
    }

    public void removeObserverCreateStore(LifecycleOwner lifecycleOwner){
        createStoreObserver.removeObservers(lifecycleOwner);
    }

    public LiveData<Response<BaseResponseModel>> updateStore(long storeId,String logoBase64, String name , String category, String address, Double latitude, Double longitude, String description, String phone, boolean hasWhatsapp, String websiteLink, String facebookUsername, String InstagramUsername){
        CreateStoreRequestModel createStoreRequestModel = new CreateStoreRequestModel();
        createStoreRequestModel.setLogoBase64(logoBase64);
        createStoreRequestModel.setName(name);
        createStoreRequestModel.setCategory(category);
        createStoreRequestModel.setAddress(address);
        createStoreRequestModel.setLatitude(latitude);
        createStoreRequestModel.setLongitude(longitude);
        createStoreRequestModel.setDescription(description);
        if(phone.length()!=0) createStoreRequestModel.setPhone(phone);
        if(hasWhatsapp) createStoreRequestModel.setWhatsappPhoneNumber(phone);
        if(websiteLink.length()!=0) createStoreRequestModel.setWebsiteLink(websiteLink);
        if(facebookUsername.length()!=0) createStoreRequestModel.setFacebookLink("https://www.facebook.com/"+facebookUsername+"/");
        if(InstagramUsername.length()!=0) createStoreRequestModel.setInstagramLink("https://www.instagram.com/"+InstagramUsername+"/");

        updateStoreObserver = repository.updateStore(token,storeId,createStoreRequestModel);
        return updateStoreObserver;
    }

    public void removeObserverUpdateStore(LifecycleOwner lifecycleOwner){
        updateStoreObserver.removeObservers(lifecycleOwner);
    }

}
