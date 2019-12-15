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

public class ReceiptItemAdapter extends RecyclerView.Adapter<ReceiptItemAdapter.ViewHolder> {

    Context context;
    List<ReceiptDetailObject> listReceiptDetail;

    public ReceiptItemAdapter(Context context, List<ReceiptDetailObject> listReceiptDetail){
        this.context = context;
        this.listReceiptDetail = listReceiptDetail;
    }


    @NonNull
    @Override
    public ReceiptItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receipt_service, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptItemAdapter.ViewHolder holder, int position) {
        holder.name.setText(listReceiptDetail.get(position).getService().getName());
        holder.type.setText(listReceiptDetail.get(position).getService().getCat().getName());
        holder.unitPrice.setText(Digit.handleDigit(listReceiptDetail.get(position).getUnitPrice()+""));
        holder.quantity.setText(Digit.handleDigit(listReceiptDetail.get(position).getQuantity()+""));
        holder.total.setText(Digit.handleDigit(listReceiptDetail.get(position).getTotal()+""));
    }

    @Override
    public int getItemCount() {
        return listReceiptDetail.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, type, unitPrice, quantity, total;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.receipt_name_service);
            type = itemView.findViewById(R.id.receipt_type_service);
            unitPrice = itemView.findViewById(R.id.receipt_unit_price);
            quantity = itemView.findViewById(R.id.receipt_quantity);
            total = itemView.findViewById(R.id.receipt_total);
        }
    }
}
