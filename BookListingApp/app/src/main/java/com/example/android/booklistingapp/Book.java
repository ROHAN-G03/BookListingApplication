package com.example.android.booklistingapp;

import android.graphics.Bitmap;

/**
 * Created by rohan03 on 02-04-2019.
 */

public class Book
{
    private String title;
    private String publishDate;
    private String author;
    private Bitmap image;

    Book(String title, String publishDate, String author, Bitmap image)
    {
        this.title=title;
        this.publishDate=publishDate;
        this.author=author;
        this.image=image;
    }

    public String getTitle()
    {
        return title;
    }
    public String getPublishDate()
    {
        return publishDate;
    }
    public String getAuthor()
    {
        return author;
    }
    public Bitmap getImage()
    {
        return image;
    }
}
