package com.example.apartmentcitizen.login;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.apartmentcitizen.R;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EasySplashScreen config = new EasySplashScreen(SplashActivity.this)
                .withFullScreen()
                .withTargetActivity(LoginActivity.class)
                .withSplashTimeOut(SPLASH_TIME)
                .withLogo(R.drawable.logo_apartment)
                .withBackgroundColor(Color.parseColor("#FFFFFF"));

        View splashScreen = config.create();
        setContentView(splashScreen);

    }

}
