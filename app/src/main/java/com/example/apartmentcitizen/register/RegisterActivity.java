package com.example.apartmentcitizen.register;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.apartmentcitizen.R;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import de.hdodenhof.circleimageview.CircleImageView;
import vn.semicolon.filepicker.FilePicker;

public class RegisterActivity extends AppCompatActivity {

    public final String FRAGMENT_INFO_TAG = "fragment_info";
    public final String FRAGMENT_IMAGE_TAG = "fragment_image";

    public final int AVATAR_REQUEST_CODE = 100;
    public final int CIF_REQUEST_CODE = 101;

    FragmentManager fm;
    Fragment registerInfo, registerImage;

    String email, lastName, firstName, birthdate;
    String phone, country, job, cif, gender, relationship;
    RadioGroup genderGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fm = getSupportFragmentManager();

        registerInfo = fm.findFragmentByTag(FRAGMENT_INFO_TAG);

        if (registerInfo == null) {
            registerInfo = new RegisterInfoFragment();
        }

        fm.beginTransaction().add(R.id.frame_layout_register_activity, registerInfo, FRAGMENT_INFO_TAG).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ((TextView) findViewById(R.id.edit_register_birthday)).setText(birthdate);
        ((EditText) findViewById(R.id.edit_register_email)).setText(email);
        ((EditText) findViewById(R.id.edit_register_last_name)).setText(lastName);
        ((EditText) findViewById(R.id.edit_register_first_name)).setText(firstName);
        ((EditText) findViewById(R.id.edit_register_phone)).setText(phone);
        ((EditText) findViewById(R.id.edit_register_country)).setText(country);
        ((EditText) findViewById(R.id.edit_register_job)).setText(job);
        ((EditText) findViewById(R.id.edit_register_identity_card_number)).setText(cif);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        registerImage.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case AVATAR_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    new FilePicker.Builder()
                            .maxSelect(1)
                            .typesOf(FilePicker.TYPE_IMAGE)
                            .start(this, AVATAR_REQUEST_CODE);
                break;
            case CIF_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    new FilePicker.Builder()
                            .maxSelect(1)
                            .typesOf(FilePicker.TYPE_IMAGE)
                            .start(this, CIF_REQUEST_CODE);
                break;
        }
    }

    public void clickToNextRegister(View view) {
        email = ((EditText) findViewById(R.id.edit_register_email)).getText().toString();
        birthdate = ((TextView) findViewById(R.id.edit_register_birthday)).getText().toString();
        lastName = ((EditText) findViewById(R.id.edit_register_last_name)).getText().toString();
        firstName = ((EditText) findViewById(R.id.edit_register_first_name)).getText().toString();
        phone = ((EditText) findViewById(R.id.edit_register_phone)).getText().toString();
        country = ((EditText) findViewById(R.id.edit_register_country)).getText().toString();
        job = ((EditText) findViewById(R.id.edit_register_job)).getText().toString();
        cif = ((EditText) findViewById(R.id.edit_register_identity_card_number)).getText().toString();

        genderGroup = findViewById(R.id.gender_radio_group);
        int genderId = genderGroup.getCheckedRadioButtonId();
        gender = ((RadioButton) findViewById(genderId)).getText().toString();

        relationship = (String) ((Spinner) findViewById(R.id.spinner_relationship)).getSelectedItem();

        registerImage = fm.findFragmentByTag(FRAGMENT_IMAGE_TAG);
        if (registerImage == null) {
            registerImage = new RegisterImageFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.bundle_key_email), email.trim());
        bundle.putString(getString(R.string.bundle_key_firstname), firstName.trim());
        bundle.putString(getString(R.string.bundle_key_lastname), lastName.trim());
        bundle.putString(getString(R.string.bundle_key_birthdate), birthdate);
        bundle.putString(getString(R.string.bundle_key_phone), phone.trim());
        bundle.putString(getString(R.string.bundle_key_country), country.trim());
        bundle.putString(getString(R.string.bundle_key_job), job.trim());
        bundle.putString(getString(R.string.bundle_key_cif), cif.trim());
        bundle.putString(getString(R.string.bundle_key_gender), gender.trim());
        bundle.putString(getString(R.string.bundle_key_relationship), relationship.trim());
        registerImage.setArguments(bundle);

        fm.beginTransaction()
                .replace(R.id.frame_layout_register_activity, registerImage)
                .addToBackStack(FRAGMENT_INFO_TAG)
                .commit();
    }

    public class RegisterResponse {
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
