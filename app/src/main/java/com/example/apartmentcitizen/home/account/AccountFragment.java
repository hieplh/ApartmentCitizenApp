package com.example.apartmentcitizen.home.account;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.home.HomeActivity;
import com.example.apartmentcitizen.home.account.wallet.WalletActivity;

import java.util.ArrayList;
import java.util.List;


public class AccountFragment extends Fragment {

    RecyclerView recyclerView1, recyclerView2;
    List<CardDTO> listCard1, listCard2;
    public AccountFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        //set up recyclerView1
        recyclerView1 = view.findViewById(R.id.list_button_1);
        listCard1 = new ArrayList<>();
        listCard1.add(new CardDTO(R.drawable.background_my_wallet_trans,R.string.information_title_item1,R.string.information_desc_item1));
        listCard1.add(new CardDTO(R.drawable.background_information_trans,R.string.information_title_item2,R.string.information_desc_item2));
        CardAdapter adapter = new CardAdapter(getContext(), listCard1);
        recyclerView1.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(layoutManager);

        //set up recyclerView2
        recyclerView2 = view.findViewById(R.id.list_button_2);
        listCard2 = new ArrayList<>();
        listCard2.add(new CardDTO(R.drawable.background_member_trans,R.string.information_title_item3, R.string.information_desc_item3));
        listCard2.add(new CardDTO(R.drawable.background_family_activities_trans,R.string.information_title_item4, R.string.information_desc_item4));
        CardAdapter adapter2 = new CardAdapter(getContext(), listCard2);
        recyclerView2.setAdapter(adapter2);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(layoutManager2);


        return view;
    }



}

class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>{
    Context context;
    List<CardDTO> listCard;


    public CardAdapter(Context context, List<CardDTO> listCard) {
        this.context = context;
        this.listCard = listCard;
    }

    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardAdapter.ViewHolder holder, final int position) {
        holder.txtTitle.setText(listCard.get(position).getTitle());
        holder.txtDesc.setText(listCard.get(position).getDesc());
        holder.imgBackground.setImageResource(listCard.get(position).getImgPath());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(listCard.get(position).getTitle()==R.string.information_title_item1){
                    intent = new Intent(context, WalletActivity.class);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCard.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
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

