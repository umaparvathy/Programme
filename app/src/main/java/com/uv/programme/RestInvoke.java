package com.uv.programme;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by venkatsr on 14/12/15.
 */
public abstract class RestInvoke {
    public static String invoke(String restUrl) throws Exception{
        String result=null;
        HttpClient httpClient=new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(restUrl);
        HttpResponse response=httpClient.execute(httpGet);
        HttpEntity httpEntity=response.getEntity();
        if(httpEntity!=null){
            InputStream in=httpEntity.getContent();
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
            StringBuffer temp=new StringBuffer();
            String currentLine=null;
            while((currentLine=reader.readLine())!=null){
                temp.append(currentLine);
            }
            result=temp.toString();
            in.close();
        }
        return(result);
    }
}
