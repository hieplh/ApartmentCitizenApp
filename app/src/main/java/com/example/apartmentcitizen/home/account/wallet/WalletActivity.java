package com.example.apartmentcitizen.home.account.wallet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.example.apartmentcitizen.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class WalletActivity extends AppCompatActivity {

    View walletCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        setUpView();

    }

    public void setUpView() {
        StringBuilder sb = new StringBuilder();

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_info), MODE_PRIVATE);
        sb.append(sharedPreferences.getString(getString(R.string.key_last_name), ""));
        if (!sharedPreferences.getString(getString(R.string.key_last_name), "").equals("")) {
            sb.append(" ");
        }
        sb.append(sharedPreferences.getString(getString(R.string.key_first_name), ""));

        ((TextView) findViewById(R.id.text_card_fullname)).setText(sb.toString());

        ((TextView) findViewById(R.id.text_money_in_wallet))
                .setText(handleDigit(Integer.toString(sharedPreferences.getInt(getString(R.string.key_house_money), 0))));

        ViewTreeObserver treeObserver = findViewById(R.id.constraint_layout_wallet_activity).getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                walletCardView = findViewById(R.id.card_wallet);

                float height = walletCardView.getHeight();
                Animation animation = new TranslateAnimation(0, 0, (0f - height), 0f);

                animation.setDuration(400);
                walletCardView.startAnimation(animation);
            }
        });
    }

    public void clickToPayNow(View view) {
    }

    public void clickToRecharge(View view) {
    }

    public void clickToShowHistoryTrans(View view) {
    }

    private String handleDigit(String digit) {
        StringBuilder result = new StringBuilder();
        List<String> list = new ArrayList<>();
        int length = digit.length();

        if (length <= 3) {
            return digit;
        } else {
            int start = length;
            int end = start - 3 >= 0 ? start - 3 : 0;
            boolean flag = false;

            do {
                if (flag) {
                    start = end;
                    end = start - 3 > 0 ? start - 3 : 0;
                    list.add(",");
                } else {
                    flag = true;
                }
                list.add(digit.substring(end, start));
            } while (end > 0);
        }
        for (int i = list.size() - 1; i >= 0; i--) {
            result.append(list.get(i));
        }
        return result.toString();
    }
}
