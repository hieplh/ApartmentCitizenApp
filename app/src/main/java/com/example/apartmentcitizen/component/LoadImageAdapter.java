package com.example.apartmentcitizen.component;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.apartmentcitizen.network.RetrofitInstance;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LoadImageAdapter extends RecyclerView.Adapter<LoadImageAdapter.ViewHolder> {

    Context context;
    ArrayList<String> mListPathImage;

    public LoadImageAdapter(Context context, ArrayList<String> mListPathImage) {
        this.context = context;
        this.mListPathImage = mListPathImage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(context);
        return new ViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(RetrofitInstance.BASE_URL + RetrofitInstance.VERSION_API
                        + RetrofitInstance.GET_POSTIMAGE_IMAGE + mListPathImage.get(position))
//                .override(500, 500)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mListPathImage.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView;
        }
    }
}
