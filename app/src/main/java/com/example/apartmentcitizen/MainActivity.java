package com.example.apartmentcitizen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.apartmentcitizen.component.DecorationRecyclerview;
import com.example.apartmentcitizen.component.LoadImageAdapter;
import com.example.apartmentcitizen.image.LoadImage;
import com.example.apartmentcitizen.login.LoginActivity;
import com.example.apartmentcitizen.network.PostImageService;
import com.example.apartmentcitizen.network.RetrofitInstance;
import com.example.apartmentcitizen.network.UploadMultipartImageService;
import com.example.apartmentcitizen.service.FirebaseService;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.semicolon.filepicker.FilePicker;

public class MainActivity extends AppCompatActivity {

    public final int BILL_HOUSE = 1;

    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    String[] pathImages;

    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.btnLogoutGg);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();

                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                finish();
                            }
                        });
            }
        });

        subscribeTopics(BILL_HOUSE);

        recyclerView = findViewById(R.id.image_recyclerview_main_activity);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new DecorationRecyclerview(20));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        retrofit = RetrofitInstance.getRetrofitInstance();

        PostImageService loadImageService = retrofit.create(PostImageService.class);
        Call<LoadImage> call = loadImageService.getPathImage();
        call.enqueue(new Callback<LoadImage>() {
            @Override
            public void onResponse(Call<LoadImage> call, Response<LoadImage> response) {
                ArrayList<String> list = initPathImageList(response.body().getPath());
                adapter = new LoadImageAdapter(MainActivity.this, list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<LoadImage> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Load image failed!", Toast.LENGTH_SHORT).show();
            }
        });

        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            pathImages = FilePicker.Companion.getResult(data);

            for (String file : pathImages) {
                uploadImageToServer(file);
            }
        }

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            Log.d("QR", "Content: " + result.getContents());
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadImage(View view) {
        grantPermission();

        new FilePicker.Builder()
                .maxSelect(20)
                .typesOf(FilePicker.TYPE_IMAGE)
                .start(this, 100);
    }

    public void generateQrcodeMainActivity(View view) {
        try {
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.encodeBitmap("Hello World", BarcodeFormat.QR_CODE, 400, 400);
            ((ImageView) findViewById(R.id.qrcode_image_main_activity)).setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scanQrcode(View view) {
        grantPermission();

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    private void subscribeTopics(int topic) {
        switch (topic) {
            case BILL_HOUSE:
                FirebaseMessaging.getInstance().subscribeToTopic(getString(R.string.register_house_topic))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(FirebaseService.TAG, "subscribe Topic: subscribe success");
                                } else {
                                    Log.d(FirebaseService.TAG, "subscribe Topic: subscribe failed");
                                }
                            }
                        });
        }
    }

    private void uploadImageToServer(String filePath) {
        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();

        UploadMultipartImageService upload = retrofit.create(UploadMultipartImageService.class);

        File file = new File(filePath);

        RequestBody fileRequestBody = RequestBody.create(MediaType.parse("image/*"), file);

        MultipartBody.Part part = MultipartBody.Part.createFormData("upload", file.getName(), fileRequestBody);

        RequestBody desc = RequestBody.create(MediaType.parse("text/plain"), "image-type");

        Call call = upload.uploadImage(part, desc);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Toast.makeText(MainActivity.this, response.code() + " - " + response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadImageFromServer(View view) {
        PostImageService loadImageService = retrofit.create(PostImageService.class);
        Call<LoadImage> call = loadImageService.getPathImage();
        call.enqueue(new Callback<LoadImage>() {
            @Override
            public void onResponse(Call<LoadImage> call, Response<LoadImage> response) {
                ArrayList<String> list = initPathImageList(response.body().getPath());
                adapter = new LoadImageAdapter(MainActivity.this, list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<LoadImage> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Load image failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void grantPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1000);
        }

    }

    private ArrayList<String> initPathImageList(String[] path) {
        ArrayList<String> list = new ArrayList<>();
        for (String x : path) {
            list.add(x);
        }
        return list;
    }
}
