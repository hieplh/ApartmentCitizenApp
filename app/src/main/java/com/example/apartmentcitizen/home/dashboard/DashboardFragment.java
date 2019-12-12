package com.example.apartmentcitizen.home.dashboard;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.component.CardDashboardAdapter;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    RecyclerView recyclerView;
    List<DashboardObject> listCardButton;

    public DashboardFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listCardButton = new ArrayList<>();
        listCardButton.add(new DashboardObject(R.drawable.banner_newsfeed, R.drawable.logo_newsfeed_resize, R.string.dashboard_newsfeed_title,R.string.dashboard_newsfeed_desc));
        listCardButton.add(new DashboardObject(R.drawable.banner_nearby_service, R.drawable.logo_service_nearby_resize, R.string.dashboard_service_nearby_title,R.string.dashboard_service_nearby_desc));
        listCardButton.add(new DashboardObject(R.drawable.banner_fix_service, R.drawable.logo_fix_service_resize, R.string.dashboard_fix_title,R.string.dashboard_fix_desc));
        listCardButton.add(new DashboardObject(R.drawable.banner_survey, R.drawable.logo_survey_resize, R.string.dashboard_survey_title,R.string.dashboard_survey_desc));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        CardDashboardAdapter adapter = new CardDashboardAdapter(getContext(), listCardButton);

        recyclerView = view.findViewById(R.id.card_button_dashboard);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        return view;
    }

}

