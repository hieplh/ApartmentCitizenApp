package com.example.apartmentcitizen.home.account.familymember;

import android.os.Bundle;
import android.widget.TextView;

import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.component.MemberCardAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    public void setUpView() {
        title = findViewById(R.id.label_title_standard);
        title.setText(R.string.member_family_activity_title);

        familyMemberView = findViewById(R.id.list_family_member);
        listFamilyMember = new ArrayList<>();
        listFamilyMember.
                add(new MemberCardDTO(1, "Duy Anh", "Nguyễn", "Con trai/Con gái", "duyanhnguyen@gmail.com", "0909090909", "16/09/1996", null));
        listFamilyMember.
                add(new MemberCardDTO(2, "Đức Hiếu", "Phạm", "Cô/chú", "duyanhnguyen@gmail.com", "0909090909", "16/09/1996", null));
        listFamilyMember.
                add(new MemberCardDTO(3, "Hoàng Hiệp", "Lê", "Anh/Em", "hoanghiepp@gmail.com", "0909090909", "16/09/1996", null));
        listFamilyMember.
                add(new MemberCardDTO(4, "Quốc Thái", "Đặng", "Cháu trai/Cháu gái", "quocthai@gmail.com", "0909090909", "16/09/1996", null));
        listFamilyMember.
                add(new MemberCardDTO(5, "Quang Bảo", "Hồ", "Chủ hộ", "quangbao@gmail.com", "0909090909", "16/09/1996", null));
        listFamilyMember.
                add(new MemberCardDTO(6, "Thanh Hải", "Hoàng Lưu", "Bạn bè", "hoanghai@gmail.com", "0909090909", "16/09/1996", null));
        MemberCardAdapter adapter = new MemberCardAdapter(this, listFamilyMember);
        familyMemberView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        familyMemberView.setLayoutManager(layoutManager);
    }
}