package com.example.lizmee_end_user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    ArrayList<ProductListModel> productListModels;
    Context context;


    public ProductListAdapter(ArrayList<ProductListModel> productListModels, Context context) {
        this.productListModels = productListModels;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_products_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ProductListModel productListModel = productListModels.get(position);
        holder.productName.setText(productListModel.getProductName());
        holder.productsCode.setText(productListModel.getProductCode());
        holder.productsPrice.setText(productListModel.getProductPrice());

        Glide.with(context)
                .load(productListModel.getProductImage())
                .into(holder.productImage);

    }

    @Override
    public int getItemCount() {
        return productListModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView productsCode, productName, productsPrice;
        public ImageView productImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productsCode = itemView.findViewById(R.id.productsCode);
            productName = itemView.findViewById(R.id.productName);
            productsPrice = itemView.findViewById(R.id.productsPrice);


        }
    }
}
