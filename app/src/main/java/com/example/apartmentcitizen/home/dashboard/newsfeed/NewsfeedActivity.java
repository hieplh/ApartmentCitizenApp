package com.example.apartmentcitizen.home.dashboard.newsfeed;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.component.PostAdapter;
import com.example.apartmentcitizen.network.LoadPostService;
import com.example.apartmentcitizen.network.RetrofitInstance;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewsfeedActivity extends AppCompatActivity {

    Retrofit retrofit;
    RecyclerView recyclerView;
    TextView title;
    List<PostDTO> listPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);

        retrofit = RetrofitInstance.getRetrofitInstance();

        setUpView();
    }


    public void setUpView() {
        //set up title
        title = findViewById(R.id.label_title_standard);
        title.setText(R.string.title_newsfeed);

        recyclerView = findViewById(R.id.list_newsfeed);

        //pull api into list
        LoadPostService loadPostService = retrofit.create(LoadPostService.class);
        Call<List<PostDTO>> callPost = loadPostService.getPost();
        callPost.enqueue(new Callback<List<PostDTO>>() {
                             @Override
                             public void onResponse(Call<List<PostDTO>> call, Response<List<PostDTO>> response) {

                                 listPost = response.body();
                                 PostAdapter adapter = new PostAdapter(NewsfeedActivity.this, listPost);
                                 recyclerView.setAdapter(adapter);
                                 LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
                                 recyclerView.setLayoutManager(layoutManager);

                                 initPost(response.body());
                             }
                             @Override
                             public void onFailure(Call<List<PostDTO>> call, Throwable t) {
                                 Toast.makeText(NewsfeedActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                             }
                         }
        );
    }

    public void initPost(List<PostDTO> list) {
        PostAdapter adapter = new PostAdapter(NewsfeedActivity.this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}

//class PostAdapter extends RecyclerView.Adapter
