package com.example.apartmentcitizen.component;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.handle.Time;
import com.example.apartmentcitizen.home.transaction.receipt.receiptdetail.ReceiptDetailActivity;
import com.example.apartmentcitizen.home.transaction.receipt.ReceiptObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ViewHolder> {

    Context context;
    List<ReceiptObject> listReceipt;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date d;
    String[] arrTime1, arrTime2;

    public ReceiptAdapter(Context context, List<ReceiptObject> listReceipt){
        this.context = context;
        this.listReceipt = listReceipt;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receipt, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if(listReceipt.get(position).getStatus()==0){
            holder.status.setText(R.string.receipt_status_unpaid);
            holder.status.setTextColor(ContextCompat.getColor(context, R.color.wallet_gradient1));
        }else if(listReceipt.get(position).getStatus()==1){
            holder.status.setText(R.string.receipt_status_paid);
            holder.status.setTextColor(ContextCompat.getColor(context, R.color.wallet_gradient3));
        }
        holder.desc.setText(listReceipt.get(position).getDescription());
        Time.setTimeForReceipt(listReceipt.get(position).getPublishDate(), holder.publishDate);

        if(listReceipt.get(position).getTitle().trim().equals("d")){
            holder.imgReceiptType.setImageResource(R.drawable.icon_receipt_electric);
        }else if(listReceipt.get(position).getTitle().trim().equals("n")){
            holder.imgReceiptType.setImageResource(R.drawable.icon_receipt_water);
        }else if(listReceipt.get(position).getTitle().trim().equals("dv")){
            holder.imgReceiptType.setImageResource(R.drawable.icon_receipt_service);
        }
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReceiptDetailActivity.class);
                intent.putExtra("receiptObject", listReceipt.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listReceipt.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView desc, publishDate, status;
        CircleImageView imgReceiptType;
        RelativeLayout parentView;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            status = itemView.findViewById(R.id.receipt_status);
            desc = itemView.findViewById(R.id.receipt_description);
            publishDate = itemView.findViewById(R.id.receipt_publish_date);
            imgReceiptType = itemView.findViewById(R.id.icon_receipt_type);
            parentView = itemView.findViewById(R.id.item_receipt);
        }
    }
}
