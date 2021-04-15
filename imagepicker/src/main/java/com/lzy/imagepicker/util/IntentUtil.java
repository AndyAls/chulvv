package com.lzy.imagepicker.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import java.io.File;

/**
 * @author Andy
 * @date   2018/7/19 14:35
 * @link   {http://blog.csdn.net/andy_l1}
 * Desc:    IntentUtil.java
 */
public class IntentUtil {

    public static Intent getPicFromCameraIntent(File tmpPicFile) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri u = Uri.fromFile(tmpPicFile);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
        return intent;
    }

    /**
     * 7.0以上调用
     * @param tmpPicFile
     * @return
     */
    public static Intent getGrantPicFromCameraIntent(Context context, File tmpPicFile) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri= FileProvider.getUriForFile(context,ProviderUtil.getFileProviderName(context), tmpPicFile);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        return intent;
    }

}
