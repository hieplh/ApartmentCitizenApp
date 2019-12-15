package com.example.apartmentcitizen.component;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.handle.Digit;
import com.example.apartmentcitizen.home.transaction.FullnameObject;
import com.example.apartmentcitizen.home.transaction.TransactionObject;
import com.example.apartmentcitizen.network.LoadFullnameByIdService;
import com.example.apartmentcitizen.network.RetrofitInstance;
import com.example.apartmentcitizen.handle.Time;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    Context context;
    List<TransactionObject> listTransaction;
    Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
    FullnameObject fullnameObj;



    public TransactionAdapter(Context context, List<TransactionObject> listTransaction) {
        this.context = context;
        this.listTransaction = listTransaction;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_history_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.title.setText(listTransaction.get(position).getTitle());
        if (listTransaction.get(position).getStatus() == 0) {
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.red_google));
        } else {
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.wallet_gradient3));
        }
        holder.amount.setText(Digit.handleDigit(listTransaction.get(position).getAmount() + ""));
        setFullName(listTransaction.get(position).getTransactorId(), holder.transactor);
        Time.setTimeForTransaction(listTransaction.get(position).getCreatedDate(), holder.day, holder.month, holder.time);

    }

    @Override
    public int getItemCount() {
        return listTransaction.size();
    }


    public void setFullName(int id, final TextView txtFullname) {
        LoadFullnameByIdService loadFullnameByIdService = retrofit.create(LoadFullnameByIdService.class);
        Call<FullnameObject> callFullname = loadFullnameByIdService.getFullnameById(id);
        callFullname.enqueue(new Callback<FullnameObject>() {
            @Override
            public void onResponse(Call<FullnameObject> call, Response<FullnameObject> response) {
                fullnameObj = response.body();
                txtFullname.setText(fullnameObj.getLastName() + " " + fullnameObj.getFirstName());
            }

            @Override
            public void onFailure(Call<FullnameObject> call, Throwable t) {
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView day, month, time, title, transactor, amount;
        RelativeLayout parentView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.transaction_time);
            day = itemView.findViewById(R.id.transaction_day);
            month = itemView.findViewById(R.id.transaction_month);
            title = itemView.findViewById(R.id.transaction_title);
            transactor = itemView.findViewById(R.id.transactor);
            amount = itemView.findViewById(R.id.transaction_amount);
            parentView = itemView.findViewById(R.id.transaction_item);


        }
    }
}
