package com.example.apartmentcitizen.home.dashboard.newsfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.component.CommentAdapter;
import com.example.apartmentcitizen.network.CommentService;
import com.example.apartmentcitizen.network.RetrofitInstance;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CommmentActivity extends AppCompatActivity {

    Button btnSaveComment;
    EditText editComment;
    Retrofit retrofit;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commment);
        retrofit = RetrofitInstance.getRetrofitInstance();
        sharedPreferences = getSharedPreferences(getString(R.string.shared_info), MODE_PRIVATE);
        setUpView();
    }

    private void setUpView() {
        Intent intent = this.getIntent();
        final int postId = intent.getIntExtra("postId", 0);
        btnSaveComment = findViewById(R.id.btnSaveComment);
        editComment = findViewById(R.id.editComment);
        recyclerView = findViewById(R.id.listComment);
        final CommentService commentService = retrofit.create(CommentService.class);
        final Call<List<CommentDTO>> callComment = commentService.getCommentByPostId(postId);
        callComment.enqueue(new Callback<List<CommentDTO>>() {
            @Override
            public void onResponse(Call<List<CommentDTO>> call, Response<List<CommentDTO>> response) {
                List<CommentDTO> commentDTOList = response.body();
                initComment(commentDTOList);
            }
            @Override
            public void onFailure(Call<List<CommentDTO>> call, Throwable t) {
                Toast.makeText(CommmentActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        btnSaveComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userId = sharedPreferences.getInt(getString(R.string.key_user_id), 0);
                String commentDetail = editComment.getText().toString();
                System.out.println(commentDetail);
                UserDTO userDTO = new UserDTO();
                userDTO.setUserId(userId);
                PostDTO postDTO = new PostDTO();
                postDTO.setPostId(postId);
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setUser(userDTO);
                commentDTO.setDetail(commentDetail);
                commentDTO.setPost(postDTO);
                Call<CommentResponse> saveCommentCall = commentService.createComment(commentDTO);
                saveCommentCall.enqueue(new Callback<CommentResponse>() {
                    @Override
                    public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                        if (response.body().getSuccess().equals("true")) {
                            setUpView();
                        }
                    }

                    @Override
                    public void onFailure(Call<CommentResponse> call, Throwable t) {
                        Toast.makeText(CommmentActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void initComment(List<CommentDTO> commentDTOS) {
        System.out.println(commentDTOS.size());
        CommentAdapter commentAdapter = new CommentAdapter(CommmentActivity.this, commentDTOS);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(commentAdapter);
    }

    public class CommentResponse {
        @SerializedName("success")
        private String success;

        @SerializedName("message")
        private String message;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
