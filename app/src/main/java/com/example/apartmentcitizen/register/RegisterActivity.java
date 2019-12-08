package com.example.apartmentcitizen.register;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.apartmentcitizen.R;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class RegisterActivity extends AppCompatActivity {

    public final String FRAGMENT_INFO_TAG = "fragment_info";
    public final String FRAGMENT_IMAGE_TAG = "fragment_image";

    FragmentManager fm;
    Fragment registerInfo, registerImage;

    String email, lastName, firstName, birthdate;
    String phone, country, job, icn, gender, relationship;
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
        ((EditText) findViewById(R.id.edit_register_identity_card_number)).setText(icn);
    }

    public void clickToNextRegister(View view) {
        birthdate = ((TextView) findViewById(R.id.edit_register_birthday)).getText().toString();
        email = ((EditText) findViewById(R.id.edit_register_email)).getText().toString();
        lastName = ((EditText) findViewById(R.id.edit_register_last_name)).getText().toString();
        firstName = ((EditText) findViewById(R.id.edit_register_first_name)).getText().toString();
        phone = ((EditText) findViewById(R.id.edit_register_phone)).getText().toString();
        country = ((EditText) findViewById(R.id.edit_register_country)).getText().toString();
        job = ((EditText) findViewById(R.id.edit_register_job)).getText().toString();
        icn = ((EditText) findViewById(R.id.edit_register_identity_card_number)).getText().toString();

        genderGroup = findViewById(R.id.gender_radio_group);
        int genderId = genderGroup.getCheckedRadioButtonId();
        gender = ((RadioButton) findViewById(genderId)).getText().toString();

        relationship = (String) ((Spinner) findViewById(R.id.spinner_relationship)).getSelectedItem();

        registerImage = fm.findFragmentByTag(FRAGMENT_IMAGE_TAG);
        if (registerImage == null) {
            registerImage = new RegisterImageFragment();
        }

        fm.beginTransaction()
                .replace(R.id.frame_layout_register_activity, registerImage)
                .addToBackStack(FRAGMENT_INFO_TAG)
                .commit();
    }

    public void generateQrcode(View view) {
        try {
            String qr_content = generateQrcodeContent();

            ImageView imageView = findViewById(R.id.qrcode_image_register);
            int height = imageView.getHeight() >= 500 ? 500 : imageView.getHeight();
            int width = height;


            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.encodeBitmap(qr_content, BarcodeFormat.QR_CODE, width, height);
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateQrcodeContent() {
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.register_email) + ":" + email + "\n"
                + getString(R.string.register_first_name) + ":" + firstName + "\n"
                + getString(R.string.register_last_name) + ":" + lastName + "\n"
                + getString(R.string.register_phone) + ":" + phone + "\n"
                + getString(R.string.register_birthdate) + ":" + birthdate + "\n"
                + getString(R.string.register_country) + ":" + country + "\n"
                + getString(R.string.register_job) + ":" + job + "\n"
                + getString(R.string.register_identity_card_number) + ":" + icn + "\n"
                + getString(R.string.register_gender) + ":" + gender + "\n"
                + getString(R.string.register_gender) + ":" + relationship);
        Log.d("QRCODE", "QRCODE: " + sb.toString());
        return sb.toString();
    }
}
