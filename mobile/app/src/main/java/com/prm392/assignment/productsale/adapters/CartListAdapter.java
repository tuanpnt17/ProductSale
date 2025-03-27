package com.prm392.assignment.productsale.adapters;

import android.annotation.SuppressLint;
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
import com.prm392.assignment.productsale.model.ProductModel;
import com.prm392.assignment.productsale.model.cart.CartItemModel;
import com.prm392.assignment.productsale.model.cart.CartModel;
import com.prm392.assignment.productsale.util.AppSettingsManager;

import lombok.Setter;

public class CartListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<CartItemModel> data;
    private RecyclerView recyclerView;
    private Context context;

    private final CartItemModel loadingCardObject = new CartItemModel();

    private final int TYPE_DATA_VIEW_HOLDER = 0;
    private final int TYPE_LOADING_VIEW_HOLDER = 1;
    @Setter
    private ProductsListAdapter.LastItemReachedListener lastItemReachedListener;
    @Setter
    private ProductsListAdapter.ItemInteractionListener itemInteractionListener;
    @Setter
    private boolean hideFavButton = false;
    @Setter
    private boolean showDate = false;

    public interface LastItemReachedListener {
        void onLastItemReached();
    }

    public interface ItemInteractionListener {
        void onProductClicked(ProductModel product);

        void onProductAddedToFav(long productId, boolean favChecked);
    }

    public CartListAdapter(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.data = new ArrayList<>();
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

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(View view) {
            super(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position) == loadingCardObject) return TYPE_LOADING_VIEW_HOLDER;
        else return TYPE_DATA_VIEW_HOLDER;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOADING_VIEW_HOLDER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_loading_layout, parent, false);
            return new ProductsListAdapter.LoadingViewHolder(view);
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
        return new CartListAdapter.DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_DATA_VIEW_HOLDER) {

            DataViewHolder holder  = (DataViewHolder) viewHolder;

            holder.productName.setText(data.get(position).getProduct().getProductName());
            holder.productPrice.setText(String.format("%s %s", data.get(position).getPrice(), context.getString(R.string.currency)));
            holder.productQuantity.setText(String.valueOf(data.get(position).getQuantity()));


//            if (renderDataInLocalLanguage())
//                holder.name.setText(Data.get(position).getNameArabic());
//            else holder.name.setText(Data.get(position).getName());
//            holder.brand.setText(Data.get(position).getBrand());
//            holder.price.setText(Data.get(position).getPrice() + context.getString(R.string.currency));
//            holder.rate.setText(String.valueOf(Data.get(position).getRate()));
//            holder.favourite.setChecked(Data.get(position).isFavorite());
//            holder.date.setText(dateTimeConvert(Data.get(position).getDate()));
//            holder.sale.setText(Data.get(position).getSalePercent() + context.getString(R.string.sale_percent));
//
//            if (hideFavButton) holder.favourite.setVisibility(View.GONE);
//            if (Data.get(position).getSalePercent() == 0) holder.sale.setVisibility(View.GONE);
//            if (Data.get(position).getBrand() == null) holder.brand.setVisibility(View.INVISIBLE);
//

            //Store
            //if(isDarkModeEnabled()) holder.store.setImageTintList(ColorStateList.valueOf(Color.WHITE));

//            if (Data.get(position).getStoreName() == null) holder.store.setVisibility(View.GONE);
//            else {
//                Glide.with(context)
//                        .load(Data.get(position).getStoreLogo())
//                        .transition(DrawableTransitionOptions.withCrossFade(250))
//                        .into(holder.store);
//            }

            //Image
//            Glide.with(context)
//                    .load(Data.get(position).getImage())
//                    .centerCrop()
//                    .transition(DrawableTransitionOptions.withCrossFade(250))
//                    .into(holder.image);

//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (itemInteractionListener != null)
//                        itemInteractionListener.onProductClicked(Data.get(holder.getAdapterPosition()));
//                }
//            });



        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder.getBindingAdapterPosition() == data.size() - 1 && lastItemReachedListener != null && !isLoading())
            lastItemReachedListener.onLastItemReached();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addCartItem(CartItemModel cartItem) {
        recyclerView.post(() -> {
            data.add(cartItem);
            notifyItemInserted(getItemCount());
        });
    }

    public  void removeCartItem(CartItemModel cartItem) {
        recyclerView.post(() -> {
            int index = data.indexOf(cartItem);
            data.remove(index);
            notifyItemRemoved(index);
        });
    }

    public void removeCartItemByIndex(int index) {
        recyclerView.post(() -> {
            data.remove(index);
            notifyItemRemoved(index);
        });
    }

    public void removeCartItemById(long cartId) {
        recyclerView.post(() -> {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getCartId() == cartId) {
                    data.remove(i);
                    notifyItemRemoved(i);
                    break;
                }
            }
        });
    }

    public void addCartItems(ArrayList<CartItemModel> cartItems) {
        recyclerView.post(() -> {
            data.addAll(cartItems);
            notifyItemRangeInserted(getItemCount(), cartItems.size());
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clearCartItems() {
        recyclerView.post(() -> {
            data.clear();
            notifyDataSetChanged();
        });
    }

    public boolean isLoading() {
        return data.contains(loadingCardObject);
    }

    public void setLoading(boolean loading) {
        recyclerView.post(() -> {
            if (isLoading() == loading) return;
            if (loading) {
                data.add(loadingCardObject);
                notifyItemInserted(getItemCount());
            } else {
                int index = data.indexOf(loadingCardObject);
                data.remove(index);
                notifyItemRemoved(index);
            }
        });
    }


//    @Override
//    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
//        CartItemModel cartItem = data.get(position);
//
//        holder.productName.setText(cartItem.getProductName());
//        holder.productPrice.setText(String.format("%s %s", cartItem.getPrice(), context.getString(R.string.currency)));
//        holder.productQuantity.setText(String.valueOf(cartItem.getQuantity()));
//
//        // Load product image using Glide
//        Glide.with(context)
//                .load(cartItem.getProductImage())
//                .centerCrop()
//                .transition(DrawableTransitionOptions.withCrossFade(250))
//                .into(holder.productImage);
//
//
//        holder.itemView.setOnClickListener(v -> {
//            if (itemInteractionListener != null) {
//                itemInteractionListener.onCartItemClicked(cartItem);
//            }
//        });
//
//        holder.favCheckBox.setOnClickListener(v -> {
//            boolean isFav = holder.favCheckBox.isChecked();
//            if (itemInteractionListener != null) {
//                itemInteractionListener.onProductAddedToFav(cartItem.getProductId(), isFav);
//            }
//        });
//    }



}
