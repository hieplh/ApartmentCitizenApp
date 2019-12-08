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

    View header;
    TextView birthdate, title;
    EditText email, lastName, firstName;
    EditText phone, country, job, icn;
    RadioGroup genderGroup;
    RadioButton gender;
    Spinner relationship;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setUpView();
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
    }

    public void clickToCreateDialogBirthdate(View view) {
        BirthdateListener birthdateListener = new RegisterInfoFragment();
        birthdateListener.initBirthdateDialog(view);
    }

    public void setUpView(){
        header = findViewById(R.id.header_standard);
        title = header.findViewById(R.id.label_title_standard);
        title.setText(R.string.register_title);
    }

    public void clickToNextRegister(View view) {
        birthdate = findViewById(R.id.edit_register_birthday);
        email = findViewById(R.id.edit_register_email);
        lastName = findViewById(R.id.edit_register_last_name);
        firstName = findViewById(R.id.edit_register_first_name);
        phone = findViewById(R.id.edit_register_phone);
        country = findViewById(R.id.edit_register_country);
        job = findViewById(R.id.edit_register_job);
        icn = findViewById(R.id.edit_register_identity_card_number);

        genderGroup = findViewById(R.id.gender_radio_group);
        int genderId = genderGroup.getCheckedRadioButtonId();
        gender = findViewById(genderId);

        relationship = findViewById(R.id.spinner_relationship);

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
        sb.append(getString(R.string.register_email) + ":" + email.getText().toString() + "\n"
                + getString(R.string.register_first_name) + ":" + firstName.getText().toString() + "\n"
                + getString(R.string.register_last_name) + ":" + lastName.getText().toString() + "\n"
                + getString(R.string.register_phone) + ":" + phone.getText().toString() + "\n"
                + getString(R.string.register_birthdate) + ":" + birthdate.getText().toString() + "\n"
                + getString(R.string.register_country) + ":" + country.getText().toString() + "\n"
                + getString(R.string.register_job) + ":" + job.getText().toString() + "\n"
                + getString(R.string.register_identity_card_number) + ":" + icn.getText().toString() + "\n"
                + getString(R.string.register_gender) + ":" + gender.getText().toString() + "\n"
                + getString(R.string.register_gender) + ":" + relationship.getSelectedItem());
        Log.d("QRCODE", "QRCODE: " + sb.toString());
        return sb.toString();
    }
}
