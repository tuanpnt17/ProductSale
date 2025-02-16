package com.prm392.assignment.productsale.products;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.prm392.assignment.productsale.MyApplication;
import com.prm392.assignment.productsale.R;
import com.prm392.assignment.productsale.data.repositories.ProductRepository;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.HiltAndroidApp;

@AndroidEntryPoint
public class ProductsActivity extends AppCompatActivity {

    private ProductsViewModel viewModel;
    private TextView txtProductName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_products);
        txtProductName = findViewById(R.id.txtProductName);
        txtProductName.setText("Product Name");
        viewModel = new ViewModelProvider(this).get(ProductsViewModel.class);
        viewModel.loadProducts();
        viewModel.getProducts().observe(this, products -> {
            txtProductName.setText(products.get(0).getProductName());
        });

    }
}