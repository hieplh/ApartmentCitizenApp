package com.example.apartmentcitizen.home.account.familymember;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apartmentcitizen.R;

import java.util.ArrayList;
import java.util.List;

public class FamilyInformationActivity extends AppCompatActivity {

    TextView title;
    RecyclerView familyMemberView;
    List<MemberCardDTO> listFamilyMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_information);
        setUpView();
    }

    public void setUpView(){
        title = findViewById(R.id.label_title_standard);
        title.setText(R.string.member_family_activity_title);

        familyMemberView = findViewById(R.id.list_family_member);
        listFamilyMember = new ArrayList<>();
        listFamilyMember.
                add(new MemberCardDTO(1, "Duy Anh", "Nguyễn", "Con trai/Con gái","duyanhnguyen@gmail.com", "0909090909","16/09/1996",null));
        listFamilyMember.
                add(new MemberCardDTO(2, "Đức Hiếu", "Phạm", "Cô/chú","duyanhnguyen@gmail.com", "0909090909","16/09/1996",null));
        listFamilyMember.
                add(new MemberCardDTO(3, "Hoàng Hiệp", "Lê", "Anh/Em","hoanghiepp@gmail.com", "0909090909","16/09/1996",null));
        listFamilyMember.
                add(new MemberCardDTO(4, "Quốc Thái", "Đặng", "Cháu trai/Cháu gái","quocthai@gmail.com", "0909090909","16/09/1996",null));
        listFamilyMember.
                add(new MemberCardDTO(5, "Quang Bảo", "Hồ", "Chủ hộ","quangbao@gmail.com", "0909090909","16/09/1996",null));
        listFamilyMember.
                add(new MemberCardDTO(6, "Thanh Hải", "Hoàng Lưu", "Bạn bè","hoanghai@gmail.com", "0909090909","16/09/1996",null));
        MemberCardAdapter adapter = new MemberCardAdapter(this, listFamilyMember);
        familyMemberView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        familyMemberView.setLayoutManager(layoutManager);
    }

}

class MemberCardAdapter extends RecyclerView.Adapter<MemberCardAdapter.ViewHolder> {
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
                .setText(listMemberCard.get(position).getLastName() +" " + listMemberCard.get(position).getFirstName());
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