package com.example.apartmentcitizen.component;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.home.account.ButtonCardDTO;
import com.example.apartmentcitizen.home.account.familymember.FamilyInformationActivity;
import com.example.apartmentcitizen.home.account.information.InformationActivity;
import com.example.apartmentcitizen.home.account.wallet.WalletActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    Context context;
    List<ButtonCardDTO> listCard;


    public CardAdapter(Context context, List<ButtonCardDTO> listCard) {
        this.context = context;
        this.listCard = listCard;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtTitle.setText(listCard.get(position).getTitle());
        holder.txtDesc.setText(listCard.get(position).getDesc());
        holder.imgBackground.setImageResource(listCard.get(position).getImgPath());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (listCard.get(position).getTitle() == R.string.information_title_item1) {
                    intent = new Intent(context, WalletActivity.class);
                    context.startActivity(intent);
                }
                if (listCard.get(position).getTitle() == R.string.information_title_item2) {
                    intent = new Intent(context, InformationActivity.class);
                    context.startActivity(intent);
                }
                if (listCard.get(position).getTitle() == R.string.information_title_item3) {
                    intent = new Intent(context, FamilyInformationActivity.class);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCard.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBackground;
        TextView txtTitle, txtDesc;
        CardView parentLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBackground = itemView.findViewById(R.id.img_item_list);
            txtTitle = itemView.findViewById(R.id.title_item_list);
            txtDesc = itemView.findViewById(R.id.desc_item_list);
            parentLayout = itemView.findViewById(R.id.list_card_item);
        }
    }
}
