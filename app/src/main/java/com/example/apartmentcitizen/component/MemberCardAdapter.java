package com.example.apartmentcitizen.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.home.account.familymember.MemberCardDTO;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MemberCardAdapter extends RecyclerView.Adapter<MemberCardAdapter.ViewHolder> {
    Context context;
    List<MemberCardDTO> listMemberCard;

    public MemberCardAdapter(Context context, List<MemberCardDTO> listMemberCard) {
        this.context = context;
        this.listMemberCard = listMemberCard;
    }

    @NonNull
    @Override
    public MemberCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_family_member, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberCardAdapter.ViewHolder holder, int position) {
        holder.txtFullname
                .setText(listMemberCard.get(position).getLastName() + " " + listMemberCard.get(position).getFirstName());
        holder.txtRelation.setText(listMemberCard.get(position).getRelation());
        holder.txtEmail.setText(listMemberCard.get(position).getEmail());
        holder.txtPhone.setText(listMemberCard.get(position).getPhoneNo());
        holder.txtBirthday.setText(listMemberCard.get(position).getBirthday());
    }

    @Override
    public int getItemCount() {
        return listMemberCard.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView txtFullname, txtRelation, txtEmail, txtPhone, txtBirthday;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.member_avatar);
            txtFullname = itemView.findViewById(R.id.member_full_name);
            txtRelation = itemView.findViewById(R.id.member_relation);
            txtEmail = itemView.findViewById(R.id.member_email);
            txtPhone = itemView.findViewById(R.id.member_phone_number);
            txtBirthday = itemView.findViewById(R.id.member_birth_day);
        }
    }
}
