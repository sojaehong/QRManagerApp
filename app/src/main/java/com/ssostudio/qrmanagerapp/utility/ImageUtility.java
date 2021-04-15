package com.ssostudio.qrmanagerapp.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtility {
    public static int SELECT_PICTURE = 200;

    public static void imageChooser(Activity activity) {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public static Bitmap uriToBitmap(Context context, Uri imageUri) {
        Bitmap imageBitmap = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                imageBitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.getContentResolver(), imageUri));
            } else {
                imageBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
            }
        } catch (IOException e) {

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            imageBitmap = imageBitmap.copy(Bitmap.Config.RGBA_F16, true);
        }

        return imageBitmap;
    }
}
