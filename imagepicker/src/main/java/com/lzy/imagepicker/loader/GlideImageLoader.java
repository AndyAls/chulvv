package com.lzy.imagepicker.loader;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.R;


public class GlideImageLoader implements ImageLoader {

    private GlideImageLoader() {
    }

    private static GlideImageLoader instance = new GlideImageLoader();

    public static GlideImageLoader getInstance() {
        return instance;
    }

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity)
                .load(path)

//                .dontAnimate()
//                .error(R.drawable.ic_image_default)
//                .placeholder(R.drawable.ic_image_default)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {
    }
}
