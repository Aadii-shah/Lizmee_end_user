package com.example.lizmee_end_user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TopSliderAdapter extends RecyclerView.Adapter<TopSliderAdapter.ViewHolder> {

    ArrayList<TopSliderModel> topSliderModels;
    Context context;


    public TopSliderAdapter(ArrayList<TopSliderModel> topSliderModels, Context context) {
        this.topSliderModels = topSliderModels;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_slider,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TopSliderModel topSliderModel= topSliderModels.get(position);
        Glide.with(context)
                .load(topSliderModel.getSliderImage())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return topSliderModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.sliderImageView);
        }
    }
}
