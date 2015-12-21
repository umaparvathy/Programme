package com.uv.programme;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by venkatsr on 14/12/15.
 */
public abstract class FacebookJSONParser {
    public static String parseLatestStatus(String json){
        String latestStatus="";
        String startTag="\"message\":\"";
        int indexOf=json.indexOf(startTag);
        if(indexOf>0){
            int start=indexOf+startTag.length();
            String endTag="\",";
            return(json.substring(start,json.indexOf(endTag,start)));
        }
        return(latestStatus);
    }
    public static ArrayList<AlbumItem> parseAlbums(String json) throws JSONException{
        ArrayList<AlbumItem> albums=new ArrayList<AlbumItem>();
        JSONObject rootObj=new JSONObject(json);
        JSONArray itemList=rootObj.getJSONArray("data");
        int albumCount=itemList.length();
        for(int albumIndex=0;albumIndex<albumCount;albumIndex++){
            JSONObject album=itemList.getJSONObject(albumIndex);
            String description="";
            try{
                description=album.getString("description");
            }catch(JSONException x){/*not implemented*/}
            albums.add(new AlbumItem(album.getString("id"),album.getString("name"),description));
        }
        return(albums);
    }
    public static ArrayList<PhotoItem> parsePhotos(String json, Photo.FETCHTYPE fetchType) throws JSONException{
        Log.e("FacebookJSONParser", "JSON before replace: " + json);
        json = json.replaceAll("\\p{C}", "");
        ArrayList<PhotoItem> photos=new ArrayList<PhotoItem>();
        Log.e("FacebookJSONParser", "JSON after replace: " + json);
        JSONObject rootObj=new JSONObject(json);
        JSONArray itemList=rootObj.getJSONArray("data");
        int photoCount=itemList.length();
        Log.e("FacebookJSONParser", "photoCount" + String.valueOf(photoCount));
        for(int photoIndex=0;photoIndex<photoCount;photoIndex++){
            JSONObject photo=itemList.getJSONObject(photoIndex);
            try {
                String name = photo.getString("name");
                Log.e("FacebookJSONParser", photoIndex + "Printing name before replace: " + name);
                name = name.replaceAll("\\p{C}", "");
                Log.e("FacebookJSONParser", photoIndex + "Printing name after replace: " + name);
                String type = (fetchType == Photo.FETCHTYPE.CONTEST) ? Photo.CONTEST_QUESTION_TAG : Photo.CONTEST_STARS_TAG;
                if (!name.trim().contains(type)) {
                    Log.e("FacebookJSONParser", name + "\n Not a contest question. Ignoring..");
                    continue;
                } else {
                    /*name = name.trim();
                    int tagStartIndex = name.indexOf(ContestActivity.CONTEST_QUESTION_TAG);
                    int tagLength = ContestActivity.CONTEST_QUESTION_TAG.length();
                    int tagEndIndex = tagStartIndex + tagLength;
                    String subString1 = name.substring(0,tagStartIndex);
                    String subString2 = name.substring(tagEndIndex, name.length());*/
                    name = name.replace(type, "").trim();

                    Log.e("FacebookJSONParser", name + "\n is a contest question.");
                    photos.add(new PhotoItem(photo.getString("picture"), photo.getString("source"), photo.getString("created_time"), name));
                }
            } catch (JSONException e) {
                Log.e("FacebookJSONParser", photoIndex + " Name not available" + e.getMessage());
                e.printStackTrace();
                continue;
            }
            Log.e("FacebookJSONParser", "Adding photo to the list");
        }

        Collections.sort(photos, new Comparator<PhotoItem>() {
            @Override
            public int compare(PhotoItem lhs, PhotoItem rhs) {
                Date leftDate = lhs.getCreatedTime();
                Date rightDate = rhs.getCreatedTime();
                int dateCompare = (rightDate).compareTo(leftDate);
                return dateCompare;
            }
        });
        return(photos);
    }
}