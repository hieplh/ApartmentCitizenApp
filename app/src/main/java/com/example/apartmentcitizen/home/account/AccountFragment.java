package com.example.apartmentcitizen.home.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    List<AccountObject> listCard1, listCard2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listCard1 = new ArrayList<>();
        listCard1.add(new AccountObject(R.drawable.background_my_wallet, R.string.information_title_item1, R.string.information_desc_item1));
        listCard1.add(new AccountObject(R.drawable.background_information, R.string.information_title_item2, R.string.information_desc_item2));
        listCard1.add(new AccountObject(R.drawable.background_my_wall, R.string.information_title_item5, R.string.information_desc_item5));

        //
        listCard2 = new ArrayList<>();
        listCard2.add(new AccountObject(R.drawable.background_scan_qr, R.string.information_title_item6, R.string.information_desc_item6));
        listCard2.add(new AccountObject(R.drawable.background_member, R.string.information_title_item3, R.string.information_desc_item3));
        listCard2.add(new AccountObject(R.drawable.background_family_activities, R.string.information_title_item4, R.string.information_desc_item4));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.shared_info), Context.MODE_PRIVATE);

        StringBuilder sb = new StringBuilder();
        sb.append(sharedPreferences.getString(getString(R.string.key_last_name), ""));
        if (!sharedPreferences.getString(getString(R.string.key_last_name), "").equals("")) {
            sb.append(" ");
        }
        sb.append(sharedPreferences.getString(getString(R.string.key_first_name), ""));
        ((TextView) view.findViewById(R.id.label_fullname_account)).setText(sb.toString());

        //set up recyclerView1
        recyclerView1 = view.findViewById(R.id.list_button_1);
        CardAdapter adapter1 = new CardAdapter(getContext(), getActivity(), listCard1, 1);
        recyclerView1.setAdapter(adapter1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        //set up recyclerView2
        recyclerView2 = view.findViewById(R.id.list_button_2);
        CardAdapter adapter2 = new CardAdapter(getContext(), getActivity(), listCard2, 2);
        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        return view;
    }
}

