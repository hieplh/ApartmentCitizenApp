package com.example.apartmentcitizen.component;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.home.dashboard.DashboardObject;
import com.example.apartmentcitizen.home.dashboard.newsfeed.NewsfeedActivity;

import java.util.List;

public class CardDashboardAdapter extends RecyclerView.Adapter<CardDashboardAdapter.ViewHolder>{
    Context context;
    List<DashboardObject> listButton;


    public CardDashboardAdapter(Context context, List<DashboardObject> listButton) {
        this.context = context;
        this.listButton = listButton;
    }

    @NonNull
    @Override
    public CardDashboardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_button2, parent, false);
        return new CardDashboardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardDashboardAdapter.ViewHolder holder, final int position) {
        holder.title.setText(listButton.get(position).getTitle());
        holder.desc.setText(listButton.get(position).getDesc());
        holder.banner.setImageResource(listButton.get(position).getBanner());
        holder.logo.setImageResource(listButton.get(position).getLogo());
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (listButton.get(position).getTitle()==R.string.dashboard_newsfeed_title){
                    context.startActivity(new Intent(context, NewsfeedActivity.class));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listButton.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView banner, logo;
        TextView title, desc;
        CardView parentView;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            banner = itemView.findViewById(R.id.banner_button);
            logo = itemView.findViewById(R.id.logo_button);
            title = itemView.findViewById(R.id.title_button);
            desc = itemView.findViewById(R.id.desc_button);
            parentView = itemView.findViewById(R.id.list_card_button2);
        }

    }
}
