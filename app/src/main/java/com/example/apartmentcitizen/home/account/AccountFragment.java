package com.example.apartmentcitizen.home.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.component.CardAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class AccountFragment extends Fragment {

    RecyclerView recyclerView1, recyclerView2;
    List<ButtonCardDTO> listCard1, listCard2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listCard1 = new ArrayList<>();
        listCard1.add(new ButtonCardDTO(R.drawable.background_my_wallet_trans, R.string.information_title_item1, R.string.information_desc_item1));
        listCard1.add(new ButtonCardDTO(R.drawable.background_information_trans, R.string.information_title_item2, R.string.information_desc_item2));
        listCard1.add(new ButtonCardDTO(R.drawable.background_my_wall_trans, R.string.information_title_item5, R.string.information_desc_item5));

        //
        listCard2 = new ArrayList<>();
        listCard2.add(new ButtonCardDTO(R.drawable.background_member_trans, R.string.information_title_item3, R.string.information_desc_item3));
        listCard2.add(new ButtonCardDTO(R.drawable.background_family_activities_trans, R.string.information_title_item4, R.string.information_desc_item4));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        //set up recyclerView1
        recyclerView1 = view.findViewById(R.id.list_button_1);
        CardAdapter adapter = new CardAdapter(getContext(), listCard1);
        recyclerView1.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(layoutManager);

        //set up recyclerView2
        recyclerView2 = view.findViewById(R.id.list_button_2);
        CardAdapter adapter2 = new CardAdapter(getContext(), listCard2);
        recyclerView2.setAdapter(adapter2);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(layoutManager2);

        return view;
    }
}

