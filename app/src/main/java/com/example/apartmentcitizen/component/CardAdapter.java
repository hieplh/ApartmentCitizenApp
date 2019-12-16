package com.example.apartmentcitizen.component;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.home.account.AccountObject;
import com.example.apartmentcitizen.home.account.familymember.FamilyInformationActivity;
import com.example.apartmentcitizen.home.account.information.InformationActivity;
import com.example.apartmentcitizen.home.account.wallet.WalletActivity;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    Context context;

    Activity activity;
    List<AccountObject> listCard;
    int index;

    public CardAdapter(Context context, Activity activity, List<AccountObject> listCard, int index) {
        this.context = context;
        this.activity = activity;
        this.listCard = listCard;
        this.index = index;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.txtTitle.setText(listCard.get(position).getTitle());
        holder.txtDesc.setText(listCard.get(position).getDesc());
        holder.imgBackground.setImageResource(listCard.get(position).getImgPath());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == 1) {
                    switch (listCard.get(position).getTitle()) {
                        case R.string.information_title_item1:
                            context.startActivity(new Intent(context, WalletActivity.class));
                            break;
                        case R.string.information_title_item2:
                            context.startActivity(new Intent(context, InformationActivity.class));
                            break;
                    }
                } else {
                    switch (listCard.get(position).getTitle()) {
                        case R.string.information_title_item6:
                            IntentIntegrator integrator = new IntentIntegrator(activity);
                            integrator.setOrientationLocked(false);
                            integrator.initiateScan();
                            break;
                        case R.string.information_title_item3:
                            context.startActivity(new Intent(context, FamilyInformationActivity.class));
                            break;
                    }
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
