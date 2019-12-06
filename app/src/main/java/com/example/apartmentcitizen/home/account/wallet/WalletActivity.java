package com.example.apartmentcitizen.home.account.wallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import com.example.apartmentcitizen.R;

public class WalletActivity extends AppCompatActivity {

    CardView walletCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        ViewTreeObserver treeObserver = findViewById(R.id.constraint_layout_wallet_activity).getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                walletCardView = findViewById(R.id.card_wallet);

                float height = walletCardView.getHeight();
                Animation animation = new TranslateAnimation(0, 0, (0f - height), 0f);

                animation.setDuration(500);
                walletCardView.startAnimation(animation);
            }
        });
    }
}
