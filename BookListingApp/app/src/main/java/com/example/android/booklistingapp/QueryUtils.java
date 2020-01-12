package com.example.android.booklistingapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.android.booklistingapp.MainActivity.LOG_TAG;

/**
 * Created by rohan03 on 03-04-2019.
 */

public  class QueryUtils
{
//    public String url="https://www.googleapis.com/books/v1/volumes?q=android";

    static StringBuilder authorsString = new StringBuilder();
    static List<Book> mBooks = new ArrayList<>();

    public static List<Book> fetchData(String stringUrl) {
        String jsonResponse = "";

        Log.d(TAG, "fetchData: started");
        try {
            URL url = createUrl(stringUrl);
            jsonResponse = makHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Book> books = fetchJsonData(jsonResponse);

        return books;
    }

    private static List<Book> fetchJsonData(String jsonResponse) {

        try {

            JSONObject object = new JSONObject(jsonResponse);
            String totalItems = object.getString("totalItems");
            JSONArray items = object.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {

                JSONObject currentItem = items.getJSONObject(i);
                JSONObject currentInfo = currentItem.getJSONObject("volumeInfo");
                String pubDate = currentInfo.getString("publishedDate");
                String title = currentInfo.getString("title");
                JSONArray authors = currentInfo.getJSONArray("authors");
                JSONObject images=currentInfo.getJSONObject("imageLinks");
                String image=images.getString("smallThumbnail");

                Bitmap mIcon_val=null;
                URL newurl = null;
                try {
                    newurl = new URL(image);
                    mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(authors.length() > 0) {
                    for (int j = 0; j < authors.length(); j++) {
                        Log.d(TAG, "fetchJsonData: " + authors.get(j));
                        authorsString.append(authors.get(j)).append(", ");
                    }
                }
                String auth="";
                if(authors.length()>0)
                    auth=(String)authors.get(0);
                Log.d(TAG, "fetchJsonData: for loop is done");
                Book book = new Book(title,auth, pubDate,mIcon_val);
                mBooks.add(book);

            }
        } catch (JSONException e) {
            Log.e(TAG, "fetchJsonData: error in jsno data");
        }

        return mBooks;
    }

    private static URL createUrl (String stringUrl) {
        URL url = null;
        if (stringUrl == null) {
            return url;
        } else {

            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return url;
    }

    private static String makHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        Log.d(TAG, "makHttpRequest: started");
        if(url == null) {
            return jsonResponse;

        } else {
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                if (urlConnection.getResponseCode() == 200) {
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);

                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(urlConnection != null) {
                    urlConnection.disconnect();
                }
                if(inputStream != null) {
                    inputStream.close();
                }
            }
            return jsonResponse;
        }
    }

    private static String readFromStream(InputStream inputStream) {
        InputStreamReader streamReader;
        BufferedReader bufferedReader;
        StringBuilder result = new StringBuilder();

        Log.d(TAG, "readFromStream: started");
        if(inputStream == null) {
            return null;
        }

        streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        bufferedReader = new BufferedReader(streamReader);

        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                result = result.append(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
    /*
    public URL createUrl(String url)
    {
        URL url1=null;
        if(url==null)
        {
            return url1;
        }
        else {
            try {
                url1 = new URL(url);



            } catch (MalformedURLException e) {
            }
        }
        return url1;
    }

    public InputStreamReader gettingConnection(URL url)
    {
        InputStream in=null;
        InputStreamReader reader=null;
        if(url==null)
        {
            return reader;
        }
        else
        {
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10000 /* milliseconds *///)//;
             //   connection.setConnectTimeout(15000 /* milliseconds */);

        /* connection.setRequestMethod("GET");
                connection.connect();
                if (connection.getResponseCode() == 200) {
                    in = connection.getInputStream();
                    reader=new InputStreamReader(in, Charset.forName("UTF-8"));
                    return reader;
           //     }
           //     else
            //    {
             //       Log.e(LOG_TAG,"ERROR"+connection.getResponseCode());
              //  }
            }
            catch(Exception e)
            {}
        }
        return reader;
    }

    public String JSONResponse(String u) throws IOException {
        StringBuilder ss=new StringBuilder();
        if(u==null)
            return ss.toString();
        URL url=(URL)createUrl(u);
        InputStreamReader reader=(InputStreamReader)gettingConnection(url);
        BufferedReader br=new BufferedReader(reader);
        String line=br.readLine();
        while(line!=null)
        {
            ss.append(line);
            line=br.readLine();
        }
        return line.toString();
    }

    public ArrayList<Book> getFeature(String u) throws IOException {
        ArrayList<Book> ar=new ArrayList<>();
        String json=JSONResponse(u);
        try {
            JSONObject obj = new JSONObject(json);
            String totalitems=obj.getString("totalitems");
            JSONArray array=obj.getJSONArray("items");
            for(int i=0;i<array.length();i++)
            {
        //        JSONObject o=array.getJSONObject(i);
         //       String date=o.getString("publisedDate");
          //      String title=o.getString("title");
           //     JSONArray authors=o.getJSONArray("authors");
            //    String author=(String)authors.get(0);
                //JSONObject images=o.getJSONObject("imagelinks");
                //String image=images.getString("smallThumbnail");
             //   ar.add(new Book("love","me","like","you"));
            }
            ar.add(new Book("hi love ","Glo loce", "pyaar ","munna"));

            ar.add(new Book("hi love ","Glo loce", "pyaar ","munna"));

        }
        catch(Exception e)
        {}
        return ar;
    }
    */