package com.example.apartmentcitizen.home.transaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apartmentcitizen.HomeActivity;
import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.component.TransactionAdapter;
import com.example.apartmentcitizen.handle.Digit;
import com.example.apartmentcitizen.home.account.wallet.WalletActivity;
import com.example.apartmentcitizen.home.transaction.receipt.HistoryReceiptActivity;
import com.example.apartmentcitizen.network.LoadTransactionByHouseIdService;
import com.example.apartmentcitizen.network.RetrofitInstance;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class TransactionFragment extends Fragment {

    RecyclerView recyclerView;
    Retrofit retrofit;
    List<TransactionObject> listTransaction;
    TextView txtCurDate, txtSpend, txtRecharge, txtMoneyInWallet, txtFullname, txtHouseCode, btnAccessWallet;
    String arrDate[];
    int month, countSpend = 0, countRecharge = 0;
    Button btnHistory;
    SharedPreferences sharedPreferences;

    public TransactionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        recyclerView = view.findViewById(R.id.list_transaction);
        retrofit = RetrofitInstance.getRetrofitInstance();

        txtFullname = view.findViewById(R.id.transaction_account_name);
        txtHouseCode = view.findViewById(R.id.transaction_house_code);
        txtMoneyInWallet = view.findViewById(R.id.transaction_money_in_wallet);
        txtCurDate = view.findViewById(R.id.transaction_current_date);
        txtSpend = view.findViewById(R.id.transaction_spend_in_month);
        txtRecharge = view.findViewById(R.id.transaction_recharge_in_month);
        btnHistory = view.findViewById(R.id.history_bill_button);
        btnAccessWallet = view.findViewById(R.id.button_access_wallet);
        btnAccessWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), WalletActivity.class);
                startActivity(intent);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), HistoryReceiptActivity.class);
                startActivity(intent);
            }
        });
        sharedPreferences = this.getContext().getSharedPreferences(getString(R.string.shared_info), Context.MODE_PRIVATE);
        txtFullname.setText(sharedPreferences.getString(getString(R.string.key_last_name), "") + " " + sharedPreferences.getString(getString(R.string.key_first_name), ""));
        int houseid = sharedPreferences.getInt(getString(R.string.key_house_id), 0);
        LoadTransactionByHouseIdService loadTransactionByHouseIdService = retrofit.create(LoadTransactionByHouseIdService.class);
        Call<List<TransactionObject>> callTransaction = loadTransactionByHouseIdService.getTransactionByHouseId(houseid);
        callTransaction.enqueue(new Callback<List<TransactionObject>>() {
            @Override
            public void onResponse(Call<List<TransactionObject>> call, Response<List<TransactionObject>> response) {
                listTransaction = response.body();
                int curMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
                int curDay = Calendar.getInstance().get(Calendar.DATE);
                int curYear = Calendar.getInstance().get(Calendar.YEAR);
                txtCurDate.setText("Ngày " + curDay + " tháng " + curMonth + " năm " + curYear);
                for (TransactionObject obj : listTransaction) {
                    arrDate = obj.getCreatedDate().split("-");
                    month = Integer.parseInt(arrDate[1]);
                    if (month == curMonth) {
                        if (obj.getStatus() == 0) {
                            countSpend += obj.getAmount();
                            txtSpend.setText(Digit.handleDigit(countSpend + ""));
                        } else if (obj.getStatus() == 1) {
                            countRecharge += obj.getAmount();
                            txtRecharge.setText(Digit.handleDigit(countRecharge + ""));
                        }
                    }
                    txtHouseCode.setText(obj.getHouse().getHouseName());
                    txtMoneyInWallet.setText(Digit.handleDigit(obj.getHouse().getCurrentMoney()+""));
                }

                TransactionAdapter adapter = new TransactionAdapter(view.getContext(), listTransaction);
                recyclerView.setAdapter(adapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);
            }

            @Override
            public void onFailure(Call<List<TransactionObject>> call, Throwable t) {
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }


}