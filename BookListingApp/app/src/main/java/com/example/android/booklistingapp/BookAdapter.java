package com.example.android.booklistingapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by rohan03 on 02-04-2019.
 */

public class BookAdapter extends ArrayAdapter<Book>
{
    BookAdapter(Context context, ArrayList<Book> books)
    {
        super(context,0,books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View listItemView=convertView;
        if(listItemView==null)
        {
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.cust_list_item,parent,false);
        }
        Book book=getItem(position);
        //image ko add krna rh gya hai

        TextView title=(TextView)listItemView.findViewById(R.id.title);
        title.setText(book.getTitle());

        TextView author=(TextView)listItemView.findViewById(R.id.author);
        author.setText(book.getAuthor());

        TextView date=(TextView)listItemView.findViewById(R.id.date);
        date.setText(book.getPublishDate());

        ImageView image=(ImageView)listItemView.findViewById(R.id.image);
        image.setImageBitmap(book.getImage());

        return listItemView;

    }
}
