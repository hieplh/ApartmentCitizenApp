package com.example.apartmentcitizen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apartmentcitizen.home.account.AccountFragment;
import com.example.apartmentcitizen.home.dashboard.DashboardFragment;
import com.example.apartmentcitizen.home.notification.NotificationFragment;
import com.example.apartmentcitizen.home.transaction.TransactionFragment;
import com.example.apartmentcitizen.login.LoginActivity;
import com.example.apartmentcitizen.network.RetrofitInstance;
import com.example.apartmentcitizen.network.UserService;
import com.example.apartmentcitizen.permission.Permission;
import com.example.apartmentcitizen.register.HouseRegister;
import com.example.apartmentcitizen.register.Register;
import com.example.apartmentcitizen.register.RegisterActivity;
import com.example.apartmentcitizen.register.RoleRegister;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.StringTokenizer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class HomeActivity extends AppCompatActivity {

    final int ACCOUNT = R.id.navAcc;
    final int DASHBOARD = R.id.navDashBoard;
    final int NOTIFICATION = R.id.navNoti;
    final int TRANSACTION = R.id.navTrans;

    final String ACCOUNT_TAG = "ACCOUNT";
    final String DASHBOARD_TAG = "DASHBOARD";
    final String NOTIFICATION_TAG = "NOTIFICATION";
    final String TRANSACTION_TAG = "TRANSACTION";

    final String EMAIL = "email";
    final String CIF = "identity_card_number";
    final String AVATAR_PATH = "avatar_path_image";
    final String RELATIONSHIP = "relationship";
    final String GENDER = "gender";
    final String CIF_PATH = "cif_path_image";
    final String JOB = "job";
    final String COUNTRY = "country";
    final String BIRTHDATE = "birthdate";
    final String PHONE = "phone";
    final String LASTNAME = "lastname";
    final String FIRSTNAME = "firstname";

    boolean doubleBackToExitPressedOnce = false;
    Button btnLogOut;
    BottomNavigationView bottomNavigationView;
    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    TextView lbTitle;
    RelativeLayout headerHome;
    Window window;

    FragmentManager fm;
    Fragment account, dashboard, notification, transaction;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fm = getSupportFragmentManager();
        sharedPreferences = getSharedPreferences(getString(R.string.shared_info), Context.MODE_PRIVATE);

        initView();

        setupView();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            Log.d("QR", "onActivityResult: " + result.getContents());
            if (result.getContents() == null) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            } else {
                Register user = parseUser(result.getContents());
                registerNewUser(user);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Permission.CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void logoutAccount(View view) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        mAuth.signOut();

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(HomeActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        finish();
                    }
                });
    }

    private void initView() {
        window = getWindow();

        headerHome = findViewById(R.id.header_home);

        lbTitle = findViewById(R.id.label_title);

        btnLogOut = findViewById(R.id.button_log_out);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.navDashBoard);

        lbTitle.setText(R.string.home_label_nav_dashboard);

        lbTitle.setTextColor(Color.WHITE);

        headerHome.setBackgroundResource(R.color.blue1);

        window.setStatusBarColor(ContextCompat.getColor(HomeActivity.this, R.color.blue1));

        btnLogOut.setVisibility(View.INVISIBLE);

        loadFragment(new DashboardFragment(), DASHBOARD);


    }

    //set up View
    private void setupView() {
        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case TRANSACTION:
                        lbTitle.setText(R.string.home_label_nav_transaction);
                        lbTitle.setTextColor(Color.BLACK);
                        headerHome.setBackgroundResource(R.color.gray_reg_frame);
                        window.setStatusBarColor(ContextCompat.getColor(HomeActivity.this, R.color.gray_reg_frame));
                        btnLogOut.setVisibility(View.INVISIBLE);

                        loadFragment(transaction, TRANSACTION);
                        return true;
                    case DASHBOARD:
                        window.setStatusBarColor(ContextCompat.getColor(HomeActivity.this, R.color.blue1));
                        lbTitle.setText(R.string.home_label_nav_dashboard);
                        lbTitle.setTextColor(Color.WHITE);
                        headerHome.setBackgroundResource(R.color.blue1);
                        btnLogOut.setVisibility(View.INVISIBLE);

                        loadFragment(dashboard, DASHBOARD);
                        return true;
                    case NOTIFICATION:
                        lbTitle.setText(R.string.home_label_nav_notification);
                        lbTitle.setTextColor(Color.BLACK);
                        headerHome.setBackgroundResource(R.color.gray_reg_frame);
                        window.setStatusBarColor(ContextCompat.getColor(HomeActivity.this, R.color.gray_reg_frame));
                        btnLogOut.setVisibility(View.INVISIBLE);

                        loadFragment(notification, NOTIFICATION);
                        return true;
                    case ACCOUNT:
                        lbTitle.setText(R.string.home_label_nav_me);
                        lbTitle.setTextColor(Color.BLACK);
                        headerHome.setBackgroundResource(R.color.gray_reg_frame);
                        window.setStatusBarColor(ContextCompat.getColor(HomeActivity.this, R.color.gray_reg_frame));
                        btnLogOut.setVisibility(View.VISIBLE);

                        loadFragment(account, ACCOUNT);
                        return true;
                }
                return false;
            }
        };
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void loadFragment(Fragment fragment, int type) {
        switch (type) {
            case ACCOUNT:
                fragment = fm.findFragmentByTag(ACCOUNT_TAG);
                if (fragment == null) {
                    fragment = new AccountFragment();
                }
                break;
            case DASHBOARD:
                fragment = fm.findFragmentByTag(DASHBOARD_TAG);
                if (fragment == null) {
                    fragment = new DashboardFragment();
                }
                break;
            case NOTIFICATION:
                fragment = fm.findFragmentByTag(NOTIFICATION_TAG);
                if (fragment == null) {
                    fragment = new NotificationFragment();
                }
                break;
            case TRANSACTION:
                fragment = fm.findFragmentByTag(TRANSACTION_TAG);
                if (fragment == null) {
                    fragment = new TransactionFragment();
                }
                break;
        }

        fm.beginTransaction()
                .replace(R.id.frame_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private Register parseUser(String user) {
        Register register = new Register();
        StringTokenizer stk = new StringTokenizer(user, "\n");
        while (stk.hasMoreTokens()) {
            String[] tmp = stk.nextToken().split(":");
            switch (tmp[0]) {
                case EMAIL:
                    if (tmp.length == 2) register.setEmail(tmp[1]);
                    break;
                case FIRSTNAME:
                    if (tmp.length == 2) register.setFirstName(tmp[1]);
                    break;
                case LASTNAME:
                    if (tmp.length == 2) register.setLastName(tmp[1]);
                    break;
                case PHONE:
                    if (tmp.length == 2) register.setPhoneNo(tmp[1]);
                    break;
                case BIRTHDATE:
                    if (tmp.length == 2) {
                        String[] tmpBirthDate = tmp[1].split("/");
                        register.setDateOfBirth(tmpBirthDate[2] + "-" + tmpBirthDate[1] + "-" + tmpBirthDate[0]);
                    }
                    break;
                case COUNTRY:
                    if (tmp.length == 2) register.setHomeTown(tmp[1]);
                    break;
                case JOB:
                    if (tmp.length == 2) register.setJob(tmp[1]);
                    break;
                case CIF:
                    if (tmp.length == 2) register.setCifNumber(tmp[1]);
                    break;
                case GENDER:
                    if (tmp.length == 2) register.setGender(tmp[1].equals("Nam") ? 1 : 0);
                    break;
                case RELATIONSHIP:
                    if (tmp.length == 2) register.setFamilyLevel(1);
                    break;
                case AVATAR_PATH:
                    if (tmp.length == 2) register.setProfileImage(tmp[1]);
                    break;
                case CIF_PATH:
                    if (tmp.length == 2) register.setCifImage(tmp[1]);
                    break;
            }
        }
        register.setRole(new RoleRegister(4));
        register.setStatus(1);
        register.setHouse(new HouseRegister(sharedPreferences.getInt(getString(R.string.key_house_id), -1)));
        return register;
    }

    private void registerNewUser(final Register user) {
        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
        UserService service = retrofit.create(UserService.class);
        Call<RegisterActivity.RegisterResponse> call = service.createUser(user);
        call.enqueue(new Callback<RegisterActivity.RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterActivity.RegisterResponse> call, Response<RegisterActivity.RegisterResponse> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(HomeActivity.this, getString(R.string.register_success), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(HomeActivity.this, getString(R.string.register_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterActivity.RegisterResponse> call, Throwable t) {
                Toast.makeText(HomeActivity.this, getString(R.string.register_error), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
