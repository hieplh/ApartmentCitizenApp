package com.example.apartmentcitizen.home.transaction;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.component.TransactionAdapter;
import com.example.apartmentcitizen.network.LoadTransactionByHouseIdService;
import com.example.apartmentcitizen.network.RetrofitInstance;

import java.util.ArrayList;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class TransactionFragment extends Fragment {

    RecyclerView recyclerView;
    Retrofit retrofit;
    List<TransactionObject> listTransaction = new ArrayList<>();
    TextView txtCurDate, txtSpend, txtRecharge, txtMoneyInWallet, txtFullname, txtHouseCode;
    String arrDate[];
    int month, countSpend = 0, countRecharge = 0, count = 0;

    public TransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        recyclerView = view.findViewById(R.id.list_transaction);
        retrofit = RetrofitInstance.getRetrofitInstance();

        txtCurDate = view.findViewById(R.id.transaction_current_date);
        txtSpend = view.findViewById(R.id.transaction_spend_in_month);
        txtRecharge = view.findViewById(R.id.transaction_recharge_in_month);


        LoadTransactionByHouseIdService loadTransactionByHouseIdService = retrofit.create(LoadTransactionByHouseIdService.class);
        Call<List<TransactionObject>> callTransaction = loadTransactionByHouseIdService.getTransactionByHouseId(6);
        callTransaction.enqueue(new Callback<List<TransactionObject>>() {
            @Override
            public void onResponse(Call<List<TransactionObject>> call, Response<List<TransactionObject>> response) {
                listTransaction = response.body();


                for (TransactionObject obj: listTransaction) {
                    arrDate = obj.getCreatedDate().split("-");
                    month = Integer.parseInt(arrDate[1]);
                    int curMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
                    if( month == curMonth){
                        count++;
                        Toast.makeText(view.getContext(), count+"", Toast.LENGTH_LONG).show();
                            if(obj.getStatus()==0){
                                    countSpend += obj.getAmount();
                                    txtSpend.setText(countSpend+"");
                            }else if(obj.getStatus()==1){
                                    countRecharge += obj.getAmount();
                                    txtRecharge.setText(countRecharge+"");
                            }
                    }
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