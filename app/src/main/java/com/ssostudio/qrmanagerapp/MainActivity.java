package com.ssostudio.qrmanagerapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.ssostudio.qrmanagerapp.model.BarcodeResultModel;
import com.ssostudio.qrmanagerapp.scanner.BarcodeScanActivity;
import com.ssostudio.qrmanagerapp.utility.BarcodeUtility;
import com.ssostudio.qrmanagerapp.utility.ImageUtility;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MaterialButton barcodeScanBtn, imageScanBtn;
    private IntentIntegrator integrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        setQrScanBtn();
        setImageScanBtn();
    }

    private void setQrScanBtn() {
        barcodeScanBtn = findViewById(R.id.barcode_scan_button);
        barcodeScanBtn.setOnClickListener(this);
    }

    private void setImageScanBtn() {
        imageScanBtn = findViewById(R.id.image_scan_button);
        imageScanBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.barcode_scan_button:
                onBarcodeScanBtnClick();
                break;
            case R.id.image_scan_button:
                onImageScanBtnClick();
                break;
        }
    }

    private void onImageScanBtnClick() {
        ImageUtility.imageChooser(this);
    }

    private void onBarcodeScanBtnClick() {
        onBarcodeScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;

        if (requestCode == ImageUtility.SELECT_PICTURE) {
            onImageChooserActivityResult(requestCode, resultCode, data);
        } else {
            onBarcodeScanActivityResult(requestCode, resultCode, data);
        }
    }

    private void onBarcodeScan(){
        integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(BarcodeScanActivity.class);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setOrientationLocked(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.setBeepEnabled(false);
        integrator.initiateScan();
    }

    private void onBarcodeScanActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                if (integrator != null) {
                    String resultContent = result.getContents();
                    BarcodeResultModel model = BarcodeUtility.parsedResult(result);
                    Log.d("resultTest", "" + model.getContents() + " : ResultType = " + model.getResult_type()
                            + " : typeText : " + model.getResult_type().toString()
                            + " : displayText : " + model.getDisplay_text()
                            + " : barcodeType : " + model.getBarcode_type()
                    );
                    integrator.addExtra("result", resultContent);
                    integrator.initiateScan();
                }
            }
        }
    }

    private void onImageChooserActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        Uri selectedImageUri = data.getData();
        if (null != selectedImageUri) {
            Result result = BarcodeUtility.scanBarcodeImage(ImageUtility.uriToBitmap(this, selectedImageUri));

            if (result == null)
                return;

            BarcodeResultModel model = BarcodeUtility.parsedResult(result);
            Log.d("resultTest", "" + model.getContents() + " : ResultType = " + model.getResult_type()
                    + " : typeText : " + model.getResult_type().toString()
                    + " : displayText : " + model.getDisplay_text()
                    + " : barcodeType : " + model.getBarcode_type()
            );
        }
    }

}
