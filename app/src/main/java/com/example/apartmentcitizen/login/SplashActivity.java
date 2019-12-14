package com.example.apartmentcitizen.login;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.apartmentcitizen.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import gr.net.maroulis.library.EasySplashScreen;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setNavigationBarColor(ContextCompat.getColor(SplashActivity.this, R.color.purple));
        EasySplashScreen config = new EasySplashScreen(SplashActivity.this)
                .withFullScreen()
                .withTargetActivity(LoginActivity.class)
                .withSplashTimeOut(SPLASH_TIME)
                .withLogo(R.drawable.logo_aht)
                .withAfterLogoText("ATH Apartment")
                .withBackgroundResource(R.drawable.background_splash);

        config.getAfterLogoTextView().setTextSize(30);
        config.getAfterLogoTextView().setPadding(20, 20, 20, 20);
        View splashScreen = config.create();
        setContentView(splashScreen);

    }

}
