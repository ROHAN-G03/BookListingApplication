package com.example.android.booklistingapp;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by rohan03 on 03-04-2019.
 */

public class BookLoader extends AsyncTaskLoader<ArrayList<Book>>{

    String url="";
    BookLoader(Context context,String url1)
    {
        super(context);
        url=url1;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Book> loadInBackground()
    {
        ArrayList<Book> ar=new ArrayList<>();
        ar= (ArrayList<Book>) QueryUtils.fetchData(url);
        return ar;
    }
}
