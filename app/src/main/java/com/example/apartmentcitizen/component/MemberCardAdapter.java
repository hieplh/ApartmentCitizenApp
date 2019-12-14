package com.example.apartmentcitizen.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.home.account.familymember.MemberCardDTO;
import com.example.apartmentcitizen.network.RetrofitInstance;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

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
        int relationCode = Integer.parseInt(listMemberCard.get(position).getRelation());
        holder.txtRelation.setText(Relation.getRelationByCode(relationCode));
        holder.txtEmail.setText(listMemberCard.get(position).getEmail());
        holder.txtPhone.setText(listMemberCard.get(position).getPhoneNo());
        String birthDay = listMemberCard.get(position).getBirthday();
        holder.txtBirthday.setText(birthDay);
        Glide.with(context).load(RetrofitInstance.BASE_URL
                + RetrofitInstance.VERSION_API
                + RetrofitInstance.GET_USER_IMAGE
                + listMemberCard.get(position).getUrlImg())
                .error(context.getResources().getDrawable(R.drawable.image_avatar_default)).fitCenter()
                .into(holder.imgAvatar);
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

    private enum Relation {
        OWNER(0, "Chủ hộ"),
        PARTNER(1, "Vợ/Chồng"),
        PARENT(2, "Bố/Mẹ"),
        GRANDPA(3, "Ông/Bà"),
        SIBLING(4, "Anh/Chị/Em"),
        UNCLE(5, "Cô/Chú"),
        CHILDREN(6, "Con trai/Con gái"),
        GRANDCHILDREN(7, "Cháu trai/cháu gái"),
        FRIEND(8, "Bạn Bè");

        private int code;
        private String text;

        private Relation(int code, String text) {
            this.code = code;
            this.text = text;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public static String getRelationByCode(int code) {
            for (Relation relation : Relation.values()) {
                if (relation.code == (code)) {
                    return relation.getText();
                }
            }
            return null;
        }
    }
}
