package com.example.forest_app.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class ImageLoader {

    public static Bitmap convertToBitMap(String imgData){
        byte[] decodedBytes = Base64.decode(imgData, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
