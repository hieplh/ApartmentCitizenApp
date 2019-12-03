package com.example.apartmentcitizen.register;

import android.os.Bundle;

import com.example.apartmentcitizen.R;
import com.google.zxing.BarcodeFormat;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CaptureQRcodeActivity extends AppCompatActivity {

    ZXingScannerView qrCodeScanner;
    List<BarcodeFormat> mListBarcodeFormat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_qrcode);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Scan Barcode");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        qrCodeScanner = findViewById(R.id.qrCodeScanner);
        mListBarcodeFormat.add(BarcodeFormat.QR_CODE);

        setScannerProperties();
        qrCodeScanner.startCamera();
//        qrCodeScanner.setResultHandler(this);
    }

    private void setScannerProperties() {
        qrCodeScanner.setFormats(mListBarcodeFormat);
        qrCodeScanner.setAutoFocus(true);
        qrCodeScanner.setLaserColor(R.color.colorAccent);
        qrCodeScanner.setMaskColor(R.color.colorAccent);
    }
}
