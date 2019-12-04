package com.example.apartmentcitizen.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.apartmentcitizen.network.RetrofitInstance;
import com.google.zxing.common.BitMatrix;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageUploadAdapter extends RecyclerView.Adapter<ImageUploadAdapter.ViewHolder> {

    Context context;
    ArrayList<String> mListPathImage;

    public ImageUploadAdapter(Context context, ArrayList<String> mListPathImage) {
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
                .override(500, 500)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView;
        }
    }
}
