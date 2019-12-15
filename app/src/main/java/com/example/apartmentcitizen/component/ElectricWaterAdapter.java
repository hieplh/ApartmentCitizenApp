package com.example.apartmentcitizen.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.handle.Digit;
import com.example.apartmentcitizen.home.transaction.receipt.receiptdetail.ReceiptDetailObject;

import java.util.List;

public class ElectricWaterAdapter extends RecyclerView.Adapter<ElectricWaterAdapter.ViewHolder> {


    Context context;
    List<ReceiptDetailObject> listReceiptDetail;
    long amount, result;
    long vat;
    public ElectricWaterAdapter(Context context, List<ReceiptDetailObject> listReceiptDetail){
        this.context = context;
        this.listReceiptDetail = listReceiptDetail;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_electric_water, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.eOld.setText(Digit.handleDigit(listReceiptDetail.get(position).getFromNum()+""));
        holder.eNew.setText(Digit.handleDigit(listReceiptDetail.get(position).getToNum()+""));
        amount = listReceiptDetail.get(position).getQuantity();
        holder.amount.setText(Digit.handleDigit(amount+""));
        result = (long) (listReceiptDetail.get(position).getTotal() - (listReceiptDetail.get(position).getTotal()*0.1));
        holder.result.setText(Digit.handleDigit(result+""));
        vat = (long) (listReceiptDetail.get(position).getTotal()*0.1);
        holder.vat.setText(Digit.handleDigit(vat+""));
        holder.fResult.setText(Digit.handleDigit((listReceiptDetail.get(position).getTotal())+""));
        if(listReceiptDetail.get(position).getReceipt().getTitle().equals("d")){
            holder.unit.setText("KWh tiêu thụ");
        }else{
            holder.unit.setText("Khối nước tiêu thụ");
        }

    }

    @Override
    public int getItemCount() {
        return listReceiptDetail.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView eOld, eNew, amount, result, vat, fResult, unit;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            unit = itemView.findViewById(R.id.unit_receipt);
            eOld = itemView.findViewById(R.id.e_old);
            eNew = itemView.findViewById(R.id.e_new);
            amount = itemView.findViewById(R.id.e_amount);
            vat = itemView.findViewById(R.id.e_vat);
            result = itemView.findViewById(R.id.e_result);
            fResult = itemView.findViewById(R.id.e_final_result);
        }
    }
}
