package com.example.apartmentcitizen.home.account.information;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.image.UploadImage;
import com.example.apartmentcitizen.network.RetrofitInstance;
import com.example.apartmentcitizen.network.UserService;
import com.example.apartmentcitizen.permission.Permission;
import com.example.apartmentcitizen.register.HouseRegister;
import com.example.apartmentcitizen.register.RoleRegister;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.semicolon.filepicker.FilePicker;

public class InformationActivity extends AppCompatActivity {

    public final int OWNER = 0;
    public final int PARTNER = 1;
    public final int PARENT = 2;
    public final int GRANDPA = 3;
    public final int SIBLING = 4;
    public final int UNCLE = 5;
    public final int CHILDREN = 6;
    public final int GRANDCHILDREN = 7;
    public final int FRIEND = 8;
    public final int AVATAR_REQUEST_CODE = 100;
    final String OWNER_String = "Chủ hộ";
    final String PARTNER_String = "Vợ/Chồng";
    final String PARENT_String = "Bố/Mẹ";
    final String GRANDPA_String = "Ông/Bà";
    final String SIBLING_String = "Anh/Chị/Em";
    final String UNCLE_String = "Cô/Chú";
    final String CHILDREN_String = "Con trai/Con gái";
    final String GRANDCHILDREN_String = "Cháu trai/cháu gái";
    final String FRIEND_String = "Bạn Bè";
    private final int DAY_OF_MONTH_INDEX = 0;
    private final int MONTH_INDEX = 1;
    private final int YEAR_INDEX = 2;

    TextView title;
    TextView fullname, houseName, owner, relationshipOwner, birthdate;
    EditText email, cif, career, domicile, phone;
    RadioGroup radioGroupGender;
    RadioButton gender;
    Button pickAvatar;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    InputMethodManager imm;

    String[] pathImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        init();

