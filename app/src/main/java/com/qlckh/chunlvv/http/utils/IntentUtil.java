package com.qlckh.chunlvv.http.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.core.content.FileProvider;

import java.io.File;

public class IntentUtil {

    public static Intent getPicFromCameraIntent(String tmpPicFilePath) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(tmpPicFilePath);
        Uri u = Uri.fromFile(f);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
        return intent;
    }

    /**
     * 7.0以上调用
     * @param tmpPicFilePath
     * @return
     */
    public static Intent getGrantPicFromCameraIntent(Context context, String tmpPicFilePath) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(tmpPicFilePath);
        Uri imageUri= FileProvider.getUriForFile(context,context.getPackageName()+".fileProvider", f);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        return intent;
    }

}
