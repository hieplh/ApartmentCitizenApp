package com.example.apartmentcitizen.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.apartmentcitizen.HomeActivity;
import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.network.LoginService;
import com.example.apartmentcitizen.network.RetrofitInstance;
import com.example.apartmentcitizen.register.RegisterActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int GOOGLE_SIGNIN_CODE = 1000;
    private final String TAG = "GOOGLE";
    private final String SUCCESS = "true";
    private final String FAIL = "false";

    Window window;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    Button sign_in_google_btn;
    ProgressBar progressBar;

    Retrofit retrofit;
    LoginService service;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(LoginActivity.this, R.color.blue1));
        window.setNavigationBarColor(ContextCompat.getColor(LoginActivity.this, R.color.purple));
        retrofit = RetrofitInstance.getRetrofitInstance();

        sharedPreferences = getSharedPreferences(getString(R.string.shared_info), MODE_PRIVATE);
        editor = sharedPreferences.edit();

        progressBar = findViewById(R.id.progress_circular);
        progressBar.setVisibility(View.GONE);
        sign_in_google_btn = findViewById(R.id.button_login_google);
        sign_in_google_btn.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        String checkedEmail = sharedPreferences.getString(getString(R.string.key_email), null);

        if (checkedEmail != null && user != null) {
            if (checkedEmail.equals(user.getEmail())) {
                updateUI(SUCCESS);
            }
        } else {
            updateUI(FAIL);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGNIN_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login_google:
                signInGoogle();
                break;
        }
    }

    public void clickToLogin(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void clickToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void signInGoogle() {
        progressBar.setVisibility(View.VISIBLE);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGNIN_CODE);
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            service = retrofit.create(LoginService.class);

                            FirebaseUser user = mAuth.getCurrentUser();

                            Call<Login> call = service.executeLogin(user.getEmail());

                            call.enqueue(new Callback<Login>() {
                                @Override
                                public void onResponse(Call<Login> call, Response<Login> response) {
                                    if (response.isSuccessful()) {
                                        Log.d("SHARED", "User: " + response.body().getHouse().getHouseId());
                                        updateSharedPreference(response.body());
                                        updateUI(SUCCESS);
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.login_error), Toast.LENGTH_LONG)
                                                .show();

                                        mAuth.signOut();
                                        mGoogleSignInClient.signOut();

                                        updateUI(FAIL);
                                    }
                                }

                                @Override
                                public void onFailure(Call<Login> call, Throwable t) {
                                    Log.d(TAG, "onFailure: " + t.getMessage());
                                    t.printStackTrace();
                                }
                            });
                        } else {
                            progressBar.setVisibility(View.GONE);
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(String response) {
        switch (response) {
            case SUCCESS:
                editor.putString(getString(R.string.key_email), response);
                editor.commit();

                startActivity(new Intent(this, HomeActivity.class));
                finish();
                break;
            case FAIL:
                progressBar.setVisibility(View.GONE);
                break;
        }
    }

    private void updateSharedPreference(Login user) {
        sharedPreferences = getSharedPreferences(getString(R.string.shared_info), MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(getString(R.string.key_email), user.getEmail());
        editor.putString(getString(R.string.key_first_name), user.getFirstName());
        editor.putString(getString(R.string.key_last_name), user.getLastName());
        editor.putString(getString(R.string.key_cif), user.getCifNumber());
        editor.putString(getString(R.string.key_phone), user.getPhoneNo());
        editor.putString(getString(R.string.key_cif_image), user.getCifImage());
        editor.putString(getString(R.string.key_profile_image), user.getProfileImage());

        editor.putString(getString(R.string.key_birthdate), user.getDateOfBirth() != null ? user.getDateOfBirth() : null);
        editor.putString(getString(R.string.key_create_date), user.getCreateDate() != null ? user.getCreateDate() : null);
        editor.putString(getString(R.string.key_last_modified), user.getLastModified() != null ? user.getLastModified() : null);

        editor.putInt(getString(R.string.key_house_id), user.getHouse().getHouseId());
        editor.putInt(getString(R.string.key_house_money), (int)user.getHouse().getCurrentMoney());
        editor.putInt(getString(R.string.key_role_id), user.getRole());
        editor.putInt(getString(R.string.key_gender), user.getGender());
        editor.putInt(getString(R.string.key_family), user.getFamilyLevel());

        editor.commit();
    }
}
