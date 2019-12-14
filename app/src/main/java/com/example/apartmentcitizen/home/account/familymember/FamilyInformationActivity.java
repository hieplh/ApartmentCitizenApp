package com.example.apartmentcitizen.home.account.familymember;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.component.MemberCardAdapter;
import com.example.apartmentcitizen.network.LoadPostService;
import com.example.apartmentcitizen.network.RetrofitInstance;
import com.example.apartmentcitizen.network.UserService;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FamilyInformationActivity extends AppCompatActivity {

    Retrofit retrofit;
    TextView title, houseName, numberOfUser, block, floor;
    RecyclerView familyMemberView;
    SharedPreferences sharedPreferences;
    List<MemberCardDTO> listFamilyMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_information);
        retrofit = RetrofitInstance.getRetrofitInstance();
        sharedPreferences = getSharedPreferences(getString(R.string.shared_info), MODE_PRIVATE);
        setUpView();
    }

    public void setUpView() {
        title = findViewById(R.id.label_title_standard);
        title.setText(R.string.member_family_activity_title);
        familyMemberView = findViewById(R.id.list_family_member);
        houseName = findViewById(R.id.txtHouseName);
        numberOfUser = findViewById(R.id.txtNumberOfUser);
        houseName.setText(sharedPreferences.getString(getString(R.string.key_house_name), ""));
        String[] houseInfo = houseName.getText().toString().split("-");
        block = findViewById(R.id.txtBlock);
        block.setText(houseInfo[0]);
        floor = findViewById(R.id.txtFloor);
        floor.setText(houseInfo[1]);
        int houseId = sharedPreferences.getInt(getString(R.string.key_house_id), 0);
        UserService userService = retrofit.create(UserService.class);
        Call<List<MemberCardDTO>> callFamilyMember = userService.getUserByHouseId(houseId);
        callFamilyMember.enqueue(new Callback<List<MemberCardDTO>>() {
            @Override
            public void onResponse(Call<List<MemberCardDTO>> call, Response<List<MemberCardDTO>> response) {
                initInfoFamily(response.body());
            }
            @Override
            public void onFailure(Call<List<MemberCardDTO>> call, Throwable t) {
                Toast.makeText(FamilyInformationActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void initInfoFamily(List<MemberCardDTO> listFamilyMember) {
        numberOfUser.setText(listFamilyMember.size() +" thành viên");
        MemberCardAdapter adapter = new MemberCardAdapter(this, listFamilyMember);
        familyMemberView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        familyMemberView.setLayoutManager(layoutManager);

    }
}