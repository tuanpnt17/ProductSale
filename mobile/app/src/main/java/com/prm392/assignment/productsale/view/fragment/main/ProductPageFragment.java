package com.prm392.assignment.productsale.view.fragment.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.prm392.assignment.productsale.R;
import com.prm392.assignment.productsale.adapters.ImagesSliderViewPagerAdapter;
import com.prm392.assignment.productsale.databinding.FragmentProductPageBinding;
import com.prm392.assignment.productsale.model.BaseResponseModel;
import com.prm392.assignment.productsale.model.ProductModel;
import com.prm392.assignment.productsale.model.ProductPageModel;
import com.prm392.assignment.productsale.model.products.ProductSaleModel;
import com.prm392.assignment.productsale.model.products.StoreLocation;
import com.prm392.assignment.productsale.util.AppSettingsManager;
import com.prm392.assignment.productsale.util.DialogsProvider;
import com.prm392.assignment.productsale.view.activity.MainActivity;
import com.prm392.assignment.productsale.viewmodel.fragment.main.ProductPageViewModel;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;


public class ProductPageFragment extends Fragment {
    private FragmentProductPageBinding vb;
    private ProductPageViewModel viewModel;
    private NavController navController;

    private ImagesSliderViewPagerAdapter imageSliderAdapter;
    private LineChartView lineChartView;
    private CheckBox[] userRatingStars;
    private int userRatingNewValue;

    private GoogleMap googleMap;

    public ProductPageFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this.getActivity(),
//                    new String[]{Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION}, 1);
//        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vb = FragmentProductPageBinding.inflate(inflater, container, false);
        return vb.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        vb = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setTitle(getString(R.string.Product));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(ProductPageViewModel.initializer))
                .get(ProductPageViewModel.class);
        if (getArguments() != null) viewModel.setProductId(getArguments().getLong("productId"));

        new Handler().post(() -> {
            navController = ((MainActivity) getActivity()).getAppNavController();
        });
//
//        imageSliderAdapter = new ImagesSliderViewPagerAdapter(getContext());
//        vb.productPageImagesSlider.setAdapter(imageSliderAdapter);

//        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(vb.productPageImagesSliderIndicator, vb.productPageImagesSlider, (tab, position) -> {
//        });
//        tabLayoutMediator.attach();

        // Map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.product_page_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(map -> {
                this.googleMap = map;

                googleMap.getUiSettings().setCompassEnabled(true);
                googleMap.getUiSettings().setMapToolbarEnabled(false);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.getUiSettings().setAllGesturesEnabled(false);
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            });
        }

//        vb.productPageSubmitRate.setVisibility(View.INVISIBLE);
//        userRatingStars = new CheckBox[]{vb.productPageRateStar1, vb.productPageRateStar2, vb.productPageRateStar3, vb.productPageRateStar4, vb.productPageRateStar5};
//        for (int i = 0; i < userRatingStars.length; i++) {
//            final int index = i;
//            userRatingStars[i].setOnClickListener((star) -> {
//                for (int j = 0; j <= index; j++) userRatingStars[j].setChecked(true);
//                for (int j = index + 1; j < userRatingStars.length; j++)
//                    userRatingStars[j].setChecked(false);
//                userRatingNewValue = index + 1;
//                showRatingSubmit(userRatingNewValue != viewModel.getProductPageModel().getUserRating());
//            });
//        }

//        vb.productPageFavourite.setOnCheckedChangeListener((button, checked) -> {
//            if (checked) vb.productPageFavouriteText.setText(R.string.Remove);
//            else vb.productPageFavouriteText.setText(R.string.Add);
//        });

//        vb.productPageFavourite.setOnClickListener(button -> {
//            setFavourite(vb.productPageFavourite.isChecked());
//        });

//        vb.productPageFavouriteText.setOnClickListener(button -> {
//            vb.productPageFavourite.performClick();
//        });

        vb.productPageBack.setOnClickListener(button -> {
            getActivity().getOnBackPressedDispatcher().onBackPressed();
        });

//        vb.productPageStore.setOnClickListener(image -> {
//            Bundle bundle = new Bundle();
//            bundle.putLong("storeId", viewModel.getProductPageModel().getStore().getStoreId());
//            navController.navigate(R.id.action_productPageFragment_to_storePageFragment, bundle);
//        });

        //Add to cart
        vb.productPageOpenSourcePageButton.setOnClickListener(button -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(viewModel.getProductPageModel().getMainInfo().getSourceUrl()));
            startActivity(intent);
        });

