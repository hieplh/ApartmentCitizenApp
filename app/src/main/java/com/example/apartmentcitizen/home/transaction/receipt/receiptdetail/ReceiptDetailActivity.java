package com.example.apartmentcitizen.home.transaction.receipt.receiptdetail;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.component.ElectricWaterAdapter;
import com.example.apartmentcitizen.component.ReceiptItemAdapter;
import com.example.apartmentcitizen.handle.Digit;
import com.example.apartmentcitizen.handle.Time;
import com.example.apartmentcitizen.home.account.wallet.WalletActivity;
import com.example.apartmentcitizen.home.transaction.HouseObject;
import com.example.apartmentcitizen.home.transaction.TransactionObject;
import com.example.apartmentcitizen.home.transaction.receipt.ReceiptObject;
import com.example.apartmentcitizen.network.LoadReceiptDetailByReceiptId;
import com.example.apartmentcitizen.network.ReceiptDetailService;
import com.example.apartmentcitizen.network.RetrofitInstance;
import com.example.apartmentcitizen.network.UserService;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReceiptDetailActivity extends AppCompatActivity {

    TextView labelReceipt, houseHolder, labelHouseName, receiptDate, totalReceipt;
    Button buttonPay;
    Retrofit retrofit;
    List<ReceiptDetailObject> listReceiptDetail;
    RecyclerView recyclerView;
    long result = 0;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_detail);
        sharedPreferences = getSharedPreferences(getString(R.string.shared_info), MODE_PRIVATE);
        setUpView();
    }

    public void setUpView() {
        final ReceiptObject receiptObject = (ReceiptObject) getIntent().getSerializableExtra("receiptObject");
        labelReceipt = findViewById(R.id.label_title_standard);
        labelReceipt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        labelHouseName = findViewById(R.id.label_house_name);
        receiptDate = findViewById(R.id.receipt_detail_date);
        recyclerView = findViewById(R.id.list_detail_receipt);
        buttonPay = findViewById(R.id.button_receipt_pay);
        buttonPay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        totalReceipt = findViewById(R.id.total_all);

        Time.setTimeForReceipt(receiptObject.getPublishDate(), receiptDate);
        labelHouseName.setText("Số nhà: " + receiptObject.getHouse().getHouseName());
        labelReceipt.setText(receiptObject.getDescription());

        retrofit = RetrofitInstance.getRetrofitInstance();
        LoadReceiptDetailByReceiptId loadReceiptDetailByReceiptId = retrofit.create(LoadReceiptDetailByReceiptId.class);
        Call<List<ReceiptDetailObject>> callReceiptDetail = loadReceiptDetailByReceiptId.getReceiptDetailByReceiptId(receiptObject.getReceiptId());
        callReceiptDetail.enqueue(new Callback<List<ReceiptDetailObject>>() {
            @Override
            public void onResponse(Call<List<ReceiptDetailObject>> call, Response<List<ReceiptDetailObject>> response) {
                listReceiptDetail = response.body();
                for (ReceiptDetailObject obj : listReceiptDetail) {
                    result += obj.getTotal();
                }
                totalReceipt.setText("Tổng tiền hoá đơn: " + Digit.handleDigit(result + ""));
                if (receiptObject.getStatus() == 0) {
                    buttonPay.setBackgroundResource(R.drawable.button_unpay);
                    buttonPay.setText(R.string.receipt_status_pay_now);
                    buttonPay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(ReceiptDetailActivity.this);
                            builder.setTitle("Xác nhận");
                            builder.setMessage("Số tiền cần thanh toán là " + Digit.handleDigit(result + ""));
                            if (receiptObject.getTitle().trim().equals("dv")) {
                                builder.setIcon(R.drawable.icon_receipt_service);
                            } else if (receiptObject.getTitle().trim().equals("d")) {
                                builder.setIcon(R.drawable.icon_receipt_electric);
                            } else {
                                builder.setIcon(R.drawable.icon_receipt_water);
                            }
                            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    if (receiptObject.getHouse().getCurrentMoney() < result) {
                                        //not enough money
                                        AlertDialog.Builder alert = new AlertDialog.Builder(ReceiptDetailActivity.this);
                                        alert.setTitle("Thất bại");
                                        alert.setMessage("Số dư hiện không đủ để thực hiện thanh toán");
                                        alert.setPositiveButton("OK", null);
                                        alert.show();
                                        dialog.dismiss();
                                    } else {
                                        //enough money
                                        ReceiptDetailService receiptDetailService = retrofit.create(ReceiptDetailService.class);
                                        Call<ReceiptDetailResponse> detailResponseCall = receiptDetailService.updateReceiptStatusByReceiptId(receiptObject.getReceiptId(), 1);
                                        detailResponseCall.enqueue(new Callback<ReceiptDetailResponse>() {
                                            @Override
                                            public void onResponse(Call<ReceiptDetailResponse> call, Response<ReceiptDetailResponse> response) {
                                                receiptObject.setStatus(1);
                                                UserService userService = retrofit.create(UserService.class);
                                                final long newWallet = receiptObject.getHouse().getCurrentMoney()-result;
                                                Call<WalletActivity.WalletResponse> call1 = userService.UpdateHouseWalletByHouseId(receiptObject.getHouse().getHouseId(), (int)newWallet);
                                                call1.enqueue(new Callback<WalletActivity.WalletResponse>() {
                                                    @Override
                                                    public void onResponse(Call<WalletActivity.WalletResponse> call, Response<WalletActivity.WalletResponse> response) {
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putInt(getString(R.string.key_house_money), (int) newWallet);
                                                        editor.apply();
                                                        final TransactionObject obj = new TransactionObject();
                                                        obj.setAmount(result);
                                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                                        obj.setCreatedDate(sdf.format(new Date()));
                                                        HouseObject houseObject = new HouseObject(receiptObject.getHouse().getHouseId(), receiptObject.getHouse().getHouseName()
                                                                , receiptObject.getHouse().getOwnerId(), receiptObject.getHouse().getCurrentMoney());
                                                        obj.setHouse(houseObject);
                                                        obj.setStatus(0);
                                                        obj.setTitle("Thanh toán " + receiptObject.getDescription());
                                                        obj.setTransactorId(sharedPreferences.getInt(getString(R.string.key_user_id), 0 ));
                                                        ReceiptDetailService service = retrofit.create(ReceiptDetailService.class);
                                                        Call<ReceiptDetailActivity.ReceiptDetailResponse> call2 = service.createUser(obj);
                                                        //final String toStr ="House amount: " + obj.getAmount() + "; createDate: " + obj.getCreatedDate() + "; houseId: " + obj.getHouse().getId() + "; TransactorId: " + obj.getTransactorId();
                                                        call2.enqueue(new Callback<ReceiptDetailResponse>() {
                                                            @Override
                                                            public void onResponse(Call<ReceiptDetailResponse> call2, Response<ReceiptDetailResponse> response2) {
                                                            }

                                                            @Override
                                                            public void onFailure(Call<ReceiptDetailResponse> call, Throwable t) {
                                                                Toast.makeText(ReceiptDetailActivity.this, "NOT OK", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }

                                                    @Override
                                                    public void onFailure(Call<WalletActivity.WalletResponse> call, Throwable t) {
                                                            Toast.makeText(ReceiptDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                finish();
                                                startActivity(getIntent());
                                            }

                                            @Override
                                            public void onFailure(Call<ReceiptDetailResponse> call, Throwable t) {
                                                Toast.makeText(ReceiptDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        dialog.dismiss();
                                    }
                                }
                            });

                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    });

                } else if (receiptObject.getStatus() == 1) {
                    buttonPay.setClickable(false);
                    buttonPay.setText(R.string.receipt_status_paid);
                    buttonPay.setBackgroundResource(R.drawable.button_paid);
                }
                if (receiptObject.getTitle().trim().equals("d") || receiptObject.getTitle().trim().equals("n")) {
                    ElectricWaterAdapter adapter = new ElectricWaterAdapter(ReceiptDetailActivity.this, listReceiptDetail);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ReceiptDetailActivity.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(layoutManager);
                    totalReceipt.setVisibility(View.GONE);
                } else {
                    ReceiptItemAdapter adapter = new ReceiptItemAdapter(ReceiptDetailActivity.this, listReceiptDetail);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ReceiptDetailActivity.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(layoutManager);
                }

            }


            @Override
            public void onFailure(Call<List<ReceiptDetailObject>> call, Throwable t) {

            }
        });


    }

    public class ReceiptDetailResponse {
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
