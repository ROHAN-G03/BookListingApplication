package com.example.android.booklistingapp;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.NetworkOnMainThreadException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Book>>{

    public static final String LOG_TAG = MainActivity.class.getName();
    ListView lv;
    BookAdapter adapter;
    ProgressBar pg;
    SearchView sv;
    public String url="https://www.googleapis.com/books/v1/volumes?q=potter&maxResults=40";;
    @Override
    protected void onCreate(Bundle savedInstanceState) throws NetworkOnMainThreadException{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sv=(SearchView)findViewById(R.id.search);

        lv=(ListView)findViewById(R.id.booklist);
        ArrayList<Book> books=new ArrayList<>();
        adapter=new BookAdapter(this,books);
        pg=(ProgressBar)findViewById(R.id.progress);
        pg.setVisibility(View.VISIBLE);
        LoaderManager lm=getLoaderManager();
        lm.initLoader(1,null,this);
        lv.setAdapter(adapter);
    }

    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this,url);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> data) {
        adapter.clear();
        pg.setVisibility(View.GONE);
        if(data!=null && data.size()>0)
        {
            adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {
        adapter.clear();
        pg.setVisibility(View.VISIBLE);
    }
}
