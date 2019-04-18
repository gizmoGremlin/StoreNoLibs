package com.pickledpepper.storenolibs.util

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory

/**
 * Created by cuongvo on 5/18/17.
 */
public class ImageUtil {

    fun getRoundedImage ( context:Context, imageId:Int): RoundedBitmapDrawable {
        var bitmap:Bitmap = BitmapFactory.decodeResource(context.getResources(), imageId);
         var roundedBitmapDrawable:RoundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
        roundedBitmapDrawable.setCornerRadius(16f);

        return roundedBitmapDrawable;
    }
}