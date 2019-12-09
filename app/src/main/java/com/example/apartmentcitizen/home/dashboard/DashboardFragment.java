package com.example.apartmentcitizen.home.dashboard;

import android.content.Context;
import android.content.Intent;
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

import com.example.apartmentcitizen.R;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    RecyclerView recyclerView;
    List<ButtonCard2DTO> listCardButton;

    public DashboardFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listCardButton = new ArrayList<>();
        listCardButton.add(new ButtonCard2DTO(R.drawable.banner_newsfeed, R.drawable.logo_newsfeed_resize, R.string.dashboard_newsfeed_title,R.string.dashboard_newsfeed_desc));
        listCardButton.add(new ButtonCard2DTO(R.drawable.banner_nearby_service, R.drawable.logo_service_nearby_resize, R.string.dashboard_service_nearby_title,R.string.dashboard_service_nearby_desc));
        listCardButton.add(new ButtonCard2DTO(R.drawable.banner_fix_service, R.drawable.logo_fix_service_resize, R.string.dashboard_fix_title,R.string.dashboard_fix_desc));
        listCardButton.add(new ButtonCard2DTO(R.drawable.banner_survey, R.drawable.logo_survey_resize, R.string.dashboard_survey_title,R.string.dashboard_survey_desc));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        recyclerView = view.findViewById(R.id.card_button_dashboard);
        CardAdapter adapter = new CardAdapter(getContext(), listCardButton);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

}

class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>{
    Context context;
    List<ButtonCard2DTO> listButton;


    public CardAdapter(Context context, List<ButtonCard2DTO> listButton) {
        this.context = context;
        this.listButton = listButton;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_button2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(listButton.get(position).getTitle());
        holder.desc.setText(listButton.get(position).getDesc());
        holder.banner.setImageResource(listButton.get(position).getBanner());
        holder.logo.setImageResource(listButton.get(position).getLogo());
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                //do sth
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
