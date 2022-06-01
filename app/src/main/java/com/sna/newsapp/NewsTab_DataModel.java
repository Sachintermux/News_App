package com.sna.newsapp;

import android.graphics.Bitmap;

import java.net.URL;

public class NewsTab_DataModel {
    private String time;
    private String title;
    private String description;
    private String imageUrl;

    private Bitmap bitmap = null;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap( Bitmap bitmap ) {
        this.bitmap = bitmap;
    }

    public NewsTab_DataModel( String time, String title, String description, String imageUrl, Bitmap bitmap ) {
        this.time = time;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.bitmap = bitmap;
    }

    public NewsTab_DataModel( String time, String title, String description, String imageUrl ) {
        this.time = time;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }
    public NewsTab_DataModel( String title, String description, String imageUrl ) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime( String time ) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl( String imageUrl ) {
        this.imageUrl = imageUrl;
    }
}
