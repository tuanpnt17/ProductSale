package com.prm392.assignment.productsale.view.fragment.main.home;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prm392.assignment.productsale.R;
import com.prm392.assignment.productsale.adapters.ProductsListAdapter;
import com.prm392.assignment.productsale.databinding.FragmentFavBinding;
import com.prm392.assignment.productsale.model.ProductModel;
import com.prm392.assignment.productsale.view.activity.MainActivity;
import com.prm392.assignment.productsale.viewmodel.fragment.main.home.FavViewModel;

public class FavFragment extends Fragment {
    private FragmentFavBinding vb;
    private NavController navController;
    private FavViewModel viewModel;

    private ProductsListAdapter adapter;

    public FavFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(vb==null) vb = FragmentFavBinding.inflate(inflater,container,false);
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

        new Handler().post(()->{
            navController = ((MainActivity)getActivity()).getAppNavController();
        });
        viewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(FavViewModel.initializer))
                .get(FavViewModel.class);

        adapter = new ProductsListAdapter(getContext(),vb.favRecyclerVeiw);
        vb.favRecyclerVeiw.setLayoutManager(new LinearLayoutManager(getContext()));
        vb.favRecyclerVeiw.setAdapter(adapter);

        adapter.setItemInteractionListener(new ProductsListAdapter.ItemInteractionListener() {
            @Override
            public void onProductClicked(ProductModel product) {
                Bundle bundle = new Bundle();
                bundle.putLong("productId",product.getId());
                navController.navigate(R.id.action_homeFragment_to_productPageFragment,bundle);
            }

            @Override
            public void onProductAddedToFav(long productId, boolean favChecked) {
                if(!favChecked){
//                    removeFavourite(productId);
                    adapter.removeProductById(productId);
                }
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT)
        {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                removeFavourite(adapter.getData().get(viewHolder.getAdapterPosition()).getId());
                adapter.removeProductByIndex(viewHolder.getAdapterPosition());
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    Paint paint = new Paint();
                    paint.setAntiAlias(true);

                    if(dX>0) {
                        Drawable icon = DrawableCompat.wrap(getContext().getDrawable(R.drawable.delete_icon)).getCurrent();
                        int leftMargin = 10;
                        float scale = 1.4f;

                        int left = viewHolder.itemView.getLeft() + leftMargin;
                        int right = (int) (left + icon.getIntrinsicWidth()*scale);

                        int vcenter = viewHolder.itemView.getTop() + viewHolder.itemView.getHeight()/2;
                        int top = (int) (vcenter - icon.getIntrinsicHeight()*scale/2);
                        int bottom = (int) (vcenter + icon.getIntrinsicHeight()*scale/2);

                        icon.setBounds(left, top, right, bottom);
                        icon.draw(c);
                    } else if (dX<0){
                        Drawable icon = DrawableCompat.wrap(getContext().getDrawable(R.drawable.delete_icon)).getCurrent();
                        int rightMargin = 10;
                        float scale = 1.4f;

                        int right = viewHolder.itemView.getRight() - rightMargin;
                        int left = (int) (right - icon.getIntrinsicWidth()*scale);

                        int vcenter = viewHolder.itemView.getTop() + viewHolder.itemView.getHeight()/2;
                        int top = (int) (vcenter - icon.getIntrinsicHeight()*scale/2);
                        int bottom = (int) (vcenter + icon.getIntrinsicHeight()*scale/2);

                        icon.setBounds(left, top, right, bottom);
                        icon.draw(c);
                    }
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }


        });
        itemTouchHelper.attachToRecyclerView(vb.favRecyclerVeiw);

        vb.favEmptyList.setVisibility(View.GONE);
        if(adapter.getItemCount()>0) adapter.clearProducts();
//        loadProducts();
    }

}