package com.ssostudio.qrmanagerapp.model;

import com.google.zxing.client.result.ParsedResultType;

import java.io.Serializable;

public class BarcodeResultModel implements Serializable {
    public String contents;
    public String display_text;
    public String barcode_type;
    public ParsedResultType result_type;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDisplay_text() {
        return display_text;
    }

    public void setDisplay_text(String display_text) {
        this.display_text = display_text;
    }

    public String getBarcode_type() {
        return barcode_type;
    }

    public void setBarcode_type(String barcode_type) {
        this.barcode_type = barcode_type;
    }

    public ParsedResultType getResult_type() {
        return result_type;
    }

    public void setResult_type(ParsedResultType result_type) {
        this.result_type = result_type;
    }
}
