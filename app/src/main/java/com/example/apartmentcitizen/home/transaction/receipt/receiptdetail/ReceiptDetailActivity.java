package com.example.apartmentcitizen.home.transaction.receipt.receiptdetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.component.ElectricWaterAdapter;
import com.example.apartmentcitizen.component.ReceiptItemAdapter;
import com.example.apartmentcitizen.handle.Digit;
import com.example.apartmentcitizen.handle.Time;
import com.example.apartmentcitizen.home.transaction.receipt.ReceiptObject;
import com.example.apartmentcitizen.network.LoadReceiptDetailByReceiptId;
import com.example.apartmentcitizen.network.RetrofitInstance;

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
    long result=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_detail);
        setUpView();
    }

    public void setUpView(){
        final ReceiptObject receiptObject = (ReceiptObject)getIntent().getSerializableExtra("receiptObject");
        labelReceipt = findViewById(R.id.label_title_standard);
        labelReceipt.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        labelHouseName = findViewById(R.id.label_house_name);
        receiptDate = findViewById(R.id.receipt_detail_date);
        recyclerView = findViewById(R.id.list_detail_receipt);
        buttonPay = findViewById(R.id.button_receipt_pay);
        buttonPay.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        totalReceipt = findViewById(R.id.total_all);

        Time.setTimeForReceipt(receiptObject.getPublishDate(), receiptDate);
        labelHouseName.setText("Số nhà: " + receiptObject.getHouse().getHouseName());
        labelReceipt.setText(receiptObject.getDescription());
        if(receiptObject.getStatus()==0){
            buttonPay.setBackgroundResource(R.drawable.button_unpay);
            buttonPay.setText(R.string.receipt_status_pay_now);
            buttonPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }else if(receiptObject.getStatus()==1){
            buttonPay.setClickable(false);
            buttonPay.setText(R.string.receipt_status_paid);
            buttonPay.setBackgroundResource(R.drawable.button_paid);
        }
        retrofit = RetrofitInstance.getRetrofitInstance();
        LoadReceiptDetailByReceiptId loadReceiptDetailByReceiptId = retrofit.create(LoadReceiptDetailByReceiptId.class);
        Call<List<ReceiptDetailObject>> callReceiptDetail = loadReceiptDetailByReceiptId.getReceiptDetailByReceiptId(receiptObject.getReceiptId());
        callReceiptDetail.enqueue(new Callback<List<ReceiptDetailObject>>() {
            @Override
            public void onResponse(Call<List<ReceiptDetailObject>> call, Response<List<ReceiptDetailObject>> response) {
                listReceiptDetail = response.body();
                if(receiptObject.getTitle().equals("d")||receiptObject.getTitle().equals("n")){
                    ElectricWaterAdapter adapter = new ElectricWaterAdapter(ReceiptDetailActivity.this, listReceiptDetail);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ReceiptDetailActivity.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(layoutManager);
                    totalReceipt.setVisibility(View.GONE);
                }else{
                    ReceiptItemAdapter adapter = new ReceiptItemAdapter(ReceiptDetailActivity.this, listReceiptDetail);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ReceiptDetailActivity.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(layoutManager);
                }
                for (ReceiptDetailObject obj: listReceiptDetail) {
                    result += obj.getTotal();
                }
                totalReceipt.setText("Tổng tiền hoá đơn: " + Digit.handleDigit(result+""));
            }


            @Override
            public void onFailure(Call<List<ReceiptDetailObject>> call, Throwable t) {

            }
        });

    }
}
