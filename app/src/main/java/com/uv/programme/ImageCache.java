package com.uv.programme;

import android.graphics.Bitmap;

import java.util.HashMap;

/**
 * Created by venkatsr on 14/12/15.
 */
public abstract class ImageCache{
    private static HashMap<String,Bitmap> hashMap;
    public static synchronized Bitmap get(String imageUrl){
        if(hashMap==null){hashMap=new HashMap<String,Bitmap>();}
        return(hashMap.get(imageUrl));
    }
    public static synchronized void put(String imageUrl,Bitmap bitmap){
        if(hashMap==null){hashMap=new HashMap<String,Bitmap>();}
        hashMap.put(imageUrl,bitmap);
    }
}
