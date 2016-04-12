package com.example.asus.androidscruminftel.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.asus.androidscruminftel.AndroidScrumINFTELActivity;
import com.example.asus.androidscruminftel.interfaces.ImageLoaderListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by aitorpagan on 10/4/16.
 */
public class ImageLoaderService {
    private ImageLoaderListener imageLoaderListener;
    private Context context;

    public ImageLoaderService() {
    }

    public ImageLoaderService(ImageLoaderListener imageLoaderListener, Context context){
        this.imageLoaderListener = imageLoaderListener;
        this.context = context;
    }

    public void execute(String URL){
        AndroidScrumINFTELActivity.getImageLoader().loadImage(URL, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Drawable d = new BitmapDrawable(context.getResources(), loadedImage);
                ImageLoaderService.this.imageLoaderListener.onImageLoaded(d);
            }
        });
    }

}
