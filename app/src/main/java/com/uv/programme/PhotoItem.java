package com.uv.programme;

import android.graphics.Bitmap;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by venkatsr on 14/12/15.
 */
public class PhotoItem {
    private String pictureUrl;
    private String sourceUrl;
    private Date createdTime;
    private String name;
    private Bitmap bitmap;
    public PhotoItem(String pictureUrl,String sourceUrl, String createdTime, String name){
        this.pictureUrl=pictureUrl;
        this.sourceUrl=sourceUrl;
        this.createdTime = getDate(createdTime);
        this.name = name;
    }
    public String getPictureUrl(){
        return pictureUrl;
    }
    public void setPictureUrl(String pictureUrl){
        this.pictureUrl=pictureUrl;
    }
    public String getSourceUrl(){
        return sourceUrl;
    }
    public void setSourceUrl(String sourceUrl){
        this.sourceUrl=sourceUrl;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private static Date getDate(String dateString) {
        String format = "yyyy-MM-dd'T'HH:mm:ssZ";
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}

