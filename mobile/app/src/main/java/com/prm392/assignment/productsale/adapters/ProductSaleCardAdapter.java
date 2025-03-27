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
import com.prm392.assignment.productsale.R;
import com.prm392.assignment.productsale.model.products.ProductSaleModel;

import java.util.ArrayList;

import lombok.Setter;

public class ProductSaleCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //    public static int TYPE_DATA_VIEW_HOLDER = 0;
//    public static int TYPE_LOADING_VIEW_HOLDER = 1;
//    public static int TYPE_NO_RESULT_VIEW_HOLDER = 2;
//    private final ProductSaleModel loadingCardObject = new ProductSaleModel();
//    private final ProductSaleModel noResultCardObject = new ProductSaleModel();
    private final ArrayList<ProductSaleModel> data;
    private final RecyclerView recyclerView;
    private final Context context;
    //    private final boolean noResultsFound = false;
    @Setter
    private boolean hideFavButton = false;

    //    @Setter
//    private ProductSaleCardAdapter.LastItemReachedListener lastItemReachedListener;
    @Setter
    private ProductSaleCardAdapter.ItemInteractionListener itemInteractionListener;

    public ProductSaleCardAdapter(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.data = new ArrayList<>();
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (data.get(position) == loadingCardObject) return TYPE_LOADING_VIEW_HOLDER;
//        else if (data.get(position) == noResultCardObject) return TYPE_NO_RESULT_VIEW_HOLDER;
//        else return TYPE_DATA_VIEW_HOLDER;
//    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if (viewType == TYPE_LOADING_VIEW_HOLDER) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_loading_layout, parent, false);
//            return new ProductSaleCardAdapter.LoadingViewHolder(view);
//        } else if (viewType == TYPE_NO_RESULT_VIEW_HOLDER) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_no_results_layout, parent, false);
//            return new ProductSaleCardAdapter.NoResultViewHolder(view);
//        }

        //Default ViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_sale_card_layout, parent, false);
        return new ProductSaleCardAdapter.DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
//        if (getItemViewType(position) == TYPE_DATA_VIEW_HOLDER) {

        ProductSaleCardAdapter.DataViewHolder holder = (ProductSaleCardAdapter.DataViewHolder) viewHolder;


        holder.name.setText(data.get(position).getProductName());
        holder.brand.setText(data.get(position).getCategoryName());
        holder.price.setText(data.get(position).getCurrencyPrice());
        holder.favourite.setChecked(false);

        if (hideFavButton) holder.favourite.setVisibility(View.GONE);

        //Image
        Glide.with(context)
                .load(data.get(position).getImageUrl())
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(250))
                .into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemInteractionListener != null)
                    itemInteractionListener.onProductClicked(data.get(holder.getBindingAdapterPosition()).getProductId(), "");
            }
        });

        holder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //data.get(holder.getAbsoluteAdapterPosition()).setFavorite(holder.favourite.isChecked());
                if (itemInteractionListener != null)
                    itemInteractionListener.onProductAddedToFav(data.get(holder.getBindingAdapterPosition()).getProductId(), holder.favourite.isChecked());
            }
        });

//        }
    }

//    @Override
//    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
//        super.onViewAttachedToWindow(holder);
//

    /// /        if (noResultsFound) return;
//        if (holder.getBindingAdapterPosition() == data.size() - 1 && lastItemReachedListener != null && !isLoading())
//            lastItemReachedListener.onLastItemReached();
//    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addProducts(ArrayList<ProductSaleModel> products) {
//        if (noResultsFound) return;

        recyclerView.post(() -> {
            int startPosition = data.size();
            data.addAll(products);
            notifyItemRangeInserted(startPosition, products.size());
        });
    }

//    public boolean isLoading() {
//        return data.contains(loadingCardObject);
//    }
//
//    public void showNoResultsFound() {
//        if (data.contains(noResultCardObject)) return;
//
//        data.add(noResultCardObject);
//        notifyItemInserted(0);

    @SuppressLint("NotifyDataSetChanged")
    public void clearProducts() {
        data.clear();
        notifyDataSetChanged();
    }

//    public void addProduct(ProductSaleModel product) {
//        if (noResultsFound) return;
//
//        recyclerView.post(() -> {
//            data.add(product);
//            notifyItemInserted(getItemCount());
//        });
//    }

    //NoPost for nested recyclerviews adapters
//    public void addProductNoPost(ProductSaleModel product) {
//        if (noResultsFound) return;
//
//        data.add(product);
//        notifyItemInserted(getItemCount());
//    }

    /// /        noResultsFound = true;
//    }

//    public interface LastItemReachedListener {
//        void onLastItemReached();
//    }

    public interface ItemInteractionListener {
        void onProductClicked(long productId, String storeType);

        void onProductAddedToFav(long productId, boolean favChecked);
    }

    //NoPost for nested recyclerviews adapters
//    public void addProductsNoPost(ArrayList<ProductSaleModel> products) {
//        if (noResultsFound) return;
//
//        data.addAll(products);
//        notifyItemRangeInserted(getItemCount(), products.size());
//    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(View view) {
            super(view);
        }
    }

    public static class NoResultViewHolder extends RecyclerView.ViewHolder {
        public NoResultViewHolder(View view) {
            super(view);
        }
    }

//    public void setLoading(boolean loading) {
//        recyclerView.post(() -> {
//            if (isLoading() == loading) return;
//
//            if (loading) {
//                data.add(loadingCardObject);
//                notifyItemInserted(getItemCount());
//            } else {
//                data.remove(loadingCardObject);
//                notifyItemChanged(getItemCount());
//            }
//        });
//
    //    }

    //NoPost for nested recyclerviews adapters
//    public void setLoadingNoPost(boolean loading) {
//        if (isLoading() == loading) return;
//
//        if (loading) {
//            data.add(loadingCardObject);
//            notifyItemInserted(getItemCount());
//        } else {
//            data.remove(loadingCardObject);
//            notifyItemChanged(getItemCount());
//        }
//    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView brand, name, price, rate, sale;
        ImageView image, store;
        CheckBox favourite;
        ImageView rateIcon;

        public DataViewHolder(View view) {
            super(view);
            brand = view.findViewById(R.id.product_sale_card_brand);
            name = view.findViewById(R.id.product_sale_card_Name);
            price = view.findViewById(R.id.product_sale_card_price);
            image = view.findViewById(R.id.product_sale_card_image);
            favourite = view.findViewById(R.id.product_sale_card_favourite);
        }
    }

//    public boolean isDarkModeEnabled() {
//        int currentMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//        return currentMode == Configuration.UI_MODE_NIGHT_YES;
//    }

//    boolean renderDataInLocalLanguage() {
//        switch (AppSettingsManager.getLanguageKey(context)) {
//            case AppSettingsManager.LANGUAGE_ENGLISH:
//                return false;
//            case AppSettingsManager.LANGUAGE_ARABIC:
//                return true;
//            default:
//                String systemLanguage = Locale.getDefault().getLanguage();
//                if (systemLanguage.equals(AppSettingsManager.LANGUAGE_ARABIC)) return true;
//                else return false;
//        }
//    }
}
