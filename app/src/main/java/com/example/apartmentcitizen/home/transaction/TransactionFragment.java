package com.example.apartmentcitizen.home.transaction;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.component.TransactionAdapter;
import com.example.apartmentcitizen.network.LoadTransactionByHouseIdService;
import com.example.apartmentcitizen.network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class TransactionFragment extends Fragment {

    RecyclerView recyclerView;
    Retrofit retrofit;
    List<TransactionObject> listTransaction;

    public TransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        recyclerView = view.findViewById(R.id.list_transaction);
        retrofit = RetrofitInstance.getRetrofitInstance();
        LoadTransactionByHouseIdService loadTransactionByHouseIdService = retrofit.create(LoadTransactionByHouseIdService.class);
        Call<List<TransactionObject>> callTransaction = loadTransactionByHouseIdService.getTransactionByHouseId(6);
        callTransaction.enqueue(new Callback<List<TransactionObject>>() {
            @Override
            public void onResponse(Call<List<TransactionObject>> call, Response<List<TransactionObject>> response) {
                listTransaction = response.body();
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