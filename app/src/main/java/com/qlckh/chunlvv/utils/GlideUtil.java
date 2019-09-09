package com.qlckh.chunlvv.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.common.GlideApp;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * @author Andy
 * @date   2018/8/22 15:40
 * @link   {http://blog.csdn.net/andy_l1}
 * Desc:    GlideUtil.java
 */

public class GlideUtil {
    public static void displayImage(Context context, Object path, ImageView imageView) {
        GlideApp.with(context)
                .load(path)
                .dontAnimate()
                .error(R.drawable.ic_default_image)
                .placeholder(R.drawable.ic_default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
    /**
     * 圆形加载
     *
     * @param mContext
     * @param path
     * @param imageview
     */
    public static void displayCircleImg(Context mContext, Object path,
                                     ImageView imageview) {
        GlideApp.with(mContext).load(path).centerCrop().apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .placeholder(R.drawable.wdtouxiang)
                .error(R.drawable.wdtouxiang)
                .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(false).into(imageview);
    }
    /**
     * 圆角加载
     *
     * @param mContext
     * @param path
     * @param imageview
     */
    public static void displayRoundConnerImg(Context mContext, Object path,
                                          ImageView imageview) {
        GlideApp.with(mContext).load(path).placeholder(R.drawable.ic_default_image)
                .error(R.drawable.ic_default_image)
                .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(20,
                        0, RoundedCornersTransformation.CornerType.ALL)))
                .optionalCenterCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(imageview);

    }

}
