package com.example.apartmentcitizen.register;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.permission.Permission;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import vn.semicolon.filepicker.FilePicker;

public class RegisterImageFragment extends Fragment implements View.OnClickListener {

    final int AVATAR_REQUEST_CODE = 100;
    final int CIF_REQUEST_CODE = 101;

    Permission permission;

    String email, lastName, firstName, birthdate;
    String phone, country, job, cif, gender, relationship;

    String[] pathImageAvatar, pathImageCIF;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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

        view.findViewById(R.id.generate_qrcode_btn).setOnClickListener(this);
        view.findViewById(R.id.upload_avatar_btn).setOnClickListener(this);
        view.findViewById(R.id.upload_cif_btn).setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AVATAR_REQUEST_CODE) {
            pathImageAvatar = FilePicker.Companion.getResult(data);
        }
        if (requestCode == CIF_REQUEST_CODE) {
            pathImageCIF = FilePicker.Companion.getResult(data);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.generate_qrcode_btn:
                generateQrcode(v.getRootView());
                break;
            case R.id.upload_avatar_btn:
                permission = new Permission(getContext(), getActivity());
                permission.grantReadExternalStoratePermission();
                new FilePicker.Builder()
                        .maxSelect(1)
                        .typesOf(FilePicker.TYPE_IMAGE)
                        .start(this, AVATAR_REQUEST_CODE);
                break;
            case R.id.upload_cif_btn:
                permission = new Permission(getContext(), getActivity());
                permission.grantCameraPermission();
                new FilePicker.Builder()
                        .maxSelect(2)
                        .typesOf(FilePicker.TYPE_IMAGE)
                        .start(this, CIF_REQUEST_CODE);
                break;
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
                + getString(R.string.register_identity_card_number) + ":" + cif + "\n"
                + getString(R.string.register_gender) + ":" + gender + "\n"
                + getString(R.string.register_relationship) + ":" + relationship);
        sb.append("\n" + getString(R.string.register_avatar) + ":");
        if (pathImageAvatar != null) {
            sb.append(pathImageAvatar[0]);
        }
        sb.append("\n" + getString(R.string.register_cif) + ":");
        if (pathImageCIF != null) {
            boolean flag = false;
            for (String path : pathImageCIF) {
                if (flag) {
                    sb.append(",");
                } else {
                    flag = true;
                }
                sb.append(path);
            }
        }
        return sb.toString();
    }


}
