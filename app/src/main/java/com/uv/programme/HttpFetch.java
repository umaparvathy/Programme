package com.uv.programme;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;

/**
 * Created by venkatsr on 14/12/15.
 */
public abstract class HttpFetch{

    static InputStream inputStream;

    //see - http://stackoverflow.com/questions/4414839/bitmapfactory-decodestream-returns-null-without-exception

    public static InputStream fetch(String address) throws MalformedURLException,IOException{

        /*MyAsync myasync  = new MyAsync();
        AsyncTask<String, Void, InputStream> instream = myasync.execute(address);*/

        InputStream instream = null;

        HttpGet httpRequest=new HttpGet(URI.create(address));

        HttpClient httpclient=new DefaultHttpClient();

        HttpResponse response= null;
        try {
            response = (HttpResponse)httpclient.execute(httpRequest);
            HttpEntity entity=response.getEntity();

            BufferedHttpEntity bufHttpEntity=new BufferedHttpEntity(entity);

            instream=bufHttpEntity.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpFetch.setInputStream(instream);
        return instream;
    }

    public static Bitmap fetchBitmap(String imageAddress) throws MalformedURLException,IOException{

        return(BitmapFactory.decodeStream(fetch(imageAddress)));

    }

    public static InputStream getInputStream() {
        return inputStream;
    }

    public static void setInputStream(InputStream is) {
        inputStream = is;
    }
}
