package com.ssostudio.qrmanagerapp.utility;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.widget.Toast;

public class AppUtility {

    public static int[] getDeviceSize(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int deviceWidth = dm.widthPixels;
        int deviceHeight = dm.heightPixels;
        int[] deviceSize = {deviceWidth, deviceHeight};
        return deviceSize;
    }

    public static void restartApp(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        context.startActivity(mainIntent);
        System.exit(0);
    }

    public static void onVibrator(Context context, long milliSecond) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        vibrator.vibrate(milliSecond);
    }

    public static void clipboardTextCopy(Context context, String text) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("result", text);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(context, "클립보드: " + text, Toast.LENGTH_SHORT).show();
    }

}
