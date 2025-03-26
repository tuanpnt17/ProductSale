package com.prm392.assignment.productsale.view.fragment.main.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;

import com.prm392.assignment.productsale.R;
import com.prm392.assignment.productsale.adapters.ProductSaleCardAdapter;
import com.prm392.assignment.productsale.databinding.FragmentSearchBinding;
import com.prm392.assignment.productsale.model.BaseResponseModel;
import com.prm392.assignment.productsale.model.products.ProductSaleModel;
import com.prm392.assignment.productsale.util.DialogsProvider;
import com.prm392.assignment.productsale.view.activity.MainActivity;
import com.prm392.assignment.productsale.viewmodel.fragment.main.home.SearchViewModel;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding vb;
    private NavController navController;
    private SearchViewModel viewModel;
    private ProductSaleCardAdapter demoAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (vb == null) vb = FragmentSearchBinding.inflate(inflater, container, false);
        return vb.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        vb = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (viewModel != null) return;

        new Handler().post(() -> {
            navController = ((MainActivity) getActivity()).getAppNavController();
        });

        viewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(SearchViewModel.initializer)).get(SearchViewModel.class);

        vb.searchSearchbar.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) searchRequest(textView.getText().toString());
            return false;
        });

        demoAdapter = new ProductSaleCardAdapter(getContext(), vb.searchProductsRecyclerView);
        vb.searchProductsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        vb.searchProductsRecyclerView.setAdapter(demoAdapter);
        demoAdapter.setHideFavButton(true);

        demoAdapter.setItemInteractionListener(new ProductSaleCardAdapter.ItemInteractionListener() {
            @Override
            public void onProductClicked(long productId, String storeType) {
                Bundle bundle = new Bundle();
                bundle.putLong("productId", productId);
                navController.navigate(R.id.action_homeFragment_to_productPageFragment, bundle);
            }

            @Override
            public void onProductAddedToFav(long productId, boolean favChecked) {
//                setFavourite(productId, favChecked);
            }
        });

        loadRecommendedProducts();

    }

    void searchRequest(String keyword) {
        if (keyword.isEmpty()) return;

        Bundle bundle = new Bundle();
        bundle.putString("keyword", keyword);
        navController.navigate(R.id.action_homeFragment_to_searchResultsFragment, bundle);
    }

    void loadRecommendedProducts() {
        vb.searchLoadingRecommended.setVisibility(View.VISIBLE);
        viewModel.getRecommendedProducts().observe(getViewLifecycleOwner(), response -> {
//            var x = response.body();
            switch (response.code()) {
                case BaseResponseModel.SUCCESSFUL_OPERATION:
                    vb.searchLoadingRecommended.setVisibility(View.GONE);

                    if (response.body().getProducts() == null || response.body().getProducts().isEmpty())
                        return;

                    ArrayList<ProductSaleModel> products = response.body().getProducts();


                    demoAdapter.addProducts(products);


                    viewModel.removeObserverRecommendedProducts(getViewLifecycleOwner());
                    break;

                case BaseResponseModel.FAILED_REQUEST_FAILURE:
                    Toast.makeText(getContext(), "Loading Recommendations Failed", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    DialogsProvider.get(getActivity()).messageDialog(getString(R.string.Server_Error), getString(R.string.Code) + response.code());
            }
        });
    }


//    void setFavourite(long productId, boolean favourite) {
//        if (favourite) {
//            viewModel.addFavourite(productId).observe(getViewLifecycleOwner(), response -> {
//                if (response.code() != BaseResponseModel.SUCCESSFUL_CREATION)
//                    Toast.makeText(getContext(), "Error" + response.code(), Toast.LENGTH_SHORT).show();
//            });
//        } else {
//            viewModel.removeFavourite(productId).observe(getViewLifecycleOwner(), response -> {
//                if (response.code() != BaseResponseModel.SUCCESSFUL_DELETED)
//                    Toast.makeText(getContext(), "Error" + response.code(), Toast.LENGTH_SHORT).show();
//            });
//        }
//
//    }

}