        setUpView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AVATAR_REQUEST_CODE) {
            pathImage = FilePicker.Companion.getResult(data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Permission.READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new FilePicker.Builder()
                        .maxSelect(1)
                        .typesOf(FilePicker.TYPE_IMAGE)
                        .start(this, AVATAR_REQUEST_CODE);
            }
        }
    }

    public void updateInfoAvatar() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Permission.READ_EXTERNAL_STORAGE);
        } else {
            new FilePicker.Builder()
                    .maxSelect(1)
                    .typesOf(FilePicker.TYPE_IMAGE)
                    .start(this, AVATAR_REQUEST_CODE);
        }
    }

    public void exitInfoForm(View view) {
        finish();
    }

    public void updateInfoUser(View view) {
        updateInfoDb();

        if (pathImage != null) {
            uploadImage();
        }

        updateInfoStatic();
    }

    private void init() {
        title = findViewById(R.id.label_title_standard);

        fullname = findViewById(R.id.label_fullname_info);
        houseName = findViewById(R.id.label_housename_info);
        owner = findViewById(R.id.label_owner_info);
        relationshipOwner = findViewById(R.id.label_relationship_with_owner_info);

        birthdate = findViewById(R.id.edit_birth_date_info);
        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDateOfBirth(((TextView) v).getText().toString());
            }
        });

        radioGroupGender = findViewById(R.id.gender_radio_group_info);

        email = findViewById(R.id.edit_email_info);

        cif = findViewById(R.id.edit_cif_info);
        cif.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return handleActionNext(actionId);
            }
        });

        career = findViewById(R.id.edit_text_job_info);
        domicile = findViewById(R.id.edit_domicile_info);
        phone = findViewById(R.id.edit_phone_number_info);

        sharedPreferences = getSharedPreferences(getString(R.string.shared_info), MODE_PRIVATE);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        pickAvatar = findViewById(R.id.update_avatar_btn);
        pickAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfoAvatar();
            }
        });
    }

    private void setUpView() {
        title.setText(R.string.information_activity_title);

        StringBuilder sb = new StringBuilder();
        sb.append(sharedPreferences.getString(getString(R.string.key_last_name), ""));
        if (!sharedPreferences.getString(getString(R.string.key_last_name), "").equals("")) {
            sb.append(" ");
        }
        sb.append(sharedPreferences.getString(getString(R.string.key_first_name), ""));
        fullname.setText(sb.toString());

        houseName.setText(sharedPreferences.getString(getString(R.string.key_house_name), ""));

        int familyLevel = sharedPreferences.getInt(getString(R.string.key_family), -1);
        switch (familyLevel) {
            case OWNER:
                owner.setText(OWNER_String);
                relationshipOwner.setText("");
                break;
            case PARTNER:
                owner.setText("");
                relationshipOwner.setText(PARTNER_String);
                break;
            case PARENT:
                owner.setText("");
                relationshipOwner.setText(PARENT_String);
                break;
            case GRANDPA:
                owner.setText("");
                relationshipOwner.setText(GRANDPA_String);
                break;
            case SIBLING:
                owner.setText("");
                relationshipOwner.setText(SIBLING_String);
                break;
            case UNCLE:
                owner.setText("");
                relationshipOwner.setText(UNCLE_String);
                break;
            case CHILDREN:
                owner.setText("");
                relationshipOwner.setText(CHILDREN_String);
                break;
            case GRANDCHILDREN:
                owner.setText("");
                relationshipOwner.setText(GRANDCHILDREN_String);
                break;
            case FRIEND:
                owner.setText("");
                relationshipOwner.setText(FRIEND_String);
                break;
            default:
                owner.setText("Không rõ");
                relationshipOwner.setText("Không rõ");
                break;
        }

        birthdate.setText(sharedPreferences.getString(getString(R.string.key_birthdate), "01/01/1970"));

        if (sharedPreferences.getInt(getString(R.string.key_gender), 1) == 1) {
            gender = findViewById(R.id.male_radio_info);
        } else {
            gender = findViewById(R.id.female_radio_info);
        }
        gender.setSelected(true);

        email.setText(sharedPreferences.getString(getString(R.string.key_email), ""));
        cif.setText(sharedPreferences.getString(getString(R.string.key_cif), ""));
        career.setText(sharedPreferences.getString(getString(R.string.key_job), ""));
        domicile.setText(sharedPreferences.getString(getString(R.string.key_home), ""));
        phone.setText(sharedPreferences.getString(getString(R.string.key_phone), ""));
    }

    private int splitStringDate(String date, int index) {
        return Integer.parseInt(date.split("/")[index]);
    }

    private long getPastMilisecondsFromNow(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 31);
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - year);
        return cal.getTimeInMillis();
    }

    private void chooseDateOfBirth(String date) {
        final Calendar calendar = Calendar.getInstance();
        int curDay, curMonth, curYear;

        if (date.isEmpty()) {
            curDay = calendar.get(Calendar.DATE);
            curMonth = calendar.get(Calendar.MONTH);
            curYear = calendar.get(Calendar.YEAR);
        } else {
            curDay = splitStringDate(date, DAY_OF_MONTH_INDEX);
            curMonth = splitStringDate(date, MONTH_INDEX) - 1;
            curYear = splitStringDate(date, YEAR_INDEX);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                birthdate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, curYear, curMonth, curDay);

        datePickerDialog.getDatePicker().setMaxDate(getPastMilisecondsFromNow(16));
        datePickerDialog.getDatePicker().setMinDate(getPastMilisecondsFromNow(720));
        datePickerDialog.show();
    }

    private boolean handleActionNext(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            imm.hideSoftInputFromWindow(cif.getWindowToken(), 0);
            chooseDateOfBirth(birthdate.getText().toString());
            return true;
        }
        return false;
    }

    private void updateInfoStatic() {
        editor = sharedPreferences.edit();

        editor.putString(getString(R.string.key_email), email.getText().toString());
        editor.putString(getString(R.string.key_cif), cif.getText().toString());
        editor.putString(getString(R.string.key_phone), phone.getText().toString());
        editor.putString(getString(R.string.key_birthdate), birthdate.getText().toString());
        editor.putString(getString(R.string.key_home), domicile.getText().toString());
        editor.putString(getString(R.string.key_job), career.getText().toString());

        if (pathImage != null) {
            File file = new File(pathImage[0]);
            editor.putString(getString(R.string.key_profile_image), file.getName());
        }

        gender = findViewById(radioGroupGender.getCheckedRadioButtonId());
        editor.putInt(getString(R.string.key_gender), gender.getText().toString().equalsIgnoreCase("Nam") ? 1 : 0);

        editor.commit();
    }

    private void updateInfoDb() {
        Information information = new Information();

        information.setEmail(email.getText().toString());
        information.setPhoneNo(phone.getText().toString());
        information.setRole(new RoleRegister(4));
        information.setHouse(new HouseRegister(sharedPreferences.getInt(getString(R.string.key_house_id), 0)));

        String[] tmpBirthDate = birthdate.getText().toString().split("/");
        information.setDateOfBirth(tmpBirthDate[2] + "-" + tmpBirthDate[1] + "-" + tmpBirthDate[0]);

        information.setCifNumber(cif.getText().toString());
        information.setGender(gender.getText().toString().equalsIgnoreCase("Nam") ? 1 : 0);
        information.setJob(career.getText().toString());
        information.setHomeTown(domicile.getText().toString());
        information.setFirstName(sharedPreferences.getString(getString(R.string.key_first_name), ""));
        information.setLastName(sharedPreferences.getString(getString(R.string.key_last_name), ""));
        information.setFamilyLevel(sharedPreferences.getInt(getString(R.string.key_family), 8));
        information.setStatus(1);

        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
        UserService service = retrofit.create(UserService.class);

        Call<Information> call = service
                .updateUser(sharedPreferences.getInt(getString(R.string.key_user_id), 0), information);
        call.enqueue(new Callback<Information>() {
            @Override
            public void onResponse(Call<Information> call, Response<Information> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(InformationActivity.this, getString(R.string.update_success), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InformationActivity.this, getString(R.string.update_data_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Information> call, Throwable t) {
                Toast.makeText(InformationActivity.this, getString(R.string.update_network_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImage() {
        UploadImage uploadImage = new UploadImage();
        uploadImage.uploadImageToServer(email.getText().toString(), pathImage[0], UploadImage.PROFILE);
    }
}
