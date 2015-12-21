package com.uv.programme;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by venkatsr on 17/11/15.
 */
public class ContestActivity extends SampleActivity {

    private final static String TAG="ContestActivity";
    private ImageView contestQuestion;
    private ImageView participateButton;
    private TextView contestDescription;
    public static String CONTEST_QUESTION_TAG, CONTEST_STARS_TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.reversehandshootmedium);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        Typeface customFont1 = Typeface.createFromAsset(getAssets(), "fonts/Aro.ttf");
        toolbarTitle.setTypeface(customFont1);
        contestDescription = (TextView)findViewById(R.id.contest_description);
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/Ayuma2yk.ttf");
        contestDescription.setTypeface(customFont);
        participateButton = (ImageView)findViewById(R.id.messageMe);
        addClickListener();
        contestQuestion = (ImageView)findViewById(R.id.contestQuestion);
        CONTEST_QUESTION_TAG = getResources().getString(R.string.contest_question_tag);
        CONTEST_STARS_TAG = getResources().getString(R.string.contest_stars_tag);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume(){
        super.onResume();
        new UpdateAlbumGalleryTask().execute("");
    }

    private void addClickListener() {
        participateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    openWhatsappContact();
                } catch (Exception e) {
                    openEmail();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.w("ContestActivity", "onCreateOptionsMenu");
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.w("ContestActivity", "onOptionsItemSelected");
        return super.onOptionsItemSelected(item);
    }

    private class UpdateAlbumGalleryTask extends UserTask<String,String,PhotoItem>{

        @Override
        public void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        public void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        public PhotoItem doInBackground(String... urls){

            try{

                long startTime=System.currentTimeMillis();
                String albumId = null;
                String albumsJson=RestInvoke.invoke(constructAlbumUrl());
                ArrayList<AlbumItem> albums=FacebookJSONParser.parseAlbums(albumsJson);
                if(albums.size()>0){
                    for(AlbumItem albumItem: albums) {
                        String albumName = albumItem.getName();
                        String compareToAlbumName = getResources().getString(R.string.album_name);
                        if(compareToAlbumName.equals(albumName)) {
                            Log.e(TAG, "Album " + compareToAlbumName + " found");
                            albumId = albumItem.getId();
                            break;
                        }
                    }
                }

                String facebookUrlPhotos = constructPhotoUrl(albumId);

                String photosJson=RestInvoke.invoke(facebookUrlPhotos);

                ArrayList<PhotoItem> photos=FacebookJSONParser.parsePhotos(photosJson, Photo.FETCHTYPE.CONTEST);

                PhotoItem currentPhoto = photos.get(0);

                String imageUrl = currentPhoto.getSourceUrl();

                Bitmap bitmap=ImageCache.get(imageUrl);

                if(bitmap == null) {
                    try {
                        bitmap=HttpFetch.fetchBitmap(imageUrl);
                        Log.e(TAG, "Bitmap is fetched from web: " + bitmap.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ImageCache.put(imageUrl, bitmap);
                }
                currentPhoto.setBitmap(bitmap);
                return currentPhoto;
                /*String imageUrl = currentPhoto.getSourceUrl();

                Bitmap bitmap=ImageCache.get(imageUrl);

                if(bitmap!=null) {
                    Log.e(TAG, "Bitmap is present in cache");
                    return(bitmap);
                }

                try {
                    bitmap=HttpFetch.fetchBitmap(imageUrl);
                    Log.e(TAG, "Bitmap is fetched from web: " + bitmap.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ImageCache.put(imageUrl, bitmap);

                long endTime = System.currentTimeMillis();

                Log.e(TAG, "Time to download contest question=" + (endTime - startTime) + "ms");
                return(bitmap);

                return currentPhoto; */
            }catch(Exception x){
                Log.e(TAG,"UpdateAlbumGalleryTask",x);
                return(null);
            }
        }

        public void onPostExecute(PhotoItem currentPhoto){
            Log.e(TAG, "Setting Contest Image");

            contestQuestion.setImageBitmap(currentPhoto.getBitmap());
            contestDescription.setText(currentPhoto.getName());
        }
    }

    private String constructAlbumUrl() {
        String graphUrl = getResources().getString(R.string.graph_url);
        String pageName = getResources().getString(R.string.page_name);
        String seperator = getResources().getString(R.string.seperator);
        String albums = getResources().getString(R.string.albums);
        String questionMark = getResources().getString(R.string.question_mark);
        String accessToken = getResources().getString(R.string.access_token);

        String albumUrl = graphUrl + pageName + seperator + albums + questionMark + accessToken;

        Log.e(TAG, "AlbumUrl: " + albumUrl);
        return albumUrl;
    }

    private String constructPhotoUrl(String albumId) {
        String graphUrl = getResources().getString(R.string.graph_url);
        String seperator = getResources().getString(R.string.seperator);
        String photos = getResources().getString(R.string.photos);
        String questionMark = getResources().getString(R.string.question_mark);
        String accessToken = getResources().getString(R.string.access_token);
        String photoFields = getResources().getString(R.string.photo_fields);
        String photoUrl = graphUrl + albumId + seperator + photos + questionMark + accessToken + photoFields;
        Log.e(TAG, "PhotoUrl: " + photoUrl);
        return photoUrl;
    }
}
