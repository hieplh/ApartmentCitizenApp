package com.example.apartmentcitizen.home.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.component.CardAdapter;
import com.example.apartmentcitizen.network.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Retrofit;

public class AccountFragment extends Fragment {

    RecyclerView recyclerView1, recyclerView2;
    List<AccountObject> listCard1, listCard2;

    SharedPreferences sharedPreferences;
    Retrofit retrofit;

    CardAdapter adapter1, adapter2;
    View view;

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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;

        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.shared_info), Context.MODE_PRIVATE);

        recyclerView1 = view.findViewById(R.id.list_button_1);
        recyclerView2 = view.findViewById(R.id.list_button_2);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (sharedPreferences.getString(getString(R.string.key_profile_image), null) == null) {
            Glide.with(getContext())
                    .load(getResources().getDrawable(R.drawable.image_avatar_default))
                    .fitCenter()
                    .into(((CircleImageView) view.findViewById(R.id.avatar_account)));
        } else {
            Glide.with(getContext())
                    .load(RetrofitInstance.BASE_URL
                            + RetrofitInstance.VERSION_API
                            + RetrofitInstance.GET_USER_IMAGE
                            + sharedPreferences.getString(getString(R.string.key_profile_image), "null"))
                    .error(getResources().getDrawable(R.drawable.image_avatar_default))
                    .fitCenter()
                    .into(((CircleImageView) view.findViewById(R.id.avatar_account)));
        }

        ((Button) view.findViewById(R.id.house_name_account))
                .setText(sharedPreferences.getString(getString(R.string.key_house_name), "A-1-101"));

        StringBuilder sb = new StringBuilder();
        sb.append(sharedPreferences.getString(getString(R.string.key_last_name), ""));
        if (!sharedPreferences.getString(getString(R.string.key_last_name), "").equals("")) {
            sb.append(" ");
        }
        sb.append(sharedPreferences.getString(getString(R.string.key_first_name), ""));
        ((TextView) view.findViewById(R.id.label_fullname_account)).setText(sb.toString());

        adapter1 = new CardAdapter(getContext(), getActivity(), listCard1, 1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView1.setAdapter(adapter1);

        adapter2 = new CardAdapter(getContext(), getActivity(), listCard2, 2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setAdapter(adapter2);
    }
}

