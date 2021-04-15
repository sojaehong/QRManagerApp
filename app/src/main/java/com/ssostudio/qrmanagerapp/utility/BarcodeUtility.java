package com.ssostudio.qrmanagerapp.utility;

import android.graphics.Bitmap;

import com.google.zxing.*;
import com.google.zxing.client.result.AddressBookAUResultParser;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.ssostudio.qrmanagerapp.model.BarcodeResultModel;

public class BarcodeUtility {

    public static Result scanBarcodeImage(Bitmap bitmap) {
        Result result = null;

        try {
            int[] intArray = new int[bitmap.getWidth() * bitmap.getHeight()];
            bitmap.getPixels(intArray, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

            LuminanceSource source = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), intArray);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));

            Reader reader = new MultiFormatReader();

            result = reader.decode(binaryBitmap);

        } catch (Exception e) {

        }

        return result;
    }

    public static Result intentResultToResult(IntentResult intentResult){
        return new Result(intentResult.getContents(), intentResult.getRawBytes(), null, null);
    }

    public static String barcodeResultToString (Result result){
        return result.getText();
    }

    public static BarcodeResultModel parsedResult(Result result){
        ParsedResult parsedResult =  ResultParser.parseResult(result);

        BarcodeResultModel barcodeResultModel = new BarcodeResultModel();
        barcodeResultModel.setContents(result.getText());
        barcodeResultModel.setBarcode_type(result.getBarcodeFormat().toString());
        barcodeResultModel.setDisplay_text(parsedResult.getDisplayResult());
        barcodeResultModel.setResult_type(parsedResult.getType());

        return barcodeResultModel;
    }

    public static BarcodeResultModel parsedResult(IntentResult intentresult){
        Result result = new Result(intentresult.getContents(), intentresult.getRawBytes(), null, null);
        ParsedResult parsedResult =  ResultParser.parseResult(result);

        BarcodeResultModel barcodeResultModel = new BarcodeResultModel();
        barcodeResultModel.setContents(intentresult.getContents());
        barcodeResultModel.setBarcode_type(intentresult.getFormatName());
        barcodeResultModel.setDisplay_text(parsedResult.getDisplayResult());
        barcodeResultModel.setResult_type(parsedResult.getType());

        return barcodeResultModel;
    }

}
