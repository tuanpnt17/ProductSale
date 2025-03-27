package com.prm392.assignment.productsale.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;

import com.prm392.assignment.productsale.R;
import com.prm392.assignment.productsale.model.cart.CartItemModel;
import com.prm392.assignment.productsale.model.cart.CartModel;
import com.prm392.assignment.productsale.util.AppSettingsManager;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.DataViewHolder> {
    private ArrayList<CartItemModel> data;
    private Context context;

    public void addCartItem(ArrayList<CartItemModel> cartItems) {
        this.data.clear(); // Xóa dữ liệu cũ
        this.data.addAll(cartItems); // Thêm dữ liệu mới vào
        notifyDataSetChanged(); // Cập nhật giao diện adapter
    }

    public interface ItemInteractionListener {
        void onCartItemClicked(CartItemModel cartItem);
        void onProductAddedToFav(long productId, boolean favChecked);
    }

    private ItemInteractionListener itemInteractionListener;

    public CartListAdapter(Context context, ArrayList<CartItemModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        CartItemModel cartItem = data.get(position);

        holder.productName.setText(cartItem.getProductName());
        holder.productPrice.setText(String.format("%s %s", cartItem.getPrice(), context.getString(R.string.currency)));
        holder.productQuantity.setText(String.valueOf(cartItem.getQuantity()));

        // Load product image using Glide
        Glide.with(context)
                .load(cartItem.getProductImage())
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(250))
                .into(holder.productImage);


        holder.itemView.setOnClickListener(v -> {
            if (itemInteractionListener != null) {
                itemInteractionListener.onCartItemClicked(cartItem);
            }
        });

        holder.favCheckBox.setOnClickListener(v -> {
            boolean isFav = holder.favCheckBox.isChecked();
            if (itemInteractionListener != null) {
                itemInteractionListener.onProductAddedToFav(cartItem.getProductId(), isFav);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setItemInteractionListener(ItemInteractionListener listener) {
        this.itemInteractionListener = listener;
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productQuantity;
        ImageView productImage;
        CheckBox favCheckBox;

        public DataViewHolder(View view) {
            super(view);
            // Initialize views based on the updated layout XML
            productName = view.findViewById(R.id.product_list_item_Name);
            productPrice = view.findViewById(R.id.product_list_item_price);
            productQuantity = view.findViewById(R.id.product_list_item_quantity); // Add a quantity field if necessary
            productImage = view.findViewById(R.id.product_list_item_image);
            favCheckBox = view.findViewById(R.id.product_list_item_favourite); // Changed ID
        }
    }
}
