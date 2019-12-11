package com.example.apartmentcitizen;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apartmentcitizen.home.account.AccountFragment;
import com.example.apartmentcitizen.home.dashboard.DashboardFragment;
import com.example.apartmentcitizen.home.notification.NotificationFragment;
import com.example.apartmentcitizen.home.transaction.TransactionFragment;
import com.example.apartmentcitizen.login.LoginActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class HomeActivity extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    Button btnLogOut;
    BottomNavigationView bottomNavigationView;
    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    TextView lbTitle;
    RelativeLayout headerHome;
    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupView();
    }

    //set up View
    public void setupView() {
        window = getWindow();
        headerHome = findViewById(R.id.header_home);
        lbTitle = findViewById(R.id.label_title);
        btnLogOut = findViewById(R.id.button_log_out);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navDashBoard);
        lbTitle.setText(R.string.home_label_nav_dashboard);
        lbTitle.setTextColor(Color.WHITE);
        headerHome.setBackgroundResource(R.color.blue1);
        window.setStatusBarColor(ContextCompat.getColor(HomeActivity.this, R.color.blue1));
        btnLogOut.setVisibility(View.INVISIBLE);
        loadFragment(new DashboardFragment());
        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.navTrans:
                        lbTitle.setText(R.string.home_label_nav_transaction);
                        lbTitle.setTextColor(Color.BLACK);
                        headerHome.setBackgroundResource(R.color.gray_reg_frame);
                        window.setStatusBarColor(ContextCompat.getColor(HomeActivity.this, R.color.gray_reg_frame));
                        btnLogOut.setVisibility(View.INVISIBLE);
                        fragment = new TransactionFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navDashBoard:
                        window.setStatusBarColor(ContextCompat.getColor(HomeActivity.this, R.color.blue1));
                        lbTitle.setText(R.string.home_label_nav_dashboard);
                        lbTitle.setTextColor(Color.WHITE);
                        headerHome.setBackgroundResource(R.color.blue1);
                        btnLogOut.setVisibility(View.INVISIBLE);
                        fragment = new DashboardFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navNoti:
                        lbTitle.setText(R.string.home_label_nav_notification);
                        lbTitle.setTextColor(Color.BLACK);
                        headerHome.setBackgroundResource(R.color.gray_reg_frame);
                        window.setStatusBarColor(ContextCompat.getColor(HomeActivity.this, R.color.gray_reg_frame));
                        btnLogOut.setVisibility(View.INVISIBLE);
                        fragment = new NotificationFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navAcc:
                        lbTitle.setText(R.string.home_label_nav_me);
                        lbTitle.setTextColor(Color.BLACK);
                        headerHome.setBackgroundResource(R.color.gray_reg_frame);
                        window.setStatusBarColor(ContextCompat.getColor(HomeActivity.this, R.color.gray_reg_frame));
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

    public void logoutAccount(View view) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        mAuth.signOut();

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(HomeActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        finish();
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
