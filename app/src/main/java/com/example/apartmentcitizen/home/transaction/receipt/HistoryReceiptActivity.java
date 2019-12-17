package com.example.apartmentcitizen.home.transaction.receipt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apartmentcitizen.HomeActivity;
import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.component.ReceiptAdapter;
import com.example.apartmentcitizen.home.transaction.TransactionFragment;
import com.example.apartmentcitizen.network.LoadReceiptByHouseId;
import com.example.apartmentcitizen.network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HistoryReceiptActivity extends AppCompatActivity {

    TextView title;
    RecyclerView recyclerView;
    List<ReceiptObject> listReceipt;
    Retrofit retrofit;

    @Override
    protected void onStart() {
        super.onStart();
        setUpView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_receipt);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(HistoryReceiptActivity.this, HomeActivity.class);
        intent.putExtra("transaction_fr", TransactionFragment.class);
        startActivity(intent);
    }

    public void setUpView(){
        title = findViewById(R.id.label_title_standard);
        title.setText(R.string.receipt_title);
        recyclerView = findViewById(R.id.list_receipt);
        retrofit = RetrofitInstance.getRetrofitInstance();
        LoadReceiptByHouseId loadReceiptByHouseId = retrofit.create(LoadReceiptByHouseId.class);
        Call<List<ReceiptObject>> callReceipt = loadReceiptByHouseId.getReceiptByHouseId(6);
        callReceipt.enqueue(new Callback<List<ReceiptObject>>() {
            @Override
            public void onResponse(Call<List<ReceiptObject>> call, Response<List<ReceiptObject>> response) {
                listReceipt = response.body();
                ReceiptAdapter adapter = new ReceiptAdapter(HistoryReceiptActivity.this, listReceipt);
                LinearLayoutManager layoutManager = new LinearLayoutManager(HistoryReceiptActivity.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
            }

            @Override
            public void onFailure(Call<List<ReceiptObject>> call, Throwable t) {
                Toast.makeText(HistoryReceiptActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
