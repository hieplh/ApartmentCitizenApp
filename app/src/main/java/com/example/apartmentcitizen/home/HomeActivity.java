package com.example.apartmentcitizen.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.home.dashboard.DashboardFragment;
import com.example.apartmentcitizen.home.newsfeed.NewsfeedFragment;
import com.example.apartmentcitizen.home.notification.NotificationFragment;
import com.example.apartmentcitizen.home.survey.SurveyFragment;
import com.example.apartmentcitizen.information.InformationActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    TextView lbTitle;
    View headerHome;
    CircleImageView imgAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupView();
    }

    public void setupView(){
        headerHome = findViewById(R.id.header_home);
        lbTitle = headerHome.findViewById(R.id.label_title);
        System.out.println("LABEL TITLE: " + lbTitle);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navNewsfeed);
        loadFragment(new NewsfeedFragment());
        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()){
                    case R.id.navSurvey:
                        lbTitle.setText("Khảo sát");
                        fragment = new SurveyFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navNewsfeed:
                        lbTitle.setText("Bảng tin");
                        fragment = new NewsfeedFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navDBoard:
                        lbTitle.setText("Bảng điều khiển");
                        fragment = new DashboardFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navNoti:
                        lbTitle.setText("Thông báo");
                        fragment = new NotificationFragment();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        };
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //set up avatar button
        imgAvatar = headerHome.findViewById(R.id.button_avatar);
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), InformationActivity.class);
                startActivity(intent);
            }
        });
    }


    private void loadFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}