//        vb.productPageShareProductButton.setOnClickListener(button -> {
//            Intent intent = new Intent(Intent.ACTION_SEND);
//            intent.setType("text/plain");
//            intent.putExtra(Intent.EXTRA_TEXT, viewModel.getProductPageModel().getMainInfo().getShareableUrl());
//            startActivity(Intent.createChooser(intent, viewModel.getProductPageModel().getMainInfo().getName()));
//        });
//
//        vb.productPageNavigateButton.setOnClickListener(button -> {
//            Uri uri = Uri.parse("google.navigation:q=" + viewModel.getProductPageModel().getStore().getStoreLatitude() + "," + viewModel.getProductPageModel().getStore().getStoreLongitude());
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(uri);
//            startActivity(intent);
//        });
//
//        vb.productPageSubmitRate.setOnClickListener(button -> {
//            rateProduct(userRatingNewValue);
//            showRatingSubmit(false);
//        });

        loadProductSale();
    }

    void loadProductSale() {
        vb.productPageLoadingPage.setVisibility(View.VISIBLE);

        viewModel.getProductSale().observe(getViewLifecycleOwner(), response -> {

            switch (response.code()) {
                case BaseResponseModel.SUCCESSFUL_OPERATION:
                    if (response.body() != null) {
                        viewModel.setProductSaleModel(response.body().getProduct());
                        viewModel.setStoreLocation(response.body().getStoreLocation());
                        renderProductSaleData();
                        vb.productPageLoadingPage.setVisibility(View.GONE);
                        vb.getRoot().startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lay_on));
                    }
                    break;

                case BaseResponseModel.FAILED_NOT_FOUND:
                    DialogsProvider.get(getActivity()).messageDialog(getString(R.string.Product_Not_Found), getString(R.string.Product_Not_Found_in_Server));
                    break;

                case BaseResponseModel.FAILED_REQUEST_FAILURE:
                    Toast.makeText(getContext(), "Loading Failed", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(getContext(), "Server Error | Code: " + response.code(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void loadProductData() {
        vb.productPageLoadingPage.setVisibility(View.VISIBLE);

        viewModel.getProduct().observe(getViewLifecycleOwner(), response -> {

            switch (response.code()) {
                case BaseResponseModel.SUCCESSFUL_OPERATION:
                    if (response.body() != null) {
                        viewModel.setProductPageModel(response.body().getProduct());
//                        renderProductData();
                        vb.productPageLoadingPage.setVisibility(View.GONE);
                        vb.getRoot().startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lay_on));
                    }
                    break;

                case BaseResponseModel.FAILED_NOT_FOUND:
                    DialogsProvider.get(getActivity()).messageDialog(getString(R.string.Product_Not_Found), getString(R.string.Product_Not_Found_in_Server));
                    break;

                case BaseResponseModel.FAILED_REQUEST_FAILURE:
                    Toast.makeText(getContext(), "Loading Failed", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    Toast.makeText(getContext(), "Server Error | Code: " + response.code(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    boolean renderDataInLocalLanguage() {
        switch (AppSettingsManager.getLanguageKey(getContext())) {
            case AppSettingsManager.LANGUAGE_ENGLISH:
                return false;
            case AppSettingsManager.LANGUAGE_ARABIC:
                return true;
            default:
                String systemLanguage = Locale.getDefault().getLanguage();
                if (systemLanguage.equals(AppSettingsManager.LANGUAGE_ARABIC)) return true;
                else return false;
        }
    }

    void renderProductSaleData() {
        ProductSaleModel productSaleModel = viewModel.getProductSaleModel();
        StoreLocation storeLocation = viewModel.getStoreLocation();
        vb.productPageBrand.setText(productSaleModel.getCategoryName());
        Double productPrice = Double.parseDouble(String.format("%.2f", productSaleModel.getPrice()));
        vb.productPagePrice.setText(productPrice + "$");

        Glide.with(this)
                .load("https://plus.unsplash.com/premium_photo-1676973464513-7489d4ca4802?q=80&w=1963&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
                .transition(DrawableTransitionOptions.withCrossFade(100))
                .into(vb.productPageImage);

        vb.productPageBrand.setText(productSaleModel.getCategoryName());
        vb.productPageTitle.setText(productSaleModel.getProductName());
        vb.productPageDescription.setText(productSaleModel.getBriefDescription());
        vb.productSaleFullDescription.setText(productSaleModel.getFullDescription());
        vb.productSaleTechSpecsText.setText(productSaleModel.getTechnicalSpecifications());
        addProductOnMap(storeLocation.getLatitude(), storeLocation.getLongitude(), storeLocation.getAddress());

        String fullDescription = productSaleModel.getFullDescription();

        if (fullDescription.length() > 120) {
            String shortDescription = fullDescription.substring(0, 110) + "... ";

            SpannableString readMore = new SpannableString(getString(R.string.Read_More));
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {

                    vb.productSaleFullDescription.animate().alpha(0).setDuration(250).withEndAction(() -> {
                        vb.productSaleFullDescription.setText(fullDescription);
                        vb.productSaleFullDescription.animate().alpha(1f).setDuration(250).start();
                    }).start();

                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                }
            };

            readMore.setSpan(clickableSpan, 0, readMore.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            vb.productSaleFullDescription.setText(shortDescription);
            vb.productSaleFullDescription.append(readMore);
            vb.productSaleFullDescription.setMovementMethod(LinkMovementMethod.getInstance());

        } else vb.productSaleFullDescription.setText(fullDescription);

    }

//    void renderProductData() {
//        ProductPageModel productPageModel = viewModel.getProductPageModel();
//
//        if (renderDataInLocalLanguage())
//            vb.productPageTitle.setText(productPageModel.getMainInfo().getNameArabic());
//        else vb.productPageTitle.setText(productPageModel.getMainInfo().getName());
//
//        vb.productPageBrand.setText(productPageModel.getMainInfo().getBrand());
//        Double productPrice = Double.parseDouble(String.format("%.2f", productPageModel.getPrices().get(0).getPrice() - (productPageModel.getPrices().get(0).getPrice() * productPageModel.getMainInfo().getSalePercent() / 100)));
//        vb.productPagePrice.setText(productPrice + getString(R.string.currency));
//        vb.productPageSalePercent.setText(productPageModel.getMainInfo().getSalePercent() + getString(R.string.sale_percent));
//        vb.productPageRate.setText(productPageModel.getProductRating().getRating().substring(0, 3));
//        vb.productPageViews.setText(productPageModel.getViews().getCount() + "");
//        vb.productPageFavourite.setChecked(productPageModel.isFavorite());
//        renderUserRating(productPageModel.getUserRating());
//
//        Glide.with(this)
//                .load(productPageModel.getStore().getStoreLogo())
//                .transition(DrawableTransitionOptions.withCrossFade(100))
//                .into(vb.productPageStore);
//
//        ArrayList<String> productImagesLinks = new ArrayList<>();
//        for (ProductPageModel.ProductImage i : productPageModel.getImages())
//            productImagesLinks.add(i.getImageUrl().replace("http://", "https://"));
//        imageSliderAdapter.addImages(productImagesLinks);
//        if (productImagesLinks.size() == 1)
//            vb.productPageImagesSliderIndicator.setVisibility(View.INVISIBLE);
//
//        drawPriceTrackerChart(productPageModel.getPrices());
//
//        if (productPageModel.getStore().getStoreType().equals(ProductModel.ONLINE_STORE)) {
//            vb.productPageMapSection.setVisibility(View.GONE);
//            vb.productPageNavigateButton.setVisibility(View.GONE);
//            vb.productPageDescription.setVisibility(View.GONE);
//        } else {
//            ProductPageModel.Store store = productPageModel.getStore();
//            addProductOnMap(store.getStoreLatitude(), store.getStoreLongitude(), store.getStoreName());
//            vb.productPageOpenSourcePageButton.setVisibility(View.GONE);
//
//            String fullDescription;
//            if (renderDataInLocalLanguage())
//                fullDescription = productPageModel.getMainInfo().getDescriptionArabic();
//            else fullDescription = productPageModel.getMainInfo().getDescription();
//
//            if (fullDescription.length() > 120) {
//                String shortDescription = fullDescription.substring(0, 110) + "... ";
//
//                SpannableString readMore = new SpannableString(getString(R.string.Read_More));
//                ClickableSpan clickableSpan = new ClickableSpan() {
//                    @Override
//                    public void onClick(@NonNull View widget) {
//
//                        vb.productPageDescription.animate().alpha(0).setDuration(250).withEndAction(() -> {
//                            vb.productPageDescription.setText(fullDescription);
//                            vb.productPageDescription.animate().alpha(1f).setDuration(250).start();
//                        }).start();
//
//                    }
//
//                    @Override
//                    public void updateDrawState(@NonNull TextPaint ds) {
//                        super.updateDrawState(ds);
//                        ds.setUnderlineText(false);
//                    }
//                };
//
//                readMore.setSpan(clickableSpan, 0, readMore.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                vb.productPageDescription.setText(shortDescription);
//                vb.productPageDescription.append(readMore);
//                vb.productPageDescription.setMovementMethod(LinkMovementMethod.getInstance());
//
//            } else vb.productPageDescription.setText(fullDescription);
//
//        }
//
//        if (productPageModel.getMainInfo().getSalePercent() == 0)
//            vb.productPageSalePercent.setVisibility(View.INVISIBLE);
//
//    }


    private void addProductOnMap(double lat, double lng, String storeName) {
        try {
            LatLng productLocation = new LatLng(lat, lng);
            googleMap.addMarker(new MarkerOptions().position(productLocation)
                    .title(storeName)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(productLocation)
                    .zoom(googleMap.getCameraPosition().zoom < 8 ? 8 : googleMap.getCameraPosition().zoom)
                    .build();

            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 2000, null);

        } catch (Exception e) {
            Toast.makeText(getContext(), "Map Marker Error", Toast.LENGTH_SHORT).show();
        }
    }

    public String dateTimeConvert(String dateTime) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss";
        String outputPattern = "dd/MM/yyyy";

        try {
            SimpleDateFormat input = new SimpleDateFormat(inputPattern);
            input.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date parsed = input.parse(dateTime);

            SimpleDateFormat destFormat = new SimpleDateFormat(outputPattern);
            destFormat.setTimeZone(TimeZone.getDefault());

            return destFormat.format(parsed);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "-";
    }

}