package com.example.apartmentcitizen.home.account.wallet;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.handle.Digit;
import com.example.apartmentcitizen.network.RetrofitInstance;
import com.example.apartmentcitizen.network.UserService;
import com.google.gson.annotations.SerializedName;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WalletActivity extends AppCompatActivity {

    View walletCardView;
    Window window;
    TextView moneyInWallet;
    int currentMoney;
    Retrofit retrofit;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        retrofit = RetrofitInstance.getRetrofitInstance();
        sharedPreferences = getSharedPreferences(getString(R.string.shared_info), MODE_PRIVATE);

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

        ((TextView) findViewById(R.id.text_card_house_code)).setText(sharedPreferences.getString(getString(R.string.key_house_name), ""));

        ((TextView) findViewById(R.id.text_money_in_wallet)).setText(Digit.handleDigit(Integer.toString(sharedPreferences.getInt(getString(R.string.key_house_money), 0))));

        window = getWindow();
        window.setNavigationBarColor(ContextCompat.getColor(WalletActivity.this, R.color.purple));
        window.setStatusBarColor(ContextCompat.getColor(WalletActivity.this, R.color.blue1));
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
        currentMoney = sharedPreferences.getInt(getString(R.string.key_house_money), 0);
    }

    public void clickToPayNow(View view) {
    }

    public void clickToRecharge(View view) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Recharge Money");
        alert.setMessage("Recharge your wallet here");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                int inputMoney = Integer.parseInt(input.getText().toString());
                final int updatedMoney = currentMoney + inputMoney;
                int houseId = sharedPreferences.getInt(getString(R.string.key_house_id), 0);
                UserService userService = retrofit.create(UserService.class);
                Call<WalletResponse> walletCall = userService.UpdateHouseWalletByHouseId(houseId, updatedMoney);
                walletCall.enqueue(new Callback<WalletResponse>() {
                    @Override
                    public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(getString(R.string.key_house_money), updatedMoney);
                        editor.apply();
                        moneyInWallet
                                .setText(Digit.handleDigit(Integer.toString(sharedPreferences.getInt(getString(R.string.key_house_money), 0))));
                        Toast.makeText(WalletActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<WalletResponse> call, Throwable t) {
                        Toast.makeText(WalletActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                setUpView();

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                setUpView();
            }
        });

        alert.show();

    }

    public void clickToShowHistoryTrans(View view) {
    }


    public class WalletResponse {
        @SerializedName("success")
        private String success;

        @SerializedName("message")
        private String message;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }


}
