package com.prm392.assignment.productsale.view.fragment.main.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import com.prm392.assignment.productsale.R;
import com.prm392.assignment.productsale.adapters.CartListAdapter;
import com.prm392.assignment.productsale.databinding.FragmentCartBinding;
import com.prm392.assignment.productsale.model.BaseResponseModel;
import com.prm392.assignment.productsale.model.cart.CartItemModel;
import com.prm392.assignment.productsale.util.DialogsProvider;
import com.prm392.assignment.productsale.view.activity.MainActivity;
import com.prm392.assignment.productsale.viewmodel.fragment.main.home.OnSaleViewModel;

public class CartFragment extends Fragment {
    private FragmentCartBinding vb;
    private NavController navController;
    private OnSaleViewModel viewModel;
    private CartListAdapter adapter;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (vb == null) vb = FragmentCartBinding.inflate(inflater, container, false);
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

        new Handler().post(() -> {
            navController = ((MainActivity) getActivity()).getAppNavController();
        });
        viewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(OnSaleViewModel.initializer)).get(OnSaleViewModel.class);

        adapter = new CartListAdapter(getContext(), vb.onSaleRecyclerVeiw, getViewLifecycleOwner(), viewModel);
        vb.onSaleRecyclerVeiw.setLayoutManager(new LinearLayoutManager(getContext()));
        vb.onSaleRecyclerVeiw.setAdapter(adapter);

        vb.checkout.setOnClickListener(v -> {
            navController.navigate(R.id.action_homeFragment_to_checkoutPageFragment);
        });

        vb.resetCartButton.setOnClickListener(v -> {
            clearCart();  // Gọi phương thức resetCart() khi nút được nhấn
        });

        loadCartItems();
    }

    private void clearCart() {
        int userId = viewModel.getUserModel().getId();  // Sử dụng userId thực tế

        // Gọi phương thức clearCart trong ViewModel
        viewModel.clearCart(userId).observe(getViewLifecycleOwner(), response -> {
            switch (response.code()) {
                case BaseResponseModel.SUCCESSFUL_OPERATION:
                    // Nếu xóa giỏ hàng thành công
                    adapter.clearCartItems();  // Xóa tất cả các sản phẩm trong adapter
                    Toast.makeText(getContext(), "Giỏ hàng đã được làm sạch", Toast.LENGTH_SHORT).show();
                    loadCartItems();
                    break;

                case BaseResponseModel.FAILED_REQUEST_FAILURE:
                    // Nếu xóa giỏ hàng thất bại
                    Toast.makeText(getContext(), "Không thể xóa giỏ hàng, vui lòng kiểm tra lại kết nối", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    // Xử lý lỗi khác
                    Toast.makeText(getContext(), "Lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    void loadCartItems() {
        vb.onSaleLoading.setVisibility(View.VISIBLE);
        int userId = viewModel.getUserModel().getId();  // Sử dụng userId thực tế
        // Lấy giỏ hàng từ ViewModel
        viewModel.getCart(userId).observe(getViewLifecycleOwner(), response -> {
            switch (response.code()) {
                case BaseResponseModel.SUCCESSFUL_OPERATION:
                    vb.onSaleLoading.setVisibility(View.GONE);
                    // Kiểm tra nếu giỏ hàng trống
                    if (response.body() == null) {
                        return;
                    }
                    if (response.body().getCartItems().stream().count() == 0) {
                        vb.checkout.setVisibility(View.GONE);
                    }

                    // Cập nhật giỏ hàng vào adapter
                    ArrayList<CartItemModel> cartItems = response.body().getCartItems(); // Đảm bảo rằng response trả về có danh sách cartItems
                    adapter.addCartItems(cartItems); // Trực tiếp dùng cartItems
                    break;

                case BaseResponseModel.FAILED_REQUEST_FAILURE:
                    DialogsProvider.get(getActivity()).messageDialog(getString(R.string.Loading_Failed), getString(R.string.Please_Check_your_connection));
                    break;

                default:
                    DialogsProvider.get(getActivity()).messageDialog(getString(R.string.Server_Error), getString(R.string.Code) + response.code());
            }
        });
    }


//    void loadProducts(){
//        vb.onSaleLoading.setVisibility(View.VISIBLE);
//
//        viewModel.getOnSaleProducts().observe(getViewLifecycleOwner(),  response ->{
//
//            switch (response.code()){
//                case BaseResponseModel.SUCCESSFUL_OPERATION:
//                    vb.onSaleLoading.setVisibility(View.GONE);
//
//                    if(response.body().getProducts() == null || response.body().getProducts().isEmpty()){
//                        DialogsProvider.get(getActivity()).messageDialog(getString(R.string.There_are_no_products_on_sale),getString(R.string.Check_this_page_later));
//                        return;
//                    }
//
//                    ArrayList<ProductModel> products = response.body().getProducts();
//
//                    adapter.addProducts(products);
//
//                    viewModel.removeObserverOfProducts(getViewLifecycleOwner());
//                    break;
//
//                case BaseResponseModel.FAILED_REQUEST_FAILURE:
//                    DialogsProvider.get(getActivity()).messageDialog(getString(R.string.Loading_Failed), getString(R.string.Please_Check_your_connection));
//                    break;
//
//                default:
//                    DialogsProvider.get(getActivity()).messageDialog(getString(R.string.Server_Error),getString(R.string.Code)+ response.code());
//            }
//        });
//
//    }

//    void setFavourite(long productId, boolean favourite){
//        if(favourite){
//            viewModel.addFavourite(productId).observe(getViewLifecycleOwner(), response ->{
//                if (response.code() != BaseResponseModel.SUCCESSFUL_CREATION)
//                    Toast.makeText(getContext(), "Error" + response.code(), Toast.LENGTH_SHORT).show();
//            });
//        }
//        else {
//            viewModel.removeFavourite(productId).observe(getViewLifecycleOwner(), response ->{
//                if (response.code() != BaseResponseModel.SUCCESSFUL_DELETED)
//                    Toast.makeText(getContext(), "Error" + response.code(), Toast.LENGTH_SHORT).show();
//            });
//        }
//
//    }
}