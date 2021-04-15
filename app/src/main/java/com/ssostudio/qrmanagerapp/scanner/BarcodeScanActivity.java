package com.ssostudio.qrmanagerapp.scanner;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.ssostudio.qrmanagerapp.R;
import com.ssostudio.qrmanagerapp.utility.AppUtility;

public class BarcodeScanActivity extends AppCompatActivity implements DecoratedBarcodeView.TorchListener, View.OnClickListener {
    private CaptureManager captureManager;
    private DecoratedBarcodeView barcodeView;
    private TextView contentTextVIew;
    private MaterialButton backButton, flashButton;
    private String result;
    private Boolean isFlashOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scan);

        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        initBarcodeScan(savedInstanceState);
        getBarcodeData();
    }

    private void initBarcodeScan(Bundle savedInstanceState){
        isFlashOn = false;
        flashButton = findViewById(R.id.flash_button);
        flashButton.setOnClickListener(this);

        if (!hasFlash()) {
            flashButton.setVisibility(View.GONE);
        }

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(this);

        barcodeView = findViewById(R.id.zxing_barcode_scanner);
        barcodeView.setTorchListener(this);

        captureManager = new CaptureManager(this, barcodeView);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.decode();
    }

    private void getBarcodeData(){
        Intent intent = getIntent();
        result = intent.getStringExtra("result");

        if (result != null && !result.equals("")){
            AppUtility.onVibrator(this, 50);
            setBarcodeDataUI();
        }
    }

    private void setBarcodeDataUI(){
        contentTextVIew = findViewById(R.id.content_text_view);
        contentTextVIew.setText(result);
        contentTextVIew.setOnClickListener(this);
    }

    public void switchFlashlight() {
        if (!isFlashOn) {
            barcodeView.setTorchOn();
        } else {
            barcodeView.setTorchOff();
        }
    }

    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    @Override
    public void onTorchOn() {
        flashButton.setIconTintResource(R.color.flashYellowColor);
        isFlashOn = true;
    }

    @Override
    public void onTorchOff() {
        flashButton.setIconTintResource(R.color.iconWhiteColor);
        isFlashOn = false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.content_text_view:
                onContentTextViewClick();
                break;
            case R.id.flash_button:
                onFlashBtnClick();
                break;
            case R.id.back_button:
                onBackBtnClick();
                break;
        }
    }

    private void onBackBtnClick() {
        finish();
    }

    private void onFlashBtnClick() {
        switchFlashlight();
    }

    private void onContentTextViewClick() {
        AppUtility.clipboardTextCopy(this, result);
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }
}
