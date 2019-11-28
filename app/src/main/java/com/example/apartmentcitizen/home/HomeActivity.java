package com.example.apartmentcitizen.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.apartmentcitizen.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    TextView lbTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupView();
    }

    public void setupView(){
        lbTitle = findViewById(R.id.lbTitle);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navNewsfeed);
        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()){
                    case R.id.navSurvey:
                        lbTitle.setText("Khảo sát");
                        return true;
                    case R.id.navNewsfeed:
                        lbTitle.setText("Bảng tin");
                        return true;
                    case R.id.navDBoard:
                        lbTitle.setText("Bảng điều khiển");
                        return true;
                    case R.id.navNoti:
                        lbTitle.setText("Thông báo");
                        return true;
                }
                return false;
            }
        };
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}

