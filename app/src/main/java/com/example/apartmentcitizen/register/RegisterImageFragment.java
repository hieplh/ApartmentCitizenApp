package com.example.apartmentcitizen.register;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.image.UploadImage;
import com.example.apartmentcitizen.permission.Permission;
import com.example.apartmentcitizen.service.FirebaseService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;
import vn.semicolon.filepicker.FilePicker;

public class RegisterImageFragment extends Fragment implements View.OnClickListener {

    public final int AVATAR_REQUEST_CODE = 100;
    public final int CIF_REQUEST_CODE = 101;
    String email, lastName, firstName, birthdate;
    String phone, country, job, cif, gender, relationship;
    CircleImageView imageView;
    String[] pathImageAvatar, pathImageCIF;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email = (String) getArguments().get(getString(R.string.bundle_key_email));
        lastName = (String) getArguments().get(getString(R.string.bundle_key_lastname));
        firstName = (String) getArguments().get(getString(R.string.bundle_key_firstname));
        birthdate = (String) getArguments().get(getString(R.string.bundle_key_birthdate));
        phone = (String) getArguments().get(getString(R.string.bundle_key_phone));
        country = (String) getArguments().get(getString(R.string.bundle_key_country));
        job = (String) getArguments().get(getString(R.string.bundle_key_job));
        cif = (String) getArguments().get(getString(R.string.bundle_key_cif));
        gender = (String) getArguments().get(getString(R.string.bundle_key_gender));
        relationship = (String) getArguments().get(getString(R.string.bundle_key_relationship));

        ((TextView) view.findViewById(R.id.label_email_register)).setText(email);
        ((TextView) view.findViewById(R.id.label_fullname_register)).setText(lastName + " " + firstName);

        imageView = view.findViewById(R.id.image_profile_register);

        view.findViewById(R.id.generate_qrcode_btn).setOnClickListener(this);
        view.findViewById(R.id.upload_avatar_btn).setOnClickListener(this);
        view.findViewById(R.id.upload_cif_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        boolean flag;
        switch (v.getId()) {
            case R.id.generate_qrcode_btn:
                uploadImageForRegister();

                subscribeUploadImageFirebase();

                generateQrcode(v.getRootView());
                break;
            case R.id.upload_avatar_btn:
                flag = new Permission(getContext(), getActivity()).grantReadExternalStoratePermission(AVATAR_REQUEST_CODE);
                pickImageOnGranted(AVATAR_REQUEST_CODE, flag);
                break;
            case R.id.upload_cif_btn:
                flag = new Permission(getContext(), getActivity()).grantReadExternalStoratePermission(CIF_REQUEST_CODE);
                pickImageOnGranted(CIF_REQUEST_CODE, flag);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case AVATAR_REQUEST_CODE:
                pathImageAvatar = FilePicker.Companion.getResult(data);

                if (pathImageAvatar.length != 0) {
                    Glide.with(this)
                            .load(pathImageAvatar[0])
                            .error(getResources().getDrawable(R.drawable.image_avatar_default))
                            .override(imageView.getWidth(), imageView.getHeight())
                            .fitCenter()
                            .into(imageView);
                } else {
                    Glide.with(this)
                            .load(getResources().getDrawable(R.drawable.image_avatar_default))
                            .override(imageView.getWidth(), imageView.getHeight())
                            .fitCenter()
                            .into(imageView);
                }

                break;
            case CIF_REQUEST_CODE:
                pathImageCIF = FilePicker.Companion.getResult(data);
                break;
        }
    }

    private void pickImageOnGranted(int requestCode, boolean flag) {
        if (flag) {
            new FilePicker.Builder()
                    .maxSelect(1)
                    .typesOf(FilePicker.TYPE_IMAGE)
                    .start(this, requestCode);
        }
    }

    private void generateQrcode(View view) {
        try {
            String qr_content = generateQrcodeContent();

            ImageView imageView = view.findViewById(R.id.qrcode_image_register);
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
                + getString(R.string.register_cif) + ":" + cif + "\n"
                + getString(R.string.register_gender) + ":" + gender + "\n"
                + getString(R.string.register_relationship) + ":" + relationship);
        return sb.toString();
    }

    private void subscribeUploadImageFirebase() {
        int end;
        if (email.lastIndexOf("@gmail.com") != -1) {
            end = email.lastIndexOf("@gmail.com");
        } else {
            end = email.lastIndexOf("@fpt.edu.vn");
        }

        FirebaseMessaging.getInstance().subscribeToTopic(email.substring(0, end))
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

    private void uploadImageForRegister() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getString(R.string.shared_register), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (pathImageAvatar != null && pathImageAvatar.length != 0) {
            editor.putString(getString(R.string.key_register_email), email);
            editor.putString(getString(R.string.key_register_avatar), UploadImage.PROFILE);
            editor.putString(getString(R.string.key_register_avatar_path), pathImageAvatar[0]);
        }

        if (pathImageCIF != null && pathImageCIF.length != 0) {
            editor.putString(getString(R.string.key_register_email), email);
            editor.putString(getString(R.string.key_register_cif), UploadImage.CIF);
            editor.putString(getString(R.string.key_register_cif_path), pathImageCIF[0]);
        }

        editor.commit();
    }
}