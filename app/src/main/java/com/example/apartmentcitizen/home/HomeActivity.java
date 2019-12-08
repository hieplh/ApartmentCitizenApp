package com.example.apartmentcitizen.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.home.dashboard.DashboardFragment;
import com.example.apartmentcitizen.home.account.AccountFragment;
import com.example.apartmentcitizen.home.notification.NotificationFragment;
import com.example.apartmentcitizen.home.transaction.TransactionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomeActivity extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    Button btnLogOut;
    BottomNavigationView bottomNavigationView;
    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    TextView lbTitle;
    View headerHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupView();
    }

    //set up View
    public void setupView() {
        headerHome = findViewById(R.id.header_home);
        lbTitle = headerHome.findViewById(R.id.label_title);
        btnLogOut = headerHome.findViewById(R.id.button_log_out);
        System.out.println("LABEL TITLE: " + lbTitle);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navDashBoard);
        lbTitle.setText(R.string.home_label_nav_dashboard);
        loadFragment(new DashboardFragment());
        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.navTrans:
                        lbTitle.setText(R.string.home_label_nav_transaction);
                        btnLogOut.setVisibility(View.INVISIBLE);
                        fragment = new TransactionFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navDashBoard:
                        lbTitle.setText(R.string.home_label_nav_dashboard);
                        btnLogOut.setVisibility(View.INVISIBLE);
                        fragment = new DashboardFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navNoti:
                        lbTitle.setText(R.string.home_label_nav_notification);
                        btnLogOut.setVisibility(View.INVISIBLE);
                        fragment = new NotificationFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navAcc:
                        lbTitle.setText(R.string.home_label_nav_me);
                        btnLogOut.setVisibility(View.VISIBLE);
                        fragment = new AccountFragment();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        };
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    private void loadFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
