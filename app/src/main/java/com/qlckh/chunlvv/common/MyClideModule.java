package com.qlckh.chunlvv.common;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.qlckh.chunlvv.R;

/**
 * @author Andy
 * @date 2018/5/17 13:34
 * Desc:
 */
@GlideModule
public class MyClideModule extends AppGlideModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_default_image)
                .error(R.drawable.error)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        builder.setDefaultRequestOptions(options);
        super.applyOptions(context, builder);
    }
}
