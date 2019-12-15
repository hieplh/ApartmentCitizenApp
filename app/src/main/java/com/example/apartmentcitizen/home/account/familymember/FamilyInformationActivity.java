package com.example.apartmentcitizen.home.account.familymember;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.component.MemberCardAdapter;
import com.example.apartmentcitizen.network.RetrofitInstance;
import com.example.apartmentcitizen.network.UserService;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FamilyInformationActivity extends AppCompatActivity {

    Retrofit retrofit;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView title, houseName, numberOfUser, block, floor;
    RecyclerView familyMemberView;
    SharedPreferences sharedPreferences;

    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_information);

        initView();

        setUpView();
    }

    public void setUpView() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initListInfoFamily(sharedPreferences.getInt(getString(R.string.key_house_id), 0));

                swipeRefreshLayout.setRefreshing(false);
            }
        });

        title.setText(R.string.member_family_activity_title);

        houseName.setText(sharedPreferences.getString(getString(R.string.key_house_name), ""));
        String[] houseInfo = houseName.getText().toString().split("-");

        block.setText(houseInfo[0]);

        floor.setText(houseInfo[1]);

        initListInfoFamily(sharedPreferences.getInt(getString(R.string.key_house_id), 0));
    }

    private void initView() {
        retrofit = RetrofitInstance.getRetrofitInstance();
        sharedPreferences = getSharedPreferences(getString(R.string.shared_info), MODE_PRIVATE);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_family_information_activity);
        title = findViewById(R.id.label_title_standard);
        familyMemberView = findViewById(R.id.list_family_member);
        houseName = findViewById(R.id.txtHouseName);
        numberOfUser = findViewById(R.id.txtNumberOfUser);
        block = findViewById(R.id.txtBlock);
        floor = findViewById(R.id.txtFloor);
    }

    private boolean initListInfoFamily(int houseId) {
        UserService userService = retrofit.create(UserService.class);
        Call<List<MemberCardDTO>> callFamilyMember = userService.getUserByHouseId(houseId);
        callFamilyMember.enqueue(new Callback<List<MemberCardDTO>>() {
            @Override
            public void onResponse(Call<List<MemberCardDTO>> call, Response<List<MemberCardDTO>> response) {
                initInfoFamily(response.body());
                flag = true;
            }

            @Override
            public void onFailure(Call<List<MemberCardDTO>> call, Throwable t) {
                Toast.makeText(FamilyInformationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return flag;
    }

    private void initInfoFamily(List<MemberCardDTO> listFamilyMember) {
        numberOfUser.setText(listFamilyMember.size() + " thành viên");

        familyMemberView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        familyMemberView.setAdapter(new MemberCardAdapter(this, listFamilyMember));
    }

}