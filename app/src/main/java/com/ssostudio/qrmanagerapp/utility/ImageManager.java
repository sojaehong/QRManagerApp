package com.ssostudio.qrmanagerapp.utility;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

public class ImageManager {
    public static int SELECT_PICTURE = 200;
    public static int RESULT_OK = -1;

    public static void imageChooser(Activity activity){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public static Bitmap uriToBitmap(Uri imageUri){
        Bitmap imageBitmap = null;

        return imageBitmap;
    }
}
